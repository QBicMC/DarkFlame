package github.qbic.darkflame.client.model.animations;

import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationChannel;

// Save this class in your mod and generate all required imports
/**
 * Made with Blockbench 4.12.5 Exported for Minecraft version 1.19 or later with
 * Mojang mappings
 * 
 * @author Author
 */
public class SmileyAnimation {
	public static final AnimationDefinition idle = AnimationDefinition.Builder.withLength(2.0F).looping()
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9583F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("left_tendrils", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9583F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("upper_tendrils", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("right_tendrils", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9583F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lower_tendrils", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.9583F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.build();
	public static final AnimationDefinition reach = AnimationDefinition.Builder.withLength(0.0F)
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.8944F, 3.9649F, 3.0487F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(150.0F, 75.0F, -180.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_finger_one", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 15.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_finger_two", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_finger_four", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -15.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, -32.5F, 72.5F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_finger_one", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-4.7866F, -5.0452F, -1.0851F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_finger_two", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_finger_three", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.4407F, -0.0115F, -0.5409F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_finger_four", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-4.8713F, 7.5277F, 0.6575F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_upper_finger_four", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_upper_finger_three", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_upper_finger_two", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_upper_finger_one", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_upper_finger_one", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0014F, 13.1248F, -21.4687F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_upper_finger_two", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(37.1217F, 4.9809F, -8.6822F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_upper_finger_three", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_upper_finger_four", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(37.8905F, -7.6358F, 10.2453F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("lower_left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-112.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("lower_right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition attack = AnimationDefinition.Builder.withLength(2.25F)
			.addAnimation("smiley", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.749F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1667F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("smiley",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -11.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_arm",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.3944F, 3.9649F, 3.0487F), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(0.5F, KeyframeAnimations.degreeVec(-17.3944F, 3.9649F, 3.0487F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.999F, KeyframeAnimations.degreeVec(-17.3944F, 3.9649F, 3.0487F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.0F, KeyframeAnimations.degreeVec(-17.3944F, 3.9649F, 3.0487F), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.25F, KeyframeAnimations.degreeVec(-24.8944F, 3.9649F, 3.0487F), AnimationChannel.Interpolations.CATMULLROM),
							new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-12.3944F, 3.9649F, 3.0487F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(150.0F, 75.0F, -180.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_finger_one", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 15.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_finger_two", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_finger_four", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -15.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(90.0F, -32.5F, 72.5F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_finger_one", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-4.7866F, -5.0452F, -1.0851F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_finger_two", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_finger_three", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.4407F, -0.0115F, -0.5409F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_finger_four", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-4.8713F, 7.5277F, 0.6575F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1.0833F, KeyframeAnimations.degreeVec(22.07F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.2083F, KeyframeAnimations.degreeVec(22.07F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("right_upper_finger_four", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_upper_finger_three", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_upper_finger_two", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_upper_finger_one", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_upper_finger_one", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0014F, 13.1248F, -21.4687F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_upper_finger_two", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(37.1217F, 4.9809F, -8.6822F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_upper_finger_three", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_upper_finger_four", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(37.8905F, -7.6358F, 10.2453F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("upper_tendrils", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.749F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(-87.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("right_tendrils", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.749F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, -40.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -85.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1667F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("left_tendrils", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0.0F, 45.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.749F, KeyframeAnimations.degreeVec(0.0F, 45.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 45.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 70.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1667F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lower_tendrils", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(1.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.4583F, KeyframeAnimations.degreeVec(57.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.749F, KeyframeAnimations.degreeVec(57.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(57.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(89.16F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1667F, KeyframeAnimations.degreeVec(19.16F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lower_left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-122.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-160.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.749F, KeyframeAnimations.degreeVec(-160.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(-160.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.0F, KeyframeAnimations.degreeVec(-127.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-157.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)))
			.addAnimation("lower_right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0833F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.2917F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("torso",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.0833F, KeyframeAnimations.posVec(0.0F, -2.0F, 7.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("upper_left_leg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("upper_left_leg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -2.0F, 7.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("lower_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4583F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(2.1667F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("lower_left_leg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("upper_right_leg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75F, KeyframeAnimations.degreeVec(-40.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("upper_right_leg",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -2.0F, 7.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("lower_right_leg",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(1.75F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(2.1667F, KeyframeAnimations.degreeVec(80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.build();
}