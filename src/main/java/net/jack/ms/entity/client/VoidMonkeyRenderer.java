package net.jack.ms.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jack.ms.ModifiedSurvival;
import net.jack.ms.entity.custom.MonkeyEntity;
import net.jack.ms.entity.custom.VoidMonkeyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VoidMonkeyRenderer extends GeoEntityRenderer<VoidMonkeyEntity> {
    public VoidMonkeyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new VoidMonkeyModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(VoidMonkeyEntity instance) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "textures/entity/monkey/void_monkey.png");
    }
    @Override
    public RenderType getRenderType(VoidMonkeyEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        if(animatable.isBaby()) {
          stack.scale(0.566F, 0.647F, 0.566F);
        } else {
            stack.scale(1.05F, 1.2F, 1.05F);
        }

        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}