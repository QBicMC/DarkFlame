package github.qbic.darkflame.events.minor;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PullDownEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.MINOR;
    }

    @Override
    public void execute() {
        Player target = target();
        Util.playCaveNoise((ServerPlayer) target);
        target.teleportRelative(0, -1, 0);
        Util.schedule(() -> target.teleportRelative(0, -1, 0), 20, "pull player down");
    }

    @Override
    public String name() {
        return "pull_down";
    }
}
