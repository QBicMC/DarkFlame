package github.qbic.darkflame.entity;

import github.qbic.darkflame.client.util.ClientUtil;
import github.qbic.darkflame.entity.nav.goal.LookAtTargetGoal;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.networking.S2C.SetOverlayPayload;
import github.qbic.darkflame.util.Util;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class SmilerEntity extends HorrorEntity {
    public static final EntityDataAccessor<Boolean> isAngry = SynchedEntityData.defineId(SmilerEntity.class, EntityDataSerializers.BOOLEAN);
    private int blackoutTimer = 0;
    private int tickCounter = 0;
    private boolean attacking = false;

    public SmilerEntity(EntityType<SmilerEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new LookAtTargetGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(isAngry, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("isAngry", isAngry());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("isAngry"))
            this.entityData.set(isAngry, compound.getBoolean("isAngry"));
    }

    @Override
    protected void onServerTick() {
        super.onServerTick();
        ServerPlayer player = (ServerPlayer) getNearestPlayer(50);
        if (player == null) {
            this.discard();
            return;
        }

        double distance = distanceToSqr(player);

        if (distance < 16) {
            this.getEntityData().set(isAngry, false);
            SetOverlayPayload.send(player, "smiler/blackout.png", 0, 0, 1280, 720, 15);
            Util.broadcastMasterTo(player, "base_blast");
            Util.schedule(() -> {
                Util.stopAllSounds(player);
                this.discard();
                Util.teleportToSpawn(player);
            }, 10, "exit dimension");
        } else if (distance < 225) {
            this.getEntityData().set(isAngry, true);

            if (distance < 100) {
                if (tickCounter % 20 == 0) {
                    Util.broadcastMasterTo(player, "heartbeat");
                }
            } else {
                if (tickCounter % 40 == 0) {
                    Util.broadcastMasterTo(player, "heartbeat");
                }
            }
        }
    }

    @Override
    protected void onClientTick() {
        super.onClientTick();

        LocalPlayer player = (LocalPlayer) getNearestPlayer(50);
        if (player == null) return;

        double distance = distanceToSqr(player);
        if (isAngry()) {
            if (blackoutTimer > 0) {
                blackoutTimer--;
            } else {
                ClientUtil.setImageFullscreen(ResourceLocation.parse("dark_flame:textures/img/smiler/red.png"));

                if (Util.gamble(0.01 * ((50 - distance) / 10))) {
                    blackoutTimer = 10;
                    ClientUtil.setImageFullscreen(ResourceLocation.parse("dark_flame:textures/img/smiler/blackout.png"));
                }
            }
        }
    }

    public boolean isAngry() {
        return this.entityData.get(isAngry);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 1);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 0);
        builder = builder.add(Attributes.STEP_HEIGHT, 0);
        builder = builder.add(Attributes.FLYING_SPEED, 0);

        return builder;
    }

    public static class Spawner {
        public static BlockPos findSpawnPos(ServerPlayer player) {
            ServerLevel level = player.serverLevel();
            BlockPos playerPos = player.blockPosition();

            for (int i = 0; i < 30; i++) {
                double angle = player.getRandom().nextDouble() * (2 * Math.PI);
                int x = playerPos.getX() + (int) Math.round(Math.cos(angle) * 25);
                int z = playerPos.getZ() + (int) Math.round(Math.sin(angle) * 25);
                int y = playerPos.getY();
                BlockPos pos = new BlockPos(x, y, z);

                if (level.getBlockState(pos).isAir()) {
                    return pos;
                }
            }
            return null;
        }

        public static void spawnAround(ServerPlayer player) {
            BlockPos pos = findSpawnPos(player);
            if (pos == null) return;

            Util.spawnEntityAt(pos, player.server.getLevel(Util.createDimResourceKey("the_hallways")), ModEntities.SMILER.get());
        }
    }
}
