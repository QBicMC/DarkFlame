package github.qbic.darkflame.util.voicechat.filters;

public class ReverseEffect implements VoiceChatEffect {
    @Override
    public short[] apply(short[] data) {
        short[] reversed = new short[data.length];
        for (int i = 0; i < data.length; i++) {
            reversed[i] = data[data.length - 1 - i];
        }
        return reversed;
    }
}