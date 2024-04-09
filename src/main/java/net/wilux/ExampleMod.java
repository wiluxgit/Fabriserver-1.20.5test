package net.wilux;

import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.wilux.items.Terminal;
import net.wilux.items.WateringCan;
import net.wilux.items.XTerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "tut";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item ITEM_WATERING_CAN = new WateringCan(new FabricItemSettings(), polymerModelData(Items.CLOCK, "item/silver_sword"));
	public static final Item ITEM_GUI_XTERM = new XTerm.XTermItemGui(new FabricItemSettings(), polymerModelData(Items.CLOCK, "wx/gui/book_overlay"));

	public static final Block BLOCK_TERMINAL = new Terminal.TerminalBlock(FabricBlockSettings.create().strength(4.0f));
	public static final Block BLOCK_XTERM = new XTerm.XTermBlock(FabricBlockSettings.create().strength(4.0f));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");

		ServerLifecycleEvents.SERVER_STARTED.register(RecipeSpoofHandler::new);

		// Register Commands
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
				net.minecraft.server.command.CommandManager.literal("foo").executes(Terminal.Util::commandOpen)
		));
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
				net.minecraft.server.command.CommandManager.literal("xterm").executes(XTerm.Util::commandOpen)
		));

		// Register Blocks/Items
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "watering_can"), ITEM_WATERING_CAN);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm"), ITEM_GUI_XTERM);
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "terminal"), BLOCK_TERMINAL);
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "xterm"), BLOCK_XTERM);
	}

	public static PolymerModelData polymerModelData(Item itemType, String assetPath) {
		return PolymerResourcePackUtils.requestModel(itemType, new Identifier(MOD_ID, assetPath));
	}
}