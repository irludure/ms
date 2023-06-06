package net.jack.ms.item;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {

    public static Tier TITANIUM;
    static {
        TITANIUM = TierSortingRegistry.registerTier(
                new ForgeTier(5,2500,25f,3f,10, ModTags.Blocks.NEEDS_TITANIUM_TOOL, () -> Ingredient.of(ModItems.TITANIUM.get())),
                new ResourceLocation(ModifiedSurvival.MOD_ID, "titanium"), List.of(Tiers.NETHERITE), List.of()
        );
    }
}
