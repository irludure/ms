package net.jack.ms.world.feature;

import net.jack.ms.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreFeature {
    public static Holder<PlacedFeature> OVERWORLD_OREGEN;
    public static void registerOreFeatures() {
        OreConfiguration overworldConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.TITANIUM_REMNANT.get().defaultBlockState(), 4);
        OVERWORLD_OREGEN = registerPlacedOreFeature("overworld_titanium_remnant", new ConfiguredFeature<>(Feature.ORE, overworldConfig),
                CountPlacement.of(2), // Veins per chunk
                InSquarePlacement.spread(),
                BiomeFilter.biome(), // Biome filter
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-45), VerticalAnchor.absolute(0))); // -45 is the lowest Y-level titanium can spawn at, and 0 is the highest.
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedOreFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
    }

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.NETHER) {
        } else if (event.getCategory() == Biome.BiomeCategory.THEEND) {
        } else {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_OREGEN);
        }
    }
}