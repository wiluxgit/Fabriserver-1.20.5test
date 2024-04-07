package net.wilux.items;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.SimplePolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wilux.ExampleMod;
import net.wilux.FakeRecipeHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Random;

import static net.wilux.FakeRecipeHandler.getFakeRecipePacket;
import static net.wilux.FakeRecipeHandler.getRealRecipePacket;

public class Terminal {
    public static class TerminalBlock extends Block implements PolymerTexturedBlock {
        public TerminalBlock(Settings settings) {
            super(settings);
        }

        @Override
        public Block getPolymerBlock(BlockState state) {
            return Blocks.NOTE_BLOCK;
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if (!world.isClient) {
                TerminalScreenHandler.open((ServerPlayerEntity)player);
                return ActionResult.SUCCESS;
            }
            return ActionResult.FAIL;
        }
    }

    public static class TerminalScreenHandler extends StonecutterScreenHandler {
        private final ServerPlayerEntity playerThatOpened;

        public TerminalScreenHandler(int syncId, PlayerInventory playerInventory, ServerPlayerEntity playerThatOpened) {
            super(syncId, playerInventory);
            this.playerThatOpened = playerThatOpened;
            this.setStackInSlot(0, 0, new ItemStack(FakeRecipeHandler.INPUT_ITEM));
        }

        @Override
        public void onClosed(PlayerEntity player) {
            super.onClosed(player);
            this.playerThatOpened.networkHandler.sendPacket(getRealRecipePacket());
        }

        @Override
        public ItemStack quickMove(PlayerEntity player, int slotId) {
            Random rand = new Random();
            int r = rand.nextInt(49)+2;
            ItemStack itemStack = ItemStack.EMPTY;

            // Kinda annoying how this scrolls the selection, care needs to be taken to only update when non full!
            Slot slot = this.slots.get(slotId);
            if (slot != null && slot.hasStack()) {
                ExampleMod.LOGGER.info("Shiftclick Terminal ITEM"+r);
            } else {
                ExampleMod.LOGGER.info("Shiftclick Terminal EMPTY"+r);
            }
            ((ServerPlayerEntity)player).networkHandler.sendPacket(getFakeRecipePacket(r));
            this.sendContentUpdates();

            return itemStack;
        }

        @Override
        public int getSelectedRecipe() {
            ExampleMod.LOGGER.info("getSelectedRecipe");
            return super.getSelectedRecipe();
        }

        @Override
        public List<RecipeEntry<StonecuttingRecipe>> getAvailableRecipes() {
            ExampleMod.LOGGER.info("getAvailableRecipes");
            return super.getAvailableRecipes();
        }

        @Override
        public int getAvailableRecipeCount() {
            ExampleMod.LOGGER.info("getAvailableRecipeCount");
            return super.getAvailableRecipeCount();
        }

        @Override
        public boolean canCraft() {
            ExampleMod.LOGGER.info("canCraft");
            return super.canCraft();
        }

        @Override
        public boolean canUse(PlayerEntity player) {
            return super.canUse(player);
        }

        @Override
        public boolean onButtonClick(PlayerEntity player, int id) {
            ExampleMod.LOGGER.info("onButtonClick");
            return super.onButtonClick(player, id);
        }

        @Override
        public void onContentChanged(Inventory inventory) {
            ExampleMod.LOGGER.info("onContentChanged");
            super.onContentChanged(inventory);
        }

        public static void open(ServerPlayerEntity splayer) {
            splayer.networkHandler.sendPacket(getFakeRecipePacket(1));
            var screenHandler = new SimpleNamedScreenHandlerFactory(
                    (syncId, playerInventory, player) -> new TerminalScreenHandler(syncId, playerInventory, splayer),
                    Text.literal("Terminal")
            );
            splayer.openHandledScreen(screenHandler);
        }
    }

    public static class Util {
        public static int commandOpen(CommandContext<ServerCommandSource> context) {
            context.getSource().sendFeedback(() -> Text.literal("hello world"), false);
            ServerPlayerEntity splayer = context.getSource().getPlayer();
            TerminalScreenHandler.open(splayer);
            return 1;
        }
    }
}
