package net.wilux.recipespoofing;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.wilux.PolyWorks;

public class RecipeSpoofHandler {
    public static final Item INPUT_ITEM = Items.BARRIER;

    static RecipeSpoofHandler INSTANCE = null;
    private final MinecraftServer server;

    public RecipeSpoofHandler(MinecraftServer server) {
        assert INSTANCE == null;
        this.server = server;
        INSTANCE = this;
    }

    public static abstract class RecipeSpoof<T>{
        public final ServerPlayerEntity splayer;

        public RecipeSpoof(ServerPlayerEntity splayer) {
            this.splayer = splayer;
        }
        public abstract void enter();
        public void exit() {
            splayer.networkHandler.sendPacket(getRealRecipePacket());
        }
    }

    public static RecipeEntry<StonecuttingRecipe> mkRecipeStonecuttingBarrier(ItemStack output) {
        StonecuttingRecipe fakeStonecutterRecipe = new StonecuttingRecipe("dummy_group", Ingredient.ofItems(INPUT_ITEM), output);
        return new RecipeEntry<StonecuttingRecipe>(new Identifier(PolyWorks.MOD_ID, output.getTranslationKey()+"_from_cutting"), fakeStonecutterRecipe);
    }

    private static SynchronizeRecipesS2CPacket getRealRecipePacket() {
        var recipes = INSTANCE.server.getRecipeManager().values();
        return new SynchronizeRecipesS2CPacket(recipes);
    }
}
