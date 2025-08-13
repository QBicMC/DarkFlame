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

public record StringClientEventPayload(String id, String arg) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<StringClientEventPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "string_client_event"));

    public static final StreamCodec<ByteBuf, StringClientEventPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            StringClientEventPayload::id,
            ByteBufCodecs.STRING_UTF8,
            StringClientEventPayload::arg,
            StringClientEventPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send(Player player, String id, String arg) {
        PacketDistributor.sendToPlayer((ServerPlayer) player, new StringClientEventPayload(id, arg));
    }
}
