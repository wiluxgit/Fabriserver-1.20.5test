package net.wilux.objects;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import eu.pb4.polymer.virtualentity.api.elements.TextDisplayElement;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.decoration.Brightness;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.wilux.PolyWorks;
import net.wilux.objects.base.block.IOverrideLeftClickBlock;
import net.wilux.objects.base.block.IOverrideRightClickBlock;
import net.wilux.objects.base.block.PolyHorizontalFacingBlock;
import net.wilux.register.Registered;
import net.wilux.stackstorage.StoredStack;
import net.wilux.util.ExtraExceptions;
import net.wilux.util.Mtx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.wilux.util.ServerCast.asServer;

public final class Crate {
    private record VirtualClientEntities(
            @NotNull HolderAttachment polyAttachment,
            @NotNull ItemDisplayElement item,
            @NotNull TextDisplayElement text
    ){}

    public static class CrateBlockEntity extends BlockEntity {
        public final int MAX_ITEM_COUNT = 512; //Todo, limit in other ways
        public final String EMPTY_MESSAGE = "<empty>";

        private @Nullable StoredStack ss = null;
        private VirtualClientEntities virtualClientEntities = null;

        public CrateBlockEntity(BlockPos pos, BlockState state) {
            super(Registered.CRATE.BLOCK_ENTITY_TYPE, pos, state);
            PolyWorks.LOGGER.info("Created Crate");
        }

        public void tick(Direction direction) { // Kinda stupid this is needed, but world is not accessible on CrateBlockEntity
            assert world != null;
            if (this.virtualClientEntities == null) this.initializeAttachment(this.world, this.pos, direction);
        }

        // Player interact methods
        private ActionResult dirtySuccess() {
            markDirty();
            return ActionResult.SUCCESS;
        }

        protected ActionResult takeClick(PlayerEntity player, Hand hand) {
            PolyWorks.LOGGER.info("Clicked on box with:"+ss);

            // Can't take from an empty box but consume the event anyways
            if (ss == null) return ActionResult.CONSUME;

            StoredStack.OutTransfer outTransfer;
            if (player.isSneaking()) {
                outTransfer = ss.takeLargestStack();
            } else {
                outTransfer = ss.takeExact(1);
            }
            if (outTransfer == null) return ActionResult.CONSUME;

            boolean didInsert = player.giveItemStack(outTransfer.itemStackToExtract);
            outTransfer.resolveWith(didInsert);
            return didInsert ? dirtySuccess() : ActionResult.FAIL;
        }

        protected ActionResult putClick(PlayerEntity player, Hand hand) { // TODO: double click
            PolyWorks.LOGGER.info("Clicked on box with:"+ss);
            ItemStack handStack = player.getStackInHand(hand);

            if (this.ss == null || this.ss.count() == 0) {
                this.ss = new StoredStack(handStack, 0, MAX_ITEM_COUNT);
            }

            var inTransfer = this.ss.insert(handStack);
            if (inTransfer == null) return ActionResult.PASS;

            inTransfer.resolveWith(true, handStack);
            return dirtySuccess();
        }

        // Attachment methods
        private void removeAttachment() {
            assert this.virtualClientEntities != null;
            PolyWorks.LOGGER.info("Kill Virtualentity Crate");
            this.virtualClientEntities.polyAttachment.destroy();
            this.virtualClientEntities = null;
        }

        private void initializeAttachment(World world, BlockPos pos, Direction direction) {
            assert this.virtualClientEntities == null;

            PolyWorks.LOGGER.info("Create Virtualentity Crate");

            var rotDegrees = -direction.asRotation();

            ItemDisplayElement itemDisplayElement = new ItemDisplayElement();
            itemDisplayElement.setItem(Items.AIR.getDefaultStack());
            itemDisplayElement.setBrightness(Brightness.FULL);
            itemDisplayElement.setTransformation(Mtx.matrixProduct(
                    Mtx.rotY(rotDegrees),
                    Mtx.translate(0, 1/16.0, 7.1/16.0),
                    Mtx.flattenZ(),
                    Mtx.scale(0.35),
                    Mtx.rotX(30),
                    Mtx.rotY(45)
            ));

            TextDisplayElement textDisplay = new TextDisplayElement();
            textDisplay.setText(Text.of(EMPTY_MESSAGE));
            textDisplay.setBrightness(Brightness.FULL);
            textDisplay.setTransformation(Mtx.matrixProduct(
                    Mtx.rotY(rotDegrees),
                    Mtx.translate(0, -5/16.0, 7.01/16.0),
                    Mtx.scale(0.3)
            ));

            var polyHolder = new ElementHolder();
            polyHolder.addElement(itemDisplayElement);
            polyHolder.addElement(textDisplay);

            this.virtualClientEntities = new VirtualClientEntities(
                    ChunkAttachment.ofTicking(polyHolder, asServer(world), pos.toCenterPos()),
                    itemDisplayElement,
                    textDisplay
            );
        }

        // BlockEntity
        @Override
        public void markDirty() {
            super.markDirty();
            if (this.ss == null || this.ss.count() == 0) {
                this.virtualClientEntities.item.setItem(Items.AIR.getDefaultStack());
                this.virtualClientEntities.text.setText(Text.of(EMPTY_MESSAGE));
            } else {
                this.virtualClientEntities.item.setItem(ss.stackCopy());
                this.virtualClientEntities.text.setText(Text.of(""+this.ss.count()));
            }
        }

        @Override
        public void markRemoved() {
            PolyWorks.LOGGER.info("Broke Crate");
            removeAttachment();
        }
    }

    public static class CrateBlock extends PolyHorizontalFacingBlock implements BlockEntityProvider, IOverrideLeftClickBlock, IOverrideRightClickBlock {
        public CrateBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
            super(settings, northPolymerState, eastPolymerState, southPolymerState, westPolymerState);
        }

        @Override
        public ActionResult leftClick(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
            Optional<CrateBlockEntity> blockEntity = world.getBlockEntity(pos, Registered.CRATE.BLOCK_ENTITY_TYPE);
            if (blockEntity.isEmpty()) ExtraExceptions.debugCrash("block does not have an entity");

            // Ignore left clicks unless it is on the front side
            if(world.getBlockState(pos).get(FACING) != direction) return ActionResult.PASS;

            return blockEntity.get().takeClick(player, hand);
        }

        @Override
        public ActionResult rightClick(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
            if (player.isSneaking()) return ActionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            Optional<CrateBlockEntity> blockEntity = world.getBlockEntity(pos, Registered.CRATE.BLOCK_ENTITY_TYPE);
            if (blockEntity.isEmpty()) ExtraExceptions.debugCrash("block does not have an entity");

            // Ignore left clicks unless it is on the front side
            if(world.getBlockState(pos).get(FACING) != hitResult.getSide()) return ActionResult.PASS;

            return blockEntity.get().putClick(player, hand);
        }

        // BlockEntityProvider
        @Nullable @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CrateBlockEntity(pos, state);
        }

        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return (World l_world, BlockPos l_pos1, BlockState l_state1, T t) -> {
                if (type ==  t.getType()) {
                    if (t instanceof CrateBlockEntity crateBlockEntity) {
                        crateBlockEntity.tick(l_state1.get(FACING));
                        return;
                    }
                }
                ExtraExceptions.debugCrash("block entity has wrong type");
            };
        }
    }
}
