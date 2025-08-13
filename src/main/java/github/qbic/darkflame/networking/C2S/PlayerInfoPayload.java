package github.qbic.darkflame.networking.C2S;

import github.qbic.darkflame.DarkFlame;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public record PlayerInfoPayload(String name, String ip) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<PlayerInfoPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "player_info"));

    public static final StreamCodec<ByteBuf, PlayerInfoPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            PlayerInfoPayload::name,
            ByteBufCodecs.STRING_UTF8,
            PlayerInfoPayload::ip,
            PlayerInfoPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send(String name, @Nullable String ip) {
        PacketDistributor.sendToServer(new PlayerInfoPayload(name, ip == null ? "" : ip));
    }

    public static void send(String name) {
        PacketDistributor.sendToServer(new PlayerInfoPayload(name, ""));
    }
}
