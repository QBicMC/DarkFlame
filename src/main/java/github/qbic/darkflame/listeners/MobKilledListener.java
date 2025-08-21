package github.qbic.darkflame.listeners;

import github.qbic.darkflame.Brain;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class MobKilledListener {

    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer) {
            Brain.worldVars().anger += 0.01;
            if (Brain.worldVars().anger > 1.0) Brain.worldVars().anger = 1.0;
            Brain.syncWorldVars();
        }
    }
}
