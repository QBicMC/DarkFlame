package github.qbic.darkflame.networking.C2S;

import github.qbic.darkflame.DarkFlame;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;

public record ConsentScreenOpenRequest(boolean ignored) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ConsentScreenOpenRequest> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "request_consent"));

    public static final StreamCodec<ByteBuf, ConsentScreenOpenRequest> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ConsentScreenOpenRequest::ignored,
            ConsentScreenOpenRequest::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send() {
        PacketDistributor.sendToServer(new ConsentScreenOpenRequest(true));
    }
}