package github.qbic.darkflame.networking;

import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.init.ModMenus;
import github.qbic.darkflame.init.ModScreens;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.client.Minecraft;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record MenuStateUpdatePayload(int elementType, String name, Object elementState) implements CustomPacketPayload {

    public static final Type<MenuStateUpdatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "menustate_update"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MenuStateUpdatePayload> STREAM_CODEC = StreamCodec.of(MenuStateUpdatePayload::write, MenuStateUpdatePayload::read);
    public static void write(FriendlyByteBuf buffer, MenuStateUpdatePayload message) {
        buffer.writeInt(message.elementType);
        buffer.writeUtf(message.name);
        if (message.elementType == 0) {
            buffer.writeUtf((String) message.elementState);
        } else if (message.elementType == 1) {
            buffer.writeBoolean((boolean) message.elementState);
        }
    }

    public static MenuStateUpdatePayload read(FriendlyByteBuf buffer) {
        int elementType = buffer.readInt();
        String name = buffer.readUtf();
        Object elementState = null;
        if (elementType == 0) {
            elementState = buffer.readUtf();
        } else if (elementType == 1) {
            elementState = buffer.readBoolean();
        }
        return new MenuStateUpdatePayload(elementType, name, elementState);
    }

    @Override
    public Type<MenuStateUpdatePayload> type() {
        return TYPE;
    }

    public static void handleMenuState(final MenuStateUpdatePayload message, final IPayloadContext context) {
        if (message.name.length() > 256 || message.elementState instanceof String string && string.length() > 8192)
            return;
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof ModMenus.MenuAccessor menu) {
                menu.getMenuState().put(message.elementType + ":" + message.name, message.elementState);
                if (context.flow() == PacketFlow.CLIENTBOUND && Minecraft.getInstance().screen instanceof ModScreens.ScreenAccessor accessor) {
                    accessor.updateMenuState(message.elementType, message.name, message.elementState);
                }
            }
        }).exceptionally(e -> {
            context.connection().disconnect(Component.literal(e.getMessage()));
            return null;
        });
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        DarkFlame.addNetworkMessage(MenuStateUpdatePayload.TYPE, MenuStateUpdatePayload.STREAM_CODEC, MenuStateUpdatePayload::handleMenuState);
    }
}