package github.qbic.darkflame.client.model;

import github.qbic.darkflame.DarkFlame;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
public class SimpleHumanoidModel extends EntityModel<LivingEntityRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "model_simple_humanoid"), "main");
	public final ModelPart simpleHumanoid;
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart right_arm;
	public final ModelPart left_arm;
	public final ModelPart right_leg;
	public final ModelPart left_leg;

	public SimpleHumanoidModel(ModelPart root) {
		super(root);
		this.simpleHumanoid = root.getChild("simple_humanoid");
		this.head = this.simpleHumanoid.getChild("head");
		this.body = this.simpleHumanoid.getChild("body");
		this.right_arm = this.simpleHumanoid.getChild("right_arm");
		this.left_arm = this.simpleHumanoid.getChild("left_arm");
		this.right_leg = this.simpleHumanoid.getChild("right_leg");
		this.left_leg = this.simpleHumanoid.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition simpleHumanoid = partdefinition.addOrReplaceChild("simple_humanoid", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition head = simpleHumanoid.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));
		PartDefinition body = simpleHumanoid.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));
		PartDefinition right_arm = simpleHumanoid.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -24.0F, 0.0F));
		PartDefinition left_arm = simpleHumanoid.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -24.0F, 0.0F));
		PartDefinition right_leg = simpleHumanoid.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 0.0F));
		PartDefinition left_leg = simpleHumanoid.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -12.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setupAnim(LivingEntityRenderState state) {
		float limbSwing = state.walkAnimationPos;
		float limbSwingAmount = state.walkAnimationSpeed;
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = state.yRot;
		float headPitch = state.xRot;

		head.setRotation((float) Math.toRadians(headPitch), (float) Math.toRadians(netHeadYaw), 0.0f);
	}
}