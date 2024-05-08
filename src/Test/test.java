package Test;

import Main.Features.AI;
import Main.Features.bgmPlayer;
import Main.Frame.GameFrame;
import Main.Frame.SaveAndLoad;


import javax.swing.JFrame;
import java.util.Random;

public class test extends JFrame {
    public static void main(String[] args) {

        AI ai = new AI();

        int[][] data = {
                {0,0,0,0},
                {0,4,0,0},
                {0,0,0,0},
                {0,0,0,2}
        };

        ai.AIMovingNonStop(data);

        printData(data);


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


}