package Main.Controller;

import java.util.Random;

public class CellMotion {

    public void test() {
        int[][] data = {
                {2, 2, 2, 2},
                {4, 2, 4, 0},
                {0, 2, 0, 0},
                {2, 2, 4, 4}
        };

        printData(data);
        System.out.println();

        moveRight(data);

        printData(data);




    }



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
        RandomAddingCell(data);
    }

    //向左移动
    public void moveLeft(int[][] data) {
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
        RandomAddingCell(data);
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
        moveLeft(SymmetryTransformation(data));
        SymmetryTransformation(data);
    }

    //向下移动
    public void moveDown(int[][] data) {
        moveRight(SymmetryTransformation(data));
        SymmetryTransformation(data);
    }

    //移动后在空白位置增加一个新的2
    private void RandomAddingCell(int[][] data) {
        Random r = new Random();
        int i = r.nextInt(data.length);
        int j = r.nextInt(data[0].length);
        if (data[i][j] == 0) {
            data[i][j] = 2;
        } else {
            RandomAddingCell(data);
        }
    }



    //在控制台输出当前棋盘
    private void printData(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.printf("%-3d", data[i][j]);
            }
            System.out.println();
        }
    }


}
