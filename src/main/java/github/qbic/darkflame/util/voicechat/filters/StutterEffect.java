package github.qbic.darkflame.util.voicechat.filters;

public class StutterEffect implements VoiceChatEffect {
    private final int stutterSize;
    private final int stutterInterval;

    public StutterEffect(int stutterSize, int stutterInterval) {
        this.stutterSize = stutterSize;
        this.stutterInterval = stutterInterval;
    }

    @Override
    public short[] apply(short[] data) {
        short[] result = data.clone();
        for (int i = 0; i < result.length; i += stutterInterval) {
            for (int j = 0; j < stutterSize && i + j < result.length; j++) {
                result[i + j] = result[i];
            }
        }
        return result;
    }
}