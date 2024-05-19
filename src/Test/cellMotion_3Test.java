package Test;

import Main.Features.AI;
import Main.Features.AIMotion_3;

public class cellMotion_3Test {
    public static void main(String[] args) {
        AIMotion_3 motion = new AIMotion_3();
        AI ai = new AI();
        int[][] data = {
                {0, 3, 27, 9},
                {27, 3, 243, 27},
                {9, 81, 81, 27},
                {0, 3, 9, 9}
        };
        ai.setMotion(1);
        ai.AIMovingNonStop(data);

    }

    public static void printData(int[][] data) {
        test.printData(data);
    }
}
