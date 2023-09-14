package net.jack.ms.vehicle;

import net.jack.ms.entity.ModEntityTypes;
import net.jack.ms.entity.custom.FastMinecart;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ReplaceMinecarts {
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof Minecart)) {
            return;
        }

        Minecart minecart = (Minecart) event.getEntity();

        // Ensure the minecart is alive and it's not a FastMinecart.
        if (!minecart.isAlive() || minecart instanceof FastMinecart) {
            return;
        }

        System.out.println("Running minecart procedure...");

        // Replace the old minecart with the new FastMinecart.
        FastMinecart newMinecart = new FastMinecart(ModEntityTypes.FAST_MINECART.get(), minecart.level);
        newMinecart.setPos(minecart.getX(), minecart.getY(), minecart.getZ());

        // Uncomment the below if you want to preserve momentum or any other properties.
        // newMinecart.setDeltaMovement(minecart.getDeltaMovement());

        // Add the new minecart to the world.
        event.getWorld().addFreshEntity(newMinecart);

        // Remove the old minecart.
        minecart.setPos(99999, 99999, 99999);
        minecart.destroy(DamageSource.ANVIL);
        minecart.shouldRender(99999, 99999, 99999);

        // Force a client-side block update around the old minecart's position.
        BlockPos pos = minecart.blockPosition();
        minecart.level.sendBlockUpdated(pos, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), 3);
    }
}
