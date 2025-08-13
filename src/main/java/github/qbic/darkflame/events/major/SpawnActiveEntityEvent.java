package github.qbic.darkflame.events;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.util.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class SpawnActiveEntityEvent extends ModEvent {

    @Override
    public EventType getType() {
        return EventType.MAJOR;
    }

    @Override
    public void execute() {
        if (!Util.isVanillaDimension(Brain.getLevel())) return;
        switch (Brain.getActiveEntity()) {
            case SMILEY -> spawnSmiley();
            case WATCHER -> spawnWatcher();
            case OBSERVER -> spawnObserver();
        }
    }

    public static void spawnSmiley() {
        Player target = Brain.getTarget();
        BlockPos spawnPos = Util.searchAroundWithBias(target.level(), Util.randomPosAtDistance(target.blockPosition(), 2), target.blockPosition());
        Util.spawnEntityAt(spawnPos, target.level(), ModEntities.SMILEY.get());
    }

    public static void spawnWatcher() {
        Player target = Brain.getTarget();
        BlockPos spawnPos = Util.searchAroundWithBias(target.level(), Util.randomPosInRange(target.blockPosition(), 32, 100), target.blockPosition());
        Util.spawnEntityAt(spawnPos, target.level(), ModEntities.WATCHER.get());
    }

    public static void spawnObserver() {
        Player target = Brain.getTarget();
        BlockPos spawnPos = Util.searchAroundWithBias(target.level(), Util.randomPosInRange(target.blockPosition(), 64, 72), target.blockPosition());
        Util.spawnEntityAt(spawnPos, target.level(), ModEntities.OBSERVER.get());
    }
}
