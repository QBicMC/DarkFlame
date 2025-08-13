package github.qbic.darkflame.listeners;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.util.Util;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber
public class ServerTickListener {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        Brain.tickServer();
        Util.Scheduler.update();
    }
}
