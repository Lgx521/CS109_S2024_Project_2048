package Main.Features;

import Main.Controller.CellMotion;
import Main.Frame.GameFrame;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class AI {

    //最高迭代训练次数
    private final int N = 350;

    //原始数组，不予改动
    int[][] data;

    //测试数组
    int[][] TempData = new int[4][4];

    public void setData(int[][] data) {
        this.data = data;
    }

    //刷新测试数组
    private void setTempData() {
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, TempData[i], 0, data[0].length);
        }
    }

    //随机数生成器
    Random r = new Random();

    //当前分数
    private int SimulateScore = 0;

    //N次模拟结果集
    int[] scoreResult = new int[N];

    //下一步预测成绩结果集
    int[] nextStepScore = new int[4];

    //训练寻找下一步
    private void NextStepPrediction() {
        int step = 0;
        while (step < N) {
            //1. move right
            moveRight(TempData);
            nextStepScore[0] += RandomMoving(TempData);
            //2. move left
            moveLeft(TempData);
            nextStepScore[1] += RandomMoving(TempData);
            //3. move up
            moveUp(TempData);
            nextStepScore[2] += RandomMoving(TempData);
            //4. move down
            moveDown(TempData);
            nextStepScore[3] += RandomMoving(TempData);
            step++;
        }
    }

    //模拟计算N步，返回最高分数
    private int RandomMoving(int[][] TempData) {
        for (int i = 0; i < N; i++) {
            int nextDirection = r.nextInt(4);
            if (!isCanNotMovable(TempData)) {
                if (nextDirection == 0) {
                    //move right
                    moveRight(TempData);
                    scoreResult[i] += SimulateScore;
                } else if (nextDirection == 1) {
                    //move left
                    moveLeft(TempData);
                    scoreResult[i] += SimulateScore;
                } else if (nextDirection == 2) {
                    //move up
                    moveUp(TempData);
                    scoreResult[i] += SimulateScore;
                } else if (nextDirection == 3) {
                    //move down
                    moveDown(TempData);
                    scoreResult[i] += SimulateScore;
                }
            } else {
                break;
            }
        }

        //找到最高分
        int result = 0;
        for (int i = 0; i < scoreResult.length; i++) {
            if (scoreResult[i] > result) {
                result = scoreResult[i];
            }
        }

        //测试：输出分数数组
//        for (int i = 0; i < N; i++) {
//            System.out.print(scoreResult[i] + " ");
//        }
//        System.out.println();

        //恢复score
        SimulateScore = 0;

        //恢复分数数组
        Arrays.fill(scoreResult, 0);

        //恢复TempData为初始值
        setTempData();

        return result;
    }

    //蒙特卡洛
    public int MonteCarlo(int[][] data) {
        //初始化
        setData(data);
        setTempData();

        //开始模拟计算
        NextStepPrediction();

        //返回期望最高者
        int temp = 0;
        int direction = 0;
        for (int i = 0; i < nextStepScore.length; i++) {
            if (nextStepScore[i] > temp) {
                temp = nextStepScore[i];
                direction = i;
            }
        }

//        System.out.printf("R = %-6d, L = %-6d, U = %-6d, D = %-6d\n",
//                nextStepScore[0], nextStepScore[1],
//                nextStepScore[2], nextStepScore[3]);

        //恢复nextStepScore数组
        Arrays.fill(nextStepScore, 0);

        return direction;

    }

    //AI走设定步数
    public void AIMoving(int targetStep, int[][] Data) {
        int step = 0;
        while (step < targetStep) {
            int direction = MonteCarlo(Data);
            if (isCanNotMovable(data)) {
                System.out.println("Max step: " + step);
                return;
            }
            if (direction == 0) {
                moveRight(Data);
            } else if (direction == 1) {
                moveLeft(Data);
            } else if (direction == 2) {
                moveUp(Data);
            } else if (direction == 3) {
                moveDown(Data);
            }
            step++;
        }
    }

    //AI不停操作
    public void AIMovingNonStop(int[][] Data) {
        int step = 0;
        while (true) {
            int direction = MonteCarlo(Data);
            if (isCanNotMovable(data)) {
                System.out.println("Max step: " + step);
                return;
            }
            if (direction == 0) {
                moveRight(Data);
            } else if (direction == 1) {
                moveLeft(Data);
            } else if (direction == 2) {
                moveUp(Data);
            } else if (direction == 3) {
                moveDown(Data);
            }
            step++;
        }
    }


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

    protected boolean isFlagRight(int[][] data) {
        boolean flag0 = false;
        for (int i = 0; i < data.length; i++) {
            if (isRightMovable(data[i]) || isRightHaveZero(data[i])) {
                flag0 = true;
                break;
            }
        }
        return flag0;
    }

    protected boolean isFlagLeft(int[][] data) {
        boolean flag0 = false;
        for (int i = 0; i < data.length; i++) {
            if (isLeftMovable(data[i]) || isLeftHaveZero(data[i])) {
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

    protected int[][] SymmetryTransformation(int[][] data) {
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

    protected void RandomAddingCell(int[][] data) {
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

    public boolean isCanNotMovable(int[][] data) {
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


}
