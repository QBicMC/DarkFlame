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

public record ClientNonSystemChatPayload(String msg) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientNonSystemChatPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "non_sys_chat"));

    public static final StreamCodec<ByteBuf, ClientNonSystemChatPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            ClientNonSystemChatPayload::msg,
            ClientNonSystemChatPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send(Player player, String msg) {
        PacketDistributor.sendToPlayer((ServerPlayer) player, new ClientNonSystemChatPayload(msg));
    }
}
