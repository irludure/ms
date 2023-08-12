package net.jack.ms.ai;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.jack.ms.ai.goal.SlaveGoToOwner;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

class CNBTRunnable implements Runnable {
    private Villager villager;
    private EntityJoinWorldEvent event;

    public CNBTRunnable(EntityJoinWorldEvent event) {
        this.villager = (Villager) event.getEntity();
        this.event = event;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();
            int data = villager.serializeNBT().getInt("Air");
            System.out.println(data);
            if (data == 420) {
                VillagerProfession profession = VillagerProfession.FARMER;
                villager.setVillagerData(villager.getVillagerData().setProfession(profession));
                villager.goalSelector.addGoal(1, new SlaveGoToOwner(villager, 0.85D, 20));
            }
        }
    }
}

public class SlaveVillager {
    private int tick = 0;

    @SubscribeEvent
    public void onTick(TickEvent event) {
        tick += 1;
    }


    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Villager) {
            System.out.println("Running villager procedure...");
            CNBTRunnable thread = new CNBTRunnable(event);
            thread.run();
        }
    }

}
