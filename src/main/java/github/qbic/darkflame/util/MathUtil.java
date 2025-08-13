package github.qbic.darkflame.util;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {
    // max still exclusive, min inclusive
    public static double randomBiased(double min, double max, double biasTarget, float biasAmount) {
        if (min > max) throw new IllegalArgumentException("min cannot be greater than max");
        if (biasTarget < min || biasTarget > max)
            throw new IllegalArgumentException("biasTarget must be within [min, max]");
        if (biasAmount < 0.0 || biasAmount > 1.0)
            throw new IllegalArgumentException("biasAmount must be between 0.0 and 1.0");

        double rand = ThreadLocalRandom.current().nextDouble(min, max);
        return lerp(rand, biasTarget, biasAmount);
    }

    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    public static int betterModulo(int n, int m) {
        return (n % m + m) % m;
    }
}
