package net.jack.ms;

import com.mojang.logging.LogUtils;
import net.jack.ms.ai.SlaveVillager;
import net.jack.ms.block.ModBlocks;
import net.jack.ms.block.entity.ModBlockEntities;
import net.jack.ms.entity.ModEntityTypes;
import net.jack.ms.entity.client.*;
import net.jack.ms.item.ModItems;
import net.jack.ms.screen.ModMenuTypes;
import net.jack.ms.screen.RadiationTableScreen;
import net.jack.ms.sound.ModSounds;
import net.jack.ms.util.world.feature.OreFeature;
import net.jack.ms.vehicle.ReplaceMinecarts;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModifiedSurvival.MOD_ID)
public class ModifiedSurvival
{
    public static final String MOD_ID = "ms";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModifiedSurvival()
    {
        // Register the setup method for modloading
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEntityTypes.register(modEventBus);


        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);




        GeckoLib.initialize();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(OreFeature::onBiomeLoadingEvent);
        MinecraftForge.EVENT_BUS.register(new SlaveVillager());
        MinecraftForge.EVENT_BUS.register(new ReplaceMinecarts());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntityTypes.RACCOON.get(), RaccoonRenderer::new);
        EntityRenderers.register(ModEntityTypes.MONKEY.get(), MonkeyRenderer::new);
        EntityRenderers.register(ModEntityTypes.TIGER.get(), TigerRenderer::new);
        EntityRenderers.register(ModEntityTypes.ELEPHANT.get(), ElephantRenderer::new);
        EntityRenderers.register(ModEntityTypes.VOID_MONKEY.get(), VoidMonkeyRenderer::new);
        EntityRenderers.register(ModEntityTypes.FAST_MINECART.get(),
                context -> new MinecartRenderer<>(context, ModelLayers.MINECART));
        EntityRenderers.register(ModEntityTypes.MOTORBOAT.get(), MotorboatRenderer::new);

        MenuScreens.register(ModMenuTypes.RADIATION_TABLE_MENU.get(), RadiationTableScreen::new);


    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            OreFeature.registerOreFeatures();
            SpawnPlacements.register(ModEntityTypes.MONKEY.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.RACCOON.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(ModEntityTypes.TIGER.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkMobSpawnRules);
            SpawnPlacements.register(ModEntityTypes.ELEPHANT.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING,
                    Animal::checkAnimalSpawnRules);
        });
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("ms: Namespace Registered");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
