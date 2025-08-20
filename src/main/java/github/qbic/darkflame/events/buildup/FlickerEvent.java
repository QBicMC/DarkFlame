package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.networking.S2C.SetOverlayPayload;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerPlayer;

public class FlickerEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        if (Util.gamble(0.5)) {
            Util.playCaveNoise((ServerPlayer) target());
        } else {
            Util.broadcastMasterTo((ServerPlayer) target(), "exclusion_hack");
            Util.schedule(() -> Util.stopAllSounds((ServerPlayer) target()), 14, "stop sounds");
        }

        for (int i = 0; i < 2; i++) {
            Util.schedule(() -> SetOverlayPayload.send(target(), "overlay/flicker", 3), 7 * i, "flicker");
        }
    }

    @Override
    public String name() {
        return "flicker";
    }
}
