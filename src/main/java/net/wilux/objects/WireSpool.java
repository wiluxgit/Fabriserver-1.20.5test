package net.wilux.objects;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment;
import eu.pb4.polymer.virtualentity.api.elements.MobAnchorElement;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.wilux.PolyWorks;
import net.wilux.objects.base.block.ElectricityConnector;
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

        PolyWorks.LOGGER.info("Used spool");
        if (!(block instanceof ElectricityConnector electricityConnector)) {
            return ActionResult.PASS;
        }
        PolyWorks.LOGGER.info("Is Cool spool:"+electricityConnector);

        var holder = new ElementHolder();
        var wireTarget = new MobAnchorElement();
        wireTarget.setGlowing(true);
        holder.addElement(wireTarget);
        ChunkAttachment.of(holder, world, clickedPoint);

        return ActionResult.CONSUME;
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
