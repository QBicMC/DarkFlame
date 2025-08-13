package github.qbic.darkflame.entity;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.client.model.animations.AnimationConditions;
import github.qbic.darkflame.events.ModEvents;
import github.qbic.darkflame.util.Util;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.Comparator;

public class SmileyEntity extends HorrorEntity {
	public static final EntityDataAccessor<Integer> attackTimer = SynchedEntityData.defineId(SmileyEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> isAggressive = SynchedEntityData.defineId(SmileyEntity.class, EntityDataSerializers.BOOLEAN);
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
				if (SmileyEntity.this.isInWater()) {
					SmileyEntity.this.setDeltaMovement(SmileyEntity.this.getDeltaMovement().add(0, 0.005, 0));
				}

				if (this.operation == Operation.MOVE_TO && !SmileyEntity.this.getNavigation().isDone()) {
					double dx = this.wantedX - SmileyEntity.this.getX();
					double dy = this.wantedY - SmileyEntity.this.getY();
					double dz = this.wantedZ - SmileyEntity.this.getZ();

					float f = (float) (Mth.atan2(dz, dx) * (180 / Math.PI)) - 90;
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
    public void onSeenByPlayer(Player player) {
		getEntityData().set(isAggressive, true, true);
		if (distanceToSqr(player) > 18) {
			this.discard();
		}

		super.onSeenByPlayer(player);
	}

	@Override
	protected double getLookSensitivity() {
		return 0.9;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(attackTimer, 0);
		builder.define(isAggressive, false);
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
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("attackTimer", this.entityData.get(attackTimer));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("attackTimer"))
			this.entityData.set(attackTimer, compound.getInt("attackTimer"));
	}

	@Override
	protected void onClientTick() {
		this.animationState0.animateWhen(AnimationConditions.smileyAttack(this), this.tickCount);
		this.animationState1.animateWhen(AnimationConditions.smileyReach(this), this.tickCount);
		this.animationState2.animateWhen(true, this.tickCount);
	}

	@Override
	public boolean isInvulnerable() {
		return true;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		tick(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader world) {
		return world.isUnobstructed(this);
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

	public boolean attacking = false;
	public void tick(LevelAccessor world, double x, double y, double z, SmileyEntity entity) {
		if (entity == null) return;
		Player target = (Player) findEntityInWorldRange(world, Player.class, x, y, z, 256);

		if (target != null) {
			entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((target.getX()), (target.getY()), (target.getZ())));
		}

		target = (Player) findEntityInWorldRange(world, Player.class, x, y, z, 4);
		if (target != null && entity.getEntityData().get(isAggressive)) {
			if (!attacking && !level().isClientSide()) {
				Util.broadcastMasterTo((ServerPlayer) target, "attack");
				attacking = true;
			}
			ModEvents.APPLY_SHADER_EVENT.execute(DarkFlame.MOD_ID + ":pixelate");

			target.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(x, (y + 3.5 - (entity instanceof SmileyEntity _datEntI ? _datEntI.getEntityData().get(SmileyEntity.attackTimer) : 0) / 60), z));
			target.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0, 0, 0));
			target.setDeltaMovement(new Vec3(0, 0, 0));

			if (entity instanceof SmileyEntity datEntSetI) {
				datEntSetI.getEntityData().set(SmileyEntity.attackTimer, (int) ((entity instanceof SmileyEntity _datEntI ? _datEntI.getEntityData().get(SmileyEntity.attackTimer) : 0) + 1));
			}

			if ((entity instanceof SmileyEntity datEntI ? datEntI.getEntityData().get(SmileyEntity.attackTimer) : 0) > 110) {
				if (target instanceof LivingEntity livingEntity) {
					livingEntity.setHealth(0);
				}

				if (!entity.level().isClientSide()) {
					entity.discard();
				}
			}
		} else {
			if (entity instanceof SmileyEntity datEntSetI) {
				datEntSetI.getEntityData().set(SmileyEntity.attackTimer, 0);
			}
		}
	}

	private static Entity findEntityInWorldRange(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		return world.getEntitiesOfClass(clazz,
				AABB.ofSize(
						new Vec3(x, y, z),
						range,
						range,
						range),
				e -> true)
				.stream()
				.sorted(Comparator.comparingDouble(
						e -> e.distanceToSqr(x, y, z)
				)).findFirst().orElse(null);
	}
}