package net.jack.ms.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;


public class SlaveGoToOwner extends Goal {

    public static double getDist(BlockPos pos1, BlockPos pos2) {
        // Getting the squared distance between the two BlockPos objects
        double squaredDistance = pos1.distSqr(pos2);

        // Returning the square root of the squared distance to get the linear distance
        return Math.sqrt(squaredDistance);
    }
    private final Mob entity;
    private final double speed;
    private final float maxDistance;
    private LivingEntity target;

    public SlaveGoToOwner(Mob entity, double speed, float maxDistance) {
        this.entity = entity;
        this.speed = speed;
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean canUse() {
        Player playerentity = this.entity.level.getNearestPlayer(this.entity, 300.0D);
        if (playerentity != null) {
            boolean con = getDist(this.entity.getOnPos(), playerentity.getOnPos()) > 20;
            boolean con2 = getDist(this.entity.getOnPos(), playerentity.getOnPos()) < 100;
            if (con && con2) {
                this.target = playerentity;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void start() {
        this.entity.getNavigation().moveTo(this.target, this.speed);
    }

    private boolean isChasingOwner = false;
    @Override
    public void tick() {
        if (canContinueToUse()) {
            this.entity.getNavigation().moveTo(this.target, this.speed);
            isChasingOwner = true;
        } else {
            this.entity.getNavigation().setSpeedModifier(this.entity.getSpeed());
            if (isChasingOwner) {
                this.entity.getNavigation().stop();
                isChasingOwner = false;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        Player playerentity = this.entity.level.getNearestPlayer(this.entity, 100.0D);
        if (playerentity != null) {
            boolean con = getDist(this.entity.getOnPos(), playerentity.getOnPos()) > 20;
            if (con) {
                this.target = playerentity;
                return true;
            }
            this.target = null;
            return false;
        }
        this.target = null;
        return false;
    }
}
