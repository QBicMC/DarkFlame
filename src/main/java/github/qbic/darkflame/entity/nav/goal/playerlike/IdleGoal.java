package github.qbic.darkflame.entity.nav.goal.playerlike;

import github.qbic.darkflame.entity.SkinWalkerEntity;
import github.qbic.darkflame.entity.nav.PlayerLikeNav;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class IdleGoal extends Goal {
    private final SkinWalkerEntity mob;
    private IdleBehavior currentBehavior = IdleBehavior.WANDERING;
    private int behaviorCooldown = 200;
    private BlockPos targetBlock;
    private double wanderAngle = 0.0;

    public IdleGoal(SkinWalkerEntity mob) {
        this.mob = mob;
        this.currentBehavior = getRandomIdleBehavior();
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.mob.behavior == PlayerLikeNav.Behavior.IDLE && !this.mob.isVehicle() && !this.mob.isInWater() && !this.mob.isInLava();
    }

    @Override
    public void tick() {
        super.tick();
        if (mob.getNavigation().isDone()) {
            if (behaviorCooldown > 0) {
                behaviorCooldown--;
            } else {
                Util.printDBG("Idle Behavior: " + currentBehavior);
                currentBehavior = getRandomIdleBehavior();
                behaviorCooldown = 200;
            }

            switch (currentBehavior) {
                case CRAFTING:
                    mob.setSprinting(false);
                    targetBlock = getNearestBlock(Blocks.CRAFTING_TABLE);
                    if (targetBlock != null) {
                        mob.getNavigation().moveTo(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), 1.0);
                    }
                    break;
                case FISHING:
                    mob.setSprinting(false);
                    targetBlock = getNearestBlock(Blocks.WATER);
                    if (targetBlock != null) {
                        mob.getNavigation().moveTo(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), 1.0);
                    }
                    break;
                case WANDERING:
                    mob.setSprinting(false);
                    mob.getNavigation().moveTo(getNextWanderPath(), 1.0);
                    break;
                case JUMPING_AROUND:
                    mob.getNavigation().moveTo(getNextJumpAroundPath(), 1.0);
                    mob.setSprinting(true);
                    break;
            }
        }

        if (targetBlock != null) {
            Vec3 targetVec = new Vec3(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
            if (mob.distanceToSqr(targetVec) < 4.0) {
                mob.getNavigation().stop();
            }
        }
    }

    @Override
    public void start() {
        super.start();
        Util.printDBG("Starting Idle with Behavior: " + currentBehavior);
        this.currentBehavior = getRandomIdleBehavior();
        if (mob.getNavigation().isStuck()) {
            mob.getNavigation().stop();
        }
    }

    private IdleBehavior getRandomIdleBehavior() {
        return IdleBehavior.values()[this.mob.getRandom().nextInt(IdleBehavior.values().length)];
    }

    private BlockPos getNearestBlock(Block block) {
        BlockPos mobPos = mob.blockPosition();
        Level level = mob.level();

        int radius = 32;
        BlockPos nearest = null;
        double nearestDistSq = Double.MAX_VALUE;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos pos = mobPos.offset(dx, dy, dz);
                    if (level.getBlockState(pos).is(block)) {
                        double distSq = mobPos.distSqr(pos);
                        if (distSq < nearestDistSq) {
                            nearestDistSq = distSq;
                            nearest = pos.immutable();
                        }
                    }
                }
            }
        }

        return nearest;
    }

    private Path getNextJumpAroundPath() {
        BlockPos mobPos = mob.blockPosition();
        int radius = 4;

        for (int i = 0; i < 8; i++) {
            double angle = (2 * Math.PI / 8) * i;

            int x1 = mobPos.getX() + (int) Math.round(radius * Math.cos(angle));
            int z1 = mobPos.getZ() + (int) Math.round(radius * Math.sin(angle));
            BlockPos target1 = new BlockPos(x1, mobPos.getY(), z1);

            Path path1 = mob.getNavigation().createPath(target1, 1);
            if (path1 != null && path1.canReach()) {
                return path1;
            }

            double oppositeAngle = angle + Math.PI;
            int x2 = mobPos.getX() + (int) Math.round(radius * Math.cos(oppositeAngle));
            int z2 = mobPos.getZ() + (int) Math.round(radius * Math.sin(oppositeAngle));
            BlockPos target2 = new BlockPos(x2, mobPos.getY(), z2);

            Path path2 = mob.getNavigation().createPath(target2, 1);
            if (path2 != null && path2.canReach()) {
                return path2;
            }
        }

        return null;
    }

    private Path getNextWanderPath() {
        wanderAngle += mob.getRandom().nextBoolean() ? 10 : -10;
        wanderAngle = Math.max(-60, Math.min(60, wanderAngle));

        Vec3 lookVec = mob.getLookAngle().normalize();

        double radians = Math.toRadians(wanderAngle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double x = lookVec.x * cos - lookVec.z * sin;
        double z = lookVec.x * sin + lookVec.z * cos;

        Vec3 rotatedVec = new Vec3(x, 0, z).normalize();

        BlockPos mobPos = mob.blockPosition();
        BlockPos targetPos = mobPos.offset(
                (int) Math.round(rotatedVec.x * 4),
                0,
                (int) Math.round(rotatedVec.z * 4)
        );

        Path path = mob.getNavigation().createPath(targetPos, 1);
        if (path != null && path.canReach()) {
            return path;
        }

        return null;
    }

    private enum IdleBehavior {
        CRAFTING,
        FISHING,
        WANDERING,
        JUMPING_AROUND
    }
}
