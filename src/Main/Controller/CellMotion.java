package Main.Controller;

import java.util.Random;

public class CellMotion {

    /*
    To test the GameOver Validation

    public void test() {
        int[][] data = {
                {128, 4, 64, 64},
                {4, 8, 256, 16},
                {4, 64, 32, 8},
                {2, 8, 4, 32}
        };
        printData(data);
        System.out.println(isRightMovable(data[0]));
        System.out.println(ifEnding(data));

        System.out.println("Move right1");
        moveRight(data);
        System.out.println(ifEnding(data));

        System.out.println("Move right2");
        moveRight(data);
    }*/

    public void test() {
        int[][] data0 = {
                {16, 16, 16, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        moveUp(data0);

        moveUp(data0);






    }

    Random r = new Random();

    Undo undo = new Undo();



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
        if (ifEnding(data)) {
            System.out.println("Game Over!");
            return;
        }
        boolean flag0 = isFlagRight(data);
        if (!flag0) {
            System.out.println("can't move right");
            return;
        }
        undo.saveStatus(data);
        for (int i = 0; i < data.length; i++) {
            loop:
            while (isRightMovable(data[i])) {
                boolean flag = false;
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
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][3] = 2 * data[i][3];
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][1] && data[i][1] != 0) {
                    data[i][2] = 2 * data[i][2];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][0] && data[i][0] != 0) {
                    data[i][1] = 2 * data[i][1];
                    data[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break loop;
                }

            }
        }

        if (ifEnding(data)) {
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

    private boolean isFlagRight(int[][] data) {
        boolean flag0 = false;
        for (int i = 0; i < data.length; i++) {
            if (isRightMovable(data[i])) {
                flag0 = true;
                break;
            }
        }
        return flag0;
    }

    //向左移动
    public void moveLeft(int[][] data) {
        if (ifEnding(data)) {
            System.out.println("Game Over!");
            return;
        }
        boolean flag0 = isFlagLeft(data);
        if (!flag0) {
            System.out.println("can't move left");
            return;
        }
        undo.saveStatus(data);
        for (int i = 0; i < data.length; i++) {
            loop:
            while (isLeftMovable(data[i])) {
                boolean flag = false;
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
                if (data[i][0] == data[i][1] && data[i][0] != 0) {
                    data[i][0] = 2 * data[i][0];
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][2] && data[i][1] != 0) {
                    data[i][1] = 2 * data[i][1];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][2] = 2 * data[i][2];
                    data[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break loop;
                }

            }
        }

        if (ifEnding(data)) {
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

    private boolean isFlagLeft(int[][] data) {
        boolean flag0 = false;
        for (int i = 0; i < data.length; i++) {
            if (isLeftMovable(data[i])) {
                flag0 = true;
                break;
            }
        }
        return flag0;
    }

    //判断给定行能否向右移动
    private boolean isRightMovable(int[] dataLine) {
        //如果非零元素右边有0，return true
        for (int i = dataLine.length - 1; i > 0; i--) {
            if (dataLine[i] == 0 && dataLine[i - 1] != 0) {
                return true;
            }
        }
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

    //判断给定行能否向左移动
    private boolean isLeftMovable(int[] dataLine) {
        //如果非零元素左边有0，return true
        for (int i = 0; i < dataLine.length - 1; i++) {
            if (dataLine[i] == 0 && dataLine[i + 1] != 0) {
                return true;
            }
        }
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

    /*
    进行向上移动，我们只需要给矩阵取对称，然后利用向左移动逻辑，再进行逆变换即可
    进行向下移动，我们只需要给矩阵取对称，然后利用向右移动逻辑，再进行逆变换即可
     */

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

    //向上移动
    public void moveUp(int[][] data) {
        if (ifEnding(data)) {
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
        SymmetryTransformation(data);
        for (int i = 0; i < data.length; i++) {
            loop:
            while (isLeftMovable(data[i])) {
                boolean flag = false;
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
                if (data[i][0] == data[i][1] && data[i][0] != 0) {
                    data[i][0] = 2 * data[i][0];
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][2] && data[i][1] != 0) {
                    data[i][1] = 2 * data[i][1];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][2] = 2 * data[i][2];
                    data[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break loop;
                }

            }
        }
        SymmetryTransformation(data);

        if (ifEnding(data)) {
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
        if (ifEnding(data)) {
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
        SymmetryTransformation(data);
        for (int i = 0; i < data.length; i++) {
            loop:
            while (isRightMovable(data[i])) {
                boolean flag = false;
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
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][3] = 2 * data[i][3];
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][1] && data[i][1] != 0) {
                    data[i][2] = 2 * data[i][2];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][0] && data[i][0] != 0) {
                    data[i][1] = 2 * data[i][1];
                    data[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break loop;
                }

            }
        }
        SymmetryTransformation(data);
        if (ifEnding(data)) {
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

    //移动后在空白位置增加一个新的2
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

    //判断游戏是否结束
    private boolean ifEnding(int[][] data) {
        boolean flag = true;
        for (int i = 0; i < data.length; i++) {
            if (isLeftMovable(data[i]) || isRightMovable(data[i])) {
                return false;
            }
        }
        int[][] temp = SymmetryTransformation(data);
        for (int i = 0; i < data[0].length; i++) {
            if (isLeftMovable(temp[i]) || isRightMovable(temp[i])) {
                SymmetryTransformation(data);
                return false;
            }
        }
        SymmetryTransformation(data);
        return true;
    }

    //UNDO
    public void UNDO(int[][] data) {
        undo.UNDO(data);
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
