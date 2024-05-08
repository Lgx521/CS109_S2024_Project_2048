package Main.Features;

import Main.Controller.CellMotion;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class AI extends CellMotion {

    //原始数组，不予改动
    int[][] data;

    //测试数组
//    int[][] initialData = new int[4][4];
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

    //最高训练次数
    private final int N = 1000;

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
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
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
//                scoreResult[i] += SimulateScore;
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

        System.out.printf("R = %-4d, L = %-4d, U = %-4d, D = %-4d\n",
                nextStepScore[0], nextStepScore[1],
                nextStepScore[2], nextStepScore[3]);

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


}
