package net.ujacha.urlshortening.util;

public class Base62Support {

    public static String encode(String codec, long offset, long value) {

        value = value + offset;
        StringBuilder sb = new StringBuilder();

        do {
            sb.append(codec.charAt((int) (value % 62)));
            value /= 62;
        } while (value > 0);

        return sb.reverse().toString();
    }

    public static long decode(String codec, long offset, String code) {
        long result = 0;
        long pow = 1;

        for (int i = code.length(); i > 0; i--) {
            int d = codec.indexOf(code.charAt(i - 1));
            result += (d * pow);
            pow *= 62;
        }

        result = result - offset;

        return result;
    }

}
