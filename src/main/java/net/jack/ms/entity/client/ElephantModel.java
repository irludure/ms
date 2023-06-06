package net.jack.ms.entity.client;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.entity.custom.ElephantEntity;
import net.jack.ms.entity.custom.TigerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ElephantModel extends AnimatedGeoModel<ElephantEntity> {
    @Override
    public ResourceLocation getModelLocation(ElephantEntity object) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "geo/elephant.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ElephantEntity object) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "textures/entity/elephant/elephant.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ElephantEntity animatable) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "animations/elephant.animation.json");
    }
}