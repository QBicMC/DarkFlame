package github.qbic.darkflame.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.qbic.darkflame.client.model.animations.CorruptedAnimation;
import github.qbic.darkflame.client.model.animations.SmileyAnimation;
import github.qbic.darkflame.entity.CorruptedEntity;
import github.qbic.darkflame.entity.HerobrineEntity;
import github.qbic.darkflame.entity.SmileyEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class CorruptedRenderer extends HumanoidMobRenderer<CorruptedEntity, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    public CorruptedRenderer(EntityRendererProvider.Context context) {
        super(context, new AnimatedModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new AnimatedModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new AnimatedModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getEquipmentRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(HumanoidRenderState renderState) {
        return ResourceLocation.parse("dark_flame:textures/entities/corrupted.png");
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public void extractRenderState(CorruptedEntity entity, HumanoidRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        if (this.model instanceof AnimatedModel) {
            ((AnimatedModel) this.model).setEntity(entity);
        }
    }

    public static class AnimatedModel extends HumanoidModel<HumanoidRenderState> {
        private CorruptedEntity entity = null;

        public AnimatedModel(ModelPart root) {
            super(root);
        }

        public void setEntity(CorruptedEntity entity) {
            this.entity = entity;
        }

        @Override
        public void setupAnim(HumanoidRenderState state) {
            super.setupAnim(state);
            this.animate(entity.animationState0, CorruptedAnimation.idle, state.ageInTicks, 1f);
        }
    }
}
