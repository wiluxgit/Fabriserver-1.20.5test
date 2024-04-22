package net.wilux.polyattchments;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.wilux.util.ServerCast.asServer;

public class BlockAttachment extends Attachment {
    protected BlockAttachment(@NotNull World world, @NotNull BlockPos pos) {
        super(args(world, pos));
    }

    // Actually retarded java moment
    private static Args args(@NotNull World world, @NotNull BlockPos pos) {
        ElementHolder holder = new ElementHolder();
        HolderAttachment holderAttachment = ChunkAttachment.ofTicking(holder, asServer(world), pos.toCenterPos());
        return new Args(holder, holderAttachment);
    }
}
