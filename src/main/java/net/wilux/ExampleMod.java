package net.wilux;

import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.wilux.items.Terminal;
import net.wilux.items.WateringCan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "tut";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item ITEM_WATERING_CAN = new WateringCan(new FabricItemSettings(), polymerModelData(Items.IRON_INGOT, "item/silver_sword"));
	public static final Block BLOCK_TERMINAL = new Terminal.TerminalBlock(FabricBlockSettings.create().strength(4.0f));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
				net.minecraft.server.command.CommandManager.literal("foo").executes(Terminal.Util::commandOpen)
		));
		ServerLifecycleEvents.SERVER_STARTED.register(FakeRecipeHandler::new);

		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "watering_can"), ITEM_WATERING_CAN);
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "terminal"), BLOCK_TERMINAL);
	}

	public static PolymerModelData polymerModelData(Item itemType, String assetPath) {
		return PolymerResourcePackUtils.requestModel(itemType, new Identifier(MOD_ID, assetPath));
	}
}