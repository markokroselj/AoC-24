package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Day10 {

    static HashSet<String> visited = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<int[]> topographicMap = readMap();

        int sum = 0;
        for (int i = 0; i < topographicMap.size(); i++) {
            for (int j = 0; j < topographicMap.get(i).length; j++) {
                if (topographicMap.get(i)[j] == 0) {
                    visited.clear();
                    traverseMap(topographicMap, j, i);
                    sum += visited.size();
                }
            }
        }
        System.out.println(sum);
    }

    public static ArrayList<int[]> readMap() throws FileNotFoundException {
        ArrayList<int[]> map = new ArrayList<>();
        Scanner scanner = new Scanner(new File("src/Day10.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[] lineArray = new int[line.length()];
            for (int i = 0; i < line.length(); i++) lineArray[i] = Integer.parseInt(String.valueOf(line.charAt(i)));
            map.add(lineArray);
        }

        scanner.close();
        return map;
    }

    public static void traverseMap(ArrayList<int[]> map, int x, int y) {
        if (map.get(y)[x] == 9) {
            visited.add(y + "," + x);
            return;
        }

        if (x + 1 < map.get(y).length && (map.get(y)[x + 1] - map.get(y)[x] == 1)) traverseMap(map, x + 1, y);
        if (x - 1 >= 0 && (map.get(y)[x - 1] - map.get(y)[x] == 1)) traverseMap(map, x - 1, y);
        if (y - 1 >= 0 && (map.get(y - 1)[x] - map.get(y)[x] == 1)) traverseMap(map, x, y - 1);
        if (y + 1 < map.size() && (map.get(y + 1)[x] - map.get(y)[x] == 1)) traverseMap(map, x, y + 1);
    }
}

