package github.qbic.darkflame.networking.S2C;

import github.qbic.darkflame.DarkFlame;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

public record ClientEventPayload(String id) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientEventPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "client_event"));

    public static final StreamCodec<ByteBuf, ClientEventPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ClientEventPayload::id,
            ClientEventPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send(Player player, String id) {
        PacketDistributor.sendToPlayer((ServerPlayer) player, new ClientEventPayload(id));
    }
}
