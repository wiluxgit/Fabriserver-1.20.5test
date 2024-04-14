package net.wilux.objects.base;

import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.wilux.objects.xterm.XTerm;

import java.util.HashMap;
import java.util.Map;

public class PolyHorizontalFacingBlock extends HorizontalFacingBlock implements PolymerTexturedBlock {
    // HorizontalFacingBlock methods
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final MapCodec<? extends PolyHorizontalFacingBlock> CODEC = createCodec(PolyHorizontalFacingBlock::new);

    private PolyHorizontalFacingBlock(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }
    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
    @Override public BlockState getPlacementState(ItemPlacementContext ctx) {
        var facing = ctx.getHorizontalPlayerFacing().getOpposite();
        return this.getDefaultState().with(FACING, facing);
    }

    // Polymer methods
    protected Map<Direction, BlockState> dirPolymerState;
    public PolyHorizontalFacingBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
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
