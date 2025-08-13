// Save this class in your mod and generate all required imports

/**
 * Made with Blockbench 4.12.5
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class ModelSingularityAnimation {
	public static final AnimationDefinition spin = AnimationDefinition.Builder.withLength(2.0F).looping()
		.addAnimation("tentacles", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 720.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1435.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition spin_start = AnimationDefinition.Builder.withLength(3.7083F)
		.addAnimation("tentacles", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 135.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 225.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 360.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.875F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 450.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.2083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 540.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 630.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.7083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 720.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition consume = AnimationDefinition.Builder.withLength(1.0F)
		.addAnimation("singularity", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-90.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tentacle0", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tentacle1", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(22.5F, 22.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tentacle2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 45.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tentacle3", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-24.1487F, 20.7048F, -9.0072F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tentacle4", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tentacle5", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-24.1487F, -20.7048F, 9.0072F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tentacle6", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -45.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tentacle7", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(22.5F, -22.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();
}