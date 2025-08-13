package github.qbic.darkflame.util;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ChatResponder {
    public static final String[] RESPONSES = {
            "where are you", "behind you",
            "what's your name", "name.guardian",
            "what can you do", "kill you"
    };

    public static final String[] GENERIC_RESPONSES = {
            "§cerr.kill.access_denied§r"
    };

    public static final Map<String, String[]> COMPLEX_RESPONSES = Map.of(
            "your name", new String[]{"name.get()", "guardian"},
            "what can you do", new String[]{"kill you", "poop"}
    );

    public static final Map<String, Consumer<ServerPlayer>> TRIGGER_RESPONSES = Map.of(
            "hello", (player) -> {
                msg(player, "you are stupid", 40);
            }
    );

    public static String getResponse(String input) {
        for (int i = 0; i < RESPONSES.length; i += 2) {
            if (input.toLowerCase().contains(RESPONSES[i])) {
                return RESPONSES[i + 1];
            }
        }

        return GENERIC_RESPONSES[(int) (Math.random() * GENERIC_RESPONSES.length)];
    }

    public static void respondTo(String input, ServerPlayer player) {
        String response = getResponse(input);

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            List<ServerPlayer> players = server.getPlayerList().getPlayers();
            players.forEach((p) -> {
                if (input.toLowerCase().contains(p.getDisplayName().getString().toLowerCase())) {
                    msg(player, "dont trust " + p.getDisplayName().getString().toLowerCase(), 20);
                }
            });
        }
        for (Map.Entry<String, String[]> entry : COMPLEX_RESPONSES.entrySet()) {
            if (input.toLowerCase().contains(entry.getKey())) {
                String[] responses = entry.getValue();
                for (int i = 0; i < responses.length; i++) {
                    msg(player, responses[i], (i + 1) * 40);
                }
                return;
            }
        }

        for (Map.Entry<String, Consumer<ServerPlayer>> entry : TRIGGER_RESPONSES.entrySet()) {
            if (input.toLowerCase().contains(entry.getKey())) {
                entry.getValue().accept(player);
                return;
            }
        }
        msg(player, response, 40);
    }

    public static void msg(ServerPlayer player, String message, int delay) {
        Util.schedule(() -> Util.chatAsFakePlayer(player.serverLevel(), "guardian", message), delay, "guardian chat response");
    }

    public static void sysMsg(ServerPlayer player, String message, int delay) {
        Util.schedule(() -> player.sendSystemMessage(Component.literal(message)), delay, "chat response");
    }
}
