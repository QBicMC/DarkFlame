package github.qbic.darkflame.util.voicechat.filters;

public class WarpSpeedEffect implements VoiceChatEffect {
    private final float speedFactor;

    public WarpSpeedEffect(float speedFactor) {
        this.speedFactor = speedFactor;
    }

    @Override
    public short[] apply(short[] data) {
        int newLength = (int) (data.length * speedFactor);
        short[] warped = new short[newLength];
        for (int i = 0; i < newLength; i++) {
            warped[i] = data[(int) (i / speedFactor)];
        }
        return warped;
    }
}
