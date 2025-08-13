package github.qbic.darkflame.entity;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.client.model.animations.SingularityAnimation;
import github.qbic.darkflame.client.util.AnimationSequence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SingularityEntity extends HorrorEntity {
    public final AnimationSequence spinAnimation = AnimationSequence.Builder
            .create(() -> true)
            .add(SingularityAnimation.spin_start, 0)
            .add(SingularityAnimation.spin, 76)
            .build();

    public final AnimationSequence siphonAnimation = AnimationSequence.Builder
            .create(() -> isPlayerNear(this.blockPosition(), 25))
            .add(SingularityAnimation.consume, 0)
            .build();

    public SingularityEntity(EntityType<SingularityEntity> type, Level level) {
        super(type, level);
        xpReward = 0;
        setNoAi(false);
        this.noPhysics = true;
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected boolean isAffectedByBlocks() {
        return !this.isRemoved();
    }

    @Override
    protected void doPush(Entity entityIn) { }

    @Override
    protected void pushEntities() { }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) { }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
    }

    @Override
    protected void onClientTick() {
        super.onClientTick();
        spinAnimation.onClientTick(this);
        siphonAnimation.onClientTick(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
        builder = builder.add(Attributes.MAX_HEALTH, 10);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 256);
        builder = builder.add(Attributes.STEP_HEIGHT, 50);
        builder = builder.add(Attributes.FLYING_SPEED, 0.3);
        return builder;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    protected void onServerTick() {
        super.onServerTick();

        LivingEntity target = Brain.getTarget();
        if (target != null && distanceToSqr(target) > 9) {
            Vec3 currentPos = this.position();
            Vec3 targetPos = target.position();

            Vec3 direction = targetPos.subtract(currentPos).normalize();
            double speed = 0.33;

            this.setDeltaMovement(direction.scale(speed));
            this.hasImpulse = true;
        }

        this.noPhysics = true;
    }

    public static boolean isPlayerNear(BlockPos pos, double distance) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null) return false;

        Vec3 center = Vec3.atCenterOf(pos);
        for (AbstractClientPlayer player : client.level.players()) {
            if (player.distanceToSqr(center) < distance * distance) {
                return true;
            }
        }
        return false;
    }
}
