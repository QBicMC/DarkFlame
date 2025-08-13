package github.qbic.darkflame;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ClientBrain {
    private static boolean noEscape = false;
    private static List<Player> skinwalkerTargets = List.of();

    public static void setNoEscape(boolean noEscape) {
        ClientBrain.noEscape = noEscape;
    }

    public static boolean isNoEscape() {
        return noEscape;
    }

    public static void addSkinwalkerTarget(Player player) {
        if (skinwalkerTargets.contains(player)) return;
        skinwalkerTargets.add(player);
    }

    public static boolean isSkinwalkerTarget(Player player) {
        return skinwalkerTargets.contains(player);
    }

    public static void onClientTick() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) return;
    }
}
