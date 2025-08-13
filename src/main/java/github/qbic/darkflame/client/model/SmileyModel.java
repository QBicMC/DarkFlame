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
public class SmileyModel extends EntityModel<LivingEntityRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "model_smiley"), "main");
	public final ModelPart smiley;
	public final ModelPart torso;
	public final ModelPart arms;
	public final ModelPart left_arm;
	public final ModelPart upper_left_arm;
	public final ModelPart lower_left_arm;
	public final ModelPart left_hand;
	public final ModelPart left_finger_one;
	public final ModelPart left_lower_finger_one;
	public final ModelPart left_upper_finger_one;
	public final ModelPart left_finger_two;
	public final ModelPart left_lower_finger_two;
	public final ModelPart left_upper_finger_two;
	public final ModelPart left_finger_three;
	public final ModelPart left_lower_finger_three;
	public final ModelPart left_upper_finger_three;
	public final ModelPart left_finger_four;
	public final ModelPart left_lower_finger_four;
	public final ModelPart left_upper_finger_four;
	public final ModelPart right_arm;
	public final ModelPart upper_right_arm;
	public final ModelPart lower_right_arm;
	public final ModelPart right_hand;
	public final ModelPart right_finger_one;
	public final ModelPart right_lower_finger_one;
	public final ModelPart right_upper_finger_one;
	public final ModelPart right_finger_two;
	public final ModelPart right_lower_finger_two;
	public final ModelPart right_upper_finger_two;
	public final ModelPart right_finger_three;
	public final ModelPart right_lower_finger_three;
	public final ModelPart right_upper_finger_three;
	public final ModelPart right_finger_four;
	public final ModelPart right_lower_finger_four;
	public final ModelPart right_upper_finger_four;
	public final ModelPart head;
	public final ModelPart skull;
	public final ModelPart tendrils;
	public final ModelPart upper_tendrils;
	public final ModelPart tendril_7;
	public final ModelPart tendril_8;
	public final ModelPart tendril_9;
	public final ModelPart tendril_10;
	public final ModelPart lower_tendrils;
	public final ModelPart tendril_0;
	public final ModelPart tendril_1;
	public final ModelPart tendril_2;
	public final ModelPart right_tendrils;
	public final ModelPart tendril_4;
	public final ModelPart tendril_5;
	public final ModelPart tendril_6;
	public final ModelPart left_tendrils;
	public final ModelPart tendril_11;
	public final ModelPart tendril_12;
	public final ModelPart tendril_13;
	public final ModelPart legs;
	public final ModelPart left_leg;
	public final ModelPart upper_left_leg;
	public final ModelPart lower_left_leg;
	public final ModelPart right_leg;
	public final ModelPart upper_right_leg;
	public final ModelPart lower_right_leg;

	public SmileyModel(ModelPart root) {
		super(root);
		this.smiley = root.getChild("smiley");
		this.torso = this.smiley.getChild("torso");
		this.arms = this.torso.getChild("arms");
		this.left_arm = this.arms.getChild("left_arm");
		this.upper_left_arm = this.left_arm.getChild("upper_left_arm");
		this.lower_left_arm = this.left_arm.getChild("lower_left_arm");
		this.left_hand = this.lower_left_arm.getChild("left_hand");
		this.left_finger_one = this.left_hand.getChild("left_finger_one");
		this.left_lower_finger_one = this.left_finger_one.getChild("left_lower_finger_one");
		this.left_upper_finger_one = this.left_finger_one.getChild("left_upper_finger_one");
		this.left_finger_two = this.left_hand.getChild("left_finger_two");
		this.left_lower_finger_two = this.left_finger_two.getChild("left_lower_finger_two");
		this.left_upper_finger_two = this.left_finger_two.getChild("left_upper_finger_two");
		this.left_finger_three = this.left_hand.getChild("left_finger_three");
		this.left_lower_finger_three = this.left_finger_three.getChild("left_lower_finger_three");
		this.left_upper_finger_three = this.left_finger_three.getChild("left_upper_finger_three");
		this.left_finger_four = this.left_hand.getChild("left_finger_four");
		this.left_lower_finger_four = this.left_finger_four.getChild("left_lower_finger_four");
		this.left_upper_finger_four = this.left_finger_four.getChild("left_upper_finger_four");
		this.right_arm = this.arms.getChild("right_arm");
		this.upper_right_arm = this.right_arm.getChild("upper_right_arm");
		this.lower_right_arm = this.right_arm.getChild("lower_right_arm");
		this.right_hand = this.lower_right_arm.getChild("right_hand");
		this.right_finger_one = this.right_hand.getChild("right_finger_one");
		this.right_lower_finger_one = this.right_finger_one.getChild("right_lower_finger_one");
		this.right_upper_finger_one = this.right_finger_one.getChild("right_upper_finger_one");
		this.right_finger_two = this.right_hand.getChild("right_finger_two");
		this.right_lower_finger_two = this.right_finger_two.getChild("right_lower_finger_two");
		this.right_upper_finger_two = this.right_finger_two.getChild("right_upper_finger_two");
		this.right_finger_three = this.right_hand.getChild("right_finger_three");
		this.right_lower_finger_three = this.right_finger_three.getChild("right_lower_finger_three");
		this.right_upper_finger_three = this.right_finger_three.getChild("right_upper_finger_three");
		this.right_finger_four = this.right_hand.getChild("right_finger_four");
		this.right_lower_finger_four = this.right_finger_four.getChild("right_lower_finger_four");
		this.right_upper_finger_four = this.right_finger_four.getChild("right_upper_finger_four");
		this.head = this.torso.getChild("head");
		this.skull = this.head.getChild("skull");
		this.tendrils = this.skull.getChild("tendrils");
		this.upper_tendrils = this.tendrils.getChild("upper_tendrils");
		this.tendril_7 = this.upper_tendrils.getChild("tendril_7");
		this.tendril_8 = this.upper_tendrils.getChild("tendril_8");
		this.tendril_9 = this.upper_tendrils.getChild("tendril_9");
		this.tendril_10 = this.upper_tendrils.getChild("tendril_10");
		this.lower_tendrils = this.tendrils.getChild("lower_tendrils");
		this.tendril_0 = this.lower_tendrils.getChild("tendril_0");
		this.tendril_1 = this.lower_tendrils.getChild("tendril_1");
		this.tendril_2 = this.lower_tendrils.getChild("tendril_2");
		this.right_tendrils = this.tendrils.getChild("right_tendrils");
		this.tendril_4 = this.right_tendrils.getChild("tendril_4");
		this.tendril_5 = this.right_tendrils.getChild("tendril_5");
		this.tendril_6 = this.right_tendrils.getChild("tendril_6");
		this.left_tendrils = this.tendrils.getChild("left_tendrils");
		this.tendril_11 = this.left_tendrils.getChild("tendril_11");
		this.tendril_12 = this.left_tendrils.getChild("tendril_12");
		this.tendril_13 = this.left_tendrils.getChild("tendril_13");
		this.legs = this.smiley.getChild("legs");
		this.left_leg = this.legs.getChild("left_leg");
		this.upper_left_leg = this.left_leg.getChild("upper_left_leg");
		this.lower_left_leg = this.left_leg.getChild("lower_left_leg");
		this.right_leg = this.legs.getChild("right_leg");
		this.upper_right_leg = this.right_leg.getChild("upper_right_leg");
		this.lower_right_leg = this.right_leg.getChild("lower_right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition smiley = partdefinition.addOrReplaceChild("smiley", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition torso = smiley.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -23.0F, -2.0F, 12.0F, 23.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.0F, 0.0F));
		PartDefinition arms = torso.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, -23.0F, 0.0F));
		PartDefinition left_arm = arms.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(6.0F, 0.0F, 0.0F));
		PartDefinition upper_left_arm = left_arm.addOrReplaceChild("upper_left_arm", CubeListBuilder.create().texOffs(40, 14).addBox(0.0F, 0.0F, -1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition lower_left_arm = left_arm.addOrReplaceChild("lower_left_arm", CubeListBuilder.create().texOffs(0, 43).addBox(0.0F, 0.0F, -1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 0.0F));
		PartDefinition left_hand = lower_left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(40, 0).addBox(-2.0F, -1.0F, -6.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(-0.001F)),
				PartPose.offsetAndRotation(1.0F, 17.0F, 0.0F, -1.5708F, -1.5708F, 3.1416F));
		PartDefinition left_finger_one = left_hand.addOrReplaceChild("left_finger_one", CubeListBuilder.create(), PartPose.offset(-2.0F, -1.0F, -2.0F));
		PartDefinition left_lower_finger_one = left_finger_one.addOrReplaceChild("left_lower_finger_one", CubeListBuilder.create().texOffs(48, 18).addBox(-3.0F, 0.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.0F, 0.0F, 1.0F));
		PartDefinition left_upper_finger_one = left_finger_one.addOrReplaceChild("left_upper_finger_one",
				CubeListBuilder.create().texOffs(48, 26).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(48, 54).addBox(-1.0F, 0.0F, -6.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, -3.0F));
		PartDefinition left_finger_two = left_hand.addOrReplaceChild("left_finger_two", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -6.0F));
		PartDefinition left_lower_finger_two = left_finger_two.addOrReplaceChild("left_lower_finger_two", CubeListBuilder.create().texOffs(24, 43).addBox(-1.0F, 0.0F, -8.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 5.0F));
		PartDefinition left_upper_finger_two = left_finger_two.addOrReplaceChild("left_upper_finger_two",
				CubeListBuilder.create().texOffs(48, 30).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(24, 55).addBox(-1.0F, 0.0F, -6.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, -3.0F));
		PartDefinition left_finger_three = left_hand.addOrReplaceChild("left_finger_three", CubeListBuilder.create(), PartPose.offset(1.0F, -1.0F, -6.0F));
		PartDefinition left_lower_finger_three = left_finger_three.addOrReplaceChild("left_lower_finger_three", CubeListBuilder.create().texOffs(24, 47).addBox(1.0F, 0.0F, -8.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-1.0F, 0.0F, 5.0F));
		PartDefinition left_upper_finger_three = left_finger_three.addOrReplaceChild("left_upper_finger_three",
				CubeListBuilder.create().texOffs(48, 34).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(56, 18).addBox(0.0F, 0.0F, -6.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, -3.0F));
		PartDefinition left_finger_four = left_hand.addOrReplaceChild("left_finger_four", CubeListBuilder.create(), PartPose.offset(2.0F, -1.0F, -5.0F));
		PartDefinition left_lower_finger_four = left_finger_four.addOrReplaceChild("left_lower_finger_four", CubeListBuilder.create().texOffs(48, 22).addBox(2.0F, 0.0F, -7.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 0.0F, 4.0F));
		PartDefinition left_upper_finger_four = left_finger_four.addOrReplaceChild("left_upper_finger_four",
				CubeListBuilder.create().texOffs(48, 38).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(56, 21).addBox(-1.0F, 0.0F, -6.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.0F, 0.0F, -3.0F));
		PartDefinition right_arm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-6.0F, 0.0F, 0.0F));
		PartDefinition upper_right_arm = right_arm.addOrReplaceChild("upper_right_arm", CubeListBuilder.create().texOffs(40, 33).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition lower_right_arm = right_arm.addOrReplaceChild("lower_right_arm", CubeListBuilder.create().texOffs(8, 43).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 0.0F));
		PartDefinition right_hand = lower_right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(40, 7).addBox(-3.0F, 0.0F, -6.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(-0.001F)),
				PartPose.offsetAndRotation(-2.0F, 17.0F, 0.0F, 0.0F, 1.5708F, -1.5708F));
		PartDefinition right_finger_one = right_hand.addOrReplaceChild("right_finger_one", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, -2.0F));
		PartDefinition right_lower_finger_one = right_finger_one.addOrReplaceChild("right_lower_finger_one", CubeListBuilder.create().texOffs(48, 42).addBox(0.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 1.0F, 0.0F));
		PartDefinition right_upper_finger_one = right_finger_one.addOrReplaceChild("right_upper_finger_one",
				CubeListBuilder.create().texOffs(48, 46).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(56, 24).addBox(-1.0F, 0.0F, -6.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.0F, 0.0F, -3.0F));
		PartDefinition right_finger_two = right_hand.addOrReplaceChild("right_finger_two", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, -6.0F));
		PartDefinition right_lower_finger_two = right_finger_two.addOrReplaceChild("right_lower_finger_two", CubeListBuilder.create().texOffs(32, 50).addBox(1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 1.0F, 1.0F));
		PartDefinition right_upper_finger_two = right_finger_two.addOrReplaceChild("right_upper_finger_two",
				CubeListBuilder.create().texOffs(56, 27).addBox(-1.0F, -1.0F, -6.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(48, 50).addBox(-1.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 1.0F, -3.0F));
		PartDefinition right_finger_three = right_hand.addOrReplaceChild("right_finger_three", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -6.0F));
		PartDefinition right_lower_finger_three = right_finger_three.addOrReplaceChild("right_lower_finger_three", CubeListBuilder.create().texOffs(24, 51).addBox(3.0F, -1.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-3.0F, 1.0F, 1.0F));
		PartDefinition right_upper_finger_three = right_finger_three.addOrReplaceChild("right_upper_finger_three",
				CubeListBuilder.create().texOffs(56, 30).addBox(-1.0F, 0.0F, -6.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(40, 52).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.0F, 0.0F, -3.0F));
		PartDefinition right_finger_four = right_hand.addOrReplaceChild("right_finger_four", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -5.0F));
		PartDefinition right_lower_finger_four = right_finger_four.addOrReplaceChild("right_lower_finger_four", CubeListBuilder.create().texOffs(16, 54).addBox(0.0F, -1.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 1.0F, 0.0F));
		PartDefinition right_upper_finger_four = right_finger_four.addOrReplaceChild("right_upper_finger_four",
				CubeListBuilder.create().texOffs(56, 33).addBox(-1.0F, 0.0F, -6.0F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(32, 54).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.0F, 0.0F, -3.0F));
		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -23.0F, 0.0F));
		PartDefinition skull = head.addOrReplaceChild("skull",
				CubeListBuilder.create().texOffs(0, 27).addBox(-5.0F, -10.0F, -3.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(16, 43).addBox(4.0F, -10.0F, -4.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(48, 14)
						.addBox(-4.0F, -10.0F, -4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(48, 16).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(20, 43)
						.addBox(-5.0F, -10.0F, -4.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition tendrils = skull.addOrReplaceChild("tendrils", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition upper_tendrils = tendrils.addOrReplaceChild("upper_tendrils", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, -4.0F));
		PartDefinition tendril_7 = upper_tendrils.addOrReplaceChild("tendril_7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));
		PartDefinition _tendril_7_r1 = tendril_7.addOrReplaceChild("_tendril_7_r1", CubeListBuilder.create().texOffs(44, 56).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.0F, 0.0F, 1.0F, 2.4435F, 0.0F, 0.0F));
		PartDefinition tendril_8 = upper_tendrils.addOrReplaceChild("tendril_8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));
		PartDefinition _tendril_8_r1 = tendril_8.addOrReplaceChild("_tendril_8_r1", CubeListBuilder.create().texOffs(56, 44).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, 0.0F, 1.0F, 2.7489F, 0.0F, 0.0F));
		PartDefinition tendril_9 = upper_tendrils.addOrReplaceChild("tendril_9", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));
		PartDefinition _tendril_9_r1 = tendril_9.addOrReplaceChild("_tendril_9_r1", CubeListBuilder.create().texOffs(46, 56).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 2.5744F, 0.0F, 0.0F));
		PartDefinition tendril_10 = upper_tendrils.addOrReplaceChild("tendril_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));
		PartDefinition _tendril_10_r1 = tendril_10.addOrReplaceChild("_tendril_10_r1", CubeListBuilder.create().texOffs(56, 46).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, 0.0F, 1.0F, 2.4871F, 0.0F, 0.0F));
		PartDefinition lower_tendrils = tendrils.addOrReplaceChild("lower_tendrils", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -4.0F));
		PartDefinition tendril_0 = lower_tendrils.addOrReplaceChild("tendril_0", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition _tendril_0_r1 = tendril_0.addOrReplaceChild("_tendril_0_r1", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		PartDefinition tendril_1 = lower_tendrils.addOrReplaceChild("tendril_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition _tendril_1_r1 = tendril_1.addOrReplaceChild("_tendril_1_r1", CubeListBuilder.create().texOffs(56, 38).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));
		PartDefinition tendril_2 = lower_tendrils.addOrReplaceChild("tendril_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition _tendril_2_r1 = tendril_2.addOrReplaceChild("_tendril_2_r1", CubeListBuilder.create().texOffs(40, 56).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.6545F, 0.0F, 0.0F));
		PartDefinition right_tendrils = tendrils.addOrReplaceChild("right_tendrils", CubeListBuilder.create(), PartPose.offset(4.0F, -4.0F, -4.0F));
		PartDefinition tendril_4 = right_tendrils.addOrReplaceChild("tendril_4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition _tendril_4_r1 = tendril_4.addOrReplaceChild("_tendril_4_r1", CubeListBuilder.create().texOffs(56, 40).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.5672F, 0.0F, -1.5708F));
		PartDefinition tendril_5 = right_tendrils.addOrReplaceChild("tendril_5", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));
		PartDefinition _tendril_5_r1 = tendril_5.addOrReplaceChild("_tendril_5_r1", CubeListBuilder.create().texOffs(42, 56).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, -1.5708F));
		PartDefinition tendril_6 = right_tendrils.addOrReplaceChild("tendril_6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition _tendril_6_r1 = tendril_6.addOrReplaceChild("_tendril_6_r1", CubeListBuilder.create().texOffs(56, 42).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.7854F, 0.0F, -1.5708F));
		PartDefinition left_tendrils = tendrils.addOrReplaceChild("left_tendrils", CubeListBuilder.create(), PartPose.offset(-4.0F, -5.0F, -4.0F));
		PartDefinition tendril_11 = left_tendrils.addOrReplaceChild("tendril_11", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));
		PartDefinition _tendril_11_r1 = tendril_11.addOrReplaceChild("_tendril_11_r1", CubeListBuilder.create().texOffs(56, 48).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.7418F, 0.0F, 1.5708F));
		PartDefinition tendril_12 = left_tendrils.addOrReplaceChild("tendril_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition _tendril_12_r1 = tendril_12.addOrReplaceChild("_tendril_12_r1", CubeListBuilder.create().texOffs(56, 50).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.9163F, 0.0F, 1.5708F));
		PartDefinition tendril_13 = left_tendrils.addOrReplaceChild("tendril_13", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));
		PartDefinition _tendril_13_r1 = tendril_13.addOrReplaceChild("_tendril_13_r1", CubeListBuilder.create().texOffs(56, 52).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.8727F, 0.0F, 1.5708F));
		PartDefinition legs = smiley.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, -23.0F, 0.0F));
		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(5.0F, 0.0F, 0.0F));
		PartDefinition upper_left_leg = left_leg.addOrReplaceChild("upper_left_leg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition lower_left_leg = left_leg.addOrReplaceChild("lower_left_leg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));
		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-5.0F, 0.0F, 0.0F));
		PartDefinition upper_right_leg = right_leg.addOrReplaceChild("upper_right_leg", CubeListBuilder.create().texOffs(32, 25).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition lower_right_leg = right_leg.addOrReplaceChild("lower_right_leg", CubeListBuilder.create().texOffs(32, 25).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
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