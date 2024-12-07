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
        drawRoute(map, visited);
        System.out.println(visited.size());
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
        while (guard.getY() > 0 && guard.getX() > 0 && (guard.getY() < map.size() - 1) && (guard.getX() < map.get(0).length() - 1)) {

            if ((int) Math.sin(guard.getAngle()) == 1) {
                if (map.get(guard.getY() - 1).charAt(guard.getX()) == '#') guard.changeDirection();
            } else if ((int) Math.sin(guard.getAngle()) == -1) {
                if (map.get(guard.getY() + 1).charAt(guard.getX()) == '#') guard.changeDirection();
            } else if ((int) Math.cos(guard.getAngle()) == 1) {
                if (map.get(guard.getY()).charAt(guard.getX() + 1) == '#') guard.changeDirection();
            } else if ((int) Math.cos(guard.getAngle()) == -1) {
                if (map.get(guard.getY()).charAt(guard.getX() - 1) == '#') guard.changeDirection();
            }

            guard.move();
            visited.add(guard.getX() + "," + guard.getY());
        }


        return visited;
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
}

class Guard {
    private int x;
    private int y;
    private double angle = Math.PI / 2;


    public Guard(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changeDirection() {
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
