package github.qbic.darkflame.events.dimension.hallways;

import github.qbic.darkflame.entity.HerobrineEntity;
import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class HallwaysSpawnHerobrineEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.UNLISTED;
    }

    @Override
    public void execute() {
        HerobrineEntity.Spawner.spawnInFront(Util.getRandomPlayerInDimension(target().getServer().getLevel(Util.createDimResourceKey("the_hallways"))), 64);
    }

    @Override
    public String name() {
        return "spawn_herobrine";
    }
}
