package github.qbic.darkflame.events.major;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.init.ModEntities;
import github.qbic.darkflame.networking.S2C.ClientEventPayload;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerPlayer;

public class IAMINYOURSYSTEMActivatorEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.MAJOR;
    }

    @Override
    public void execute() {
        ServerPlayer player = (ServerPlayer) target();
        Util.broadcastMasterTo(player, "exclusion_hack");
        Util.spawnEntityBehindPlayer(player, ModEntities.EXCLUSION_JUMPSCARE.get(), 3, 2);
        ClientEventPayload.send(target(), "in_your_system");
    }

    @Override
    public String name() {
        return "IAMINYOURSYSTEM";
    }
}
