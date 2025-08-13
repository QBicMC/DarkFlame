package github.qbic.darkflame.networking.C2S;

import github.qbic.darkflame.DarkFlame;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public record ChangePerspectivePayload(int perspective) implements CustomPacketPayload {
    public static final Type<ChangePerspectivePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "change_perspective"));

    public static final StreamCodec<ByteBuf, ChangePerspectivePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ChangePerspectivePayload::perspective,
            ChangePerspectivePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send(int perspective) {
        PacketDistributor.sendToServer(new ChangePerspectivePayload(perspective));
    }
}