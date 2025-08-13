package github.qbic.darkflame.entity;

import github.qbic.darkflame.entity.nav.goal.LookAtTargetGoal;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class JumpscareEntity extends HorrorEntity {
    protected JumpscareEntity(EntityType<? extends HorrorEntity> type, Level level) {
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
    public void onSeenByPlayer(Player player) {
        super.onSeenByPlayer(player);
        if (player instanceof ServerPlayer sPlayer) {
            Util.broadcastMasterTo(sPlayer, jumpscareSound());
            Util.schedule(this::discard, 10, "despawn jumpscare entity");
        }
    }

    abstract String jumpscareSound();
}
