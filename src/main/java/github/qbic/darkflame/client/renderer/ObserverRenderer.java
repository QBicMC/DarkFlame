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

import github.qbic.darkflame.entity.ObserverEntity;
import github.qbic.darkflame.client.model.animations.watcherAnimation;
import github.qbic.darkflame.client.model.Modelwatcher;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ObserverRenderer extends MobRenderer<ObserverEntity, LivingEntityRenderState, Modelwatcher> {
	private ObserverEntity entity = null;

	public ObserverRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(Modelwatcher.LAYER_LOCATION)), 0.5f);
		this.addLayer(new RenderLayer<>(this) {
			final ResourceLocation LAYER_TEXTURE = ResourceLocation.parse("dark_flame:textures/entities/watcher.png");

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
	public void extractRenderState(ObserverEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
		if (this.model instanceof AnimatedModel) {
			((AnimatedModel) this.model).setEntity(entity);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("dark_flame:textures/entities/watcher.png");
	}

	private static final class AnimatedModel extends Modelwatcher {
		private ObserverEntity entity = null;

		public AnimatedModel(ModelPart root) {
			super(root);
		}

		public void setEntity(ObserverEntity entity) {
			this.entity = entity;
		}

		@Override
		public void setupAnim(LivingEntityRenderState state) {
			this.root().getAllParts().forEach(ModelPart::resetPose);
			this.animate(entity.animationState0, watcherAnimation.idle, state.ageInTicks, 1f);
			this.animateWalk(watcherAnimation.run, state.walkAnimationPos, state.walkAnimationSpeed, 1f, 1f);
			this.animate(entity.animationState2, watcherAnimation.disappear, state.ageInTicks, 1f);
			super.setupAnim(state);
		}
	}
}