package github.qbic.darkflame.entity.goal;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.util.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class LookAtTargetGoal extends Goal {
    protected final PathfinderMob mob;
    private final Player target;

    public LookAtTargetGoal(PathfinderMob mob) {
        this.mob = mob;
        this.target = Brain.getTarget();
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive();
    }

    @Override
    public void tick() {
        if (target == null) return;
        mob.getLookControl().setLookAt(target);
    }
}
