package net.wilux.objects.base.blockentity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWireConnector<TBlockEntity extends BlockEntityWithAttachments<?>>  {
    TBlockEntity getBlockEntity(World world, BlockPos pos);
}
