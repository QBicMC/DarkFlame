package github.qbic.darkflame.events.major;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;

public class SpawnFakeMobEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.MAJOR;
    }

    @Override
    public void execute() {
        EntityType<?> type = null;
        switch (RANDOM.nextInt(0, 4)) {
            case 0 -> type = ModEntities.FAKE_VILLAGER.get();
            case 1 -> type = ModEntities.FAKE_PIG.get();
            case 2 -> type = ModEntities.FAKE_CHICKEN.get();
            case 3 -> type = ModEntities.FAKE_COW.get();
        }

        Util.spawnEntityBehindPlayer((ServerPlayer) target(), type, 4, 2, 5);
    }

    @Override
    public String name() {
        return "fake_mob";
    }
}
