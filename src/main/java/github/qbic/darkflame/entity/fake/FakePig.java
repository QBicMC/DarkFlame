package github.qbic.darkflame.entity.fake;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.events.ModEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FakeCow extends Cow implements FakeEntity {
    public FakeCow(EntityType<? extends Cow> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        getAI(this.goalSelector);
    }

    @Override
    public void die(DamageSource damageSource) {
        onDie(damageSource);
        super.die(damageSource);
    }

    @Override
    public PathfinderMob getInstance() {
        return this;
    }
}
