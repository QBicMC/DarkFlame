package github.qbic.darkflame.entity;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.client.model.animations.AnimationConditions;
import github.qbic.darkflame.entity.nav.goal.LookAtTargetGoal;
import github.qbic.darkflame.init.ModSounds;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;

public class ObserverEntity extends HorrorEntity {
	public int despawnTimer = 10;
	public static final EntityDataAccessor<Boolean> despawning = SynchedEntityData.defineId(ObserverEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> target = SynchedEntityData.defineId(ObserverEntity.class, EntityDataSerializers.STRING);
	public final AnimationState animationStateIdle = new AnimationState();
	public final AnimationState animationStateDisappear = new AnimationState();
	public AI ai = Util.randomEnum(AI.class);

	public ObserverEntity(EntityType<ObserverEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(despawning, false);
		builder.define(target, "");
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(3, new LookAtTargetGoal(this));
		initSpecialGoals();
//		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 64.0f));
	}

	public void initSpecialGoals() {
		if (this.ai == AI.AVOID_PLAYER) {
			this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 32.0f, 1.0f, 1.2f));
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
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("despawning", this.entityData.get(despawning));
		compound.putString("target", this.entityData.get(target));
	}

	@Override
	protected float tickHeadTurn(float p_21538_, float p_21539_) {
		return super.tickHeadTurn(p_21538_, p_21539_);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("despawning")) {
			this.entityData.set(despawning, compound.getBoolean("despawning"));
		}

		if (compound.contains("target")) {
			this.entityData.set(target, compound.getString("target"));
		}
	}

	@Override
	protected void onClientTick() {
		this.animationStateIdle.animateWhen(true, this.tickCount);
		this.animationStateDisappear.animateWhen(AnimationConditions.disappear(this), this.tickCount);

		super.onClientTick();
	}

	@Override
	protected void onSpawn(ServerLevelAccessor level) {
		super.onSpawn(level);
		if (getRandom().nextDouble() < 0.5 && !level.isClientSide()) {
			Util.broadcastMasterTo((ServerPlayer) Brain.getTarget(), ModSounds.OBSERVER_SPAWN);
		}
	}

	@Override
	protected void onServerTick() {
		String targetName = Brain.getTarget().getName().getString();
		Player playerTarget = getTarget();
		if (!targetName.equals(this.getEntityData().get(target))) {
			this.getEntityData().set(target, targetName, true);
		}

		if (ai.equals(AI.WATCH_PLAYER) && playerTarget != null && distanceToSqr(playerTarget) < 64) {
			discard();
		}

		super.onServerTick();
	}

	@Override
	public boolean isInvulnerable() {
		return true;
	}

	@Override
	public void onPictureTaken(Player player) {
		this.discard();
	}

	@Override
    public void onSeenByPlayer(Player player) {
		super.onSeenByPlayer(player);
		Util.broadcastMasterTo((ServerPlayer) player, Util.chooseRandom(
				ModSounds.BELLS,
				ModSounds.MUSIC_BOX
		));
		if (this.ai == AI.DISAPPEAR) {
			this.discard();
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
		tick(this.level(), this.getX(), this.getY(), this.getZ());
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

	public void tick(LevelAccessor world, double x, double y, double z) {
		if (findEntityInWorldRange(world, Player.class, x, y, z, 15) != null) {
			getEntityData().set(despawning, true);
		}

		if (getEntityData().get(despawning)) {
			despawnTimer--;
		}

		if (despawnTimer < 0 && !level().isClientSide()) {
			this.discard();
		}
	}

	private static Entity findEntityInWorldRange(LevelAccessor world, Class<? extends Entity> clazz, double x, double y, double z, double range) {
		return world.getEntitiesOfClass(clazz, AABB.ofSize(new Vec3(x, y, z), range, range, range), e -> true).stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(x, y, z))).orElse(null);
	}

	@Nullable
	public Player getTarget() {
		String targetName = this.getEntityData().get(target);
		if (targetName.isEmpty()) {
			return null;
		}
		return Util.getPlayerFromName(level(), targetName);
	}

	public enum AI {
		WATCH_PLAYER,
		AVOID_PLAYER,
		DISAPPEAR
	}
}