// Tigers not ready for implementation yet due to the inability to make it only hostile at night



package net.jack.ms.entity.custom;

import net.jack.ms.entity.ModEntityTypes;
import net.jack.ms.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.NoteBlockEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.UUID;

import static net.jack.ms.entity.ModEntityTypes.MONKEY;
import static net.jack.ms.entity.ModEntityTypes.TIGER;

public class TigerEntity extends TamableAnimal implements IAnimatable {
    private static Player owner;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;
    private static final Ingredient FOOD_ITEMS = Ingredient.of(ModItems.BANANA.get());
    private AnimationFactory factory = new AnimationFactory(this);

    public static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(TigerEntity.class, EntityDataSerializers.BOOLEAN);
    public TigerEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }


    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 70.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0f)
                .add(Attributes.ATTACK_SPEED, 1.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.23f).build();
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(4, new TigerAttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new TigerTargetGoal<>(this, Player.class));
        this.targetSelector.addGoal(2, new TigerTargetGoal<>(this, Monster.class));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
    }

    static class TigerTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public TigerTargetGoal(TigerEntity entity, Class<T> targetEntity) {
            super(entity, targetEntity, true);
        }}

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        return TIGER.get().create(serverLevel);
    }


    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == Items.MUTTON;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tiger.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tiger.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_STRAY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.DOLPHIN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();


        Item itemForTaming = (ModItems.BANANA.get());


        if (isFood(itemstack)) {
            owner = player;
            return super.mobInteract(player, hand);
        }

         if (item == itemForTaming && !isTame()) {
         if (this.level.isClientSide) {
          return InteractionResult.CONSUME;
        } else {
        if (!player.getAbilities().instabuild) {
          itemstack.shrink(1);
        }

              if (!ForgeEventFactory.onAnimalTame(this, player)) {
              if (!this.level.isClientSide) {
                super.tame(player);
              this.navigation.recomputePath();
            this.setTarget(null);
          this.level.broadcastEntityEvent(this, (byte)7);
          setInSittingPose(true);
          }
        }

         return InteractionResult.SUCCESS;
         }
         }

         if(isTame() && !this.level.isClientSide && hand == InteractionHand.MAIN_HAND) {
        setInSittingPose(!isInSittingPose());
          return InteractionResult.SUCCESS;
        }

        if (itemstack.getItem() == itemForTaming) {
          return InteractionResult.PASS;
        }

        return super.mobInteract(player, hand);
    }


    class TigerAttackGoal extends MeleeAttackGoal {
        public TigerAttackGoal(TigerEntity p_33822_) {
            super(p_33822_, 1.5D, true);
        }
        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return super.canUse() && !this.mob.isVehicle() && !super.mob.level.isDay();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            if (super.mob.level.isDay()) {
                this.mob.setTarget((LivingEntity)null);
                return false;
            } else {
                return super.canContinueToUse();
            }
        }

        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return (double)(6.0F + pAttackTarget.getBbWidth());
        }
    }

    //@Override
    //public Team getTeam() {
        //return super.getTeam();
    //}

    public boolean canBeLeashed(Player player) {
        return false;
    }
}
