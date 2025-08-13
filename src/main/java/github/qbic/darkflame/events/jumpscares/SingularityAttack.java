package github.qbic.darkflame.events.jumpscares;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.networking.S2C.ClientEventPayload;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class SingularityAttack extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.UNLISTED;
    }

    @Override
    public void execute() {
        if (target().level() instanceof ServerLevel level) {
            level.setDayTime(17000);
            Util.broadcastToNoStop((ServerPlayer) target(), "singularity_jumpscare");
            Util.broadcastToNoStop((ServerPlayer) target(), "singularity_jumpscare_overlay");
            ClientEventPayload.send(target(), "singularity_attack");
        }
    }

    @Override
    public String name() {
        return "singularity_attack";
    }
}
