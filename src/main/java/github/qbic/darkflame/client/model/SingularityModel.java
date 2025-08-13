package github.qbic.darkflame.client.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
public class ModelSingularity extends EntityModel<LivingEntityRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("dark_flame", "model_singularity"), "main");
    public final ModelPart singularity;
    public final ModelPart tentacles;
    public final ModelPart tentacle0;
    public final ModelPart tentacle1;
    public final ModelPart tentacle2;
    public final ModelPart tentacle3;
    public final ModelPart tentacle4;
    public final ModelPart tentacle5;
    public final ModelPart tentacle6;
    public final ModelPart tentacle7;
    public final ModelPart head;

    public ModelSingularity(ModelPart root) {
        super(root);
        this.singularity = root.getChild("singularity");
        this.tentacles = this.singularity.getChild("tentacles");
        this.tentacle0 = this.tentacles.getChild("tentacle0");
        this.tentacle1 = this.tentacles.getChild("tentacle1");
        this.tentacle2 = this.tentacles.getChild("tentacle2");
        this.tentacle3 = this.tentacles.getChild("tentacle3");
        this.tentacle4 = this.tentacles.getChild("tentacle4");
        this.tentacle5 = this.tentacles.getChild("tentacle5");
        this.tentacle6 = this.tentacles.getChild("tentacle6");
        this.tentacle7 = this.tentacles.getChild("tentacle7");
        this.head = this.singularity.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition singularity = partdefinition.addOrReplaceChild("singularity", CubeListBuilder.create(), PartPose.offset(0.0F, -110.0F, 0.0F));
        PartDefinition tentacles = singularity.addOrReplaceChild("tentacles", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition tentacle0 = tentacles.addOrReplaceChild("tentacle0", CubeListBuilder.create().texOffs(0, 0).addBox(-22.0F, 1.0F, -1.0F, 44.0F, 101.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));
        PartDefinition tentacle1 = tentacles.addOrReplaceChild("tentacle1", CubeListBuilder.create(), PartPose.offset(-9.0F, 9.0F, 0.0F));
        PartDefinition tentacle1_r1 = tentacle1.addOrReplaceChild("tentacle1_r1", CubeListBuilder.create().texOffs(88, 0).addBox(-22.0F, 16.0F, -1.0F, 44.0F, 101.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(9.0F, -11.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
        PartDefinition tentacle2 = tentacles.addOrReplaceChild("tentacle2", CubeListBuilder.create(), PartPose.offset(-16.0F, 0.0F, 0.0F));
        PartDefinition tentacle2_r1 = tentacle2.addOrReplaceChild("tentacle2_r1", CubeListBuilder.create().texOffs(0, 101).addBox(-22.0F, 16.0F, -1.0F, 44.0F, 101.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, -2.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
        PartDefinition tentacle3 = tentacles.addOrReplaceChild("tentacle3", CubeListBuilder.create(), PartPose.offset(-11.0F, -14.0F, 0.0F));
        PartDefinition tentacle3_r1 = tentacle3.addOrReplaceChild("tentacle3_r1", CubeListBuilder.create().texOffs(88, 101).addBox(-22.0F, 16.0F, -1.0F, 44.0F, 101.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(11.0F, 12.0F, 0.0F, 0.0F, 0.0F, 2.3562F));
        PartDefinition tentacle4 = tentacles.addOrReplaceChild("tentacle4", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, 0.0F));
        PartDefinition tentacle4_r1 = tentacle4.addOrReplaceChild("tentacle4_r1", CubeListBuilder.create().texOffs(176, 0).addBox(-22.0F, 16.0F, -1.0F, 44.0F, 101.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0F, 0.0F, -3.1416F));
        PartDefinition tentacle5 = tentacles.addOrReplaceChild("tentacle5", CubeListBuilder.create(), PartPose.offset(11.0F, -13.0F, 0.0F));
        PartDefinition tentacle5_r1 = tentacle5.addOrReplaceChild("tentacle5_r1", CubeListBuilder.create().texOffs(176, 101).addBox(-22.0F, 16.0F, -1.0F, 44.0F, 101.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-11.0F, 11.0F, 0.0F, 0.0F, 0.0F, -2.3562F));
        PartDefinition tentacle6 = tentacles.addOrReplaceChild("tentacle6", CubeListBuilder.create(), PartPose.offset(16.0F, 0.0F, 0.0F));
        PartDefinition tentacle6_r1 = tentacle6.addOrReplaceChild("tentacle6_r1", CubeListBuilder.create().texOffs(0, 202).addBox(-22.0F, 16.0F, -1.0F, 44.0F, 101.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-16.0F, -2.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
        PartDefinition tentacle7 = tentacles.addOrReplaceChild("tentacle7", CubeListBuilder.create(), PartPose.offset(12.0F, 10.0F, 0.0F));
        PartDefinition tentacle7_r1 = tentacle7.addOrReplaceChild("tentacle7_r1", CubeListBuilder.create().texOffs(88, 202).addBox(-22.0F, 16.0F, -1.0F, 44.0F, 101.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -12.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        PartDefinition head = singularity.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(22.0F, 1.0F, 0.0F));
        PartDefinition eye_r1 = head.addOrReplaceChild("eye_r1", CubeListBuilder.create().texOffs(176, 202).addBox(-15.4437F, -16.4437F, -1.0F, 32.0F, 32.0F, 0.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(-22.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    public void setupAnim(LivingEntityRenderState state) {
        float limbSwing = state.walkAnimationPos;
        float limbSwingAmount = state.walkAnimationSpeed;
        float ageInTicks = state.ageInTicks;
        float netHeadYaw = state.yRot;
        float headPitch = state.xRot;

    }

}