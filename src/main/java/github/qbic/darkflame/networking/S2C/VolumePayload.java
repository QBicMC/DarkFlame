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

public record VolumePayload(double volume) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<VolumePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "volume_payload"));

    public static final StreamCodec<ByteBuf, VolumePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            VolumePayload::volume,
            VolumePayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send(Player player, double volume) {
        PacketDistributor.sendToPlayer((ServerPlayer) player, new VolumePayload(volume));
    }
}