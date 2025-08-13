package github.qbic.darkflame.compat;

import github.qbic.darkflame.compat.exposure.PictureTaken;
import net.neoforged.fml.ModList;

public class CompatUtils {
    public static final String EXPOSURE_ID = "exposure";

    public static boolean runIfExists(String modid, Runnable runnable) {
        if (ModList.get().isLoaded(modid)) {
            try {
                runnable.run();
            } catch (Exception ignored) {}

            return true;
        }

        return false;
    }

    //TODO: register in main class
    public static void registerCompatEvents() {
        runIfExists(EXPOSURE_ID, PictureTaken::registerEvent);
    }
}
