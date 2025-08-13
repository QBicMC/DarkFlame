package github.qbic.darkflame.events;

import github.qbic.darkflame.util.ModEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

public class DespawnMobsAroundEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        BlockPos playerPos = target().blockPosition();
        int radius = 64;

        target().level().getEntitiesOfClass(Mob.class, new AABB(playerPos).inflate(radius))
            .forEach(mob -> {
                if (mob != null && !mob.isRemoved()) {
                    mob.discard();
                }
            });
    }
}
