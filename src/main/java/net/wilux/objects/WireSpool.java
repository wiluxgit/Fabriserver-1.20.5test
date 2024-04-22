package net.wilux.objects;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.virtualentity.api.elements.*;
import eu.pb4.polymer.virtualentity.api.tracker.EntityTrackedData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MarkerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.wilux.PolyWorks;
import net.wilux.objects.base.blockentity.IWireConnector;
import net.wilux.objects.base.blockentity.BlockEntityWithAttachments;
import net.wilux.polyattchments.BlockAttachment;
import net.wilux.polyattchments.NamedVirtualElement;
import net.wilux.util.Result;
import net.wilux.util.Dirty;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.wilux.util.ServerCast.asServer;
import static net.wilux.util.UtilEntityAttachS2CPacket.entityAttachS2CPacketFromIds;

public final class WireSpool {
    public static class WireBallEntity extends MarkerEntity implements PolymerEntity {
        public WireBallEntity(EntityType<?> type, World world) {
            super(type, world);
        }

        @Override
        public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
            return EntityType.PIG;
        }
    }

    public static class WireEntityVirtualElement extends GenericEntityElement {
        public static final NamedVirtualElement<WireEntityVirtualElement> WIRE_VIRTUAL_ELEMENT = NamedVirtualElement.register();
        private final Dirty<@Nullable Entity> leashHolder;

        public WireEntityVirtualElement() {
            this.dataTracker.set(EntityTrackedData.SILENT, true);
            this.dataTracker.set(EntityTrackedData.NO_GRAVITY, true);
            this.dataTracker.set(EntityTrackedData.NAME_VISIBLE, true);
            this.dataTracker.set(EntityTrackedData.CUSTOM_NAME, Optional.of(Text.of("foo")));
            this.setOffset(new Vec3d(0, -0.49, 0));
            this.setInvisible(true);
            leashHolder = new Dirty<>(null);
        }

        public void setLeashHolder(@Nullable Entity entity) {
            leashHolder.set(entity);
        }
        public @Nullable Entity getLeashHolder() {
            return leashHolder.get();
        }

        @Override
        protected void sendTrackerUpdates() {
            if (this.leashHolder.takeDirty() instanceof Result.Ok<Entity,?> ok) {
                PolyWorks.LOGGER.info("Leash target changed!");
                @Nullable Entity newLeashHolder = ok.value;

                int holdingEntityId = newLeashHolder != null ? newLeashHolder.getId() : 0;
                // Attached Entity but be a MobEntity, the holder can probably be anything?
                EntityAttachS2CPacket packet = entityAttachS2CPacketFromIds(this.getEntityId(), holdingEntityId);

                assert this.getHolder() != null;
                this.getHolder().sendPacket(packet);
            }
            super.sendTrackerUpdates();
        }

        @Override
        protected EntityType<? extends Entity> getEntityType() {
            // Class must extend MobEntity or it won't be leash-able
            return EntityType.VEX;
        }
    }

    public static class WireSpoolItem extends Item implements PolymerItem {
        final PolymerModelData polymerModelData;

        public WireSpoolItem(Settings settings, PolymerModelData polymerModelData) {
            super(settings);
            this.polymerModelData = polymerModelData;
        }

        @Override
        public ActionResult useOnBlock(ItemUsageContext context) {
            // TODO: support multiple connections at the same time?
            var world = asServer(context.getWorld());
            var clickedPoint = context.getBlockPos();
            var blockState = world.getBlockState(clickedPoint);
            var block = blockState.getBlock();

            if (block instanceof IWireConnector<?> wireConnector) {
                PolyWorks.LOGGER.info("SPOOL> used on: "+ wireConnector);
                return useOnWireConnector(context, wireConnector);
            }
            if (blockState.isIn(BlockTags.FENCES)) {
                PolyWorks.LOGGER.info("SPOOL> used on: "+ blockState);
                return useOnFence(context);
            }

            PolyWorks.LOGGER.info("SPOOL> used on: nothing");
            return ActionResult.PASS;
        }

        private ActionResult useOnFence(ItemUsageContext context) {
            ServerWorld world = asServer(context.getWorld());
            ServerPlayerEntity player = asServer(context.getPlayer(), world);
            return ActionResult.PASS;
        }

        private ActionResult useOnWireConnector(ItemUsageContext context, IWireConnector<?> wireConnector) {
            var world = asServer(context.getWorld());
            var player = asServer(context.getPlayer(), world);

            BlockEntityWithAttachments<?> blockEntityWithAttachments = wireConnector.getBlockEntity(context.getWorld(), context.getBlockPos());
            BlockAttachment attachment = blockEntityWithAttachments.getAttachment();

            if (attachment == null) {
                PolyWorks.LOGGER.warn("SPOOL> Attachment implementer has no attachment, hmmm");
                return ActionResult.PASS;
            }

            @Nullable WireEntityVirtualElement elem = attachment.getNamedElement(WireEntityVirtualElement.WIRE_VIRTUAL_ELEMENT);
            if (elem == null) {
                elem = new WireEntityVirtualElement();
                attachment.putNamedElement(WireEntityVirtualElement.WIRE_VIRTUAL_ELEMENT, elem);
                PolyWorks.LOGGER.info("SPOOL> Creating Virtual Entity");
            }
            PolyWorks.LOGGER.info("SPOOL> Modifying  Virtual Entity");

            // If player is already attached to block, unattach them
            if (elem.getLeashHolder() == player) {
                elem.setLeashHolder(null);
                return ActionResult.SUCCESS;
            } else {
                elem.setLeashHolder(player);
            }

            return ActionResult.SUCCESS;
        }

        @Override
        public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
            return polymerModelData.item();
        }

        @Override
        public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, ServerPlayerEntity player) {
            return PolymerItemUtils.createItemStack(itemStack, context, player);
        }

        @Override
        public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
            return this.polymerModelData.value();
        }

    }
}
