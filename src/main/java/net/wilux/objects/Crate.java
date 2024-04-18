package net.wilux.objects;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wilux.PolyWorks;
import net.wilux.objects.base.block.PolyHorizontalFacingBlock;
import net.wilux.register.Registered;
import net.wilux.stackstorage.StoredStack;
import net.wilux.util.ExtraExceptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.logging.Logger;

import static net.wilux.util.ServerCast.asServer;

public final class Crate {
    public static class CrateBlockEntity extends BlockEntity {
        public final int MAX_SIZE = 512; //Todo, limit in other ways

        private @NotNull StoredStack ss;

        public CrateBlockEntity(BlockPos pos, BlockState state) {
            super(Registered.CRATE.BLOCK_ENTITY_TYPE, pos, state);
            ss = new StoredStack(ItemStack.EMPTY, 0, MAX_SIZE);
        }

        public static void tick(World world, BlockPos pos, BlockState state, CrateBlockEntity be) {
            // PolyWorks.LOGGER.info(CrateBlockEntity.class.getName()+" Ticked at: "+pos.toString());
        }

        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            PolyWorks.LOGGER.info("Clicked on box!"+hit.getType());
            ItemStack handStack = player.getStackInHand(hand);
            return ActionResult.PASS;
        }

        private int number = 7;
        @Override
        public void writeNbt(NbtCompound nbt) {
            nbt.putInt("number", number);
            super.writeNbt(nbt);
        }
    }

    public static class CrateBlock extends PolyHorizontalFacingBlock implements BlockEntityProvider {
        public CrateBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
            super(settings, northPolymerState, eastPolymerState, southPolymerState, westPolymerState);
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            Optional<CrateBlockEntity> blockEntity = world.getBlockEntity(pos, Registered.CRATE.BLOCK_ENTITY_TYPE);
            if (blockEntity.isEmpty()) {
                ExtraExceptions.debugCrash("block does not have an entity");
                return ActionResult.PASS;
            }
            return blockEntity.get().onUse(state, world, pos, player, hand, hit);
        }

        @Nullable @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CrateBlockEntity(pos, state);
        }

        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return (World l_world, BlockPos l_pos1, BlockState l_state1, T t) -> {
                if (type ==  t.getType()) {
                    if (t instanceof CrateBlockEntity crateBlockEntity) {
                        CrateBlockEntity.tick(l_world, l_pos1, l_state1, crateBlockEntity);
                        return;
                    }
                }
                throw new ExtraExceptions.ProbablyImpossibleException();
            };
        }
    }
}
