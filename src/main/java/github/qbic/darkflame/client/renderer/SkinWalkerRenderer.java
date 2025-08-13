package github.qbic.darkflame.client.renderer;

import github.qbic.darkflame.client.util.ClientUtil;
import github.qbic.darkflame.entity.SkinWalkerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SkinWalkerRenderer extends HumanoidMobRenderer<SkinWalkerEntity, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
    private SkinWalkerEntity entity = null;

    public SkinWalkerRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<HumanoidRenderState>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getEquipmentRenderer()));
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public void extractRenderState(SkinWalkerEntity entity, HumanoidRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        this.entity = entity;
    }

    @Override
    public ResourceLocation getTextureLocation(HumanoidRenderState renderState) {
        if (entity == null) return ClientUtil.getSkinTexture(null);

        String targetName = entity.getEntityData().get(SkinWalkerEntity.target);
        if (targetName == null || targetName.isEmpty()) return ClientUtil.getSkinTexture(null);

        ClientLevel world = Minecraft.getInstance().level;
        if (world == null) return ClientUtil.getSkinTexture(null);

        for (Player player : world.players()) {
            if (player.getName().getString().equals(targetName)) {
                return ClientUtil.getSkinTexture(player);
            }
        }

        return ClientUtil.getSkinTexture(null);
    }
}
