package net.jack.ms.entity.custom;

import net.jack.ms.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class MotorboatEntity extends Boat {

    private static final EntityDataAccessor<Integer> DATA_ID_BUBBLE_TIME = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);
    private float invFriction;
    private Status oldStatus;
    private Status status;
    private float landFriction;
    private double waterLevel;

    public MotorboatEntity(EntityType<? extends Boat> p_38290_, Level p_38291_) {
        super(p_38290_, p_38291_);
    }

    public MotorboatEntity(Level p_38293_, double p_38294_, double p_38295_, double p_38296_) {
        this(ModEntityTypes.MOTORBOAT.get(), p_38293_);
        this.setPos(p_38294_, p_38295_, p_38296_);
        this.xo = p_38294_;
        this.yo = p_38295_;
        this.zo = p_38296_;
    }
    @Override
    public void lavaHurt() {
    }

    @Override
    public boolean isInLava() {
        return false;
    }

    @Override
    public void push(Entity pEntity) {
        if (pEntity instanceof MotorboatEntity) {
            pEntity.shouldRender(0, 100000, 0);
            pEntity.kill();
            return;
        }
        if (pEntity instanceof Boat) {
            if (pEntity.getBoundingBox().minY < this.getBoundingBox().maxY) {
                super.push(pEntity);
            }
        } else if (pEntity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.push(pEntity);
        }

    }

    private int getBubbleTime() {
        return this.entityData.get(DATA_ID_BUBBLE_TIME);
    }

    private void setBubbleTime(int pTicks) {
        this.entityData.set(DATA_ID_BUBBLE_TIME, pTicks);
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void tick() {
        FluidState fluid = this.level.getFluidState(this.blockPosition());
        String fluidStr = fluid.getType().getRegistryName().toString();
        if (fluidStr.equals("minecraft:lava") || fluidStr.equals("minecraft:flowing_lava")) {
            this.setDeltaMovement(this.getDeltaMovement().x, 0.04, this.getDeltaMovement().z);
            List<Entity> passengers2 = this.getPassengers();
        }
        super.tick();
    }


    private Boat.Status getStatus() {
        Boat.Status boat$status = this.isUnderwater();
        if (boat$status != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return boat$status;
        } else if (this.getBlockStateOn().is(Blocks.LAVA) || this.getBlockStateOn().is(Blocks.WATER)) {
            return Boat.Status.IN_WATER;
        } else {
            float f = this.getGroundFriction();
            if (f > 0.0F) {
                this.landFriction = f;
                return Boat.Status.ON_LAND;
            } else {
                return Boat.Status.IN_AIR;
            }
        }
    }

    @Override
    protected float getBlockSpeedFactor() {
        BlockState blockstate = this.level.getBlockState(this.blockPosition());
        float f = blockstate.getBlock().getSpeedFactor();
        if (!blockstate.is(Blocks.WATER) && !blockstate.is(Blocks.BUBBLE_COLUMN) && !blockstate.is(Blocks.LAVA)) {
            float ret = (double) f == 1.0D ? this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getBlock().getSpeedFactor() : f;
            return (ret * 1.05f);
        } else {
            if (blockstate.is(Blocks.LAVA)) {
                f = Blocks.WATER.getSpeedFactor();
            }
            return (f * 1.05f);
        }
    }

    @Nullable
    private Boat.Status isUnderwater() {
        AABB aabb = this.getBoundingBox();
        double d0 = aabb.maxY + 0.001D;
        int i = Mth.floor(aabb.minX);
        int j = Mth.ceil(aabb.maxX);
        int k = Mth.floor(aabb.maxY);
        int l = Mth.ceil(d0);
        int i1 = Mth.floor(aabb.minZ);
        int j1 = Mth.ceil(aabb.maxZ);
        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
                for(int i2 = i1; i2 < j1; ++i2) {
                    blockpos$mutableblockpos.set(k1, l1, i2);
                    FluidState fluidstate = this.level.getFluidState(blockpos$mutableblockpos);
                    if ( (fluidstate.is(FluidTags.WATER) || fluidstate.is(FluidTags.LAVA)) && d0 < (double)((float)blockpos$mutableblockpos.getY() + fluidstate.getHeight(this.level, blockpos$mutableblockpos))) {
                        if (!fluidstate.isSource()) {
                            return Boat.Status.UNDER_FLOWING_WATER;
                        }

                        flag = true;
                    }
                }
            }
        }

        return flag ? Boat.Status.UNDER_WATER : null;
    }
}
