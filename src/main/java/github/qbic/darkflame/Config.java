package github.qbic.darkflame;

import github.qbic.darkflame.util.Util;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = DarkFlame.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue EVENT_FREQUENCY_MULTIPLIER = BUILDER.comment("How often events happen, i.e. 2 means twice as often and 0.5 means half as often").defineInRange("eventFrequency", 1.0, 0.0, Double.MAX_VALUE);
//    private static final ModConfigSpec.IntValue AUTOSAVE_INTERVAL = BUILDER.comment("How often to autosave in ticks, 0 to disable autosave").defineInRange("autosaveInterval", 6000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue CORRUPTION_SUPPRESSION = BUILDER.comment("Corruption will change a max of (ticks / this number) blocks when a chunk is loaded").defineInRange("corruptionSuppression", 48000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue ALLOW_WORLD_CORRUPTION = BUILDER.comment("If the mod can ban you from your world or permanently kick you, rendering it unusable").define("allowBanning", true);
    private static final ModConfigSpec.BooleanValue STREAMER_MODE = BUILDER.comment("The mod will not share or display your real name and location, only your minecraft name. Your location will appear as [REDACTED]").define("streamerMode", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static double eventFrequencyMultiplier = EVENT_FREQUENCY_MULTIPLIER.getDefault();
//    public static int autosaveInterval = 1;
    public static int corruptionSuppression = CORRUPTION_SUPPRESSION.getDefault();
    public static boolean allowWorldCorruption = false;
    public static boolean streamerMode = false;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        eventFrequencyMultiplier = EVENT_FREQUENCY_MULTIPLIER.get();
//        autosaveInterval = AUTOSAVE_INTERVAL.get();
        corruptionSuppression = CORRUPTION_SUPPRESSION.get();
        allowWorldCorruption = ALLOW_WORLD_CORRUPTION.get();
        streamerMode = STREAMER_MODE.get();
        Util.printDBG("Config loaded: eventFrequencyMultiplier=%.2f, corruptionSuppression=%d", eventFrequencyMultiplier, corruptionSuppression);
    }
}
