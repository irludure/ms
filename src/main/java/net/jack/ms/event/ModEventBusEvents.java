package net.jack.ms.event;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.entity.ModEntityTypes;
import net.jack.ms.entity.client.MonkeyRenderer;
import net.jack.ms.entity.custom.ElephantEntity;
import net.jack.ms.entity.custom.MonkeyEntity;
import net.jack.ms.entity.custom.RaccoonEntity;
import net.jack.ms.entity.custom.TigerEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = ModifiedSurvival.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>>
                                                           event) {
        event.getRegistry().registerAll(
        );
    }

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.RACCOON.get(), RaccoonEntity.setAttributes());
        event.put(ModEntityTypes.MONKEY.get(), MonkeyEntity.setAttributes());
        event.put(ModEntityTypes.TIGER.get(), TigerEntity.setAttributes());
        event.put(ModEntityTypes.ELEPHANT.get(), ElephantEntity.setAttributes());
    }
}