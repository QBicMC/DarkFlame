package github.qbic.darkflame.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.qbic.darkflame.client.model.SimpleHumanoidModel;
import github.qbic.darkflame.client.model.animations.WatcherAnimation;
import github.qbic.darkflame.entity.ObserverEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ObserverRenderer extends MobRenderer<ObserverEntity, LivingEntityRenderState, SimpleHumanoidModel> {
	private ObserverEntity entity = null;

	public ObserverRenderer(EntityRendererProvider.Context context) {
		super(context, new AnimatedModel(context.bakeLayer(SimpleHumanoidModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new RenderLayer<>(this) {
			final ResourceLocation LAYER_TEXTURE = getTextureLocation();

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
		if (this.model instanceof AnimatedModel aniModel) {
			aniModel.setEntity(entity);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return this.getTextureLocation();
	}

	public ResourceLocation getTextureLocation() {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null && player.level() != null) {
			float skyAngle = player.level().getTimeOfDay(1.0F);
			boolean isNight = skyAngle > 0.75F || skyAngle < 0.25F;

			return isNight
					? ResourceLocation.parse("dark_flame:textures/entities/observer_night.png")
					: ResourceLocation.parse("dark_flame:textures/entities/observer_day.png");
		}

		// fallback
		return ResourceLocation.parse("dark_flame:textures/entities/observer_day.png");
	}

	@Override
	public boolean shouldRender(ObserverEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
		String playerName = Minecraft.getInstance().player.getName().getString();
		return livingEntity.getEntityData().get(ObserverEntity.target).equals(playerName);
	}

	private static final class AnimatedModel extends SimpleHumanoidModel {
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
			this.animate(entity.animationStateIdle, WatcherAnimation.idle, state.ageInTicks, 1f);
			this.animateWalk(WatcherAnimation.run, state.walkAnimationPos, state.walkAnimationSpeed, 1f, 1f);
			this.animate(entity.animationStateDisappear, WatcherAnimation.disappear, state.ageInTicks, 1f);
			super.setupAnim(state);
		}
	}
}