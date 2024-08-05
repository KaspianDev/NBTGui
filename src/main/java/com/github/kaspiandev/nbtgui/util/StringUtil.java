package com.github.kaspiandev.nbtgui.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    private StringUtil() {}

    public static List<String> wrapString(String string, int limit) {
        int length = string.length();
        if (length <= limit) return new ArrayList<>(List.of(string));

        List<String> lines = new ArrayList<>();
        for (int i = limit; i <= length; i += limit) {
            lines.add(insertWrapSymbol(string.substring(i - limit, i), lines, '-'));
        }

        int remainingChars = length % limit;
        if (remainingChars != 0) {
            lines.add(string.substring(length - remainingChars));
        }

        return lines;
    }

    private static String insertWrapSymbol(String line, List<String> lines, char symbol) {
        return (lines.size() % 2 == 0)
                ? line + symbol
                : line;
    }

}
