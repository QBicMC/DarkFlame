package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class SpawnVillagerInAirEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        Player target = Brain.getTarget();
        BlockPos spawnPos = Util.randomAirPosBehindPlayer(target.level(), target, 32, 32);
        if (spawnPos == null) return;
        Util.spawnEntityAt(new Vec3(spawnPos), target.level(), EntityType.VILLAGER, true);
    }

    @Override
    public String name() {
        return "floating_villager";
    }
}
