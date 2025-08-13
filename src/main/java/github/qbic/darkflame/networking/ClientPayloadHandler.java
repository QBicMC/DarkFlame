package github.qbic.darkflame.networking;

import github.qbic.darkflame.ClientBrain;
import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.events.ModEvents;
import github.qbic.darkflame.listeners.ClientEvents;
import github.qbic.darkflame.networking.S2C.*;
import github.qbic.darkflame.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleClientEventDataOnMain(final ClientEventPayload data, final IPayloadContext context) {
        ModEvents.CLIENT_EVENTS.get(data.id()).execute();
    }

    public static void handleStringClientEventDataOnMain(final StringClientEventPayload data, final IPayloadContext context) {
        ModEvents.STRING_CLIENT_EVENTS.get(data.id()).execute(data.arg());
    }

    public static void handleClientBrainData(final ClientBrainPayload data, final IPayloadContext context) {
        ClientBrain.setNoEscape(data.noEscape());
    }

    public static void handleNonSystemChatData(final ClientNonSystemChatPayload data, final IPayloadContext context) {
        Minecraft.getInstance().gui.getChat().addMessage(Component.literal(data.msg()), null, null);
    }

    public static void handleSetOverlayData(final SetOverlayPayload data, final IPayloadContext context) {
        if (data.ticks() == 0) {
            ClientEvents.setImage(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "textures/img/" + data.resourceLocation()), data.x(), data.y(), data.width(), data.height());
        } else {
            ClientEvents.setImageForTime(ResourceLocation.fromNamespaceAndPath(DarkFlame.MOD_ID, "textures/img/" + data.resourceLocation()), data.x(), data.y(), data.width(), data.height(), data.ticks());
        }
    }

    public static void handleVolumeDataOnMain(final VolumePayload data, final IPayloadContext context) {
        Minecraft mc = Minecraft.getInstance();
        Options options = mc.options;
        double volume = data.volume();

        if (volume < 0f || volume > 1f) return;

        Volumes originalVolumes = new Volumes(
            options.getSoundSourceOptionInstance(SoundSource.AMBIENT).get(),
            options.getSoundSourceOptionInstance(SoundSource.BLOCKS).get(),
            options.getSoundSourceOptionInstance(SoundSource.HOSTILE).get(),
            options.getSoundSourceOptionInstance(SoundSource.MUSIC).get(),
            options.getSoundSourceOptionInstance(SoundSource.NEUTRAL).get(),
            options.getSoundSourceOptionInstance(SoundSource.PLAYERS).get(),
            options.getSoundSourceOptionInstance(SoundSource.RECORDS).get(),
            options.getSoundSourceOptionInstance(SoundSource.VOICE).get(),
            options.getSoundSourceOptionInstance(SoundSource.WEATHER).get()
        );

        options.getSoundSourceOptionInstance(SoundSource.AMBIENT).set(volume);
        options.getSoundSourceOptionInstance(SoundSource.BLOCKS).set(volume);
        options.getSoundSourceOptionInstance(SoundSource.HOSTILE).set(volume);
        options.getSoundSourceOptionInstance(SoundSource.MUSIC).set(volume);
        options.getSoundSourceOptionInstance(SoundSource.NEUTRAL).set(volume);
        options.getSoundSourceOptionInstance(SoundSource.PLAYERS).set(volume);
        options.getSoundSourceOptionInstance(SoundSource.RECORDS).set(volume);
        options.getSoundSourceOptionInstance(SoundSource.VOICE).set(volume);
        options.getSoundSourceOptionInstance(SoundSource.WEATHER).set(volume);
        options.save();

        Util.scheduleClient(() -> {
            options.getSoundSourceOptionInstance(SoundSource.AMBIENT).set(originalVolumes.ambient);
            options.getSoundSourceOptionInstance(SoundSource.BLOCKS).set(originalVolumes.blocks);
            options.getSoundSourceOptionInstance(SoundSource.HOSTILE).set(originalVolumes.hostile);
            options.getSoundSourceOptionInstance(SoundSource.MUSIC).set(originalVolumes.music);
            options.getSoundSourceOptionInstance(SoundSource.NEUTRAL).set(originalVolumes.neutral);
            options.getSoundSourceOptionInstance(SoundSource.PLAYERS).set(originalVolumes.players);
            options.getSoundSourceOptionInstance(SoundSource.RECORDS).set(originalVolumes.records);
            options.getSoundSourceOptionInstance(SoundSource.VOICE).set(originalVolumes.voice);
            options.getSoundSourceOptionInstance(SoundSource.WEATHER).set(originalVolumes.weather);
            options.save();
        }, 1200, "reset volumes");
    }

    public record Volumes(double ambient, double blocks, double hostile, double music, double neutral, double players, double records, double voice, double weather) {}
}