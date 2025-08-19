package github.qbic.darkflame.events.client;

import github.qbic.darkflame.client.util.ClientUtil;
import github.qbic.darkflame.client.util.Window;
import github.qbic.darkflame.events.ClientModEvent;
import github.qbic.darkflame.util.Util;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class IAMINYOURSYSTEMEventClient extends ClientModEvent {
    @Override
    public String clientEventID() {
        return "in_your_system";
    }

    @Override
    public void execute() {
        Window.show("help me", "LET ME OUT", true);
        ClientUtil.setWallpaper(ResourceLocation.parse("dark_flame:wallpapers/let_me_out.png"));

        Util.scheduleClient(() -> {
            try {
                new ProcessBuilder("cmd", "/c", "start", "microsoft.windows.camera:").start();
            } catch (IOException e) {
                Util.printDBG("could not open camera");
            }
        }, 30, "open camera");

        Util.scheduleClient(() -> {
            ClientUtil.openInDefaultApp(ResourceLocation.parse("dark_flame:open/escape.txt"));
        }, 60, "open escape.txt");
    }
}
