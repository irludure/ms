package net.jack.ms.entity.custom;

import net.jack.ms.item.ModItems;
import net.jack.ms.sound.ModSounds;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.UUID;

import static net.jack.ms.entity.ModEntityTypes.ELEPHANT;
import static net.jack.ms.entity.ModEntityTypes.MONKEY;

public class EagleEntity extends Animal implements IAnimatable {
    // LivingEntity mob = (LivingEntity) (Mob) this;

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(60, 120);
    private int remainingPersistentAngerTime;
    @javax.annotation.Nullable
    private UUID persistentAngerTarget;
    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.BREAD);
    private AnimationFactory factory = new AnimationFactory(this);

    public static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(EagleEntity.class, EntityDataSerializers.BOOLEAN);
    public EagleEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 15.0f)
                .add(Attributes.ATTACK_SPEED, 1.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ATTACK_KNOCKBACK, 2f).build();
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        //this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class,true));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25D, false));
        // this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0D, 20.0F, 5.0F, false));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob mob) {
        return ELEPHANT.get().create(serverLevel);
    }


    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == ModItems.BANANA.get();
    }
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        System.out.println("Attempting predicate...");
        if (event.isMoving()) {
            System.out.println("IsMoving = true!");
            if (getTarget() != null) {
                System.out.println("Target != null!");
                event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
            } else {
                System.out.println("Target is null...");
                event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            }
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
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
        this.playSound(SoundEvents.GRASS_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.elephant_ambient.get();
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
            return super.mobInteract(player, hand);
        }

        // if (item == itemForTaming && !isTame()) {
        // if (this.level.isClientSide) {
        //  return InteractionResult.CONSUME;
        //} else {
        //if (!player.getAbilities().instabuild) {
        //  itemstack.shrink(1);
        //}

        //      if (!ForgeEventFactory.onAnimalTame(this, player)) {
        //      if (!this.level.isClientSide) {
        //        super.tame(player);
        //      this.navigation.recomputePath();
        //    this.setTarget(null);
        //  this.level.broadcastEntityEvent(this, (byte)7);
        //  setSitting(true);
        //  }
        //}

        // return InteractionResult.SUCCESS;
        // }
        /// }

        // if(isTame() && !this.level.isClientSide && hand == InteractionHand.MAIN_HAND) {
        //setSitting(!isSitting());
        //  return InteractionResult.SUCCESS;
        //}

        //if (itemstack.getItem() == itemForTaming) {
        //  return InteractionResult.PASS;
        //}

        return super.mobInteract(player, hand);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }
    // public void setTame(boolean tamed) {
    //  super.setTame(tamed);
    //if (tamed) {
    //  getAttribute(Attributes.MAX_HEALTH).setBaseValue(140.0D);
    //getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(9D);
    //getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)0.2f);
    //} else {
    //  getAttribute(Attributes.MAX_HEALTH).setBaseValue(140.0D);
    //getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(9D);
    // getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)0.2f);
    // }
    //}

    @Override
    public Team getTeam() {
        return super.getTeam();
    }

    public boolean canBeLeashed(Player player) {
        return false;
    }
}
