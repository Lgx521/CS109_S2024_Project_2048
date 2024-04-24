package Main.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class InitialGrids implements KeyListener {

    public void gameStart() {
        gameCore();
    }

    int[][] data = new int[4][4];

    private void gameCore() {
        initialData();
        printData(data);
    }



    private int[][] initialData() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = 0;
            }
        }
        Random r = new Random();

        int[] initialNumSet = {2, 4};
        int[] position = generateRandomPosition();

        if (position[0] != position[2] || position[1] != position[3]) {
            data[position[0]][position[1]] = 2;
            data[position[2]][position[3]] = initialNumSet[r.nextInt(2)];
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
                System.out.printf("%-3d", datum[j]);
            }
            System.out.println();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }
}