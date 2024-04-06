package com.example;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	}
	public int foo(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(() -> Text.literal("hello world"), false);

		ServerPlayerEntity splayer = context.getSource().getPlayer();
		var screenHandler = new SimpleNamedScreenHandlerFactory(
				(syncId, playerInventory, player) -> new MyStonecutterScreenHandler(0, player.getInventory()),
				Text.literal("Title")
		);
		splayer.openHandledScreen(screenHandler);
		splayer.currentScreenHandler.setStackInSlot(0, 0, new ItemStack(Items.DIAMOND_SWORD));

		return 1;
	}
	public class MyStonecutterScreenHandler extends StonecutterScreenHandler {
		public MyStonecutterScreenHandler(int syncId, PlayerInventory playerInventory) {
			super(syncId, playerInventory);
		}

		@Override
		public void updateSyncHandler(ScreenHandlerSyncHandler screenHandler) {
			super.updateSyncHandler(screenHandler);
			System.out.println(">>>>>>>>>>>>> updateSyncHandler");
		}

		@Override
		public void addListener(ScreenHandlerListener screenHandler) {
			super.addListener(screenHandler);
			System.out.println(">>>>>>>>>>>>> addListener");
		}
	}
}