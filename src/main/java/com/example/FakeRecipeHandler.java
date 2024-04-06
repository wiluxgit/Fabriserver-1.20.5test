package com.example;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collection;

public class FakeRecipeHandler {
    public static final Item INPUT_ITEM = Items.BARRIER;

    static FakeRecipeHandler INSTANCE = null;
    private final MinecraftServer server;

    public FakeRecipeHandler(MinecraftServer server) {
        assert INSTANCE == null;
        this.server = server;
        INSTANCE = this;
    }

    private static RecipeEntry barrierOutput(ItemStack output) {
        StonecuttingRecipe fakeStonecutterRecipe = new StonecuttingRecipe("dummy_group", Ingredient.ofItems(INPUT_ITEM), output);
        RecipeEntry fakeRecipe = new RecipeEntry(new Identifier("wx:"+output.getTranslationKey()), fakeStonecutterRecipe);
        return fakeRecipe;
    }

    public static SynchronizeRecipesS2CPacket getFakeRecipePacket() {
        Collection<RecipeEntry<?>> recipes = Arrays.asList(
                barrierOutput(new ItemStack(Items.STONE, 2)),
                barrierOutput(new ItemStack(Items.COBBLESTONE, 4)),
                barrierOutput(new ItemStack(Items.DIORITE, 8)),
                barrierOutput(new ItemStack(Items.DIRT, 16)),
                barrierOutput(new ItemStack(Items.GLASS, 64)),
                barrierOutput(new ItemStack(Items.GRANITE, 64)),
                barrierOutput(new ItemStack(Items.SPONGE, 64)),
                barrierOutput(new ItemStack(Items.IRON_BLOCK, 64)),
                barrierOutput(new ItemStack(Items.DIAMOND, 64)),
                barrierOutput(new ItemStack(Items.EMERALD, 64)),
                barrierOutput(new ItemStack(Items.GOLD_INGOT, 64)),
                barrierOutput(new ItemStack(Items.IRON_INGOT, 64)),
                barrierOutput(new ItemStack(Items.STICK, 64))
        );
        return new SynchronizeRecipesS2CPacket(recipes);
    }

    public static SynchronizeRecipesS2CPacket getRealRecipePacket() {
        var recipes = INSTANCE.server.getRecipeManager().values();
        return new SynchronizeRecipesS2CPacket(recipes);
    }
}
