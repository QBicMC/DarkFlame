package github.qbic.darkflame.util.voicechat.filters;

public class EchoEffect implements VoiceChatEffect {
    private final int delaySamples;
    private final float decay;

    public EchoEffect(int delaySamples, float decay) {
        this.delaySamples = delaySamples;
        this.decay = decay;
    }

    @Override
    public short[] apply(short[] data) {
        short[] result = new short[data.length];
        for (int i = 0; i < data.length; i++) {
            float sample = data[i];
            if (i >= delaySamples) {
                sample += result[i - delaySamples] * decay;
            }
            result[i] = (short) Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, (int) sample));
        }
        return result;
    }
}