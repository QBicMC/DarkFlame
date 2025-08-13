package github.qbic.darkflame.entity.nav.goal;

import github.qbic.darkflame.entity.WatcherEntity;
import github.qbic.darkflame.init.ModSounds;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class ChaseEntityGoal extends Goal {
    public double speedModifier;
    public int chaseTime;
    public final int maxChaseTime;
    public Entity target;
    public Mob mob;
    public double originalStepHeight;
    public boolean isDespawning = false;

    public ChaseEntityGoal(Mob mob, Entity target, double speedModifier, int maxChaseTime) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.target = target;
        this.chaseTime = 0;
        this.maxChaseTime = maxChaseTime;
        this.originalStepHeight = mob.getAttribute(Attributes.STEP_HEIGHT).getBaseValue();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return target != null && target.isAlive() && chaseTime < maxChaseTime;
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive() && chaseTime < maxChaseTime;
    }

    @Override
    public void tick() {
        super.tick();
        if (isDespawning) return;

        if (mob.distanceToSqr(target) < 16) {
            isDespawning = true;
            mob.getNavigation().stop();
            ServerLevel level = (ServerLevel) mob.level();
            Entity _target = target;

            Util.schedule(() -> {
                mob.discard();
            }, 40, "despawn entity");

            Util.schedule(() -> {
                level.setDayTime(17000);
                if (_target instanceof Player p) {
                    Util.broadcastMasterTo((ServerPlayer) p, Util.chooseRandom(
                            ModSounds.BELLS,
                            ModSounds.WATCHER_AMBIENT,
                            ModSounds.MUSIC_BOX
                    ));
                }
            }, 60, "despawn effects");
        } else {
            mob.getNavigation().moveTo(target, speedModifier);
            mob.getLookControl().setLookAt(target);
            mob.setSprinting(true);
        }

        chaseTime++;
    }

    @Override
    public void start() {
        super.start();
        mob.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1.6);
        originalStepHeight = mob.getAttribute(Attributes.STEP_HEIGHT).getBaseValue();
    }

    @Override
    public void stop() {
        super.stop();
        mob.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(originalStepHeight);
        if (mob instanceof WatcherEntity) {
            mob.discard();
        }
    }
}
