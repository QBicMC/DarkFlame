package github.qbic.darkflame.entity.goal;

import github.qbic.darkflame.Brain;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class FollowTargetGoal extends Goal {

    protected final PathfinderMob mob;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    protected final float minDistance;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;

    public FollowTargetGoal(
            PathfinderMob mob,
            float minDistance,
            double walkSpeedModifier,
            double sprintSpeedModifier
    ) {
        this.mob = mob;
        this.pathNav = mob.getNavigation();
        this.minDistance = minDistance;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = Brain.getTarget();
        if (target == null || !target.isAlive()) return false;
        if (mob.distanceToSqr(target) <= minDistance * minDistance) return false;

        this.path = pathNav.createPath(target, 0);
        return this.path != null;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = Brain.getTarget();
        return target != null && target.isAlive() && !pathNav.isDone() && mob.distanceToSqr(target) > minDistance * minDistance;
    }

    @Override
    public void start() {
        LivingEntity target = Brain.getTarget();
        if (target == null) return;

        double distSqr = mob.distanceToSqr(target);
        double speed = distSqr > 1024.0D ? sprintSpeedModifier : walkSpeedModifier;
        pathNav.moveTo(target, speed);
    }

    @Override
    public void stop() {
        pathNav.stop();
    }

    @Override
    public void tick() {
        LivingEntity target = Brain.getTarget();
        if (target == null || !target.isAlive()) return;

        double distSqr = mob.distanceToSqr(target);
        if (distSqr <= minDistance * minDistance) {
            pathNav.stop();
            return;
        }

        double speed = distSqr > 1024.0D ? sprintSpeedModifier : walkSpeedModifier;
        pathNav.moveTo(target, speed);
    }
}
