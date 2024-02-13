package net.jack.ms.bentityrecipes.custom;

import net.jack.ms.bentityrecipes.RadiationRecipe;
import net.jack.ms.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class UraniumRadiationTableRecipe extends RadiationRecipe {
    public static Item fuel = Items.LAVA_BUCKET;
    public static Item top = ModItems.TITANIUM.get();
    public static Item bottom = ModItems.TITANIUM.get();
    public static Item result = ModItems.URANIUM.get();
}
