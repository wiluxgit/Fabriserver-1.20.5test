package com.example;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.FakeRecipeHandler.getFakeRecipePacket;
import static com.example.FakeRecipeHandler.getRealRecipePacket;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
				net.minecraft.server.command.CommandManager.literal("foo").executes(this::foo)
		));
		ServerLifecycleEvents.SERVER_STARTED.register(FakeRecipeHandler::new);
	}

	public int foo(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("hello world"), false);

		ServerPlayerEntity splayer = context.getSource().getPlayer();

		// Spoof the client to override all recipes with some dummy stoneCutting recipes
		splayer.networkHandler.sendPacket(getFakeRecipePacket());
		var screenHandler = new SimpleNamedScreenHandlerFactory(
				(syncId, playerInventory, player) -> new TerminalScreenHandler(syncId, playerInventory, splayer),
				Text.literal("Terminal")
		);
		splayer.openHandledScreen(screenHandler);
		splayer.currentScreenHandler.setStackInSlot(0, 0, new ItemStack(FakeRecipeHandler.INPUT_ITEM));

		return 1;
	}

	public class TerminalScreenHandler extends StonecutterScreenHandler {
		private final ServerPlayerEntity playerThatOpened;

		public TerminalScreenHandler(int syncId, PlayerInventory playerInventory, ServerPlayerEntity playerThatOpened) {
			super(syncId, playerInventory);
			this.playerThatOpened = playerThatOpened;
		}

		@Override
		public void onClosed(PlayerEntity player) {
			super.onClosed(player);
			this.playerThatOpened.networkHandler.sendPacket(getRealRecipePacket());
		}
	}
}