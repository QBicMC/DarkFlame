package github.qbic.darkflame.entity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;

import github.qbic.darkflame.procedures.WatcherOnEntityTickUpdateProcedure;
import github.qbic.darkflame.procedures.WatcherDisappearPlaybackConditionProcedure;
import github.qbic.darkflame.procedures.PreventDamageProcedure;

public class WatcherEntity extends Monster {
	public static final EntityDataAccessor<Integer> DATA_despawnTimer = SynchedEntityData.defineId(WatcherEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_despawning = SynchedEntityData.defineId(WatcherEntity.class, EntityDataSerializers.BOOLEAN);
	public final AnimationState animationState0 = new AnimationState();
	public final AnimationState animationState2 = new AnimationState();

	public WatcherEntity(EntityType<WatcherEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_despawnTimer, 30);
		builder.define(DATA_despawning, false);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, (float) 6, 1, 1.2));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, (float) 256));
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
	public boolean hurtServer(ServerLevel level, DamageSource damagesource, float amount) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Level world = this.level();
		Entity entity = this;
		Entity sourceentity = damagesource.getEntity();
		Entity immediatesourceentity = damagesource.getDirectEntity();
		if (!PreventDamageProcedure.execute())
			return false;
		return super.hurtServer(level, damagesource, amount);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("DatadespawnTimer", this.entityData.get(DATA_despawnTimer));
		compound.putBoolean("Datadespawning", this.entityData.get(DATA_despawning));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("DatadespawnTimer"))
			this.entityData.set(DATA_despawnTimer, compound.getInt("DatadespawnTimer"));
		if (compound.contains("Datadespawning"))
			this.entityData.set(DATA_despawning, compound.getBoolean("Datadespawning"));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			this.animationState0.animateWhen(true, this.tickCount);
			this.animationState2.animateWhen(WatcherDisappearPlaybackConditionProcedure.execute(this), this.tickCount);
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		WatcherOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
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