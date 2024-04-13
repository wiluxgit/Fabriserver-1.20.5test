package net.wilux.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class ServerCast {
    public static ServerPlayerEntity asServer(PlayerEntity player, World world) {
        if (world.isClient) {
            throw new UnsupportedOperationException("WORLD IS CLIENT");
        }
        return (ServerPlayerEntity)player;
    }

}
