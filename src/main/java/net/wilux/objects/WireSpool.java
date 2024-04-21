package net.wilux.objects;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.virtualentity.api.elements.*;
import eu.pb4.polymer.virtualentity.api.tracker.EntityTrackedData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.wilux.PolyWorks;
import net.wilux.objects.base.blockentity.IWireConnector;
import net.wilux.objects.base.blockentity.BlockEntityWithAttachments;
import net.wilux.polyattchments.BlockAttachment;
import net.wilux.util.Result;
import net.wilux.util.dirty.DirtyAble;
import org.jetbrains.annotations.Nullable;

import static net.wilux.util.ServerCast.asServer;
import static net.wilux.util.UtilEntityAttachS2CPacket.entityAttachS2CPacketFromIds;

public class WireSpool extends Item implements PolymerItem {
    public static final BlockAttachment.NamedVirtualElement<WireEntityVirtualElement> WIRE_VIRTUAL_ELEMENT = BlockAttachment.NamedVirtualElement.register();
    final PolymerModelData polymerModelData;

    public static class WireEntityVirtualElement extends GenericEntityElement {
        private final DirtyAble<@Nullable Entity> leashHolder;

        public WireEntityVirtualElement() {
            this.dataTracker.set(EntityTrackedData.SILENT, true);
            this.dataTracker.set(EntityTrackedData.NO_GRAVITY, true);
            leashHolder = new DirtyAble<>(null);
        }

        public void setLeashHolder(@Nullable Entity entity) {
            leashHolder.set(entity);
        }

        @Override
        protected void sendTrackerUpdates() {
            if (this.leashHolder.takeIfDirty() instanceof Result.Ok<Entity,?> ok) {
                PolyWorks.LOGGER.info("Leash target changed!");
                @Nullable Entity newLeashHolder = ok.value;

                var holdingEntityId = newLeashHolder != null ? newLeashHolder.getId() : 0;
                EntityAttachS2CPacket packet = entityAttachS2CPacketFromIds(this.getEntityId(), holdingEntityId);

                assert this.getHolder() != null;
                this.getHolder().sendPacket(packet);
            }
            super.sendTrackerUpdates();
        }

        @Override
        protected EntityType<? extends Entity> getEntityType() {
            return EntityType.PIG;
        }
    }

    public WireSpool(Settings settings, PolymerModelData polymerModelData) {
        super(settings);
        this.polymerModelData = polymerModelData;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = asServer(context.getWorld());
        var clickedPoint = context.getBlockPos();
        var block = world.getBlockState(clickedPoint).getBlock();
        var player = asServer(context.getPlayer(), world);

        if (!(block instanceof IWireConnector wireConnector)) {
            PolyWorks.LOGGER.info("SPOOL> used on: nothing");
            return ActionResult.PASS;
        }
        PolyWorks.LOGGER.info("SPOOL> used on: "+ wireConnector);
        BlockEntityWithAttachments<?> blockEntityWithAttachments = wireConnector.getBlockEntity(context.getWorld(), context.getBlockPos());
        BlockAttachment attachment = blockEntityWithAttachments.getAttachment();

        if (attachment == null) {
            PolyWorks.LOGGER.warn("SPOOL> Attachment implementer has no attachment, hmmm");
            return ActionResult.PASS;
        }

        var existingElement = attachment.getNamedElement(WIRE_VIRTUAL_ELEMENT);
        if (existingElement != null) {
            PolyWorks.LOGGER.info("SPOOL> Modifying existing entity");
            existingElement.setLeashHolder(player);
            return ActionResult.SUCCESS;
        }

        var elem = new WireEntityVirtualElement();
        elem.setInvisible(false);
        elem.setCustomName(Text.of("foo"));
        elem.setCustomNameVisible(true);
        elem.setLeashHolder(player);
        attachment.putNamedElement(WIRE_VIRTUAL_ELEMENT, elem);

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
