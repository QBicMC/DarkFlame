package github.qbic.darkflame.client.util;

import github.qbic.darkflame.util.QuadConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Mob;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

public class AnimationSequence {
    private record Entry(AnimationState state, AnimationDefinition def, int startAt) {}

    private final Supplier<Boolean> play;
    private final List<Entry> entries = new ArrayList<>();
    private boolean running = false;
    private int currentIndex = 0;
    private int elapsed = 0;

    public AnimationSequence(Supplier<Boolean> play, List<Entry> in) {
        this.play = play;
        this.entries.addAll(in);
        this.entries.sort(Comparator.comparingInt(Entry::startAt));
    }

    public void onClientTick(Mob mob) {
        if (!play.get() || entries.isEmpty()) { reset(); return; }
        if (!running) { running = true; currentIndex = 0; elapsed = 0; stopAll(); }

        while (currentIndex + 1 < entries.size() && elapsed >= entries.get(currentIndex + 1).startAt()) {
            entries.get(currentIndex).state().stop();
            currentIndex++;
        }

        entries.get(currentIndex).state().animateWhen(true, mob.tickCount);
        elapsed++;
    }

    public void onSetupAnim(QuadConsumer<AnimationState, AnimationDefinition, Float, Float> animate, LivingEntityRenderState rs) {
        if (running && currentIndex < entries.size()) {
            Entry e = entries.get(currentIndex);
            animate.accept(e.state(), e.def(), rs.ageInTicks, 1f);
        }
    }

    public void reset() {
        running = false;
        currentIndex = 0;
        elapsed = 0;
        stopAll();
    }

    private void stopAll() {
        for (Entry e : entries) e.state().stop();
    }

    public static class Builder {
        private final Supplier<Boolean> play;
        private final List<Entry> list = new ArrayList<>();

        public Builder(Supplier<Boolean> play) {
            this.play = play;
        }
        public static Builder create(Supplier<Boolean> play) { return new Builder(play); }

        public Builder add(AnimationDefinition animation, int startAtTick) {
            list.add(new Entry(new AnimationState(), animation, startAtTick));
            return this;
        }

        public AnimationSequence build() {
            return new AnimationSequence(play, list);
        }
    }
}
