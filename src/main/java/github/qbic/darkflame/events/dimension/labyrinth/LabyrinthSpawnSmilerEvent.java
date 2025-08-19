package github.qbic.darkflame.events.dimension.labyrinth;

import github.qbic.darkflame.entity.HerobrineEntity;
import github.qbic.darkflame.entity.SmilerEntity;
import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;

public class LabyrinthSpawnSmilerEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.UNLISTED;
    }

    @Override
    public void execute() {
        SmilerEntity.Spawner.spawnAround(Util.getRandomPlayerInDimension(target().getServer().getLevel(Util.createDimResourceKey("the_hallways"))));

    }

    @Override
    public String name() {
        return "spawn_smiler";
    }
}
