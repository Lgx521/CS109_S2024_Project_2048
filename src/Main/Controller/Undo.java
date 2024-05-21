package Main.Controller;

import javax.swing.*;

public class Undo extends JFrame {

    private final int MAX_TIMES = 0;
    private final int START_PLACE = 1;

    //用于保存之前三步信息的矩阵
    private final int[][] temp_0 = new int[4][4];
    private final int[][] temp_1 = new int[4][4];
    private final int[][] temp_2 = new int[4][4];
    private final int[][] temp_3 = new int[4][4];

    //步数统计
    private int steps = 0;

    //回退次数，初始值为3，表示最多回退3次
    private int undoTimes = 3;

    //得到步数
    public int getSteps() {
        return steps;
    }

    //设置步数
    public void setSteps(int steps) {
        this.steps = steps;
        System.out.println("Current Steps: " + this.steps);
    }

    //恢复undo次数
    public void setUndoTimes() {
        if (this.undoTimes < 3) {
            this.undoTimes = this.undoTimes + 1;
        }
    }

    //undo次数设为0
    public void setZeroUndoTime() {
        this.undoTimes = 0;
    }

    //保存某一步的状态
    public void saveStatus(int[][] data) {
        if ((this.steps + 1) % 4 == 3) {
            for (int i = 0; i < data.length; i++) {
                System.arraycopy(data[i], 0, temp_3[i], 0, data[0].length);
            }
        } else if ((this.steps + 1) % 4 == 2) {
            for (int i = 0; i < data.length; i++) {
                System.arraycopy(data[i], 0, temp_2[i], 0, data[0].length);
            }
        } else if ((this.steps + 1) % 4 == 1) {
            for (int i = 0; i < data.length; i++) {
                System.arraycopy(data[i], 0, temp_1[i], 0, data[0].length);
            }
        } else if ((this.steps + 1) % 4 == 0) {
            for (int i = 0; i < data.length; i++) {
                System.arraycopy(data[i], 0, temp_0[i], 0, data[0].length);
            }
        }
    }

    //核心方法
    public void UNDO(int[][] data) {
        //初始位置，不可继续UNDO
        if (steps == 0) {
            setIssue(START_PLACE);
            return;
        }
        //最多回退3次判断
        if (undoTimes > 0) {
            if (steps % 4 == 1) {
                for (int i = 0; i < temp_1.length; i++) {
                    System.arraycopy(temp_1[i], 0, data[i], 0, temp_1[0].length);
                }
            } else if (steps % 4 == 2) {
                for (int i = 0; i < temp_2.length; i++) {
                    System.arraycopy(temp_2[i], 0, data[i], 0, temp_2[0].length);
                }
            } else if (steps % 4 == 3) {
                for (int i = 0; i < temp_3.length; i++) {
                    System.arraycopy(temp_3[i], 0, data[i], 0, temp_3[0].length);
                }
            } else if (steps % 4 == 0) {
                for (int i = 0; i < temp_0.length; i++) {
                    System.arraycopy(temp_0[i], 0, data[i], 0, temp_0[0].length);
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


}