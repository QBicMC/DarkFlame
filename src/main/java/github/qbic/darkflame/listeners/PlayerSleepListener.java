package github.qbic.darkflame.listeners;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

@EventBusSubscriber
public class PlayerSleepListener {

    @SubscribeEvent
    public static void onPlayerInBed(CanPlayerSleepEvent event) {
        event.setProblem(Player.BedSleepingProblem.TOO_FAR_AWAY);
    }
}