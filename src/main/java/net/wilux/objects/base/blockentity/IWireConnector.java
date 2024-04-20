package net.wilux.objects.base.blockentity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wilux.objects.base.blockentity.polyattchments.BlockEntityWithAttachments;

public interface IWireConnector<T extends BlockEntityWithAttachments<?>>  {
    T getEntity(World world, BlockPos pos);
}
