package github.qbic.darkflame.entity.nav.goal;

import github.qbic.darkflame.Brain;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class LookAtTargetGoal extends Goal {
    protected final PathfinderMob mob;
    private Player target;

    public LookAtTargetGoal(PathfinderMob mob) {
        this.mob = mob;
        this.target = Brain.getTarget();
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public void start() {
        this.target = Brain.getTarget();
        super.start();
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
        mob.getLookControl().setLookAt(target);
    }
}
