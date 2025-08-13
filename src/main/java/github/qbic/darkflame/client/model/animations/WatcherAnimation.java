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
public class watcherAnimation {
	public static final AnimationDefinition idle = AnimationDefinition.Builder.withLength(0.7083F).looping()
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.0833F, KeyframeAnimations.degreeVec(1.1069F, -12.4517F, -5.1208F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.2083F, KeyframeAnimations.degreeVec(-2.6243F, -2.2641F, 5.7517F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.104F, -2.2798F, -2.4925F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-2.2726F, 2.7083F, 5.0008F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5833F, KeyframeAnimations.degreeVec(4.8529F, 5.2256F, -2.0649F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-5.357F, -1.5951F, 9.9655F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_arm",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7083F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_arm",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.build();
	public static final AnimationDefinition run = AnimationDefinition.Builder.withLength(0.5417F).looping()
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.125F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_arm",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5417F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_arm",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5417F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
					new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.125F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.build();
	public static final AnimationDefinition disappear = AnimationDefinition.Builder.withLength(0.25F)
			.addAnimation("watcher",
					new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.2F, 0.0F), AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("watcher", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)))
			.build();
}