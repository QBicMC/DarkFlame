package github.qbic.darkflame.events;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;

public abstract class ModEvent {
    public static final Random RANDOM = new Random();

    public ModEvent () {
        switch (getType()) {
            case MAJOR -> ModEvents.MAJOR_EVENTS.add(this);
            case MINOR -> ModEvents.MINOR_EVENTS.add(this);
            case BUILDUP -> ModEvents.BUILDUP_EVENTS.add(this);
        }
    }

    public abstract EventType getType();

    public abstract void execute();

    public void executeIfUsable() {
        if (canUse()) execute();
        else Util.printDBG("tried to execute event \"" + name() + "\", but was not usable");
    }

    public Player target() {
        return Brain.getTarget();
    }

    public Level level() {
        return target().level();
    }

    public abstract String name(); //can have two be the same, only for logging

    public boolean canUse() { //can still force execute by calling the execute method directly
        return true;
    }

    public enum EventType {
        BUILDUP,
        MINOR,
        MAJOR,
        UNLISTED
    }

    @Override
    public String toString() {
        return name();
    }
}
