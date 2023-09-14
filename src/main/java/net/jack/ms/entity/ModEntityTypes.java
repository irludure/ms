package net.jack.ms.entity;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.entity.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, ModifiedSurvival.MOD_ID);

    public static final RegistryObject<EntityType<RaccoonEntity>> RACCOON =
            ENTITY_TYPES.register("raccoon",
                    () -> EntityType.Builder.of(RaccoonEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.6f)
                            .build(new ResourceLocation(ModifiedSurvival.MOD_ID, "raccoon").toString()));
    public static final RegistryObject<EntityType<MonkeyEntity>> MONKEY =
            ENTITY_TYPES.register("monkey",
                    () -> EntityType.Builder.of(MonkeyEntity::new, MobCategory.CREATURE)
                            .sized(1.05f, 1.2f)
                            .build(new ResourceLocation(ModifiedSurvival.MOD_ID, "monkey").toString()));

    public static final RegistryObject<EntityType<VoidMonkeyEntity>> VOID_MONKEY =
            ENTITY_TYPES.register("void_monkey",
                    () -> EntityType.Builder.of(VoidMonkeyEntity::new, MobCategory.CREATURE)
                            .sized(1.05f, 1.2f)
                            .build(new ResourceLocation(ModifiedSurvival.MOD_ID, "void_monkey").toString()));
    public static final RegistryObject<EntityType<TigerEntity>> TIGER =
            ENTITY_TYPES.register("tiger",
                    () -> EntityType.Builder.of(TigerEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(ModifiedSurvival.MOD_ID, "tiger").toString()));
    public static final RegistryObject<EntityType<ElephantEntity>> ELEPHANT =
            ENTITY_TYPES.register("elephant",
                    () -> EntityType.Builder.of(ElephantEntity::new, MobCategory.CREATURE)
                            .sized(3.2f, 4f)
                            .build(new ResourceLocation(ModifiedSurvival.MOD_ID, "elephant").toString()));

    public static final RegistryObject<EntityType<FastMinecart>> FAST_MINECART =
            ENTITY_TYPES.register("fast_minecart",
                    () -> EntityType.Builder.<FastMinecart>of(FastMinecart::new, MobCategory.MISC)
                            .sized(0.98F, 0.7F)  // you can adjust these sizes
                            .setTrackingRange(80)
                            .setUpdateInterval(3)
                            .setShouldReceiveVelocityUpdates(true)
                            .build(new ResourceLocation(ModifiedSurvival.MOD_ID, "fast_minecart").toString())
            );

    public static final RegistryObject<EntityType<MotorboatEntity>> MOTORBOAT =
            ENTITY_TYPES.register("motorboat",
                    () -> EntityType.Builder.<MotorboatEntity>of(MotorboatEntity::new, MobCategory.MISC)
                            .sized(1.375F, 0.5625F)  // you can adjust these sizes
                            .setTrackingRange(10)
                            .setUpdateInterval(3)
                            .setShouldReceiveVelocityUpdates(true)
                            .build(new ResourceLocation(ModifiedSurvival.MOD_ID, "motorboat").toString())
            );
    // public static final EntityType<Boat> BOAT = register("boat", EntityType.Builder.<Boat>of(Boat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}