package github.qbic.darkflame.events;

import github.qbic.darkflame.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;

public abstract class CountDownEvent extends ModEvent {
    @Override
    public void execute() {
        int count = count();
        for (int i = 0; i < count; i++) {
            int _i = i;
            Util.schedule(() -> {
                Util.broadcastToNoStop(serverTarget(), "bell");
                ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(Component.literal(String.valueOf(count - _i)));
                serverTarget().connection.send(packet);
            }, i * delayTicks(), "countdown: " + (count - i));
        }

        Util.schedule(() -> {
            Util.broadcastToNoStop(serverTarget(), "bell");
            ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(Component.literal(endingMessage()));
            serverTarget().connection.send(packet);
            executeAfterCountdown();
        }, delayTicks() * count + 20, "run after countdown");
    }

    public abstract String endingMessage();

    public abstract void executeAfterCountdown();

    public int count() {
        return 10;
    }

    public int delayTicks() {
        return 60;
    }

    public ServerPlayer serverTarget() {
        return (ServerPlayer) target();
    }
}
