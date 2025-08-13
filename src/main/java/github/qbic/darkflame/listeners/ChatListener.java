package github.qbic.darkflame.listeners;

import github.qbic.darkflame.util.ChatResponder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;

@EventBusSubscriber
public class ChatListener {

    @SubscribeEvent
    public static void onChatMessage(ServerChatEvent event) {
        ChatResponder.respondTo(event.getMessage().getString(), event.getPlayer());
    }
}
