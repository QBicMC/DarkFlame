package github.qbic.darkflame.networking;

import github.qbic.darkflame.networking.C2S.ChangePerspectivePayload;
import github.qbic.darkflame.networking.C2S.ConsentScreenOpenRequest;
import github.qbic.darkflame.networking.C2S.PlayerInfoPayload;
import github.qbic.darkflame.networking.S2C.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class RegisterPayloads {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                VolumePayload.TYPE,
                VolumePayload.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        ClientPayloadHandler::handleVolumeDataOnMain
                ));

        registrar.playToClient(
                ClientEventPayload.TYPE,
                ClientEventPayload.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        ClientPayloadHandler::handleClientEventDataOnMain
                ));

        registrar.playToClient(
                StringClientEventPayload.TYPE,
                StringClientEventPayload.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        ClientPayloadHandler::handleStringClientEventDataOnMain
                ));

        registrar.playToClient(
                ClientBrainPayload.TYPE,
                ClientBrainPayload.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        ClientPayloadHandler::handleClientBrainData
                ));

        registrar.playToClient(
                SetOverlayPayload.TYPE,
                SetOverlayPayload.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        ClientPayloadHandler::handleSetOverlayData
                ));

        registrar.playToClient(
                ClientNonSystemChatPayload.TYPE,
                ClientNonSystemChatPayload.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        ClientPayloadHandler::handleNonSystemChatData
                ));

        registrar.playToServer(
                ConsentScreenOpenRequest.TYPE,
                ConsentScreenOpenRequest.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        ServerPayloadHandler::handleConsentRequest
                ));

        registrar.playToServer(
                PlayerInfoPayload.TYPE,
                PlayerInfoPayload.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        ServerPayloadHandler::handlePlayerInfo
                ));

        registrar.playToServer(
                ChangePerspectivePayload.TYPE,
                ChangePerspectivePayload.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        ServerPayloadHandler::handleChangePerspective
                ));

//        registrar.playBidirectional(
//                VolumePayload.TYPE,
//                VolumePayload.STREAM_CODEC,
//                new DirectionalPayloadHandler<>(
//                        ClientPayloadHandler::handleDataOnMain,
//                        ServerPayloadHandler::handleDataOnMain
//                )
//        );
    }
}
