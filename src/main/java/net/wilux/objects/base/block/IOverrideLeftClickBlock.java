package net.wilux.objects.base.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface IOverrideLeftClickBlock {
    ActionResult leftClick(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction);

    static ActionResult eventHandler(PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) {
        if (player.isSpectator()) return ActionResult.PASS;
        BlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof IOverrideLeftClickBlock clickable)) return ActionResult.PASS;

        return clickable.leftClick(player, world, hand, pos, direction);
    }
}
