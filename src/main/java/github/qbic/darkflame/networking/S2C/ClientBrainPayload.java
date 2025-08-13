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

public record ClientBrainPayload(boolean noEscape) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientBrainPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "client_brain_payload"));

    public static final StreamCodec<ByteBuf, ClientBrainPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ClientBrainPayload::noEscape,
            ClientBrainPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send(Player player, boolean noEscape) {
        PacketDistributor.sendToPlayer((ServerPlayer) player, new ClientBrainPayload(noEscape));
    }
}
