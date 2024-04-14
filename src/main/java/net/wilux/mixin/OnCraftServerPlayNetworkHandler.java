package net.wilux.mixin;


import net.minecraft.network.packet.c2s.play.CraftRequestC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.wilux.PolyWorks;
import net.wilux.objects.xterm.XTerm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class OnCraftServerPlayNetworkHandler {
    @Shadow public ServerPlayerEntity player;

    @Inject(at = @At("RETURN"), method = "onCraftRequest")
    private void init(CraftRequestC2SPacket packet, CallbackInfo ci) {
        PolyWorks.LOGGER.info("onCraftRequest");
        if (this.player.currentScreenHandler instanceof XTerm.XTermScreenHandler xTermScreenHandler) {
            if (xTermScreenHandler.syncId != packet.getSyncId()) {
                PolyWorks.LOGGER.warn("Serverplayer has the wrong syncid");
                return;
            }
            xTermScreenHandler.onRecipeBookClick(packet.getRecipe(), packet.shouldCraftAll());
        }
    }
}
