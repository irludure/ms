package net.jack.ms.entity.client;

import net.jack.ms.ModifiedSurvival;
import net.jack.ms.entity.custom.MonkeyEntity;
import net.jack.ms.entity.custom.VoidMonkeyEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VoidMonkeyModel extends AnimatedGeoModel<VoidMonkeyEntity> {
    @Override
    public ResourceLocation getModelLocation(VoidMonkeyEntity object) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "geo/monkey.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(VoidMonkeyEntity object) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "textures/entity/monkey/void_monkey.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(VoidMonkeyEntity animatable) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "animations/monkey.animation.json");
    }
}