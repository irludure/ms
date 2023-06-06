package net.jack.ms.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jack.ms.ModifiedSurvival;
import net.jack.ms.entity.custom.ElephantEntity;
import net.jack.ms.entity.custom.TigerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ElephantRenderer extends GeoEntityRenderer<ElephantEntity> {
    public ElephantRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ElephantModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(ElephantEntity instance) {
        return new ResourceLocation(ModifiedSurvival.MOD_ID, "textures/entity/elephant/elephant.png");
    }
    @Override
    public RenderType getRenderType(ElephantEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        if(animatable.isBaby()) {
            stack.scale(1F, 1F, 1F);
        } else {
            stack.scale(2F, 2F, 2F);
            // 8.75, 18.75
        }

        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}