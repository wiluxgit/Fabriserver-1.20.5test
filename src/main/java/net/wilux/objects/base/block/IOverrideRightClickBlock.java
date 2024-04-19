package net.wilux.objects.base.block;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface IOverrideRightClickBlock {
    ActionResult rightClick(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult);

    static ActionResult eventHandler(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (player.isSpectator()) return ActionResult.PASS;
        BlockState state = world.getBlockState(hitResult.getBlockPos());
        if (!(state.getBlock() instanceof IOverrideRightClickBlock clickable)) return ActionResult.PASS;

        return clickable.rightClick(player, world, hand, hitResult);
    }
}
