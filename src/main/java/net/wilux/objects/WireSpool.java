package net.wilux.objects;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.wilux.PolyWorks;
import net.wilux.objects.base.blockentity.IWireConnector;
import net.wilux.objects.base.blockentity.BlockEntityWithAttachments;
import org.jetbrains.annotations.Nullable;

import static net.wilux.util.ServerCast.asServer;

public class WireSpool extends Item implements PolymerItem {
    final PolymerModelData polymerModelData;

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
            PolyWorks.LOGGER.info("Used spool on: nothing");
            return ActionResult.PASS;
        }
        PolyWorks.LOGGER.info("Used spool on: "+ wireConnector);
        BlockEntityWithAttachments<?> blockEntityWithAttachments = wireConnector.getBlockEntity(context.getWorld(), context.getBlockPos());

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
