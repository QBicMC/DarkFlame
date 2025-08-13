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

public record SetOverlayPayload(String resourceLocation, int x, int y, int width, int height, int ticks) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SetOverlayPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "set_overlay"));

    public static final StreamCodec<ByteBuf, SetOverlayPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SetOverlayPayload::resourceLocation,
            ByteBufCodecs.INT,
            SetOverlayPayload::x,
            ByteBufCodecs.INT,
            SetOverlayPayload::y,
            ByteBufCodecs.INT,
            SetOverlayPayload::width,
            ByteBufCodecs.INT,
            SetOverlayPayload::height,
            ByteBufCodecs.INT,
            SetOverlayPayload::ticks,
            SetOverlayPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send(Player player, String id, int x, int y, int width, int height) {
        PacketDistributor.sendToPlayer((ServerPlayer) player, new SetOverlayPayload(id, x, y, width, height, 0));
    }

    public static void send(Player player, String id, int x, int y, int width, int height, int ticks) {
        PacketDistributor.sendToPlayer((ServerPlayer) player, new SetOverlayPayload(id, x, y, width, height, ticks));
    }
}
