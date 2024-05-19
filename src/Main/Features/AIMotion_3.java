package Main.Features;

import Main.Controller.AIMotionBasic;

import java.util.Random;

public class AIMotion_3 extends AIMotionBasic {


    Random r = new Random();

    @Override
    public void moveRight(int[][] TempData) {
        if (isCanNotMovable(TempData)) {
            return;
        }
        boolean flag0 = isFlagRight(TempData);
        if (!flag0) {
            return;
        }
        for (int i = 0; i < TempData.length; i++) {
            while (isRightHaveZero(TempData[i])) {
                if (TempData[i][3] == 0) {
                    TempData[i][3] = TempData[i][2];
                    TempData[i][2] = TempData[i][1];
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                }
                if (TempData[i][2] == 0) {
                    TempData[i][2] = TempData[i][1];
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                }
                if (TempData[i][1] == 0) {
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                }
            }

            while (isRightMovable(TempData[i])) {
                boolean flag = false;
                /*
                0  1  2  3       0  1  2  3
                [3][9][9][9] --> [0][0][3][27]
                */
                if (TempData[i][1] == TempData[i][2] && TempData[i][2] == TempData[i][3] && TempData[i][1] != 0) {
                    TempData[i][3] = 3 * TempData[i][3];
                    SimulateScore += TempData[i][3];
                    TempData[i][2] = TempData[i][0];
                    TempData[i][1] = 0;
                    TempData[i][0] = 0;
                    flag = true;
                }
                /*
             0  1  2   3        0  1   2   3
            [9][9][9][81] --> [0][0][27][81]
             */
                if (TempData[i][2] == TempData[i][1] && TempData[i][1] == TempData[i][0] && TempData[i][1] != 0) {
                    TempData[i][2] = 3 * TempData[i][2];
                    SimulateScore += TempData[i][2];
                    TempData[i][1] = 0;
                    TempData[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }

        if (isCanNotMovable(TempData)) {
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(TempData);
        }
    }

    @Override
    public void moveLeft(int[][] TempData) {
        if (isCanNotMovable(TempData)) {
            return;
        }
        boolean flag0 = isFlagLeft(TempData);
        if (!flag0) {
            return;
        }
        for (int i = 0; i < TempData.length; i++) {
            while (isLeftHaveZero(TempData[i])) {
                if (TempData[i][0] == 0) {
                    TempData[i][0] = TempData[i][1];
                    TempData[i][1] = TempData[i][2];
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                }
                if (TempData[i][1] == 0) {
                    TempData[i][1] = TempData[i][2];
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                }
                if (TempData[i][2] == 0) {
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                }
            }
            while (isLeftMovable(TempData[i])) {
                boolean flag = false;
                /*
                 0  1  2  3         0  1  2  3
                [3][3][3][*]  -->  [9][*][0][0]
                 */
                if (TempData[i][0] == TempData[i][1] && TempData[i][1] == TempData[i][2] && TempData[i][0] != 0) {
                    TempData[i][0] = 3 * TempData[i][0];
                    SimulateScore += TempData[i][0];
                    TempData[i][1] = TempData[i][3];
                    TempData[i][2] = 0;
                    TempData[i][3] = 0;
                    flag = true;
                }
                /*
                 0  1  2  3         0  1  2  3
                [*][3][3][3]  -->  [*][9][0][0]
                 */
                if (TempData[i][1] == TempData[i][2] && TempData[i][2] == TempData[i][3] && TempData[i][1] != 0) {
                    TempData[i][1] = 3 * TempData[i][1];
                    SimulateScore += TempData[i][1];
                    TempData[i][2] = 0;
                    TempData[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }

        if (isCanNotMovable(TempData)) {
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(TempData);
        }
    }

    @Override
    public void moveUp(int[][] TempData) {
        if (isCanNotMovable(TempData)) {
            return;
        }
        boolean flag0 = isFlagLeft(SymmetryTransformation(TempData));
        SymmetryTransformation(TempData);
        if (!flag0) {
            return;
        }
        SymmetryTransformation(TempData);
        for (int i = 0; i < TempData.length; i++) {
            while (isLeftHaveZero(TempData[i])) {
                if (TempData[i][0] == 0) {
                    TempData[i][0] = TempData[i][1];
                    TempData[i][1] = TempData[i][2];
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                }
                if (TempData[i][1] == 0) {
                    TempData[i][1] = TempData[i][2];
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                }
                if (TempData[i][2] == 0) {
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                }
            }
            while (isLeftMovable(TempData[i])) {
                boolean flag = false;
                /*
                 0  1  2  3         0  1  2  3
                [3][3][3][*]  -->  [9][*][0][0]
                 */
                if (TempData[i][0] == TempData[i][1] && TempData[i][1] == TempData[i][2] && TempData[i][0] != 0) {
                    TempData[i][0] = 3 * TempData[i][0];
                    SimulateScore += TempData[i][0];
                    TempData[i][1] = TempData[i][3];
                    TempData[i][2] = 0;
                    TempData[i][3] = 0;
                    flag = true;
                }
                /*
                 0  1  2  3         0  1  2  3
                [*][3][3][3]  -->  [*][9][0][0]
                 */
                if (TempData[i][1] == TempData[i][2] && TempData[i][2] == TempData[i][3] && TempData[i][1] != 0) {
                    TempData[i][1] = 3 * TempData[i][1];
                    SimulateScore += TempData[i][1];
                    TempData[i][2] = 0;
                    TempData[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }
        SymmetryTransformation(TempData);

        if (isCanNotMovable(TempData)) {
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(TempData);
        }

    }

    @Override
    public void moveDown(int[][] TempData) {
        if (isCanNotMovable(TempData)) {
            return;
        }
        boolean flag0 = isFlagRight(SymmetryTransformation(TempData));
        SymmetryTransformation(TempData);
        if (!flag0) {
            return;
        }
        SymmetryTransformation(TempData);
        for (int i = 0; i < TempData.length; i++) {
            while (isRightHaveZero(TempData[i])) {
                if (TempData[i][3] == 0) {
                    TempData[i][3] = TempData[i][2];
                    TempData[i][2] = TempData[i][1];
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                }
                if (TempData[i][2] == 0) {
                    TempData[i][2] = TempData[i][1];
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                }
                if (TempData[i][1] == 0) {
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                }
            }
            while (isRightMovable(TempData[i])) {
                boolean flag = false;
                /*
                0  1  2  3       0  1  2  3
                [3][9][9][9] --> [0][0][3][27]
                */
                if (TempData[i][1] == TempData[i][2] && TempData[i][2] == TempData[i][3] && TempData[i][1] != 0) {
                    TempData[i][3] = 3 * TempData[i][3];
                    SimulateScore += TempData[i][3];
                    TempData[i][2] = TempData[i][0];
                    TempData[i][1] = 0;
                    TempData[i][0] = 0;
                    flag = true;
                }
                /*
             0  1  2   3        0  1   2   3
            [9][9][9][81] --> [0][0][27][81]
             */
                if (TempData[i][2] == TempData[i][1] && TempData[i][1] == TempData[i][0] && TempData[i][1] != 0) {
                    TempData[i][2] = 3 * TempData[i][2];
                    SimulateScore += TempData[i][2];
                    TempData[i][1] = 0;
                    TempData[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }
        SymmetryTransformation(TempData);
        if (isCanNotMovable(TempData)) {
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(TempData);
        }


    }

    protected int[][] SymmetryTransformation(int[][] data) {
        int[][] Transformed = new int[data[0].length][data.length];
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data.length; j++) {
                Transformed[i][j] = data[j][i];
            }
        }
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(Transformed[i], 0, data[i], 0, data[0].length);
        }
        return data;
    }

    private boolean isFlagRight(int[][] TempData) {
        for (int i = 0; i < TempData.length; i++) {
            if (isRightMovable(TempData[i]) || isRightHaveZero(TempData[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isFlagLeft(int[][] TempData) {
        for (int i = 0; i < TempData.length; i++) {
            if (isLeftMovable(TempData[i]) || isLeftHaveZero(TempData[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isRightHaveZero(int[] dataLine) {
        //如果非零元素右边有0，return true
        for (int i = dataLine.length - 1; i > 0; i--) {
            if (dataLine[i] == 0 && dataLine[i - 1] != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isRightMovable(int[] dataLine) {
        for (int i = 0; i < dataLine.length - 2; i++) {
            for (int j = i + 1; j < dataLine.length - 1; j++) {
                for (int k = j + 1; k < dataLine.length; k++) {
                    if (dataLine[i] != 0 && dataLine[i] == dataLine[j] && dataLine[j] == dataLine[k]) {
                        if (dataLine[0] == 0 || dataLine[1] == 0 || dataLine[2] == 0 || dataLine[3] == 0) {
                            return true;
                        } else if ((i == 1 && j == 2 && k == 3) || (i == 0 && j == 1 && k == 2)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isLeftHaveZero(int[] dataLine) {
        //如果非零元素左边有0，return true
        for (int i = 0; i < dataLine.length - 1; i++) {
            if (dataLine[i] == 0 && dataLine[i + 1] != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isLeftMovable(int[] dataLine) {
        for (int i = 0; i < dataLine.length - 2; i++) {
            for (int j = i + 1; j < dataLine.length - 1; j++) {
                for (int k = j + 1; k < dataLine.length; k++) {
                    if (dataLine[i] != 0 && dataLine[i] == dataLine[j] && dataLine[k] == dataLine[j]) {
                        if (dataLine[0] == 0 || dataLine[1] == 0 || dataLine[2] == 0 || dataLine[3] == 0) {
                            return true;
                        } else if ((i == 1 && j == 2 && k == 3) || (i == 0 && j == 1 && k == 2)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void RandomAddingCell(int[][] TempData) {
        boolean flag = false;
        for (int i = 0; i < TempData.length; i++) {
            for (int j = 0; j < TempData[0].length; j++) {
                if (TempData[i][j] == 0) {
                    flag = true;
                    break;
                }
            }
        }

        if (flag) {
            int newCell = r.nextInt(0, 2);
            int i = r.nextInt(TempData.length);
            int j = r.nextInt(TempData[0].length);
            if (TempData[i][j] == 0) {
                TempData[i][j] = 3;
            } else {
                RandomAddingCell(TempData);
            }
        }

    }

    //判断游戏是否已经不能移动了
    @Override
    public boolean isCanNotMovable(int[][] TempData) {
        for (int i = 0; i < TempData.length; i++) {
            if (isLeftMovable(TempData[i]) || isRightMovable(TempData[i])
                    || isLeftHaveZero(TempData[i]) || isRightHaveZero(TempData[i])) {
                return false;
            }
        }
        SymmetryTransformation(TempData);
        for (int i = 0; i < TempData[0].length; i++) {
            if (isLeftMovable(TempData[i]) || isRightMovable(TempData[i])
                    || isLeftHaveZero(TempData[i]) || isRightHaveZero(TempData[i])) {
                SymmetryTransformation(TempData);
                return false;
            }
        }
        SymmetryTransformation(TempData);
        return true;
    }

}
