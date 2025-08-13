package github.qbic.darkflame.events.minor;

import github.qbic.darkflame.events.ModEvent;
import net.minecraft.server.level.ServerLevel;

public class DamagePlayerEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.MINOR;
    }

    @Override
    public void execute() {
        target().hurtServer((ServerLevel) target().level(), target().damageSources().generic(), 1.0F);
        target().push(Math.random() / 8, 0.1, Math.random() / 8);
    }

    @Override
    public String name() {
        return "damage_player";
    }
}
