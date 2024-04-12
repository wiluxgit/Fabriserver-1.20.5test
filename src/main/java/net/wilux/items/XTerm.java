package net.wilux.items;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import net.minecraft.network.packet.s2c.play.UnlockRecipesS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookOptions;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wilux.ExampleMod;
import net.wilux.RecipeSpoofHandler;
import net.wilux.stackstorage.StoredStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.min;
import static net.wilux.ServerCast.asServer;

public class XTerm {
    public static class XTermBlock extends Block implements PolymerTexturedBlock {
        public XTermBlock(Settings settings) {
            super(settings);
        }

        @Override
        public Block getPolymerBlock(BlockState state) {
            return Blocks.NOTE_BLOCK;
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            var splayer = asServer(player, world);
            XTermScreenHandler.open(splayer);

            return ActionResult.SUCCESS;
        }
    }

    public static class XTermSpoofer extends RecipeSpoofHandler.RecipeSpoof<ShapelessRecipe> {
        private final Map<Identifier, StoredStack> items;
        private final RecipeBookOptions recipeBookSettings;

        public XTermSpoofer(ServerPlayerEntity splayer) {
            super(splayer);

            recipeBookSettings = new RecipeBookOptions();
            recipeBookSettings.setGuiOpen(RecipeBookCategory.CRAFTING, true);
            recipeBookSettings.setGuiOpen(RecipeBookCategory.FURNACE, true);
            recipeBookSettings.setGuiOpen(RecipeBookCategory.SMOKER, true);
            recipeBookSettings.setGuiOpen(RecipeBookCategory.BLAST_FURNACE, true);

            var tempItems = Stream.of(
                    Items.DIAMOND,
                    Items.DIRT,
                    Items.SPONGE,
                    Items.STONE,
                    Items.COBBLESTONE,
                    Items.OAK_LOG
            ).map(ItemStack::new).toList();

            this.items = tempItems.stream().collect(Collectors.toMap(
                (ItemStack x) -> new Identifier(ExampleMod.MOD_ID, "xterm."+x.getItem().getTranslationKey()),
                (ItemStack x) -> new StoredStack(x, 244)
            ));
        }

        public void forget(Identifier recipe) {
            var unlockRecipes = new UnlockRecipesS2CPacket(UnlockRecipesS2CPacket.Action.REMOVE, List.of(recipe), List.of(), recipeBookSettings);
            splayer.networkHandler.sendPacket(unlockRecipes);
        }

        public @Nullable StoredStack.StackTransfer takeLargestStackIfExists(Identifier recipe) {
            var storedStack = this.items.get(recipe);
            if (storedStack == null) {
                ExampleMod.LOGGER.warn("Player tried to take item that is not contained");
                return null;
            }
            return storedStack.takeLargest();
        }

        private Collection<RecipeEntry<?>> getSpoofRecipeList() {
            // TODO? Organize categories
            return this.items.entrySet().stream().map(entry -> {
                var id = entry.getKey();
                var itemStack = entry.getValue().stackCopy();
                DefaultedList<Ingredient> inputIngredients = DefaultedList.of();
                inputIngredients.add(Ingredient.ofItems(ExampleMod.ITEM_GUI_XTERM_L)); // Todo? have some dummy item instead
                ShapelessRecipe fakeCraftRecipe = new ShapelessRecipe("", CraftingRecipeCategory.BUILDING, itemStack, inputIngredients);
                return new RecipeEntry<ShapelessRecipe>(id, fakeCraftRecipe);
            }).collect(Collectors.toList());
        }

        @Override
        public void enter() {
            Collection<RecipeEntry<?>> recipeEntries = this.getSpoofRecipeList();
            var declareRecipes = new SynchronizeRecipesS2CPacket(recipeEntries);

            Collection<Identifier> fakeKeys = recipeEntries.stream().map(RecipeEntry::id).collect(Collectors.toList());
            var unlockRecipes = new UnlockRecipesS2CPacket(UnlockRecipesS2CPacket.Action.INIT, fakeKeys, List.of(), this.recipeBookSettings);

            this.splayer.networkHandler.sendPacket(declareRecipes);
            this.splayer.networkHandler.sendPacket(unlockRecipes);
        }

        @Override
        public void exit() {
            super.exit();
            splayer.getRecipeBook().sendInitRecipesPacket(splayer);
        }
    }

    public static class XTermScreenHandler extends CraftingScreenHandler {
        public static final int RESULT_ID = 0;
        private static final int INPUT_START = 1;
        private static final int INPUT_END = 10;
        private static final int INVENTORY_START = 10;
        private static final int INVENTORY_END = 37;
        private static final int HOTBAR_START = 37;
        private static final int HOTBAR_END = 46;

        private final XTermSpoofer spoofer;

        public XTermScreenHandler(int syncId, PlayerInventory playerInventory, XTermSpoofer spoofer) {
            super(syncId, playerInventory);
            this.spoofer = spoofer;
            this.setStackInSlot(INPUT_START, 0, new ItemStack(ExampleMod.ITEM_GUI_XTERM_L, 1));
            this.setStackInSlot(INPUT_START+1, 0, new ItemStack(ExampleMod.ITEM_GUI_XTERM_R, 1));
            this.sendContentUpdates();
        }

        public void onRecipeBookClick(Identifier recipe, boolean craftAll) {
            ExampleMod.LOGGER.info("onRecipeBookClick {"+ "\n\trecipe="+recipe+"\n\tcraftAll="+craftAll+"\n}");

            var stackTransfer = spoofer.takeLargestStackIfExists(recipe);
            if (stackTransfer == null) return;
            boolean tookItem = tryGiveStackToPlayer(stackTransfer.itemStack, craftAll);
            int remainingItems = stackTransfer.resolveWith(tookItem);
            ExampleMod.LOGGER.info("Transfered items out of terminal. "+remainingItems+" now remain.");

            this.setStackInSlot(RESULT_ID, this.nextRevision(), stackTransfer.itemStack.copyWithCount(1)); // for visuals only
            if (remainingItems == 0) {
                this.spoofer.forget(recipe); // should be handled elsewhere?
            }
        }
        private boolean tryGiveStackToPlayer(ItemStack itemStack, boolean shiftClick) {
            if (shiftClick) {
                if (!this.insertItem(itemStack, INVENTORY_START, HOTBAR_END, true)) return false;
            } else {
                var cursorStack = getCursorStack();
                if (!cursorStack.isEmpty()) return false;
                setCursorStack(itemStack);
            }
            return true;
        }

        @Override
        public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
            if (INPUT_START <= slot.id && slot.id <= INPUT_END) { return false; }
            if (slot.id == RESULT_ID) { return false; }
            return super.canInsertIntoSlot(stack, slot);
        }

        @Override
        public void onClosed(PlayerEntity player) {
            super.onClosed(player);
            this.spoofer.exit();
        }

        @Override
        public void fillInputSlots(boolean craftAll, RecipeEntry<?> recipe, ServerPlayerEntity splayer) {
            assert false; // This should never be called by vanilla since the selected recipe will always be invalid.
        }

        public static void open(ServerPlayerEntity splayer) {
            var xtermContents = new XTermSpoofer(splayer);
            xtermContents.enter();
            var screenHandler = new SimpleNamedScreenHandlerFactory(
                    (syncId, playerInventory, player) -> new XTermScreenHandler(syncId, playerInventory, xtermContents),
                    Text.literal("XTerm")
            );
            splayer.openHandledScreen(screenHandler);
        }
    }

    public static class XTermItemGui extends Item implements PolymerItem {
        final PolymerModelData cmd;

        public XTermItemGui(Settings settings, PolymerModelData cmd) {
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
            //out.addEnchantment(Enchantments.LURE, 0);
            return out;
        }

        @Override
        public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
            return this.cmd.value();
        }
    }

    public static class Util {
        public static int commandOpen(CommandContext<ServerCommandSource> context) {
            context.getSource().sendFeedback(() -> Text.literal("hello world"), false);
            ServerPlayerEntity splayer = context.getSource().getPlayer();
            XTermScreenHandler.open(splayer);
            return 1;
        }
    }
}
