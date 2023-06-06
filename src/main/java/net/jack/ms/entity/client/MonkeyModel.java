package net.jack.ms.entity.client;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.entity.custom.MonkeyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
public class MonkeyModel extends AnimatedGeoModel<MonkeyEntity> {
    @Override
    public ResourceLocation getModelLocation(MonkeyEntity object) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "geo/monkey.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MonkeyEntity object) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "textures/entity/monkey/monkey.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MonkeyEntity animatable) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "animations/monkey.animation.json");
    }
}