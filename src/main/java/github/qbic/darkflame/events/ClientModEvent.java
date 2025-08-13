package github.qbic.darkflame.util;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.events.ModEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public abstract class ClientModEvent extends ModEvent {
    public abstract String clientEventID();

    public ClientModEvent() {
        ModEvents.CLIENT_EVENTS.put(clientEventID(), this);
    }

    @Override
    public EventType getType() {
        return EventType.UNLISTED;
    }

    @Override
    public Player target() {
        return Minecraft.getInstance().player;
    }

    public Minecraft minecraft() {
        return Minecraft.getInstance();
    }
}
