package github.qbic.darkflame.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.qbic.darkflame.client.model.SimpleHumanoidModel;
import github.qbic.darkflame.entity.jumpscare.JumpscareEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class JumpscareRenderer extends MobRenderer<JumpscareEntity, LivingEntityRenderState, SimpleHumanoidModel> {
	private JumpscareEntity entity = null;

	public JumpscareRenderer(EntityRendererProvider.Context context) {
		super(context, new SimpleHumanoidModel(context.bakeLayer(SimpleHumanoidModel.LAYER_LOCATION)), 0.5f);
		this.addLayer(new RenderLayer<>(this) {
			final Supplier<ResourceLocation> LAYER_TEXTURE = () -> entity.texture();

			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, LivingEntityRenderState state, float headYaw, float headPitch) {
				VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(entity == null ? ResourceLocation.parse("dark_flame:textures/entities/blank.png") : LAYER_TEXTURE.get()));
				this.getParentModel().renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
			}
		});
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(JumpscareEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return entity == null ? ResourceLocation.parse("dark_flame:textures/entities/blank.png") : entity.texture();
	}
}