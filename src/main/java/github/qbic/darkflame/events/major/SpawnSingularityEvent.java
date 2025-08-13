package github.qbic.darkflame.events.major;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.events.ModEvents;
import github.qbic.darkflame.events.client.SingularityAttackClient;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class SpawnSingularityEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.MAJOR;
    }

    @Override
    public void execute() {
        BlockPos spawnPos = Util.randomAirPosBehindPlayer(target().level(), target(), 60, 10);
        Entity entity = Util.spawnEntityAt(spawnPos, target().level(), ModEntities.SINGULARITY.get());
        ModEvents.SINGULARITY_ATTACK_EVENT.execute();
        Util.schedule(() -> {
            entity.discard();
            Util.safeDisconnect(target(), Component.translatable("dark_flame.singularity.disconnect"));
        }, SingularityAttackClient.TIME_TICKS + 5);
    }

    @Override
    public String name() {
        return "spawn_singularity";
    }
}
