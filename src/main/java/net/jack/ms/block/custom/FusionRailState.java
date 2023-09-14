package net.jack.ms.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.RailState;
import net.minecraft.world.level.block.state.BlockState;

public class FusionRailState extends RailState {
    public final BlockPos pos;
    public FusionRailState(Level pLevel, BlockPos pPos, BlockState pState, BlockPos pos) {
        super(pLevel, pPos, pState);
        this.pos = pos;
        this.level = pLevel;
    }
    public Level level = null;
    public boolean hasRail(BlockPos pPos) {
        return BaseRailBlock.isRail(this.level, pPos) || BaseRailBlock.isRail(this.level, pPos.above()) || BaseRailBlock.isRail(this.level, pPos.below());
    }
    public int countPotentialConnections() {
        int i = 0;

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (this.hasRail(this.pos.relative(direction))) {
                ++i;
            }
        }

        return i;
    }
}
