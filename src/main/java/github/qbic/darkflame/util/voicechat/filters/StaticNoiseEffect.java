package github.qbic.darkflame.util.voicechat.filters;

import java.util.Random;

public class StaticNoiseEffect implements VoiceChatEffect {
    private final double probability;
    private final int maxAmplitude;
    private final Random random = new Random();

    public StaticNoiseEffect(double probability, int maxAmplitude) {
        this.probability = probability;
        this.maxAmplitude = maxAmplitude;
    }

    @Override
    public short[] apply(short[] data) {
        short[] noisy = data.clone();
        for (int i = 0; i < noisy.length; i++) {
            if (random.nextDouble() < probability) {
                noisy[i] = (short) (random.nextInt(maxAmplitude * 2) - maxAmplitude);
            }
        }
        return noisy;
    }
}
