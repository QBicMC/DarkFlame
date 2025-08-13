package github.qbic.darkflame.util.voicechat;

import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.audiochannel.AudioPlayer;
import de.maxhenkel.voicechat.api.audiochannel.LocationalAudioChannel;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import github.qbic.darkflame.DarkFlame;
import github.qbic.darkflame.util.voicechat.filters.VoiceChatEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class VoiceChat implements VoicechatPlugin {
    public static VoiceChat INSTANCE;
    private VoicechatServerApi voicechat;

    private final Map<UUID, OpusDecoder> decoders = new ConcurrentHashMap<>();
    private final Map<UUID, List<short[]>> recordedClips = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicPacket);
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStart);
    }

    private void onServerStart(VoicechatServerStartedEvent e) {
        voicechat = e.getVoicechat();
        INSTANCE = this;
    }

    public void onMicPacket(MicrophonePacketEvent e) {
        VoicechatConnection connection = e.getSenderConnection();
        if (connection == null || !(connection.getPlayer().getPlayer() instanceof ServerPlayer player)) return;

        UUID uuid = player.getUUID();
        byte[] encoded = e.getPacket().getOpusEncodedData();
        if (encoded == null || encoded.length == 0) return;

        decoders.putIfAbsent(uuid, e.getVoicechat().createDecoder());
        OpusDecoder decoder = decoders.get(uuid);

        short[] pcm;
        try {
            pcm = decoder.decode(encoded);
        } catch (Exception ex) {
            return;
        }

        if (pcm.length == 0) return;

        recordedClips.computeIfAbsent(uuid, k -> new ArrayList<>());
        List<short[]> clips = recordedClips.get(uuid);

        if (clips.size() < 20) {
            clips.add(pcm);
        } else {
            clips.set(random.nextInt(clips.size()), pcm);
        }
    }

    public void playRandomClipAt(ServerLevel level, BlockPos pos, UUID sourceUUID, VoiceChatEffect... effects) {
        List<short[]> clips = recordedClips.get(sourceUUID);
        System.out.println(recordedClips);
        if (clips == null || clips.isEmpty()) return;

        short[] data = clips.get(random.nextInt(clips.size()));
        if (data == null || data.length == 0) return;

        for (VoiceChatEffect effect : effects) {
            data = effect.apply(data);
        }

        LocationalAudioChannel channel = voicechat.createLocationalAudioChannel(
                sourceUUID,
                voicechat.fromServerLevel(level),
                voicechat.createPosition(pos.getX(), pos.getY(), pos.getZ())
        );

        if (channel == null) return;

        channel.updateLocation(voicechat.createPosition(pos.getX(), pos.getY(), pos.getZ()));

        AudioPlayer player = voicechat.createAudioPlayer(channel, voicechat.createEncoder(), data);
        if (!player.isPlaying()) {
            player.startPlaying();
        }
    }

    public void cleanup(UUID uuid) {
        OpusDecoder decoder = decoders.remove(uuid);
        if (decoder != null) decoder.close();
        recordedClips.remove(uuid);
    }

    public void clearAll() {
        decoders.values().forEach(OpusDecoder::close);
        decoders.clear();
        recordedClips.clear();
    }

    @Override
    public String getPluginId() {
        return DarkFlame.MOD_ID;
    }
}
