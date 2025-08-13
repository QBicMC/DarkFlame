package github.qbic.darkflame.networking.C2S;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.client.util.consentneeded.ConsentManager;
import github.qbic.darkflame.client.util.consentneeded.NameFetcher;
import github.qbic.darkflame.util.Util;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

// another mcreator thing
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record ConsentButtonPayload(int buttonID, int x, int y, int z) implements CustomPacketPayload {

    public static final Type<ConsentButtonPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "consent_gui_buttons"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ConsentButtonPayload> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, ConsentButtonPayload message) -> {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }, (RegistryFriendlyByteBuf buffer) -> new ConsentButtonPayload(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
    @Override
    public Type<ConsentButtonPayload> type() {
        return TYPE;
    }

    public static void handleData(final ConsentButtonPayload message, final IPayloadContext context) {
        if (context.flow() == PacketFlow.SERVERBOUND) {
            context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
                context.connection().disconnect(Component.literal(e.getMessage()));
                return null;
            });
        }
    }

    public static void handleButtonAction(Player player, int buttonID, int x, int y, int z) {
        if (buttonID == 0) { // accept
            player.closeContainer();
            ConsentManager.setConsent(true);
            PlayerInfoPayload.send(NameFetcher.getFirstName().orElse(NameFetcher.getSystemName()));
        }
        if (buttonID == 1) { // cancel
            Util.safeDisconnect(player, Component.translatable("dark_flame.no_consent.disconnect"));
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        DarkFlame.addNetworkMessage(ConsentButtonPayload.TYPE, ConsentButtonPayload.STREAM_CODEC, ConsentButtonPayload::handleData);
    }
}