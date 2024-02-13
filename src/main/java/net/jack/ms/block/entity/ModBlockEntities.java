package net.jack.ms.block.entity;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.block.ModBlocks;
import net.jack.ms.block.entity.custom.RadiationTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ModifiedSurvival.MOD_ID);

    public static final RegistryObject<BlockEntityType<RadiationTableBlockEntity>> RADIATION_TABLE =
            BLOCK_ENTITIES.register("radiation_table", () ->
                    BlockEntityType.Builder.of(RadiationTableBlockEntity::new,
                            ModBlocks.RADIATION_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
