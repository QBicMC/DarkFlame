package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.CountDownEvent;
import net.minecraft.server.level.ServerLevel;

public class CountDownToNightEvent extends CountDownEvent {
    @Override
    public String endingMessage() {
        return "HIDE";
    }

    @Override
    public void executeAfterCountdown() {
        serverTarget().serverLevel().setDayTime(17000);
    }

    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public String name() {
        return "countdown_night";
    }

    @Override
    public boolean canUse() {
        return target().level().isDay();
    }
}
