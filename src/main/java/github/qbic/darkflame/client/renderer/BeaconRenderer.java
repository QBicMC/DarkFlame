package github.qbic.darkflame.client.renderer;

import github.qbic.darkflame.client.model.BlankModel;
import github.qbic.darkflame.entity.BeaconEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class BeaconRenderer extends MobRenderer<BeaconEntity, LivingEntityRenderState, BlankModel> {
	public BeaconRenderer(EntityRendererProvider.Context context) {
		super(context, new BlankModel(context.bakeLayer(BlankModel.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("dark_flame:textures/entities/blank.png");
	}

	@Override
	public boolean shouldRender(BeaconEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
		return true;
	}
}