package github.qbic.darkflame.entity.fake;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.entity.nav.goal.FollowTargetGoal;
import github.qbic.darkflame.entity.nav.goal.LookAtTargetGoal;
import github.qbic.darkflame.entity.nav.goal.StareAtLocationGoal;
import github.qbic.darkflame.events.ModEvents;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.player.Player;

public interface FakeEntity {
    PathfinderMob getInstance();

    default void getAI(GoalSelector goalSelector) {
        switch (Util.randomEnum(FakeEntityAI.class)) {
            case STARE:
                goalSelector.addGoal(0, new LookAtTargetGoal(getInstance()));
                Util.printDBG("creating " + getInstance().getType().toShortString() + " with ai type STARE");
                break;
            case FOLLOW:
                goalSelector.addGoal(0, new FollowTargetGoal(getInstance(), 5, 1.0f, 1.5f));
                Util.printDBG("creating " + getInstance().getType().toShortString() + " with ai type FOLLOW");
                break;
            case STARE_OTHER:
                goalSelector.addGoal(0, new StareAtLocationGoal(getInstance(), Brain.getStarePosition()));
                Util.printDBG("creating " + getInstance().getType().toShortString() + " with ai type STARE_OTHER");
                break;
        }
    }

    default void onDie(DamageSource source) {
        if (source.getEntity() instanceof Player p && getInstance().level() instanceof ServerLevel) {
            Brain.setNewTarget(p);
            ModEvents.SPAWN_SINGULARITY_EVENT.execute();
        }
    }

    default void onTick() {
        if (getInstance().level() instanceof ServerLevel) {
            if (getInstance().tickCount >= getDespawnTime()) {
                getInstance().discard();
            }
        }
    }

    default int getDespawnTime() {
        return 6000;
    }

    enum FakeEntityAI {
        STARE,
        FOLLOW,
        STARE_OTHER
    }
}
