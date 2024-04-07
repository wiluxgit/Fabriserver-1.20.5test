package net.wilux.items;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class WateringCan extends Item implements PolymerItem {
    final PolymerModelData cmd;

    public WateringCan(Settings settings, PolymerModelData cmd) {
        super(settings);
        this.cmd = cmd;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return cmd.item();
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, ServerPlayerEntity player) {
        ItemStack out = PolymerItemUtils.createItemStack(itemStack, context, player);
        out.addEnchantment(Enchantments.LURE, 0);
        return out;
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return this.cmd.value();
    }
}
