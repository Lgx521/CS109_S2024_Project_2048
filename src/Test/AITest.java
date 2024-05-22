package Test;

import Main.Features.AI;

import javax.swing.*;

public class AITest extends JFrame implements Runnable {
    static AI ai = new AI();

    static int[][] data = {
            {2, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 4, 0, 0},
            {0, 0, 0, 0}
    };


    static int flag = 1;

    public static void main(String[] args) {

        ai.setMotion(0);
        int[][] data = {
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0}
        };
//        int[][] data = {
//                {2, 0, 0, 0},
//                {0, 0, 0, 0},
//                {0, 4, 0, 0},
//                {0, 0, 0, 0}
//        };

//        int[][] data = {
//                {0, 3, 27, 9},
//                {27, 3, 243, 27},
//                {9, 81, 81, 27},
//                {0, 3, 9, 9}
//        };
        ai.AIMoving(2500, data);
//        ai.AIMovingNonStop(data);
        printData(data);
        System.out.println("------------------");

    }

    public static void printData(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.printf("%-5d", data[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void run() {
        System.out.println("run?");
    }
}