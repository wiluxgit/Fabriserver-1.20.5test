package net.wilux.objects.base.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wilux.PolyWorks;
import net.wilux.objects.base.blockentity.IWireConnector;
import net.wilux.objects.base.blockentity.BlockEntityWithAttachments;
import net.wilux.polyattchments.BlockAttachment;
import net.wilux.register.Registered;
import net.wilux.util.ExtraExceptions;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class Machine {
    public abstract static class MachineBlock<TBlockEntity extends BlockEntityWithAttachments<?>> extends PolyHorizontalFacingBlock implements BlockEntityProvider, IWireConnector<TBlockEntity> {
        public MachineBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
            super(settings, northPolymerState, eastPolymerState, southPolymerState, westPolymerState);
        }
    }
}