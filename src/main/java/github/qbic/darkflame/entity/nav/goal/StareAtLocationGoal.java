package github.qbic.darkflame.entity.nav.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class StareAtLocationGoal extends Goal {
    private final Mob mob;
    private final Vec3 location;
    private int ticks = 0;

    public StareAtLocationGoal(Mob mob, Vec3 location) {
        this.mob = mob;
        this.location = location;
    }

    @Override
    public void start() {
        mob.getLookControl().setLookAt(location);
        ticks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        mob.getLookControl().setLookAt(location);
    }

    @Override
    public boolean canUse() {
        return true;
    }
}
