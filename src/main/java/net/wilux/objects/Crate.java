package net.wilux.objects;

import net.minecraft.block.BlockState;
import net.wilux.objects.base.horizontal.PolyHorizontalFacingBlock;

public class Crate extends PolyHorizontalFacingBlock {
    public Crate(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
        super(settings, northPolymerState, eastPolymerState, southPolymerState, westPolymerState);
    }
}
