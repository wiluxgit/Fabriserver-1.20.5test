package net.wilux.objects;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wilux.PolyWorks;
import net.wilux.objects.base.block.PolyHorizontalFacingBlock;
import net.wilux.register.Registered;
import net.wilux.util.ExtraExceptions;
import org.jetbrains.annotations.Nullable;

public final class Crate {
    public static class CrateBlockEntity extends BlockEntity {
        private int number = 7;

        public CrateBlockEntity(BlockPos pos, BlockState state) {
            super(Registered.CRATE.BLOCK_ENTITY_TYPE, pos, state);
        }
        public static void tick(World world, BlockPos pos, BlockState state, CrateBlockEntity be) {
            // PolyWorks.LOGGER.info(CrateBlockEntity.class.getName()+" Ticked at: "+pos.toString());
        }

        @Override
        public void writeNbt(NbtCompound nbt) {
            nbt.putInt("number", number);
            super.writeNbt(nbt);
        }
    }

    public static class CrateBlock extends PolyHorizontalFacingBlock implements BlockEntityProvider {
        public CrateBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
            super(settings, northPolymerState, eastPolymerState, southPolymerState, westPolymerState);
        }

        @Nullable @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new CrateBlockEntity(pos, state);
        }

        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
            return (World l_world, BlockPos l_pos1, BlockState l_state1, T t) -> {
                if (type ==  t.getType()) {
                    if (t instanceof CrateBlockEntity crateBlockEntity) {
                        CrateBlockEntity.tick(l_world, l_pos1, l_state1, crateBlockEntity);
                        return;
                    }
                }
                throw new ExtraExceptions.ProbablyImpossibleException();
            };
        }
    }
}
