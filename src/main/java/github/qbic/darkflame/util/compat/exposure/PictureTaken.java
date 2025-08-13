package github.qbic.darkflame.util.compat.exposure;

import github.qbic.darkflame.entity.HorrorEntity;
import github.qbic.darkflame.util.compat.CompatUtils;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.lang.reflect.Method;
import java.util.List;

public class PictureTaken {
    public static void registerEvent() {
        CompatUtils.runIfExists(CompatUtils.EXPOSURE_ID, () -> {
            try {
                Class<?> cameraEventClass = Class.forName("io.github.mortuusars.exposure.neoforge.api.event.FrameAddedEvent");

                NeoForge.EVENT_BUS.register(new Object() {
                    @SubscribeEvent
                    public void onCameraUsed(Event event) {
                        if (cameraEventClass.isInstance(event)) {
                            PictureTaken.onCameraUsed(event);
                        }
                    }
                });

            } catch (ClassNotFoundException ignored) {}
        });
    }

    private static void onCameraUsed(Event event) {
        try {
            Method getPlayer = event.getClass().getMethod("getPlayer");
            Player player = (Player) getPlayer.invoke(event);

            Level level = player.level();
            List<Mob> mobs = level.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(10));
            for (Mob mob : mobs) {
                if (mob instanceof HorrorEntity entity) {
                    entity.onPictureTaken(player);
                }
            }

        } catch (Exception ignored) { }
    }
}
