package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Day6 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> map = readMap();
        HashSet<String> visited = moveGuard(map);
        System.out.println(visited.size());
        numberOfPositions(map);
    }

    public static ArrayList<String> readMap() throws FileNotFoundException {
        ArrayList<String> map = new ArrayList<String>();
        Scanner scanner = new Scanner(new File("src/Day6.txt"));
        while (scanner.hasNextLine()) map.add(scanner.nextLine());
        scanner.close();
        return map;
    }

    public static HashSet<String> moveGuard(ArrayList<String> map) {
        int[] initialPosition = Guard.getGuardPositionFromMap(map);
        Guard guard = new Guard(initialPosition[0], initialPosition[1]).setInitialAngle(map);
        HashSet<String> visited = new HashSet<>();
        visited.add(guard.getX() + "," + guard.getY());
        int cornerRepetitionCounter = 0;
        HashSet<String> cornersVisited = new HashSet<>();

        while (true) {
            if ((int) Math.sin(guard.getAngle()) == 1) {
                if (guard.getY() <= 0) break;
                if (map.get(guard.getY() - 1).charAt(guard.getX()) == '#') {
                    guard.rotateDirection();
                    rotate(map, guard);
                    if (cornersVisited.contains(guard.getX() + "," + guard.getY())) cornerRepetitionCounter++;
                    else cornersVisited.add(guard.getX() + "," + guard.getY());
                }
            } else if ((int) Math.sin(guard.getAngle()) == -1) {
                if (guard.getY() >= map.size() - 1) break;
                if (map.get(guard.getY() + 1).charAt(guard.getX()) == '#') {
                    guard.rotateDirection();
                    rotate(map, guard);
                    if (cornersVisited.contains(guard.getX() + "," + guard.getY())) cornerRepetitionCounter++;
                    else cornersVisited.add(guard.getX() + "," + guard.getY());
                }
            } else if ((int) Math.cos(guard.getAngle()) == 1) {
                if (guard.getX() >= map.get(guard.getY()).length() - 1) break;
                if (map.get(guard.getY()).charAt(guard.getX() + 1) == '#') {
                    guard.rotateDirection();
                    rotate(map, guard);
                    if (cornersVisited.contains(guard.getX() + "," + guard.getY())) cornerRepetitionCounter++;
                    else cornersVisited.add(guard.getX() + "," + guard.getY());
                }
            } else if ((int) Math.cos(guard.getAngle()) == -1) {
                if (guard.getX() <= 0) break;
                if (map.get(guard.getY()).charAt(guard.getX() - 1) == '#') {
                    guard.rotateDirection();
                    rotate(map, guard);
                    if (cornersVisited.contains(guard.getX() + "," + guard.getY())) cornerRepetitionCounter++;
                    else cornersVisited.add(guard.getX() + "," + guard.getY());
                }
            }

            if (cornerRepetitionCounter == 4) return null;

            guard.move();
            visited.add(guard.getX() + "," + guard.getY());
        }

        return visited;
    }

    public static Guard rotate(ArrayList<String> map, Guard guard) {
        if ((int) Math.sin(guard.getAngle()) == 1) {

            if (map.get(guard.getY() - 1).charAt(guard.getX()) == '#') {
                guard.rotateDirection();
                rotate(map, guard);
            } else return guard;
        } else if ((int) Math.sin(guard.getAngle()) == -1) {
            if (map.get(guard.getY() + 1).charAt(guard.getX()) == '#') {
                guard.rotateDirection();
                rotate(map, guard);
            } else return guard;
        } else if ((int) Math.cos(guard.getAngle()) == 1) {
            if (map.get(guard.getY()).charAt(guard.getX() + 1) == '#') {
                guard.rotateDirection();
                rotate(map, guard);
            } else return guard;
        } else if ((int) Math.cos(guard.getAngle()) == -1) {
            if (map.get(guard.getY()).charAt(guard.getX() - 1) == '#') {
                guard.rotateDirection();
                rotate(map, guard);
            } else return guard;
        }

        return guard;
    }

    public static void drawRoute(ArrayList<String> map, HashSet<String> visited) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                if (visited.contains(j + "," + i)) System.out.print("X");
                else System.out.print(map.get(i).charAt(j));
            }
            System.out.println();
        }
    }

    public static int numberOfPositions(ArrayList<String> map) {
        int counter = 0;
        int c = 1;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).length(); j++) {
                if (map.get(i).charAt(j) == '#' || map.get(i).charAt(j) == '^' || map.get(i).charAt(j) == 'v' || map.get(i).charAt(j) == '>' || map.get(i).charAt(j) == '<') {
                    continue;
                }

                ArrayList<String> newMap = new ArrayList<>(map);
                StringBuilder tmp = new StringBuilder();
                for (int k = 0; k < newMap.get(i).length(); k++) {
                    if (k == j) tmp.append('#');
                    else tmp.append(newMap.get(i).charAt(k));
                }
                newMap.set(i, tmp.toString());

                if (moveGuard(newMap) == null) {
                  /*  System.out.println();
                    System.out.println(c++ + ".");

                    for (int k = 0; k < newMap.size(); k++) {
                        for (int l = 0; l < map.get(k).length(); l++) {
                            if (k == i && l == j) System.out.print('⭕');
                            else System.out.print(map.get(k).charAt(l));
                        }
                        System.out.println();
                    }*/
                    counter++;
                }

            }
        }

        System.out.println(counter);
        return counter;
    }
}

class Guard {
    private int x;
    private int y;
    private double angle = Math.PI / 2;


    public Guard(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void rotateDirection() {
        this.angle = ((this.angle + (Math.PI / 2))) % (2 * Math.PI);

    }

    public void move() {
        this.x += (int) (Math.cos(angle)) * -1;
        this.y += (int) Math.sin(angle) * -1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getAngle() {
        return (Math.PI - angle);
    }


    public static int[] getGuardPositionFromMap(ArrayList<String> map) {
        for (int i = 0; i < map.size(); i++)
            for (int j = 0; j < map.get(i).length(); j++)
                if (map.get(i).charAt(j) == '^' || map.get(i).charAt(j) == 'v' || map.get(i).charAt(j) == '>' || map.get(i).charAt(j) == '<')
                    return new int[]{j, i};
        return new int[]{-1, -1};
    }

    public Guard setInitialAngle(ArrayList<String> map) {
        char guard = map.get(y).charAt(x);
        switch (guard) {
            case '^' -> this.angle = Math.PI / 2;
            case '>' -> this.angle = Math.PI;
            case 'v' -> this.angle = -(Math.PI / 2);
            case '<' -> this.angle = 0;
        }
        return this;
    }
}
