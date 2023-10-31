package fr.dawan.bank.tools;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomTool {
    private static final Random RANDOM = new Random();
    public static String generateCode(int digits) {
        return RANDOM.ints(digits,0,10).mapToObj(Objects::toString).collect(Collectors.joining());
    }
}
