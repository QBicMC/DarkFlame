package github.qbic.darkflame.entity;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.entity.nav.PlayerLikeNav;
import github.qbic.darkflame.entity.nav.goal.playerlike.IdleGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.List;
import java.util.Random;

public class SkinWalkerEntity extends HorrorEntity {
    public static final EntityDataAccessor<String> target = SynchedEntityData.defineId(SkinWalkerEntity.class, EntityDataSerializers.STRING);
    public PlayerLikeNav.Behavior behavior = PlayerLikeNav.Behavior.IDLE;
    public Player stolenPlayer = null;

    public SkinWalkerEntity(EntityType<SkinWalkerEntity> type, Level world) {
        super(type, world);
        xpReward = 0;
        setNoAi(false);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new PlayerLikeNav(this, level());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected boolean canPerformAttack(LivingEntity entity) {
                return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < (this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()) && this.mob.getSensing().hasLineOfSight(entity);
            }
        });
//        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
//        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
//        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
//        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(5, new IdleGoal(this));
    }

    @Override
    public boolean isInvulnerable() {
        return false;
    }

    @Override
    protected void onServerTick() {
        super.onServerTick();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(target, "");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("target", this.entityData.get(target));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("target")) {
            this.entityData.set(target, compound.getString("target"));
        }
    }

    @Override
    protected void onSpawn(ServerLevelAccessor level) {
        stolenPlayer = Brain.getTarget();
        setupArmor();
        setCustomName(stolenPlayer.getDisplayName());
        stolenPlayer.setInvisible(true);
        ((ServerPlayer) stolenPlayer).setGameMode(GameType.ADVENTURE);
        setCustomNameVisible(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 10000);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
        builder = builder.add(Attributes.FOLLOW_RANGE, 256);
        builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
        return builder;
    }

    @Override
    public void remove(RemovalReason p_276115_) {
        if (!level().isClientSide()) {
            if (stolenPlayer != null) {
                stolenPlayer.setInvisible(false);
                ((ServerPlayer) stolenPlayer).setGameMode(GameType.SURVIVAL);
            }
        }
        super.remove(p_276115_);
    }

    private void setupArmor() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        Player _target = Brain.getTarget();

        if (server != null) {
            List<ServerPlayer> players = server.getPlayerList().getPlayers();
            if (!players.isEmpty()) {
                _target = players.get(new Random().nextInt(players.size()));
            }
        }

        if (_target != null) {
            String targetName = _target.getGameProfile().getName();
            this.entityData.set(target, targetName);

            this.setItemSlot(EquipmentSlot.HEAD, _target.getItemBySlot(EquipmentSlot.HEAD).copy());
            this.setItemSlot(EquipmentSlot.CHEST, _target.getItemBySlot(EquipmentSlot.CHEST).copy());
            this.setItemSlot(EquipmentSlot.LEGS, _target.getItemBySlot(EquipmentSlot.LEGS).copy());
            this.setItemSlot(EquipmentSlot.FEET, _target.getItemBySlot(EquipmentSlot.FEET).copy());
            this.setItemSlot(EquipmentSlot.MAINHAND, _target.getItemBySlot(EquipmentSlot.MAINHAND).copy());
            this.setItemSlot(EquipmentSlot.OFFHAND, _target.getItemBySlot(EquipmentSlot.OFFHAND).copy());
        } else {
            this.entityData.set(target, "");
        }
    }
}
