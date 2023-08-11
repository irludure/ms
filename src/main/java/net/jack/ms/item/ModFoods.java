package net.jack.ms.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties BANANA = new FoodProperties.Builder().nutrition(6).saturationMod(0.3f).build();
    public static final FoodProperties HOMEOSTASIS_ACCELERATOR = new FoodProperties.Builder().nutrition(-6).saturationMod(0f).build();
}
