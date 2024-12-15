package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Day12 {
    static ArrayList<String> map;
    static HashSet<String> visitedPlants = new HashSet<String>();
    static HashSet<String> region = new HashSet<String>();

    public static void main(String[] args) throws FileNotFoundException {

        map = readGardenPlotsMap();

        int totalPrice = 0;
        int perimeter = 0;
        int corners = 0;
        int totalPrice2 = 0;

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                if (!visitedPlants.contains(j + "," + i)) {
                    char type = map.get(i).charAt(j);
                    traverseMap(type, j, i);

                    for (String visitedPlant : region) {
                        int x = Integer.parseInt(visitedPlant.split(",")[0]);
                        int y = Integer.parseInt(visitedPlant.split(",")[1]);

                        if (y - 1 < 0) perimeter++;
                        if (y - 1 >= 0 && map.get(y - 1).charAt(x) != type)
                            perimeter++;

                        if (x - 1 < 0) perimeter++;
                        if (x - 1 >= 0 && map.get(y).charAt(x - 1) != type)
                            perimeter++;

                        if (x + 1 >= map.get(y).length()) perimeter++;
                        if (x + 1 < map.get(y).length() && map.get(y).charAt(x + 1) != type)
                            perimeter++;

                        if (y + 1 >= map.size()) perimeter++;
                        if (y + 1 < map.size() && map.get(y + 1).charAt(x) != type)
                            perimeter++;


                        if (!region.contains(coordinate(x - 1, y - 1)) && !region.contains(coordinate(x - 1, y))
                                && !region.contains(coordinate(x, y - 1)))
                            corners++;

                        if (!region.contains(coordinate(x + 1, y - 1)) && !region.contains(coordinate(x + 1, y))
                                && !region.contains(coordinate(x, y - 1)))
                            corners++;

                        if (!region.contains(coordinate(x - 1, y + 1)) && !region.contains(coordinate(x - 1, y))
                                && !region.contains(coordinate(x, y + 1)))
                            corners++;

                        if (!region.contains(coordinate(x + 1, y + 1)) && !region.contains(coordinate(x + 1, y))
                                && !region.contains(coordinate(x, y + 1)))
                            corners++;

                        if (region.contains(coordinate(x, y + 1)) && region.contains(coordinate(x + 1, y)) &&
                                !region.contains(coordinate(x + 1, y + 1)))
                            corners++;

                        if (region.contains(coordinate(x, y + 1)) && region.contains(coordinate(x - 1, y)) &&
                                !region.contains(coordinate(x - 1, y + 1)))
                            corners++;

                        if (region.contains(coordinate(x, y - 1)) && region.contains(coordinate(x + 1, y)) &&
                                !region.contains(coordinate(x + 1, y - 1)))
                            corners++;

                        if (region.contains(coordinate(x, y - 1)) && region.contains(coordinate(x - 1, y)) &&
                                !region.contains(coordinate(x - 1, y - 1)))
                            corners++;

                        if (!region.contains(coordinate(x + 1, y)) && region.contains(coordinate(x + 1, y + 1))
                                && !region.contains(coordinate(x, y + 1)))
                            corners++;

                        if (!region.contains(coordinate(x - 1, y)) && region.contains(coordinate(x - 1, y - 1))
                                && !region.contains(coordinate(x, y - 1)))
                            corners++;

                        if (!region.contains(coordinate(x - 1, y)) && region.contains(coordinate(x - 1, y + 1))
                                && !region.contains(coordinate(x, y + 1)))
                            corners++;

                        if (!region.contains(coordinate(x + 1, y)) && region.contains(coordinate(x + 1, y - 1))
                                && !region.contains(coordinate(x, y - 1)))
                            corners++;
                    }

                    totalPrice += region.size() * perimeter;
                    visitedPlants.addAll(region);
                    perimeter = 0;
                    totalPrice2 += region.size() * corners;
                    corners = 0;
                    region.clear();
                }
            }
        }
        System.out.println(totalPrice);
        System.out.println(totalPrice2);
    }

    public static String coordinate(int x, int y) {
        return x + "," + y;
    }

    public static ArrayList<String> readGardenPlotsMap() throws FileNotFoundException {
        ArrayList<String> map = new ArrayList<String>();
        Scanner scanner = new Scanner(new File("src/Day12.txt"));
        while (scanner.hasNextLine()) map.add(scanner.nextLine());
        return map;
    }


    public static void traverseMap(char type, int x, int y) {
        if (region.contains(x + "," + y)) return;

        region.add(x + "," + y);

        if (x - 1 >= 0 && map.get(y).charAt(x - 1) == type) traverseMap(type, x - 1, y);
        if (x + 1 < map.get(y).length() && map.get(y).charAt(x + 1) == type) traverseMap(type, x + 1, y);
        if (y + 1 < map.size() && map.get(y + 1).charAt(x) == type) traverseMap(type, x, y + 1);
        if (y - 1 >= 0 && map.get(y - 1).charAt(x) == type) traverseMap(type, x, y - 1);
    }
}
