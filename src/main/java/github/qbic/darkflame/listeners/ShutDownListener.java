package github.qbic.darkflame.listeners;

import github.qbic.darkflame.client.util.Window;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.GameShuttingDownEvent;

@EventBusSubscriber
public class ShutDownListener {

    @SubscribeEvent
    public static void onShutdown(GameShuttingDownEvent event) {
        Window.show("you didnt even say goodbye", "ERROR", true);
    }
}
