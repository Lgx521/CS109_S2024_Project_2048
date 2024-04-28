package Main.Controller;

import Main.Frame.GameFrame;

import javax.swing.*;
import java.util.Random;

public class CellMotion extends JFrame {

/*
    public void test() {
        int[][] data = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {16, 4, 2, 4}
        };
        printData(data);
        System.out.println("Move");
        moveUp(data);
        System.out.println(ifEnding(data));
    }
*/

    Random r = new Random();

    Undo undo = new Undo();

    public final int YOU_WIN_CAN_PLAY = 0;
    public final int YOU_WIN_CANNOT_PLAY = 1;
    public final int YOU_LOST = 2;

    public final int RIGHT = 0;
    public final int LEFT = 1;
    public final int UP = 2;
    public final int DOWN = 3;

    public int status = 0;

    private int target = 2048;

    //获取步数
    public int getSteps() {
        int steps = undo.getSteps();
        return steps;
    }

    //分数记录解释
    /*
    [0][0][2][2]  --->  [0][0][0][4]  分数 +4
    每合成一次新的格子，分数增加这个新格子的大小
     */
    private int[] score = {0, 0, 0, 0};

    //用于在出现有效格点融合后增加分数
    private void setScore(int step, int scoreIncreasing) {
        if (step % 4 == 0) {
            score[0] = score[0] + scoreIncreasing;
        } else if (step % 4 == 1) {
            score[1] = score[1] + scoreIncreasing;
        } else if (step % 4 == 2) {
            score[2] = score[2] + scoreIncreasing;
        } else if (step % 4 == 3) {
            score[3] = score[3] + scoreIncreasing;
        }
    }

    //用于获取上一步已得到的分数
    private int getScoreByIndex(int step) {
        if ((step - 1) % 4 >= 0) {
            score[(step) % 4] = 0;
            return score[(step - 1) % 4];
        } else {
            score[0] = 0;
            return score[3];
        }
    }

    //获取分数
    public int getScore(int step) {
        if (step % 4 == 0) {
            return score[0];
        } else if (step % 4 == 1) {
            return score[1];
        } else if (step % 4 == 2) {
            return score[2];
        } else {
            return score[3];
        }
    }

    //在游戏结束之前调用，判断是否出现目标格点
    public void moveBeforeWin(int issue, int[][] data) {
        if (issue == RIGHT) {
            moveRight(data);
        } else if (issue == LEFT) {
            moveLeft(data);
        } else if (issue == UP) {
            moveUp(data);
        } else if (issue == DOWN) {
            moveDown(data);
        }
        isEnding(data);
    }

    //在游戏结束之后调用，不判断是否出现目标格点，只判断是否能继续移动
    public void moveAfterWin(int issue, int[][] data) {
        if (isCanNotMovable(data)) {
            JOptionPane.showMessageDialog(null, "Game Over!", "Notice", JOptionPane.NO_OPTION);
        }
        if (issue == RIGHT) {
            moveRight(data);
        } else if (issue == LEFT) {
            moveLeft(data);
        } else if (issue == UP) {
            moveUp(data);
        } else if (issue == DOWN) {
            moveDown(data);
        }
        if (isCanNotMovable(data)) {
            JOptionPane.showMessageDialog(null, "Game Over!", "Notice", JOptionPane.NO_OPTION);
        }
    }

//移动方式解释
/*
    每一次都进行移动，如果遇到相同的就进行融合
    最后把0过掉
    以向右移动为例，从右侧开始扫描，左边前三个数同时开始向右移动，
    若2处元素能与3处融合，则移动成功，0索引处补0
    然后再使左边两个数同时开始向右移动，
    如果1索引处能与2索引处融合，则移动成功，0索引处补0
    然后再使左边第一个数向右移动
    如果0索引处能与1索引处融合，则移动成功，0索引处补0

    step1:
    [4][4][8][8]  -->  [0][4][4][16]

    step2:
    [0][4][4][16]  --> [0][0][8][16]

    step3:
    (Only Valid for both of the steps above can't operate)
    [2][2][4][8]  -->  [0][4][4][8]

 */

    //向右移动
    public void moveRight(int[][] data) {
        if (isCanNotMovable(data)) {
            System.out.println("Game Over!");
            return;
        }
        boolean flag0 = isFlagRight(data);
        if (!flag0) {
            System.out.println("can't move right");
            return;
        }
        undo.saveStatus(data);
        setScore(undo.getSteps() + 1, getScoreByIndex((undo.getSteps() + 1) % 4));
        for (int i = 0; i < data.length; i++) {
            while (isRightHaveZero(data[i])) {
                if (data[i][3] == 0) {
                    data[i][3] = data[i][2];
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                }
                if (data[i][2] == 0) {
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                }
                if (data[i][1] == 0) {
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                }
            }
            loop:
            while (isRightMovable(data[i])) {
                boolean flag = false;
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][3] = 2 * data[i][3];
                    setScore(undo.getSteps() + 1, data[i][3]);
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][1] && data[i][1] != 0) {
                    data[i][2] = 2 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][0] && data[i][0] != 0) {
                    data[i][1] = 2 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
                    data[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break loop;
                }

            }
        }

        if (isCanNotMovable(data)) {
            printData(data);
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(data);
            undo.setSteps(undo.getSteps() + 1);
            undo.saveStatus(data);
            undo.setUndoTimes();
            printData(data);
        }
    }

    //向左移动
    public void moveLeft(int[][] data) {
        if (isCanNotMovable(data)) {
            System.out.println("Game Over!");
            return;
        }
        boolean flag0 = isFlagLeft(data);
        if (!flag0) {
            System.out.println("can't move left");
            return;
        }
        undo.saveStatus(data);
        setScore(undo.getSteps() + 1, getScoreByIndex((undo.getSteps() + 1) % 4));
        for (int i = 0; i < data.length; i++) {
            while (isLeftHaveZero(data[i])) {
                if (data[i][0] == 0) {
                    data[i][0] = data[i][1];
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                }
                if (data[i][1] == 0) {
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                }
                if (data[i][2] == 0) {
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                }
            }
            loop:
            while (isLeftMovable(data[i])) {
                boolean flag = false;
                if (data[i][0] == data[i][1] && data[i][0] != 0) {
                    data[i][0] = 2 * data[i][0];
                    setScore(undo.getSteps() + 1, data[i][0]);
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][2] && data[i][1] != 0) {
                    data[i][1] = 2 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][2] = 2 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
                    data[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break loop;
                }

            }
        }

        if (isCanNotMovable(data)) {
            printData(data);
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(data);
            undo.setSteps(undo.getSteps() + 1);
            undo.saveStatus(data);
            undo.setUndoTimes();
            printData(data);
        }
    }

    //上下移动调用上下移动的逻辑方式
    /*
   进行向上移动，我们只需要给矩阵取对称，然后利用向左移动逻辑，再进行逆变换即可
   进行向下移动，我们只需要给矩阵取对称，然后利用向右移动逻辑，再进行逆变换即可
    */

    //向上移动
    public void moveUp(int[][] data) {
        if (isCanNotMovable(data)) {
            System.out.println("Game Over!");
            return;
        }
        boolean flag0 = isFlagLeft(SymmetryTransformation(data));
        SymmetryTransformation(data);
        if (!flag0) {
            System.out.println("can't move up");
            return;
        }
        undo.saveStatus(data);
        setScore(undo.getSteps() + 1, getScoreByIndex((undo.getSteps() + 1) % 4));
        SymmetryTransformation(data);
        for (int i = 0; i < data.length; i++) {
            while (isLeftHaveZero(data[i])) {
                if (data[i][0] == 0) {
                    data[i][0] = data[i][1];
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                }
                if (data[i][1] == 0) {
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                }
                if (data[i][2] == 0) {
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                }
            }
            loop:
            while (isLeftMovable(data[i])) {
                boolean flag = false;
                if (data[i][0] == data[i][1] && data[i][0] != 0) {
                    data[i][0] = 2 * data[i][0];
                    setScore(undo.getSteps() + 1, data[i][0]);
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][2] && data[i][1] != 0) {
                    data[i][1] = 2 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][2] = 2 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
                    data[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break loop;
                }

            }
        }
        SymmetryTransformation(data);

        if (isCanNotMovable(data)) {
            printData(data);
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(data);
            undo.setSteps(undo.getSteps() + 1);
            undo.saveStatus(data);
            undo.setUndoTimes();
            printData(data);
        }

    }

    //向下移动
    public void moveDown(int[][] data) {
        if (isCanNotMovable(data)) {
            System.out.println("Game Over!");
            return;
        }
        boolean flag0 = isFlagRight(SymmetryTransformation(data));
        SymmetryTransformation(data);
        if (!flag0) {
            System.out.println("can't move down");
            return;
        }
        undo.saveStatus(data);
        setScore(undo.getSteps() + 1, getScoreByIndex((undo.getSteps() + 1) % 4));
        SymmetryTransformation(data);
        for (int i = 0; i < data.length; i++) {
            while (isRightHaveZero(data[i])) {
                if (data[i][3] == 0) {
                    data[i][3] = data[i][2];
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                }
                if (data[i][2] == 0) {
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                }
                if (data[i][1] == 0) {
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                }
            }
            loop:
            while (isRightMovable(data[i])) {
                boolean flag = false;
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][3] = 2 * data[i][3];
                    setScore(undo.getSteps() + 1, data[i][3]);
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][1] && data[i][1] != 0) {
                    data[i][2] = 2 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][0] && data[i][0] != 0) {
                    data[i][1] = 2 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
                    data[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break loop;
                }

            }
        }
        SymmetryTransformation(data);
        if (isCanNotMovable(data)) {
            printData(data);
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(data);
            undo.setSteps(undo.getSteps() + 1);
            undo.saveStatus(data);
            undo.setUndoTimes();
            printData(data);
        }


    }

    //判断所有行能否向右移动
    private boolean isFlagRight(int[][] data) {
        boolean flag0 = false;
        for (int i = 0; i < data.length; i++) {
            if (isRightMovable(data[i]) || isRightHaveZero(data[i])) {
                flag0 = true;
                break;
            }
        }
        return flag0;
    }

    //判断所有行能否向左移动
    private boolean isFlagLeft(int[][] data) {
        boolean flag0 = false;
        for (int i = 0; i < data.length; i++) {
            if (isLeftMovable(data[i]) || isLeftHaveZero(data[i])) {
                flag0 = true;
                break;
            }
        }
        return flag0;
    }

    //判断给定行能否合并的向右移动
    private boolean isRightHaveZero(int[] dataLine) {
        //如果非零元素右边有0，return true
        for (int i = dataLine.length - 1; i > 0; i--) {
            if (dataLine[i] == 0 && dataLine[i - 1] != 0) {
                return true;
            }
        }
        return false;
    }

    //判断给定行能否在右侧有零的情况下移动
    private boolean isRightMovable(int[] dataLine) {
        //如果存在相邻相等非零元素，return true
        boolean flag = false;
        for (int i = 0; i < dataLine.length - 1; i++) {
            for (int j = i + 1; j < dataLine.length; j++) {
                if (dataLine[i] == dataLine[j] && dataLine[i] != 0) {
                    if (j == i + 1) {
                        return true;
                    } else if (j == i + 2) {
                        if (dataLine[j - 1] == 0) {
                            return true;
                        } else {
                            flag = false;
                        }
                    } else if (j == i + 3) {
                        if (dataLine[i + 1] == 0 && dataLine[j - 1] == 0) {
                            return true;
                        } else {
                            flag = false;
                        }
                    }
                }
            }
        }
        return flag;
    }

    //判断给定行能否合并的向左移动
    private boolean isLeftHaveZero(int[] dataLine) {
        //如果非零元素左边有0，return true
        for (int i = 0; i < dataLine.length - 1; i++) {
            if (dataLine[i] == 0 && dataLine[i + 1] != 0) {
                return true;
            }
        }
        return false;
    }

    //判断给定行能否在左边有零的情况下移动
    private boolean isLeftMovable(int[] dataLine) {
        //如果存在相邻相等非零元素，return true
        boolean flag = false;
        for (int i = 0; i < dataLine.length - 1; i++) {
            for (int j = i + 1; j < dataLine.length; j++) {
                if (dataLine[i] == dataLine[j] && dataLine[i] != 0) {
                    if (j == i + 1) {
                        return true;
                    } else if (j == i + 2) {
                        if (dataLine[j - 1] == 0) {
                            return true;
                        } else {
                            flag = false;
                        }
                    } else if (j == i + 3) {
                        if (dataLine[i + 1] == 0 && dataLine[j - 1] == 0) {
                            return true;
                        } else {
                            flag = false;
                        }
                    }
                }
            }
        }
        return flag;
    }

    //取对称矩阵
    private int[][] SymmetryTransformation(int[][] data) {
        int[][] Transformed = new int[data[0].length][data.length];
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data.length; j++) {
                Transformed[i][j] = data[j][i];
            }
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = Transformed[i][j];
            }
        }
        return data;
    }

    //移动后在空白位置增加一个新的2或4
    private void RandomAddingCell(int[][] data) {
        boolean flag = false;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] == 0) {
                    flag = true;
                    break;
                }
            }
        }

        if (flag) {
            int[] selectable = {2, 4};
            int newCell = r.nextInt(0, 2);
            int i = r.nextInt(data.length);
            int j = r.nextInt(data[0].length);
            if (data[i][j] == 0) {
                data[i][j] = selectable[newCell];
            } else {
                RandomAddingCell(data);
            }
        } else {
            return;
        }


    }

    //判断游戏是否已经不能移动了
    public boolean isCanNotMovable(int[][] data) {
        boolean flag = true;
        for (int i = 0; i < data.length; i++) {
            if (isLeftMovable(data[i]) || isRightMovable(data[i])
                    || isLeftHaveZero(data[i]) || isRightHaveZero(data[i])) {
                return false;
            }
        }
        SymmetryTransformation(data);
        for (int i = 0; i < data[0].length; i++) {
            if (isLeftMovable(data[i]) || isRightMovable(data[i])
                    || isLeftHaveZero(data[i]) || isRightHaveZero(data[i])) {
                SymmetryTransformation(data);
                return false;
            }
        }
        SymmetryTransformation(data);
        return true;
    }

    //判断是否胜利
    public boolean ifYouWin(int[][] data) {
        int target = getTarget();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    //getter
    public int getTarget() {
        return target;
    }

    //setter
    public void setTarget(int target) {
        this.target = target;
    }

    //UNDO
    public void UNDO(int[][] data) {
        undo.UNDO(data);
    }

    //判断游戏结束
    public void isEnding(int[][] data) {
        if (ifYouWin(data) && !isCanNotMovable(data)) {
            //胜利：胜利并且可以继续游移动
            System.out.println("Win and Can play");
            EndingNotice(YOU_WIN_CAN_PLAY);
        } else if (ifYouWin(data) && isCanNotMovable(data)) {
            //胜利：胜利且不能继续游移动
            System.out.println("Win and can't play");
            EndingNotice(YOU_WIN_CANNOT_PLAY);
        } else if (!ifYouWin(data) && !isCanNotMovable(data)) {
            //游戏中：未胜利且可以继续移动
            System.out.println("In progress");
        } else if (!ifYouWin(data) && isCanNotMovable(data)) {
            //失败：未胜利且不能继续移动
            System.out.println("lost");
            EndingNotice(YOU_LOST);
        }
    }

    //调用提示框
    private void EndingNotice(int issue) {
        String content = "initial";
        if (issue == YOU_WIN_CAN_PLAY) {
            content = "You Win!\n" +
                    "Do you want to continue your play?\n" +
                    "Choose \"Yes\" to Continue\n" +
                    "Choose \"No\" to Restart game in next step";
            int option = JOptionPane.showConfirmDialog(null, content, "Notice", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                //继续游玩
                status = 1;
            } else {
                status = 2;
            }
        } else if (issue == YOU_WIN_CANNOT_PLAY) {
            content = "You Win!\n" +
                    "Game Over!";
            JOptionPane.showMessageDialog(null, content, "Notice", JOptionPane.NO_OPTION);
        } else if (issue == YOU_LOST) {
            content = "You Lost,\n" +
                    "Game Over!";
            JOptionPane.showMessageDialog(null, content, "Notice", JOptionPane.NO_OPTION);
        }
    }

    //在控制台输出当前棋盘
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