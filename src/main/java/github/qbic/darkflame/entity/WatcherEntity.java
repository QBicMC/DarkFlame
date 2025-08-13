package github.qbic.darkflame.entity;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.client.model.animations.AnimationConditions;
import github.qbic.darkflame.entity.nav.goal.ChaseEntityGoal;
import github.qbic.darkflame.entity.nav.goal.LookAtTargetGoal;
import github.qbic.darkflame.init.ModSounds;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class WatcherEntity extends HorrorEntity {
	public int despawnTimer = 10;
	public final AnimationState animationState0 = new AnimationState();
	public final AnimationState animationState2 = new AnimationState();

	public WatcherEntity(EntityType<WatcherEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 24, 1, 1.2));
		this.goalSelector.addGoal(3, new LookAtTargetGoal(this));
	}

	@Override
    public void onSeenByPlayer(Player player) {
		super.onSeenByPlayer(player);
		this.goalSelector.addGoal(1, new ChaseEntityGoal(this, Brain.getTarget(), 2.2, 500));
		Util.schedule(this::discard, 30, "discard watcher");
	}

	@Override
	protected void onSpawn(ServerLevelAccessor level) {
		super.onSpawn(level);
		if (getRandom().nextDouble() < 0.3 && !level.isClientSide()) {
			Util.broadcastMasterTo((ServerPlayer) Brain.getTarget(), ModSounds.WATCHER_SPAWN);
		}
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(ResourceLocation.parse("entity.generic.death"));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			this.animationState0.animateWhen(true, this.tickCount);
			this.animationState2.animateWhen(AnimationConditions.disappear(this), this.tickCount);
		}
	}

	@Override
	public boolean isInvulnerable() {
		return true;
	}

	@Override
	public void onPictureTaken(Player player) {
		this.discard();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 256);
		builder = builder.add(Attributes.STEP_HEIGHT, 1.2);
		return builder;
	}
}