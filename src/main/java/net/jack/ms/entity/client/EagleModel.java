package net.jack.ms.entity.client;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.entity.custom.TigerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EagleModel extends AnimatedGeoModel<TigerEntity> {
    @Override
    public ResourceLocation getModelLocation(TigerEntity object) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "geo/eagle.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TigerEntity object) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "textures/entity/eagle/eagle.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TigerEntity animatable) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "animations/eagle.animation.json");
    }
}