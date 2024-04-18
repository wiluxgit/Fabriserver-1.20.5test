package net.wilux;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import net.wilux.objects.XTerm;
import net.wilux.recipespoofing.RecipeSpoofHandler;
import net.wilux.register.RegisterHelpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolyWorks implements ModInitializer {
	public static final String MOD_ID = "polyworks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello "+MOD_ID+"!");

		ServerLifecycleEvents.SERVER_STARTED.register(RecipeSpoofHandler::new); // FIXME: should retrigger on reloads

		// Register Commands
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
				net.minecraft.server.command.CommandManager.literal("xterm").executes(XTerm.Util::commandOpen)
		));

		// Register polymer asset mapping
		PolymerResourcePackUtils.addModAssets(MOD_ID);

		// Register minecraft blocks/items/etc.
		RegisterHelpers.registerAll();
	}
}