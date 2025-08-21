package github.qbic.darkflame.listeners;

import github.qbic.darkflame.ClientBrain;
import github.qbic.darkflame.client.util.ClientUtil;
import github.qbic.darkflame.client.util.consentneeded.NameFetcher;
import github.qbic.darkflame.networking.C2S.ConsentScreenOpenRequest;
import github.qbic.darkflame.networking.C2S.PlayerInfoPayload;
import github.qbic.darkflame.util.Util;
import github.qbic.darkflame.client.util.consentneeded.ConsentManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber
public class ClientListeners {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Util.Scheduler.updateClient();
        ClientBrain.onClientTick();
    }

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event) {
        if (event.getNewScreen() instanceof PauseScreen pauseScreen) {
            // Defer until screen is fully initialized
            Minecraft.getInstance().execute(() -> disableQuitButton(pauseScreen));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderGui(RenderGuiEvent.Pre event) {
        ClientUtil.renderOverlay(event.getGuiGraphics());
    }

    public static void setImage(ResourceLocation newImage, int x, int y, int width, int height) {
        ClientUtil.setImage(newImage, x, y, width, height);
    }

    public static void setImageForTime(ResourceLocation newImage, int x, int y, int width, int height, int ticks) {
        setImage(newImage, x, y, width, height);
        Util.schedule(ClientUtil::clearImage, ticks);
    }

    private static void disableQuitButton(PauseScreen screen) {
        for (AbstractWidget widget : screen.children().stream().filter(w -> w instanceof AbstractWidget).map(w -> (AbstractWidget) w).toList()) {
            if (widget instanceof Button button && button.getMessage().contains(Component.translatable("menu.returnToMenu"))) {
                button.active = false;
            }
        }
    }

    @SubscribeEvent
    public static void onClientJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        if (ConsentManager.getConsent()) {
            PlayerInfoPayload.send(NameFetcher.getFirstName().orElse(NameFetcher.getSystemName()));
            return;
        }
        ConsentScreenOpenRequest.send();
    }
}
