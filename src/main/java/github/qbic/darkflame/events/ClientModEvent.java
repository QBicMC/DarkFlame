package github.qbic.darkflame.events;

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

    @Override
    public String name() {
        return clientEventID();
    }
}
