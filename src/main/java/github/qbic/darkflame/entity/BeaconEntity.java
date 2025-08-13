package github.qbic.darkflame.entity;

import github.qbic.darkflame.Brain;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BeaconEntity extends HorrorEntity{
    private Runnable onLookedAt = () -> { };
    private int maxDistance = 50;
    private double lookSensitivity = 0.99;

    public BeaconEntity(EntityType<BeaconEntity> type, Level level) {
        super(type, level);
        setNoAi(true);
        setNoGravity(true);
    }

    public void setOnLookedAt(Runnable onLookedAt) {
        this.onLookedAt = onLookedAt;
    }

    public void setLookSensitivity(double lookSensitivity) {
        this.lookSensitivity = lookSensitivity;
    }

    @Override
    protected double getLookSensitivity() {
        return lookSensitivity;
    }

    @Override
    public int getMaxDistance() {
        return maxDistance;
    }

    public void setDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public void onSeenByPlayer(Player player) {
        if (this.onLookedAt != null) this.onLookedAt.run();
        super.onSeenByPlayer(player);
        this.discard();
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entityIn) { }

    @Override
    protected void pushEntities() { }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) { }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
        builder = builder.add(Attributes.MAX_HEALTH, 1);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 0);
        builder = builder.add(Attributes.STEP_HEIGHT, 0);
        builder = builder.add(Attributes.FLYING_SPEED, 0);

        return builder;
    }
}
