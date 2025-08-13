package github.qbic.darkflame.entity.jumpscare;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.entity.HorrorEntity;
import github.qbic.darkflame.entity.nav.goal.FollowTargetGoal;
import github.qbic.darkflame.entity.nav.goal.LookAtTargetGoal;
import github.qbic.darkflame.util.Util;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class JumpscareEntity extends HorrorEntity {
    public JumpscareEntity(EntityType<? extends HorrorEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtTargetGoal(this));
        this.goalSelector.addGoal(2, new FollowTargetGoal(this, 2, 1.0f, 1.0f));
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void onSeenByPlayer(Player player) {
        super.onSeenByPlayer(player);
        if (player instanceof ServerPlayer sPlayer) {
            Util.broadcastMasterTo(sPlayer, jumpscareSound());
            Util.schedule(() -> Util.stopAllSounds(sPlayer), 50, "stop sound");
            Util.schedule(this::discard, 10, "despawn jumpscare entity");
            sPlayer.lookAt(EntityAnchorArgument.Anchor.EYES, this, EntityAnchorArgument.Anchor.EYES);
            onSeenServer(sPlayer);
        }
    }

    @Override
    protected void onServerTick() {
        super.onServerTick();
        if (distanceToSqr(Brain.getTarget()) > 64) discard();
    }

    public void onSeenServer(ServerPlayer player) { }

    @Override
    public void onSeenThirdPerson(Player player) {
        if (player instanceof ServerPlayer sPlayer) {
            Util.broadcastMasterTo(sPlayer, jumpscareSound());
            Util.schedule(() -> Util.stopAllSounds(sPlayer), 50, "stop sound");
            Util.schedule(this::discard, 10, "despawn jumpscare entity");
        }
    }

    abstract String jumpscareSound();

    public abstract ResourceLocation texture();

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
