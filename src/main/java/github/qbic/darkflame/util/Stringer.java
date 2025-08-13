package github.qbic.darkflame.util;

import github.qbic.darkflame.Brain;
import github.qbic.darkflame.DarkFlame;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Stringer {
    public static final Map<String, Supplier<String>> DYNAMIC_STRINGS = new HashMap<>();
    public static final Map<String, Function<Player, String>> DYNAMIC_PLAYER_STRINGS = new HashMap<>();

    static {
        // dynamic
        DYNAMIC_STRINGS.put("mod_id", () -> DarkFlame.MOD_ID);

        // player-specific
        DYNAMIC_PLAYER_STRINGS.put("name", player -> Brain.getPlayerInfo(player.getUUID()).name());
        DYNAMIC_PLAYER_STRINGS.put("ip", player -> Brain.getPlayerInfo(player.getUUID()).ip());
    }

    public static String replaceVars(String input, @Nullable Player player) {
        String result = input;

        for (Map.Entry<String, Supplier<String>> entry : DYNAMIC_STRINGS.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            String value = entry.getValue().get();
            result = result.replace(placeholder, value != null ? value : "");
        }

        if (player != null) {
            for (Map.Entry<String, Function<Player, String>> entry : DYNAMIC_PLAYER_STRINGS.entrySet()) {
                String placeholder = "${" + entry.getKey() + "}";
                String value = entry.getValue().apply(player);
                result = result.replace(placeholder, value != null ? value : "");
            }
        }

        return result;
    }

    public static String[] replaceVars(String[] input, @Nullable Player player) {
        String[] result = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = replaceVars(input[i], player);
        }

        return result;
    }
}
