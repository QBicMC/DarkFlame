package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.core.BlockPos;

public class NameTagEvent extends ModEvent {
    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        BlockPos pos = Util.randomPosAtDistance()
    }

    @Override
    public String name() {
        return "";
    }
}
