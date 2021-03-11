package net.ujacha.urlshortening.util;

public class Base62Support {


    private static String INDEX = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String encode(long value, long offset) {

        value = value + offset;
        StringBuilder sb = new StringBuilder();

        do {
            sb.append(INDEX.charAt((int) (value % 62)));
            value /= 62;
        } while (value > 0);

        return sb.reverse().toString();
    }

    public static long decode(String code, long offset) {
        long result = 0;
        long pow = 1;

        for (int i = code.length(); i > 0; i--) {
            int d = INDEX.indexOf(code.charAt(i - 1));
            result += (d * pow);
            pow *= 62;
        }

        result = result - offset;

        return result;
    }


}
