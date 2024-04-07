package net.wilux.items;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wilux.ExampleMod;
import net.wilux.TerminalRecipeSpoofHandler;
import net.wilux.TerminalRecipeSpoofHandler.TerminalContents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            if (world.isClient) {
                ExampleMod.LOGGER.error("WORLD IS CLIENT");
                return ActionResult.FAIL;
            }

            TerminalScreenHandler.open((ServerPlayerEntity)player);
            return ActionResult.SUCCESS;
        }
    }

    public static class TerminalScreenHandler extends ScreenHandler {
        public static final int INPUT_ID = 0;
        public static final int OUTPUT_ID = 1;
        private static final int INVENTORY_START = 2;
        private static final int INVENTORY_END = 29;
        private static final int HOTBAR_START = 29;
        private static final int HOTBAR_END = 38;
        private final ScreenHandlerContext context;
        private final ServerPlayerEntity playerThatOpened;
        private final Property selectedIndex;
        private final World world;
        private final TerminalContents initContents;
        private ItemStack inputStack;
        long lastTakeTime;
        final Slot inputSlot;
        final Slot outputSlot;
        Runnable contentsChangedListener;
        public final Inventory input;
        final CraftingResultInventory output;

        public TerminalScreenHandler(int syncId, PlayerInventory playerInventory, ServerPlayerEntity playerThatOpened, TerminalContents contents) {
            this(syncId, playerInventory, playerThatOpened, contents, ScreenHandlerContext.EMPTY);
        }

        public TerminalScreenHandler(int syncId, PlayerInventory playerInventory, ServerPlayerEntity playerThatOpened, TerminalContents contents, ScreenHandlerContext context) {
            super(ScreenHandlerType.STONECUTTER, syncId);
            this.playerThatOpened = playerThatOpened;
            this.selectedIndex = Property.create();
            this.initContents = contents;
            this.inputStack = ItemStack.EMPTY;
            this.contentsChangedListener = () -> {};
            this.input = new SimpleInventory(1) {
                public void markDirty() {
                    super.markDirty();
                    TerminalScreenHandler.this.onContentChanged(this);
                    TerminalScreenHandler.this.contentsChangedListener.run();
                }
            };
            this.output = new CraftingResultInventory();
            this.context = context;
            this.world = playerInventory.player.getWorld();
            this.inputSlot = this.addSlot(new Slot(this.input, INPUT_ID, 20, 33));
            this.outputSlot = this.addSlot(new Slot(this.output, OUTPUT_ID, 143, 33) {
                public boolean canInsert(ItemStack stack) {
                    return false;
                }

                public void onTakeItem(PlayerEntity player, ItemStack stack) {
                    ExampleMod.LOGGER.info("onTakeItem");
                    stack.setCount(1);
                    TerminalScreenHandler.this.populateResult();
                    super.onTakeItem(player, stack);
                }
            });

            // Setup Slots
            {
                int i;
                for(i = 0; i < 3; ++i) {
                    for(int j = 0; j < 9; ++j) {
                        this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
                    }
                }

                for(i = 0; i < 9; ++i) {
                    this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
                }
            }

            this.addProperty(this.selectedIndex);
            this.setStackInSlot(0, 0, new ItemStack(TerminalRecipeSpoofHandler.INPUT_ITEM, 3));
        }

        public boolean canUse(PlayerEntity player) {
            return canUse(this.context, player, Blocks.STONECUTTER);
        }

        public boolean onButtonClick(PlayerEntity player, int id) {
            ExampleMod.LOGGER.info("onButtonClick");
            if (this.isInBounds(id)) {
                this.selectedIndex.set(id);
                this.populateResult();
            }
            return true;
        }

        private boolean isInBounds(int id) {
            return id >= 0 && id < this.initContents.items.size();
        }

        public void onContentChanged(Inventory inventory) {
            ExampleMod.LOGGER.info("onContentChanged");

            ItemStack itemStack = this.inputSlot.getStack();
            if (!itemStack.isOf(this.inputStack.getItem())) {
                this.inputStack = itemStack.copy();
                this.updateInput(inventory, itemStack);
            }
        }

        private void updateInput(Inventory input, ItemStack stack) {
            ExampleMod.LOGGER.info("updateInput");
        }

        void populateResult() {
            if (!this.initContents.items.isEmpty() && this.isInBounds(this.selectedIndex.get())) {
                ExampleMod.LOGGER.info("populateResult NONEMPTY");
                var item = this.initContents.items.get(this.selectedIndex.get()).copyWithCount(64);
                this.outputSlot.setStack(item);
            } else {
                ExampleMod.LOGGER.info("populateResult EMPTY");
                this.outputSlot.setStack(ItemStack.EMPTY);
            }
            this.sendContentUpdates();
        }

        public ScreenHandlerType<?> getType() {
            return ScreenHandlerType.STONECUTTER;
        }

        public void setContentsChangedListener(Runnable contentsChangedListener) {
            this.contentsChangedListener = contentsChangedListener;
        }

        public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
            return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
        }

        public ItemStack quickMove(PlayerEntity player, int slotId) {
            Random rand = new Random();
            int r = rand.nextInt(49)+2;
            ExampleMod.LOGGER.info("Shiftclick Terminal ITEM"+r);

            Slot slot = this.slots.get(slotId);
            ItemStack itemStack = slot.getStack();
            if (slotId == OUTPUT_ID) {
                var outStack = itemStack.copyWithCount(64);
                if (!this.insertItem(outStack, INVENTORY_START, HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
            }

            return ItemStack.EMPTY;
        }

        public void onClosed(PlayerEntity player) {
            ExampleMod.LOGGER.info("onClosed");
            super.onClosed(player);
            this.output.removeStack(1);
            this.context.run((world, pos) -> {
                this.dropInventory(player, this.input);
            });
            this.playerThatOpened.networkHandler.sendPacket(TerminalRecipeSpoofHandler.getRealRecipePacket());
        }

        /*
        @Override
        public ItemStack quickMove(PlayerEntity player, int slotId) {
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
        */

        public static void open(ServerPlayerEntity splayer) {
            var terminalContents = new TerminalContents(1);
            splayer.networkHandler.sendPacket(terminalContents.createPacket());
            var screenHandler = new SimpleNamedScreenHandlerFactory(
                    (syncId, playerInventory, player) -> new TerminalScreenHandler(syncId, playerInventory, splayer, terminalContents),
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
