package net.jack.ms.item.custom;

import ca.weblite.objc.Proxy;
import net.jack.ms.entity.ModEntityTypes;
import net.jack.ms.entity.custom.MotorboatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class IndustrialBoatItem extends Item {
    public IndustrialBoatItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        HitResult hitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY);
        if (hitresult.getType() == HitResult.Type.BLOCK) {
            MotorboatEntity boat = new MotorboatEntity(pLevel, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z);
            boat.setYRot(pPlayer.getYRot());
            if (!pLevel.noCollision(boat, boat.getBoundingBox())) {
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            } else {
                if (!pLevel.isClientSide) {
                    pLevel.addFreshEntity(boat);
                    pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, new BlockPos(hitresult.getLocation()));
                    if (!pPlayer.getAbilities().instabuild) {
                        pPlayer.getItemInHand(pUsedHand).shrink(1);
                    }
                }

                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
            }
        } else {
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }
    }
}
