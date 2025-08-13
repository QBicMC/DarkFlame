package github.qbic.darkflame.util;

import github.qbic.darkflame.Brain;
import net.minecraft.world.entity.player.Player;

public abstract class ModEvent {

    public ModEvent () {
        switch (getType()) {
            case MAJOR -> ModEvents.MAJOR_EVENTS.add(this);
            case MINOR -> ModEvents.MINOR_EVENTS.add(this);
            case BUILDUP -> ModEvents.BUILDUP_EVENTS.add(this);
        }
    }

    public abstract EventType getType();

    public abstract void execute();

    public Player target() {
        return Brain.getTarget();
    }

    public enum EventType {
        BUILDUP,
        MINOR,
        MAJOR,
        UNLISTED
    }
}
