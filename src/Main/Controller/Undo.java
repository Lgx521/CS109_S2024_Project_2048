package Main.Controller;

import javax.swing.*;

public class Undo extends JFrame {

    private int steps = 0;
    private int undoTimes = 3;

    private final int MAX_TIMES = 0;
    private final int START_PLACE = 1;

    //getter and setter
    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
        System.out.println("Current Steps: " + this.steps);
    }

    public void setUndoTimes() {
        if (this.undoTimes < 3) {
            this.undoTimes = this.undoTimes + 1;
        }
    }

    private int[][] temp_1 = new int[4][4];
    private int[][] temp_2 = new int[4][4];
    private int[][] temp_3 = new int[4][4];

    //保存某一步的状态
    public void saveStatus(int[][] data) {
        if (this.steps % 3 == 1) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    temp_1[i][j] = data[i][j];
                }
            }
        } else if (this.steps % 3 == 2) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    temp_2[i][j] = data[i][j];
                }
            }
        } else if (this.steps % 3 == 0) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    temp_3[i][j] = data[i][j];
                }
            }
        }

        System.out.println("print temp 1");
        printData(temp_1);
        System.out.println("print temp 2");
        printData(temp_2);
        System.out.println("print temp 3");
        printData(temp_3);
    }

    //控制台输出矩阵
    private void printData(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.printf("%-4d", data[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    //核心方法
    public void UNDO(int[][] data) {
        if (steps == 0) {
            setIssue(START_PLACE);
            return;
        }
        if (undoTimes > 0) {
            if (steps % 3 == 1) {
                for (int i = 0; i < temp_3.length; i++) {
                    for (int j = 0; j < temp_3[0].length; j++) {
                        data[i][j] = temp_3[i][j];
                    }
                }
            } else if (steps % 3 == 2) {
                for (int i = 0; i < temp_1.length; i++) {
                    for (int j = 0; j < temp_1[0].length; j++) {
                        data[i][j] = temp_1[i][j];
                    }
                }
            } else if (steps % 3 == 0) {
                for (int i = 0; i < temp_2.length; i++) {
                    for (int j = 0; j < temp_2[0].length; j++) {
                        data[i][j] = temp_2[i][j];
                    }
                }
            }
            this.steps = this.steps - 1;
            this.undoTimes = this.undoTimes - 1;
            System.out.println("Undo data:");
            printData(data);
        } else {
            System.out.println("Maximun Undo Times is Reached!");
            setIssue(MAX_TIMES);
        }
    }

    //不可移动提示框生成
    private void setIssue(int issue) {
        String content = "initial";
        if (issue == MAX_TIMES) {
            content = "Maximum UNDO time is reached,\n" +
                    "You can't perform UNDO operation again!";
        } else if (issue == START_PLACE) {
            content = "This is the initial board configuration, \n" +
                    "You can't perform UNDO operation now!";
        }

        JOptionPane.showMessageDialog(null, content, "Caution", JOptionPane.WARNING_MESSAGE);
    }
}
