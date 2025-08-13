package github.qbic.darkflame.events.buildup;

import github.qbic.darkflame.events.SingleUseEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerPlayer;

public class JumpMessageEvent extends SingleUseEvent {
    @Override
    public void execute() {
        super.execute();
        ServerPlayer player = ((ServerPlayer) target());
        player.connection.send(new ClientboundSetTitlesAnimationPacket(0, 20, 0));
        player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("JUMP")));
        Util.broadcastMasterTo(player, "static_overlay");
        Util.schedule(() -> Util.stopAllSounds(player), 20, "stop sound");
    }

    @Override
    public EventType getType() {
        return EventType.UNLISTED;
    }

    @Override
    public String name() {
        return "jump_message";
    }
}
