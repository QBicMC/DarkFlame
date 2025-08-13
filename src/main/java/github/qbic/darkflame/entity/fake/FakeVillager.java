package github.qbic.darkflame.entity.fake;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

public class FakeVillager extends Villager implements FakeEntity {
    public FakeVillager(EntityType<? extends Villager> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        getAI(this.goalSelector);
    }

    @Override
    public int getDespawnTime() {
        return 2000;
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

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return (Brain<Villager>) this.brainProvider().makeBrain(dynamic);
    }

    public void refreshBrain(ServerLevel serverLevel) {
        Brain<Villager> brain = this.getBrain();
        brain.stopAll(serverLevel, this);
        this.brain = brain.copyWithoutBehaviors();
    }

    @Override
    public void tick() {
        onTick();
        super.tick();
    }
}
