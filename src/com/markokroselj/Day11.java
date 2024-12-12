package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Day11 {
    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, Long> stoneArrangement = readInitialArrangement();

        for (int i = 0; i < 75; i++) stoneArrangement = blink(stoneArrangement);
        Long counter = 0L;
        for (String key : stoneArrangement.keySet()) counter += stoneArrangement.get(key);

        System.out.println(counter);
    }

    public static HashMap<String, Long> readInitialArrangement() throws FileNotFoundException {
        String[] array = new Scanner(new File("src/Day11.txt")).nextLine().split("\\s+");
        HashMap<String, Long> stoneArrangement = new HashMap<>();
        for (String stone : array)
            if (stoneArrangement.containsKey(stone)) stoneArrangement.put(stone, stoneArrangement.get(stone) + 1);
            else stoneArrangement.put(stone, 1L);
        return stoneArrangement;
    }

    public static HashMap<String, Long> blink(HashMap<String, Long> stoneArrangement) {
        HashMap<String, Long> newStoneArrangement = new HashMap<>();

        for (String stone : stoneArrangement.keySet()) {
            if (stone.equals("0")) addStone(stoneArrangement, newStoneArrangement, stone, "1");
            else if (stone.length() % 2 == 0) {
                addStone(stoneArrangement, newStoneArrangement, stone, stone.substring(0, stone.length() / 2));

                String rightStone = stone.substring(stone.length() / 2);
                if (rightStone.startsWith("0") && rightStone.length() > 1) {
                    boolean foundNonZero = false;
                    StringBuilder tmp = new StringBuilder();
                    for (int i = 0; i < rightStone.length(); i++) {
                        if (rightStone.charAt(i) != '0') foundNonZero = true;
                        if (foundNonZero) tmp.append(rightStone.charAt(i));
                    }
                    rightStone = foundNonZero ? tmp.toString() : "0";
                }
                addStone(stoneArrangement, newStoneArrangement, stone, rightStone);
            } else addStone(stoneArrangement, newStoneArrangement, stone, String.valueOf(Long.parseLong(stone) * 2024));
        }
        return newStoneArrangement;
    }

    private static void addStone(HashMap<String, Long> stoneArrangement, HashMap<String, Long> newStoneArrangement, String stone, String newStone) {
        if (newStoneArrangement.containsKey(newStone))
            newStoneArrangement.put(newStone, newStoneArrangement.get(newStone) + stoneArrangement.get(stone));
        else newStoneArrangement.put(newStone, stoneArrangement.get(stone));
    }
}
