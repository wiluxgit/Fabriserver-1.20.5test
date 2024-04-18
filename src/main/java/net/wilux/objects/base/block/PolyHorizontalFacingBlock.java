package net.wilux.objects.base.block;

import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Map;

public class PolyHorizontalFacingBlock extends Block implements PolymerTexturedBlock {
    // HorizontalFacingBlock methods
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private final MapCodec<? extends PolyHorizontalFacingBlock> CODEC;

    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }
    @Override protected MapCodec<? extends PolyHorizontalFacingBlock> getCodec() {
        return CODEC;
    }
    @Override public BlockState getPlacementState(ItemPlacementContext ctx) {
        var facing = ctx.getHorizontalPlayerFacing().getOpposite();
        return this.getDefaultState().with(FACING, facing);
    }

    // Polymer methods
    protected Map<Direction, BlockState> dirPolymerState;
    public PolyHorizontalFacingBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
        super(settings);
        this.CODEC = createCodec((Settings __) -> this);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
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
