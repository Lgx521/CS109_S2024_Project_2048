package Main.Features;

import Main.Controller.AIMotionBasic;

import java.util.Random;

public class AIMotion_2 extends AIMotionBasic {


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
                if (TempData[i][2] == TempData[i][3] && TempData[i][2] != 0) {
                    TempData[i][3] = 2 * TempData[i][3];
                    SimulateScore += TempData[i][3];
                    TempData[i][2] = TempData[i][1];
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                    flag = true;
                }
                if (TempData[i][2] == TempData[i][1] && TempData[i][1] != 0) {
                    TempData[i][2] = 2 * TempData[i][2];
                    SimulateScore += TempData[i][2];
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                    flag = true;
                }
                if (TempData[i][1] == TempData[i][0] && TempData[i][0] != 0) {
                    TempData[i][1] = 2 * TempData[i][1];
                    SimulateScore += TempData[i][1];
                    TempData[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }

        if (!isCanNotMovable(TempData)) {
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
                if (TempData[i][0] == TempData[i][1] && TempData[i][0] != 0) {
                    TempData[i][0] = 2 * TempData[i][0];
                    SimulateScore += TempData[i][0];
                    TempData[i][1] = TempData[i][2];
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                    flag = true;
                }
                if (TempData[i][1] == TempData[i][2] && TempData[i][1] != 0) {
                    TempData[i][1] = 2 * TempData[i][1];
                    SimulateScore += TempData[i][1];
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                    flag = true;
                }
                if (TempData[i][2] == TempData[i][3] && TempData[i][2] != 0) {
                    TempData[i][2] = 2 * TempData[i][2];
                    SimulateScore += TempData[i][2];
                    TempData[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }

        if (!isCanNotMovable(TempData)) {
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
                if (TempData[i][0] == TempData[i][1] && TempData[i][0] != 0) {
                    TempData[i][0] = 2 * TempData[i][0];
                    SimulateScore += TempData[i][0];
                    TempData[i][1] = TempData[i][2];
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                    flag = true;
                }
                if (TempData[i][1] == TempData[i][2] && TempData[i][1] != 0) {
                    TempData[i][1] = 2 * TempData[i][1];
                    SimulateScore += TempData[i][1];
                    TempData[i][2] = TempData[i][3];
                    TempData[i][3] = 0;
                    flag = true;
                }
                if (TempData[i][2] == TempData[i][3] && TempData[i][2] != 0) {
                    TempData[i][2] = 2 * TempData[i][2];
                    SimulateScore += TempData[i][2];
                    TempData[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }
        SymmetryTransformation(TempData);

        if (!isCanNotMovable(TempData)) {
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
                if (TempData[i][2] == TempData[i][3] && TempData[i][2] != 0) {
                    TempData[i][3] = 2 * TempData[i][3];
                    SimulateScore += TempData[i][3];
                    TempData[i][2] = TempData[i][1];
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                    flag = true;
                }
                if (TempData[i][2] == TempData[i][1] && TempData[i][1] != 0) {
                    TempData[i][2] = 2 * TempData[i][2];
                    SimulateScore += TempData[i][2];
                    TempData[i][1] = TempData[i][0];
                    TempData[i][0] = 0;
                    flag = true;
                }
                if (TempData[i][1] == TempData[i][0] && TempData[i][0] != 0) {
                    TempData[i][1] = 2 * TempData[i][1];
                    SimulateScore += TempData[i][1];
                    TempData[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }
        SymmetryTransformation(TempData);
        if (!isCanNotMovable(TempData)) {
            RandomAddingCell(TempData);
        }


    }

    protected boolean isFlagRight(int[][] TempData) {
        boolean flag0 = false;
        for (int i = 0; i < TempData.length; i++) {
            if (isRightMovable(TempData[i]) || isRightHaveZero(TempData[i])) {
                flag0 = true;
                break;
            }
        }
        return flag0;
    }

    protected boolean isFlagLeft(int[][] TempData) {
        boolean flag0 = false;
        for (int i = 0; i < TempData.length; i++) {
            if (isLeftMovable(TempData[i]) || isLeftHaveZero(TempData[i])) {
                flag0 = true;
                break;
            }
        }
        return flag0;
    }

    protected boolean isRightHaveZero(int[] dataLine) {
        //如果非零元素右边有0，return true
        for (int i = dataLine.length - 1; i > 0; i--) {
            if (dataLine[i] == 0 && dataLine[i - 1] != 0) {
                return true;
            }
        }
        return false;
    }

    protected boolean isRightMovable(int[] dataLine) {
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

    protected boolean isLeftHaveZero(int[] dataLine) {
        //如果非零元素左边有0，return true
        for (int i = 0; i < dataLine.length - 1; i++) {
            if (dataLine[i] == 0 && dataLine[i + 1] != 0) {
                return true;
            }
        }
        return false;
    }

    protected boolean isLeftMovable(int[] dataLine) {
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

    protected int[][] SymmetryTransformation(int[][] TempData) {
        int[][] Transformed = new int[TempData[0].length][TempData.length];
        for (int i = 0; i < TempData[0].length; i++) {
            for (int j = 0; j < TempData.length; j++) {
                Transformed[i][j] = TempData[j][i];
            }
        }
        for (int i = 0; i < TempData.length; i++) {
            for (int j = 0; j < TempData[0].length; j++) {
                TempData[i][j] = Transformed[i][j];
            }
        }
        return TempData;
    }

    protected void RandomAddingCell(int[][] TempData) {
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
            int[] selectable = {2, 4};
            int newCell = r.nextInt(0, 2);
            int i = r.nextInt(TempData.length);
            int j = r.nextInt(TempData[0].length);
            if (TempData[i][j] == 0) {
                TempData[i][j] = selectable[newCell];
            } else {
                RandomAddingCell(TempData);
            }
        } else {
            return;
        }


    }

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
