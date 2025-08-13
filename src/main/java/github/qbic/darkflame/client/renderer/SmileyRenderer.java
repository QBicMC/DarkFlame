package github.qbic.darkflame.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.ModelPart;

import github.qbic.darkflame.entity.SmileyEntity;
import github.qbic.darkflame.client.model.animations.SmileyAnimation;
import github.qbic.darkflame.client.model.ModelSmiley;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class SmileyRenderer extends MobRenderer<SmileyEntity, LivingEntityRenderState, ModelSmiley> {
	private SmileyEntity entity = null;

	public SmileyRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(ModelSmiley.LAYER_LOCATION)), 0.8f);
		this.addLayer(new RenderLayer<>(this) {
			final ResourceLocation LAYER_TEXTURE = ResourceLocation.parse("dark_flame:textures/entities/smiley.png");

			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, LivingEntityRenderState state, float headYaw, float headPitch) {
				VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(LAYER_TEXTURE));
				this.getParentModel().renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
			}
		});
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(SmileyEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
		if (this.model instanceof AnimatedModel) {
			((AnimatedModel) this.model).setEntity(entity);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("dark_flame:textures/entities/smiley.png");
	}

	private static final class AnimatedModel extends ModelSmiley {
		private SmileyEntity entity = null;

		public AnimatedModel(ModelPart root) {
			super(root);
		}

		public void setEntity(SmileyEntity entity) {
			this.entity = entity;
		}

		@Override
		public void setupAnim(LivingEntityRenderState state) {
			this.root().getAllParts().forEach(ModelPart::resetPose);
			this.animate(entity.animationState0, SmileyAnimation.attack, state.ageInTicks, 1f);
			this.animate(entity.animationState1, SmileyAnimation.reach, state.ageInTicks, 1f);
			this.animate(entity.animationState2, SmileyAnimation.idle, state.ageInTicks, 1f);
			super.setupAnim(state);
		}
	}
}