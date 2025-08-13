package github.qbic.darkflame.entity;

import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.common.NeoForgeMod;

import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;

import github.qbic.darkflame.procedures.SmileyReachPlaybackConditionProcedure;
import github.qbic.darkflame.procedures.SmileyOnEntityTickUpdateProcedure;
import github.qbic.darkflame.procedures.SmileyAttackPlaybackConditionProcedure;
import github.qbic.darkflame.procedures.PreventDamageProcedure;

public class SmileyEntity extends Monster {
	public static final EntityDataAccessor<Integer> DATA_attackTimer = SynchedEntityData.defineId(SmileyEntity.class, EntityDataSerializers.INT);
	public final AnimationState animationState0 = new AnimationState();
	public final AnimationState animationState1 = new AnimationState();
	public final AnimationState animationState2 = new AnimationState();

	public SmileyEntity(EntityType<SmileyEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
		this.setPathfindingMalus(PathType.WATER, 0);
		this.moveControl = new MoveControl(this) {
			@Override
			public void tick() {
				if (SmileyEntity.this.isInWater())
					SmileyEntity.this.setDeltaMovement(SmileyEntity.this.getDeltaMovement().add(0, 0.005, 0));
				if (this.operation == MoveControl.Operation.MOVE_TO && !SmileyEntity.this.getNavigation().isDone()) {
					double dx = this.wantedX - SmileyEntity.this.getX();
					double dy = this.wantedY - SmileyEntity.this.getY();
					double dz = this.wantedZ - SmileyEntity.this.getZ();
					float f = (float) (Mth.atan2(dz, dx) * (double) (180 / Math.PI)) - 90;
					float f1 = (float) (this.speedModifier * SmileyEntity.this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
					SmileyEntity.this.setYRot(this.rotlerp(SmileyEntity.this.getYRot(), f, 10));
					SmileyEntity.this.yBodyRot = SmileyEntity.this.getYRot();
					SmileyEntity.this.yHeadRot = SmileyEntity.this.getYRot();
					if (SmileyEntity.this.isInWater()) {
						SmileyEntity.this.setSpeed((float) SmileyEntity.this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
						float f2 = -(float) (Mth.atan2(dy, (float) Math.sqrt(dx * dx + dz * dz)) * (180 / Math.PI));
						f2 = Mth.clamp(Mth.wrapDegrees(f2), -85, 85);
						SmileyEntity.this.setXRot(this.rotlerp(SmileyEntity.this.getXRot(), f2, 5));
						float f3 = Mth.cos(SmileyEntity.this.getXRot() * (float) (Math.PI / 180.0));
						SmileyEntity.this.setZza(f3 * f1);
						SmileyEntity.this.setYya((float) (f1 * dy));
					} else {
						SmileyEntity.this.setSpeed(f1 * 0.05F);
					}
				} else {
					SmileyEntity.this.setSpeed(0);
					SmileyEntity.this.setYya(0);
					SmileyEntity.this.setZza(0);
				}
			}
		};
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_attackTimer, 0);
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new WaterBoundPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, (float) 256));
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
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
		if (damagesource.is(DamageTypes.IN_FIRE))
			return false;
		if (damagesource.getDirectEntity() instanceof AbstractArrow)
			return false;
		if (damagesource.getDirectEntity() instanceof Player)
			return false;
		if (damagesource.getDirectEntity() instanceof ThrownPotion || damagesource.getDirectEntity() instanceof AreaEffectCloud || damagesource.typeHolder().is(NeoForgeMod.POISON_DAMAGE))
			return false;
		if (damagesource.is(DamageTypes.FALL))
			return false;
		if (damagesource.is(DamageTypes.CACTUS))
			return false;
		if (damagesource.is(DamageTypes.DROWN))
			return false;
		if (damagesource.is(DamageTypes.EXPLOSION) || damagesource.is(DamageTypes.PLAYER_EXPLOSION))
			return false;
		if (damagesource.is(DamageTypes.TRIDENT))
			return false;
		if (damagesource.is(DamageTypes.WITHER) || damagesource.is(DamageTypes.WITHER_SKULL))
			return false;
		return super.hurtServer(level, damagesource, amount);
	}

	@Override
	public boolean ignoreExplosion(Explosion explosion) {
		return true;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("DataattackTimer", this.entityData.get(DATA_attackTimer));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("DataattackTimer"))
			this.entityData.set(DATA_attackTimer, compound.getInt("DataattackTimer"));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			this.animationState0.animateWhen(SmileyAttackPlaybackConditionProcedure.execute(this), this.tickCount);
			this.animationState1.animateWhen(SmileyReachPlaybackConditionProcedure.execute(this), this.tickCount);
			this.animationState2.animateWhen(true, this.tickCount);
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		SmileyOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader world) {
		return world.isUnobstructed(this);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 500);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 10);
		builder = builder.add(Attributes.FOLLOW_RANGE, 256);
		builder = builder.add(Attributes.STEP_HEIGHT, 1.2);
		builder = builder.add(NeoForgeMod.SWIM_SPEED, 0.35);
		return builder;
	}
}