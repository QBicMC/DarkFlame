package github.qbic.darkflame.events.jumpscares;

import github.qbic.darkflame.entity.BeaconEntity;
import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class NameTagEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        BlockPos pos = Util.randomPosAtDistance(target().blockPosition(), 25);
        BeaconEntity entity = Util.spawnBeacon(pos, level(), () -> Util.playCaveNoise((ServerPlayer) target()), 0.8);
        entity.setCustomName(Component.literal("_Xclusion"));
        entity.setCustomNameVisible(true);
    }

    @Override
    public String name() {
        return "name_tag";
    }
}
