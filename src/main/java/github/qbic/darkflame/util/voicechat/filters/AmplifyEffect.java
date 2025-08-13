package github.qbic.darkflame.util.voicechat.filters;

public class AmplifyEffect implements VoiceChatEffect {
    private final float gain;

    public AmplifyEffect(float gain) {
        this.gain = gain;
    }

    @Override
    public short[] apply(short[] data) {
        short[] result = new short[data.length];
        for (int i = 0; i < data.length; i++) {
            int amplified = (int) (data[i] * gain);
            result[i] = (short) Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, amplified));
        }
        return result;
    }
}
