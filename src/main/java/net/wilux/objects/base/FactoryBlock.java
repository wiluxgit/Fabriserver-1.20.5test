package net.wilux.objects.base;

import net.minecraft.block.BlockState;
import net.wilux.objects.base.horizontal.PolyHorizontalFacingBlock;

public class FactoryBlock extends PolyHorizontalFacingBlock implements ElectricityConnector {
    public FactoryBlock(Settings settings, BlockState northPolymerState, BlockState eastPolymerState, BlockState southPolymerState, BlockState westPolymerState) {
        super(settings, northPolymerState, eastPolymerState, southPolymerState, westPolymerState);
    }
}