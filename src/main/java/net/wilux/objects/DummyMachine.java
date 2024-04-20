package net.wilux.objects;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wilux.PolyWorks;
import net.wilux.objects.base.block.Machine;
import net.wilux.objects.base.blockentity.BlockEntityWithAttachments;
import net.wilux.polyattchments.BlockAttachment;
import net.wilux.register.Registered;
import net.wilux.util.ExtraExceptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class DummyMachine {
    private static class DummyDisplayEntities extends BlockAttachment {
        protected DummyDisplayEntities(@NotNull World world, @NotNull BlockPos pos) {
            super(world, pos);
        }
    }

    public static final class DummyMachineBlockEntity extends BlockEntityWithAttachments<DummyDisplayEntities> {
        public static final BlockEntityType<DummyMachineBlockEntity> BLOCK_ENTITY_TYPE = Registered.MACHINE_DUMMY.BLOCK_ENTITY_TYPE_DUMMY_MACHINE;

        private DummyDisplayEntities dummyDisplayEntities = null;
        public DummyMachineBlockEntity(BlockPos pos, BlockState state) {
            super(Registered.MACHINE_DUMMY.BLOCK_ENTITY_TYPE_DUMMY_MACHINE, pos, state);
            PolyWorks.LOGGER.info("Created Dummy Machine");
        }

        @Override
        public void initializeAttachment(World world, BlockPos pos, BlockState blockState) {
            assert this.dummyDisplayEntities == null;
            PolyWorks.LOGGER.info("Create Virtual Entity Dummy");
            dummyDisplayEntities = new DummyDisplayEntities(world, pos);
        }

        @Override
        public void removeAttachment() {
            assert this.dummyDisplayEntities != null;
            PolyWorks.LOGGER.info("Kill Virtual Entity Dummy");
            this.dummyDisplayEntities.destroy();
            this.dummyDisplayEntities = null;
        }

        @Override
        public @Nullable DummyDisplayEntities getAttachment() {
            return this.dummyDisplayEntities;
        }
    }

    public static final class DummyMachineBlock extends Machine.MachineBlock<DummyMachineBlockEntity> {
        public DummyMachineBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
            super(settings, northPolymerState, eastPolymerState, southPolymerState, westPolymerState);
        }

        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new DummyMachineBlockEntity(pos, state);
        }

        @Override
        public DummyMachineBlockEntity getBlockEntity(World world, BlockPos pos) {
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

        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return (World l_world, BlockPos l_pos1, BlockState l_state1, T t) -> {
                if (type ==  t.getType()) {
                    if (t instanceof DummyMachineBlockEntity dummyMachineBlockEntity) {
                        dummyMachineBlockEntity.tick(l_world, l_pos1, l_state1);
                        return;
                    }
                }
                ExtraExceptions.debugCrash("block entity has wrong type");
            };
        }
    }
}