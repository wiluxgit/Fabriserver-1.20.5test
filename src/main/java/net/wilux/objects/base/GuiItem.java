package net.wilux.objects.base;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public final class GuiItem extends Item implements PolymerItem {
    public final PolymerModelData polymerModelData;

    public GuiItem(Settings settings, PolymerModelData polymerModelData) {
        super(settings);
        this.polymerModelData = polymerModelData;
    }

    public ItemStack getStack() {
        return new ItemStack(this);
    }
    public ItemStack getDataOnlyStack() {
        Item clientsideItem = this.polymerModelData.item();
        ItemStack stack = new ItemStack(clientsideItem);
        NbtCompound tag = stack.getOrCreateNbt();
        tag.putInt("CustomModelData", this.polymerModelData.value());
        stack.setNbt(tag);
        return stack;
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
