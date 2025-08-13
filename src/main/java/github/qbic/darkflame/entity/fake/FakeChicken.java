package github.qbic.darkflame.entity.fake;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;

public class FakeChicken extends Chicken implements FakeEntity {
    public FakeChicken(EntityType<? extends Chicken> type, Level level) {
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

    @Override
    public void tick() {
        onTick();
        super.tick();
    }
}
