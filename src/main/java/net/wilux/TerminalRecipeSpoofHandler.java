package net.wilux;

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
import java.util.List;
import java.util.stream.Collectors;

public class TerminalRecipeSpoofHandler {
    public static final Item INPUT_ITEM = Items.BARRIER;

    static TerminalRecipeSpoofHandler INSTANCE = null;
    private final MinecraftServer server;

    public TerminalRecipeSpoofHandler(MinecraftServer server) {
        assert INSTANCE == null;
        this.server = server;
        INSTANCE = this;
    }

    private static RecipeEntry<StonecuttingRecipe> barrierOutput(ItemStack output) {
        StonecuttingRecipe fakeStonecutterRecipe = new StonecuttingRecipe("dummy_group", Ingredient.ofItems(INPUT_ITEM), output);
        RecipeEntry fakeRecipe = new RecipeEntry(new Identifier(ExampleMod.MOD_ID, output.getTranslationKey()+"_from_cutting"), fakeStonecutterRecipe);
        return fakeRecipe;
    }

    public static class TerminalContents {
        public final List<ItemStack> items;
        public TerminalContents(int amount) {
            this.items = Arrays.asList(
                    new ItemStack(Items.STONE, 99),
                    new ItemStack(Items.COBBLESTONE, 99),
                    new ItemStack(Items.DIORITE, 99),
                    new ItemStack(Items.DIRT, 99),
                    new ItemStack(Items.GLASS, 99),
                    new ItemStack(Items.GRANITE, 99),
                    new ItemStack(Items.SPONGE, 99),
                    new ItemStack(Items.IRON_BLOCK, 99),
                    new ItemStack(Items.DIAMOND, 99),
                    new ItemStack(Items.EMERALD, 99),
                    new ItemStack(Items.GOLD_INGOT, 99),
                    new ItemStack(Items.IRON_INGOT, 99),
                    new ItemStack(Items.STICK, 99)
            );
        }
        public SynchronizeRecipesS2CPacket createPacket() {
            Collection<RecipeEntry<?>> recipeEntries = this.items.stream().map(TerminalRecipeSpoofHandler::barrierOutput).collect(Collectors.toList());
            return new SynchronizeRecipesS2CPacket(recipeEntries);
        }
    }

    public static SynchronizeRecipesS2CPacket getRealRecipePacket() {
        var recipes = INSTANCE.server.getRecipeManager().values();
        return new SynchronizeRecipesS2CPacket(recipes);
    }
}
