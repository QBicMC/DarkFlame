package github.qbic.darkflame.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.qbic.darkflame.client.model.SingularityModel;
import github.qbic.darkflame.entity.SingularityEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class SingularityRenderer extends MobRenderer<SingularityEntity, LivingEntityRenderState, SingularityModel> {
    private SingularityEntity entity = null;

    public SingularityRenderer(EntityRendererProvider.Context context) {
        super(context, new AnimatedModel(context.bakeLayer(SingularityModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new RenderLayer<>(this) {
            final ResourceLocation LAYER_TEXTURE = ResourceLocation.parse("dark_flame:textures/entities/singularity.png");

            @Override
            public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, LivingEntityRenderState state, float headYaw, float headPitch) {
                VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(LAYER_TEXTURE));
                this.getParentModel().renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
            }
        });

    }

    @Override
    public boolean shouldRender(SingularityEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(LivingEntityRenderState renderState) {
        return ResourceLocation.parse("dark_flame:textures/entities/singularity.png");
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void extractRenderState(SingularityEntity entity, LivingEntityRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        this.entity = entity;

        if (this.model instanceof AnimatedModel aniModel) {
            aniModel.setEntity(entity);
        }
    }

    @Override
    public void render(LivingEntityRenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int p_115313_) {
        poseStack.scale(5.0f, 5.0f, 5.0f);
        super.render(state, poseStack, bufferSource, p_115313_);
    }

    public static final class AnimatedModel extends SingularityModel {
        private SingularityEntity entity = null;

        public AnimatedModel(ModelPart root) {
            super(root);
        }

        public void setEntity(SingularityEntity entity) {
            this.entity = entity;
        }

        @Override
        public void setupAnim(LivingEntityRenderState state) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            entity.spinAnimation.onSetupAnim(this::animate, state);

            Player target = Minecraft.getInstance().player;
            if (target != null) {
                float entityYaw = entity.getYRot() * (float)(Math.PI / 180.0);

                double dx = target.getX() - entity.getX();
                double dz = target.getZ() - entity.getZ();
                double dy = target.getEyeY() - (entity.getY() + head.y / 16.0 + 20);

                double distXZ = Math.sqrt(dx * dx + dz * dz);

                float absoluteYaw = (float) Math.atan2(-dx, dz);
                float absolutePitch = (float) Math.atan2(-dy, distXZ);

                this.root().yRot = wrapRadians(absoluteYaw - entityYaw);

                head.yRot = wrapRadians(absoluteYaw - (this.root().yRot + entityYaw));
                head.xRot = -Mth.clamp(absolutePitch, -1.0f, 1.0f);
            }

            super.setupAnim(state);
        }

        private float wrapRadians(float angle) {
            while (angle > Math.PI) angle -= (float)(Math.PI * 2);
            while (angle < -Math.PI) angle += (float)(Math.PI * 2);
            return angle;
        }
    }
}
