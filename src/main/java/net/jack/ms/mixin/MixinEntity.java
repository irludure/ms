package net.jack.ms.mixin;

import net.jack.ms.vehicle.VehicleMechanicGetter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Entity.class)
public class MixinEntity {
    @Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/world/entity/Entity;isOnFire()Z", cancellable = true)
    private void isOnFire(CallbackInfoReturnable info) {
        Entity self = (Entity)((Object)this);
        if (self.isPassenger() && VehicleMechanicGetter.isVehicleIndustrial(self.getVehicle())) {
            self.setSharedFlagOnFire(false);
            self.clearFire();
            info.setReturnValue(false);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/world/entity/Entity;isInLava()Z", cancellable = true)
    private void isInLava(CallbackInfoReturnable info) {
        Entity self = (Entity)((Object)this);
        if (self.isPassenger() && VehicleMechanicGetter.isVehicleIndustrial(self.getVehicle())) {
            self.setSharedFlagOnFire(false);
            self.clearFire();
            info.setReturnValue(false);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/world/entity/Entity;lavaHurt()V", cancellable = true)
    private void lavaHurt(CallbackInfo info) {
        info.cancel();
        Entity self = (Entity)((Object)this);
        if (!(self.isPassenger() && VehicleMechanicGetter.isVehicleIndustrial(self.getVehicle()))) {
                if (!self.fireImmune()) {
                    self.setSecondsOnFire(15);
                    if (self.hurt(DamageSource.LAVA, 4.0F)) {
                        self.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + new Random().nextFloat() * 0.4F);
                    }
                }
            }
    }



    @Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", cancellable = true)
    public void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable info) {
        Entity self = (Entity) ((Object) this);
        if (self.isPassenger() && VehicleMechanicGetter.isVehicleIndustrial(self.getVehicle())) {
            if (pSource.isFire()) {
                self.setSharedFlagOnFire(false);
                self.clearFire();
                info.cancel();
            }
        }
    }
}
