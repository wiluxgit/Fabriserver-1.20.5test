package net.wilux;

import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.wilux.items.PolyBlockItem;
import net.wilux.items.WateringCan;
import net.wilux.items.XTerm;
import net.wilux.recipespoofing.RecipeSpoofHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolyWorks implements ModInitializer {
	public static final String MOD_ID = "polyworks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item ITEM_WATERING_CAN = new WateringCan(new FabricItemSettings(), polymerModelData(Items.CLOCK, "item/watering_can"));

	public static final Block BLOCK_XTERM = new XTerm.XTermBlock(FabricBlockSettings.create().strength(4.0f), polymerBlockData(BlockModelType.FULL_BLOCK, "block/xterm"));
	public static final BlockItem ITEM_XTERM = new PolyBlockItem(BLOCK_XTERM, new FabricItemSettings(), polymerModelData(Items.BARRIER, "item/xterm"));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft itemStack in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");

		ServerLifecycleEvents.SERVER_STARTED.register(RecipeSpoofHandler::new); // FIXME: should retrigger on reloads

		// Register Commands
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
				net.minecraft.server.command.CommandManager.literal("xterm").executes(XTerm.Util::commandOpen)
		));

		// Register polymer asset mapping
		PolymerResourcePackUtils.addModAssets(MOD_ID);

		// Register blocks and items
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "watering_can"), ITEM_WATERING_CAN);
		GuiItems.registerAll();
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "xterm"), BLOCK_XTERM);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "xterm"), ITEM_XTERM);
	}

	public static PolymerModelData polymerModelData(Item itemType, String assetPath) {
		return PolymerResourcePackUtils.requestModel(itemType, new Identifier(MOD_ID, assetPath));
	}
	public static BlockState polymerBlockData(BlockModelType blockModelType, String assetPath){
		BlockState bs = PolymerBlockResourceUtils.requestBlock(blockModelType, PolymerBlockModel.of(new Identifier(MOD_ID, assetPath)));
		assert bs != null; // we are a
		return bs;
	}
}