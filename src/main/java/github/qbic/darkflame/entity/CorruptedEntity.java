package github.qbic.darkflame.entity;

import github.qbic.darkflame.client.model.animations.AnimationConditions;
import github.qbic.darkflame.entity.nav.goal.LookAtTargetGoal;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.Level;

public class CorruptedEntity extends HorrorEntity {
    public final AnimationState animationState0 = new AnimationState();

    public CorruptedEntity(EntityType<? extends HorrorEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtTargetGoal(this));
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    protected void onClientTick() {
        super.onClientTick();
        this.animationState0.animateWhen(true, this.tickCount);
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
}
