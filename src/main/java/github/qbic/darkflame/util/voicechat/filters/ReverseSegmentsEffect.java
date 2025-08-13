package github.qbic.darkflame.util.voicechat.filters;

import java.util.Random;

public class ReverseSegmentsEffect implements VoiceChatEffect {
    private final int segmentSize;
    private final Random random = new Random();

    public ReverseSegmentsEffect(int segmentSize) {
        this.segmentSize = segmentSize;
    }

    @Override
    public short[] apply(short[] data) {
        short[] result = data.clone();
        for (int i = 0; i < result.length; i += segmentSize * 2) {
            if (random.nextBoolean()) {
                int start = i;
                int end = Math.min(i + segmentSize, result.length);
                for (int j = 0; j < (end - start) / 2; j++) {
                    short temp = result[start + j];
                    result[start + j] = result[end - 1 - j];
                    result[end - 1 - j] = temp;
                }
            }
        }
        return result;
    }
}
