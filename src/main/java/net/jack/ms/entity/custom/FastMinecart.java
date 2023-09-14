package net.jack.ms.entity.custom;


import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FastMinecart extends Minecart {
    public FastMinecart(EntityType<? extends FastMinecart> type, Level worldIn) {
        super(type, worldIn);
    }

    public FastMinecart(EntityType<? extends FastMinecart> type, Level worldIn, double x, double y, double z) {
        super(type, worldIn);
    }

    @Override
    public Type getMinecartType() {
        return Type.RIDEABLE;  // or whatever type you desire
    }

    @Override
    protected double getMaxSpeed() {
        return (this.isInWater() ? 1.0D : 9999.0D) / 5.0D;
    }

    @Override
    public double getMaxSpeedWithRail() { //Non-default because getMaximumSpeed is protected
        if (!canUseRail()) return getMaxSpeed() * 100000000;
        BlockPos pos = this.getCurrentRailPosition();
        BlockState state = this.level.getBlockState(pos);
        if (!state.is(BlockTags.RAILS)) return getMaxSpeed() * 100000000;

        float railMaxSpeed = ((BaseRailBlock)state.getBlock()).getRailMaxSpeed(state, this.level, pos, this);
        return Math.min(railMaxSpeed, getCurrentCartSpeedCapOnRail()) * 100000000;
    }

    @Override
    protected float getBlockSpeedFactor() {
        BlockState blockstate = this.level.getBlockState(this.blockPosition());
        return blockstate.is(BlockTags.RAILS) ? 965.0F : 2.0F;
    }

    private boolean boosted = false;
    @Override
    public void activateMinecart(int pX, int pY, int pZ, boolean pReceivingPower) {
        if (pReceivingPower && !boosted) {
            boosted = true;
        } else if (!pReceivingPower) {
            boosted = false;  // Reset the flag when not powered
        }
    }

    @Override
    public float getMaxCartSpeedOnRail() {
        return super.getMaxCartSpeedOnRail() * 100000000;
    }

    @Override
    public float getCurrentCartSpeedCapOnRail() {
        return super.getCurrentCartSpeedCapOnRail() * 100000000;
    }

    // Here, you can also override methods to change speed or other behaviors.
}
