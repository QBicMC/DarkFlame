package github.qbic.darkflame.util.voicechat.filters;

public class BlankEffect implements VoiceChatEffect {
    @Override
    public short[] apply(short[] data) {
        return data;
    }
}
