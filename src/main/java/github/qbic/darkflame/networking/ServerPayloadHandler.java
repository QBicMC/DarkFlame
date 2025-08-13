package github.qbic.darkflame.networking;

import github.qbic.darkflame.networking.S2C.VolumePayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleDataOnMain(final VolumePayload data, final IPayloadContext context) {
        // Do something with the data, on the main thread
        // blah(data.age());
    }
}