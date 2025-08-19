package github.qbic.darkflame.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.qbic.darkflame.client.model.SmilerModel;
import github.qbic.darkflame.entity.HerobrineEntity;
import github.qbic.darkflame.entity.SmilerEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class SmilerRenderer extends MobRenderer<SmilerEntity, LivingEntityRenderState, SmilerModel> {
    private SmilerEntity entity = null;
    
    public SmilerRenderer(EntityRendererProvider.Context context) {
        super(context, new SmilerModel(context.bakeLayer(SmilerModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new RenderLayer<>(this) {
            final ResourceLocation LAYER_TEXTURE = texture();

            @Override
            public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, LivingEntityRenderState state, float headYaw, float headPitch) {
                VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(LAYER_TEXTURE));
                this.getParentModel().renderToBuffer(poseStack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(state, 0));
            }
        });
    }

    @Override
    public void extractRenderState(SmilerEntity entity, LivingEntityRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        this.entity = entity;
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState renderState) {
        return texture();
    }
    
    public ResourceLocation texture() {
        return ResourceLocation.parse("dark_flame:textures/entities/smiler_angry.png");
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
