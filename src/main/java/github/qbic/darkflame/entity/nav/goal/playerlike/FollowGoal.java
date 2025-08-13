package github.qbic.darkflame.entity.nav.goal.playerlike;

import github.qbic.darkflame.entity.SkinWalkerEntity;
import github.qbic.darkflame.entity.nav.PlayerLikeNav;
import github.qbic.darkflame.util.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FollowGoal extends Goal {
    public final SkinWalkerEntity entity;
    private float angleDeg = randomAngle();
    private int ticksNearPlayer = 0;

    public FollowGoal(SkinWalkerEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public Player getTargetPlayer() {
        return Util.getPlayerFromName(this.entity.level(), this.entity.getEntityData().get(SkinWalkerEntity.target));
    }

    @Override
    public boolean canUse() {
        return this.entity.behavior.equals(PlayerLikeNav.Behavior.FOLLOW) && getTargetPlayer() != null;
    }

    @Override
    public void start() {
        this.ticksNearPlayer = 0;
    }

    @Override
    public void stop() {
        this.entity.getNavigation().stop();
    }

    @Override
    public void tick() {
        Player player = getTargetPlayer();
        if (player == null) return;

        Vec3 entityPos = entity.position();
        Vec3 playerPos = player.position();

        double distSq = entityPos.distanceToSqr(playerPos);

        if (distSq < 49) {
            ticksNearPlayer++;

            if (ticksNearPlayer > 100) {
                angleDeg = randomAngle();
                ticksNearPlayer = 0;
            }
            entity.getNavigation().stop();
        } else {
            ticksNearPlayer = 0;
            Vec3 target = getOffsetAroundPlayer(player.position(), angleDeg, 5.0);
            entity.getNavigation().moveTo(target.x, target.y, target.z, 1.0);
        }
    }

    private Vec3 getOffsetAroundPlayer(Vec3 center, float angleDeg, double radius) {
        double angleRad = Math.toRadians(angleDeg);
        double x = center.x + radius * Math.cos(angleRad);
        double z = center.z + radius * Math.sin(angleRad);
        return new Vec3(x, center.y, z);
    }

    private float randomAngle() {
        return Mth.nextFloat(entity.getRandom(), -180, 180);
    }
}
