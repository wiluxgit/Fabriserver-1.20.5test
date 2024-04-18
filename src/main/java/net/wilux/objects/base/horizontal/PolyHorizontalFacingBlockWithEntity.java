package net.wilux.objects.base.horizontal;

import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PolyHorizontalFacingBlockWithEntity extends BlockWithEntity implements PolymerTexturedBlock {
    // BlockWithEntity methods
    @Nullable @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    // HorizontalFacingBlock methods
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final MapCodec<? extends PolyHorizontalFacingBlockWithEntity> CODEC = createCodec(PolyHorizontalFacingBlockWithEntity::new);

    private PolyHorizontalFacingBlockWithEntity(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }
    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override protected MapCodec<? extends PolyHorizontalFacingBlockWithEntity> getCodec() {
        return CODEC;
    }
    @Override public BlockState getPlacementState(ItemPlacementContext ctx) {
        var facing = ctx.getHorizontalPlayerFacing().getOpposite();
        return this.getDefaultState().with(FACING, facing);
    }

    // Polymer methods
    protected Map<Direction, BlockState> dirPolymerState;
    public PolyHorizontalFacingBlockWithEntity(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
        this(settings);
        dirPolymerState = new HashMap<>();
        dirPolymerState.put(Direction.NORTH, northPolymerState);
        dirPolymerState.put(Direction.EAST, eastPolymerState);
        dirPolymerState.put(Direction.SOUTH, southPolymerState);
        dirPolymerState.put(Direction.WEST, westPolymerState);
    }

    @Override
    public Block getPolymerBlock(BlockState state) {
        return dirPolymerState.get(Direction.NORTH).getBlock();
    }
    @Override
    public BlockState getPolymerBlockState(BlockState state, ServerPlayerEntity player) {
        return dirPolymerState.get(state.get(FACING));
    }
}
