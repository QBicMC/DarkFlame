package github.qbic.darkflame.entity;

import github.qbic.darkflame.util.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class HorrorEntity extends Monster {
    public VisibilityState visibilityState = VisibilityState.VISIBLE;
    public List<ServerPlayer> seenByPlayers = List.of();

    public HorrorEntity(EntityType<? extends HorrorEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        if (this.level().isClientSide()) {
            onClientTick();
        } else {
            onServerTick();
        }

        super.tick();
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    public abstract boolean isInvulnerable();

    // public abstract void getAnimationResource();

    public void onPictureTaken(Player player) { }

    protected void onSpawn(ServerLevelAccessor level) { }

    public void onSeenByPlayer(Player player) { }

    protected void onSeenByPlayerTick(List<ServerPlayer> players) { }

    protected void onNotSeenTick(VisibilityState visibilityType) { }

    public void onSeenThirdPerson(Player player) { }

    protected void onServerTick() {
        seenByPlayers = Util.getPlayersLookingAt(this, (ServerLevel) this.level(), getMaxDistance(), getLookSensitivity());

        switch (visibilityState) {
            case VISIBLE -> onSeenByPlayerTick(seenByPlayers);
            case BLOCKED -> onNotSeenTick(VisibilityState.BLOCKED);
            case BEHIND -> onNotSeenTick(VisibilityState.BEHIND);
        }
    }

    protected int getMaxDistance() {
        return 50;
    }

    protected void onClientTick() { }

    // a higher number, like 0.9, means the player needs to look directly at the entity to be count as seen
    protected double getLookSensitivity() {
        return 0.5;
    }

    protected List<ResourceKey<DamageType>> immuneTo() {
        return List.of(DamageTypes.ARROW);
    }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damagesource, float amount) {

        AtomicBoolean canceled = new AtomicBoolean(isInvulnerable());

        immuneTo().forEach(damageType -> {
            if (damagesource.is(damageType)) {
                canceled.set(true);
            }
        });

        if (canceled.get() && !damagesource.is(DamageTypes.GENERIC_KILL)) {
            return false;
        } else {
            return super.hurtServer(level, damagesource, amount);
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        onSpawn(level);
        return super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
    }

    public enum VisibilityState {
        VISIBLE,
        BEHIND,
        BLOCKED
    }
}