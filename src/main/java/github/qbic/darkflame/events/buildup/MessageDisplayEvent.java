package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.ModEvent;
import github.qbic.darkflame.networking.S2C.SetOverlayPayload;
import github.qbic.darkflame.util.Util;
import net.minecraft.server.level.ServerPlayer;

public class MessageDisplayEvent extends ModEvent {
    public static final String[] MESSAGES = {
            "behind_you_red.png",
            "behind_you_white.png",
            "i_see_you_red.png",
            "i_see_you_white.png",
            "let_me_out_red.png",
            "let_me_out_white.png",
            "the_end_red.png",
            "the_end_white.png"
    };

    @Override
    public EventType getType() {
        return EventType.BUILDUP;
    }

    @Override
    public void execute() {
        SetOverlayPayload.send(target(), "message/" + MESSAGES[(int) Math.random() * MESSAGES.length], 5);

        ServerPlayer player = (ServerPlayer) target();
        Util.broadcastMasterTo(player, "static_overlay");
        Util.schedule(() -> Util.stopAllSounds(player), 5, "stop sounds");
    }

    @Override
    public String name() {
        return "message_display";
    }
}
