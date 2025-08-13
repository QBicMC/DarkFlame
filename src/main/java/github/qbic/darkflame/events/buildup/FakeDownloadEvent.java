package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FakeDownloadEvent extends ModEvent {
    public static final String[] SEQUENCE = {
            "establishing connection",
            "downloading dark_flame_controller.jar",
            "extracting data",
            "download complete",
            "running",
            "injecting file dark_flame_§ktrojan§r.class",
            "§cWARNING: FILE CORRUPTED§r, shutting down dark_flame_controller.jar",
            "§cERROR: COULD NOT TERMINATE",
            "§cERROR: COULD NOT TERMINATE",
            "§cERROR: PLEASE RESTART GAME",
            "§cERROR: TH§kE§rY  A§kR§rE TAKING   §kO§rVER",
            "execution complete",
            "file successfully installed, do not restart your game"
    };

    @Override
    public EventType getType() {
        return EventType.UNLISTED;
    }

    @Override
    public void execute() {
        ServerPlayer player = (ServerPlayer) target();
        for (int i = 0; i < SEQUENCE.length; i++) {
            int _i = i;
            Util.schedule(() -> player.sendSystemMessage(Component.literal(SEQUENCE[_i])), RANDOM.nextInt(15, 30) * i, "fake download");
        }
    }

    @Override
    public String name() {
        return "fake_download";
    }
}
