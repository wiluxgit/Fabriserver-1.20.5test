package net.wilux.polyattchments;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static net.wilux.util.ServerCast.asServer;

public abstract class BlockAttachment {
    protected final @NotNull HolderAttachment chunkAttachment;
    protected final @NotNull ElementHolder elementHolder;

    protected BlockAttachment(@NotNull World world, @NotNull BlockPos pos) {
        this.elementHolder =  new ElementHolder();
        this.chunkAttachment = ChunkAttachment.ofTicking(this.elementHolder, asServer(world), pos.toCenterPos());
    }

    public void destroy() {
        this.chunkAttachment.destroy();
    }
}
