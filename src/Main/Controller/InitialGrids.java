package Main.Controller;

import java.util.Random;

public class InitialGrids {

    public int[][] setup() {
        int[][] data = initialData();
        printData(data);
        return data;
    }

    public int[][] setup_3() {
        int[][] data = initialData_3();
        printData(data);
        return data;
    }

    int[][] data = new int[4][4];

    private int[][] initialData() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = 0;
            }
        }
        Random r = new Random();

        int[] position = generateRandomPosition();

        if (position[0] != position[2] || position[1] != position[3]) {
            data[position[0]][position[1]] = 2;
            data[position[2]][position[3]] = 4;
        } else {
            initialData();
        }
        return data;
    }

    private int[][] initialData_3() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = 0;
            }
        }
        Random r = new Random();

        int[] position = generateRandomPosition();

        if (position[0] != position[2] || position[1] != position[3]) {
            data[position[0]][position[1]] = 3;
            data[position[2]][position[3]] = 3;
        } else {
            initialData();
        }
        return data;
    }

    private int[] generateRandomPosition() {
        Random r = new Random();
        int x_1 = r.nextInt(0, data.length);
        int y_1 = r.nextInt(0, data[0].length);
        int x_2 = r.nextInt(0, data.length);
        int y_2 = r.nextInt(0, data[0].length);
        return new int[]{x_1, y_1, x_2, y_2};
    }

    private void printData(int[][] data) {
        for (int[] datum : data) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.printf("%-4d", datum[j]);
            }
            System.out.println();
        }
    }

}