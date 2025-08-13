package github.qbic.darkflame.util.voicechat.filters;

public class BitCrushEffect implements VoiceChatEffect {
    private final int resolution;

    public BitCrushEffect(int resolution) {
        this.resolution = resolution;
    }

    @Override
    public short[] apply(short[] data) {
        int mask = -(1 << (16 - resolution));
        short[] crushed = new short[data.length];
        for (int i = 0; i < data.length; i++) {
            crushed[i] = (short) (data[i] & mask);
        }
        return crushed;
    }
}