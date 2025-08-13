package github.qbic.darkflame.events;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.networking.ModVariables;
import github.qbic.darkflame.util.Util;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

public record EventSequence(List<ModEvent> buildup, List<ModEvent> minor, ModEvent major) {
    public void runAll() {
        ModVariables.WorldVariables vars = Brain.worldVars();
        if (vars == null) return;

        double anger = Mth.clamp(vars.anger, 0.0, 1.0);
        double buildupSpacing = 300 * (2 - anger);
        double minorSpacing = 700 * (1.5 - anger / 2);

        int delay = 0;
        for (ModEvent event : buildup) {
            Util.schedule(event::execute, delay, "running event: " + event);
            delay += (int) buildupSpacing;
        }

        for (ModEvent event : minor) {
            delay += (int) minorSpacing;
            Util.schedule(event::execute, delay, "running event: " + event);
        }

        Util.schedule(major::execute, delay + 2400, "running event: " + major);
        Util.printDBG("running sequence with buildup events: " + buildup + ", minor events: " + minor + ", and major event: " + major);
    }

    public static EventSequence create(int buildupEvts, int minorEvts, boolean majorEvent) {
        List<ModEvent> buildup = new ArrayList<>();
        List<ModEvent> minor = new ArrayList<>();
        ModEvent major = ModEvents.BLANK;
        for (int i = 0; i < 6; i++) {
            ModEvent event = ModEvents.MAJOR_EVENTS.get((int) (Math.random() * ModEvents.MAJOR_EVENTS.size()));
            if (event.canUse()) {
                major = majorEvent && Util.gamble(0.3) ? event : ModEvents.BLANK;
                break;
            }
        }


        for (int i = 0; i < buildupEvts; i++) {
            for (int j = 0; j < 6; j++) {
                ModEvent event = ModEvents.BUILDUP_EVENTS.get((int) (Math.random() * ModEvents.BUILDUP_EVENTS.size()));
                if (event.canUse()) {
                    buildup.add(event);
                    break;
                }
            }
        }

        for (int i = 0; i < minorEvts; i++) {
            for (int j = 0; j < 6; j++) {
                ModEvent event = ModEvents.MINOR_EVENTS.get((int) (Math.random() * ModEvents.MINOR_EVENTS.size()));
                if (event.canUse()) {
                    minor.add(event);
                    break;
                }
            }
        }

        return new EventSequence(buildup, minor, major);
    }

    public static EventSequence create(int buildupEvts, int minorEvts) {
        final EventSequence blank = new EventSequence(List.of(), List.of(), ModEvents.BLANK);
        ModVariables.WorldVariables vars = Brain.worldVars();
        if (vars == null) return blank;

        if (vars.halfDays > 3) return create(buildupEvts, minorEvts, true);
        return create(buildupEvts, minorEvts, false);
    }
}
