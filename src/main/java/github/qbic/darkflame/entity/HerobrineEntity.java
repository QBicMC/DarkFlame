package github.qbic.darkflame.entity;

import github.qbic.darkflame.entity.nav.goal.LookAtTargetGoal;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.util.Util;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class HerobrineEntity extends HorrorEntity {
    public static int spawns = 0;
    private AIType ai;

    public HerobrineEntity(EntityType<? extends HorrorEntity> type, Level level) {
        super(type, level);
        if (level.isClientSide()) return;

        if (isNoAi()) {
            ai = AIType.NONE;
            return;
        }

        ai = getAIType();
        Util.printDBG("spawned herobrine of type: " + ai + " and spawns: " + spawns);
        spawns++;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void onSeenByPlayer(Player player) {
        super.onSeenByPlayer(player);
        if (player instanceof ServerPlayer sPlayer) {
            if (ai == AIType.JUMPSCARE_LOOK) {
                jumpscare(sPlayer);
            } else if (ai == AIType.DISAPPEAR_LOOK) {
                disappear(sPlayer);
            }
        }
    }

    @Override
    protected double getLookSensitivity() {
        return 0.97;
    }

    @Override
    protected void onServerTick() {
        super.onServerTick();
        if (isPlayerInRange(29)) {
            ServerPlayer player = (ServerPlayer) getNearestPlayer(35);
            switch (ai) {
                case DISAPPEAR -> disappear(player);
                case JUMPSCARE -> jumpscare(player);
                case FOLLOW -> follow(player);
            }
        }
    }

    public void jumpscare(ServerPlayer player) {
        Vec3 targetPos = Util.getPosInFront(player, 3);
        this.teleportTo(targetPos.x, targetPos.y, targetPos.z);
        this.ai = AIType.NONE;
        this.lookAt(EntityAnchorArgument.Anchor.EYES, player.getEyePosition());
        Util.schedule(this::discard, 10, "discard herobrine");
        Util.broadcastMasterToFor(player, "guardian_jumpscare", 10);
    }

    public void disappear(ServerPlayer player) {
        Util.playCaveNoise(player);
        this.discard();
    }

    public void follow(ServerPlayer player) {
        AIType type = AIType.DISAPPEAR_LOOK;
        if (Util.gamble(0.5)) type = AIType.JUMPSCARE_LOOK;

        Spawner.spawnInFront(player, -30).setAi(type);
        if (Util.gamble(0.3)) Util.playCaveNoise(player);

        this.discard();
    }

    public void setAi(AIType aiType) {
        this.ai = aiType;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new LookAtTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 1);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
        builder = builder.add(Attributes.FOLLOW_RANGE, 256);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
        return builder;
    }

    private static AIType getAIType() {
        AIType type = AIType.DISAPPEAR;
        if (spawns < 1) return type;

        if (Util.gamble(0.3)) type = AIType.FOLLOW;
        if (Util.gamble(0.4)) type = AIType.JUMPSCARE;
        return type;
    }

    public enum AIType {
        DISAPPEAR,
        JUMPSCARE,
        FOLLOW,
        DISAPPEAR_LOOK,
        JUMPSCARE_LOOK,
        NONE
    }

    public static class Spawner {
        public static HerobrineEntity spawnInFront(ServerPlayer player, int distance) {
            Level level = player.level();
            if (level.isClientSide) return null;

            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            float yaw = player.getYRot();
            double zOffset = (yaw >= -90 && yaw <= 90) ? distance : -distance;

            double spawnZ = z + zOffset;

            EntityType<HerobrineEntity> type = ModEntities.HEROBRINE.get();
            return (HerobrineEntity) Util.spawnEntityAt(new Vec3(x, y, spawnZ), level, type, false);
        }
    }
}
