package net.wilux.objects.base.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wilux.PolyWorks;
import net.wilux.objects.base.blockentity.IWireConnector;
import net.wilux.objects.base.blockentity.polyattchments.BlockEntityWithAttachments;
import net.wilux.objects.base.blockentity.polyattchments.BlockAttachment;
import net.wilux.register.Registered;
import net.wilux.util.ExtraExceptions;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Optional;

public final class Machine {

    private static class DummyDisplayEntities extends BlockAttachment {
        private DummyDisplayEntities(World world, BlockPos pos) {
            super(world, pos);
        }
    }

    public static final class DummyMachineBlockEntity extends BlockEntityWithAttachments<DummyDisplayEntities> {
        public static final BlockEntityType<Machine.DummyMachineBlockEntity> BLOCK_ENTITY_TYPE = Registered.MACHINE_DUMMY.BLOCK_ENTITY_TYPE_DUMMY_MACHINE;
        public DummyMachineBlockEntity(BlockPos pos, BlockState state) {
            super(Registered.MACHINE_DUMMY.BLOCK_ENTITY_TYPE_DUMMY_MACHINE, pos, state);
            PolyWorks.LOGGER.info("Created Dummy Factory of");
        }

        @Override
        public void initializeAttachment(World world, BlockPos pos, BlockState blockState) {
            throw new NotImplementedException("initializeAttachment");
        }

        @Override
        public void removeAttachment() {
            throw new NotImplementedException("removeAttachment");
        }

        @Override
        public DummyDisplayEntities getAttachment() {
            throw new NotImplementedException("getAttachment");
        }
    }

    public static final class DummyMachineBlock extends MachineBlock<DummyMachineBlockEntity> {
        public DummyMachineBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
            super(settings, northPolymerState, eastPolymerState, southPolymerState, westPolymerState);
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new DummyMachineBlockEntity(pos, state);
        }

        @Override
        public DummyMachineBlockEntity getEntity(World world, BlockPos pos) {
            Optional<? extends BlockEntity> blockEntity = world.getBlockEntity(pos, DummyMachineBlockEntity.BLOCK_ENTITY_TYPE);
            if (blockEntity.isEmpty()) {
                ExtraExceptions.debugCrash("block has no block entity!");
            }
            if (!(blockEntity.get() instanceof DummyMachineBlockEntity machineBlockEntity)) {
                ExtraExceptions.debugCrash("block entity is not a "+ DummyMachineBlockEntity.class.getSimpleName());
                return null;
            }
            return machineBlockEntity;
        }
    }

    public abstract static class MachineBlock<T extends BlockEntityWithAttachments<?>> extends PolyHorizontalFacingBlock implements BlockEntityProvider, IWireConnector<T> {
        public MachineBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
            super(settings, northPolymerState, eastPolymerState, southPolymerState, westPolymerState);
        }
    }
}