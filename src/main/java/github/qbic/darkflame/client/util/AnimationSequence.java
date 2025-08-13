package github.qbic.darkflame.util;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Mob;

import java.util.*;
import java.util.function.Supplier;

public class AnimationSequence {
    private final Supplier<Boolean> play;
    private final Map<AnimationState, AnimationDefinition> animationDefinitions;
    private boolean running = false;
    private int currentIndex = 0;

    public AnimationSequence(Supplier<Boolean> play, Map<AnimationState, AnimationDefinition> animationDefinitions) {
        this.play = play;
        this.animationDefinitions = animationDefinitions;
    }

    public void onClientTick(Mob mob) {
        running = play.get();
        if (!running) {
            currentIndex = 0;
        }

        List<AnimationState> states = new ArrayList<>(animationDefinitions.keySet());
        for (int i = 0; i < states.size(); i++) {
            AnimationState state = states.get(i);
            state.animateWhen(currentIndex == i && running, mob.tickCount);
            if (currentIndex == i && !state.isStarted()) {
                currentIndex = i + 1;
            }
        }
    }

    public void onSetupAnim(QuadConsumer<AnimationState, AnimationDefinition, Float, Float> animate, LivingEntityRenderState state) {
        Set<AnimationState> states = animationDefinitions.keySet();
        states.forEach((animationState) -> {
            AnimationDefinition definition = animationDefinitions.get(animationState);
            animate.accept(animationState, definition, state.ageInTicks, 1f);
        });
    }

    public void reset() {
        currentIndex = 0;
    }

    public static class Builder {
        private final Supplier<Boolean> play;
        private Map<AnimationState, AnimationDefinition> animationDefinitions;

        public Builder(Supplier<Boolean> play) {
            this.play = play;
            this.animationDefinitions = new LinkedHashMap<>();
        }

        public static Builder create(Supplier<Boolean> play) {
            return new Builder(play);
        }

        public Builder add(AnimationDefinition animation) {
            this.animationDefinitions.put(new AnimationState(), animation);
            return this;
        }

        public AnimationSequence build() {
            return new AnimationSequence(this.play, this.animationDefinitions);
        }
    }
}
