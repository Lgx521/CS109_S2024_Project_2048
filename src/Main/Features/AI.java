package Main.Features;

import Main.Controller.AIMotionBasic;

import java.util.Arrays;
import java.util.Random;

public class AI {

    AIMotionBasic motion;

    //2^n, 3^n切换
    public void setMotion(int mode) {
        if (mode == 0) {
            motion = new AIMotion_2();
            this.N = 360;
        } else if (mode == 1) {
            motion = new AIMotion_3();
            this.N = 650;
        }
    }

    //最高迭代训练次数
    private int N = 350;

    //原始数组，不予改动
    int[][] data;

    //测试数组
    int[][] TempData = new int[4][4];

    //setter
    public void setData(int[][] data) {
        this.data = data;
    }

    //刷新测试数组
    public void setTempData() {
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, TempData[i], 0, data[0].length);
        }
    }

    //随机数生成器
    Random r = new Random();

    //N次模拟结果集
    int[] scoreResult = new int[N];

    //下一步预测成绩结果集
    int[] nextStepScore = new int[4];

    //训练寻找下一步
    private void NextStepPrediction() {
        int step = 0;
        while (step < N) {
            //1. move right
            motion.moveRight(TempData);
            nextStepScore[0] += RandomMoving(TempData);
            //2. move left
            motion.moveLeft(TempData);
            nextStepScore[1] += RandomMoving(TempData);
            //3. move up
            motion.moveUp(TempData);
            nextStepScore[2] += RandomMoving(TempData);
            //4. move down
            motion.moveDown(TempData);
            nextStepScore[3] += RandomMoving(TempData);
            step++;
        }
    }

    //模拟计算N步，返回最高分数
    private int RandomMoving(int[][] TempData) {
        for (int i = 0; i < N; i++) {
            int nextDirection = r.nextInt(4);
            if (!motion.isCanNotMovable(TempData)) {
                if (nextDirection == 0) {
                    //move right
                    motion.moveRight(TempData);
                    scoreResult[i] += motion.SimulateScore;
                } else if (nextDirection == 1) {
                    //move left
                    motion.moveLeft(TempData);
                    scoreResult[i] += motion.SimulateScore;
                } else if (nextDirection == 2) {
                    //move up
                    motion.moveUp(TempData);
                    scoreResult[i] += motion.SimulateScore;
                } else {
                    //move down
                    motion.moveDown(TempData);
                    scoreResult[i] += motion.SimulateScore;
                }
            } else {
                break;
            }
        }

        //找到最高分
        int result = 0;
        for (int j : scoreResult) {
            if (j > result) {
                result = j;
            }
        }

        //恢复score
        motion.SimulateScore = 0;

        //恢复分数数组
        Arrays.fill(scoreResult, 0);

        //恢复TempData为初始值
        setTempData();

        return result;
    }

    private int stepVerify = 0;

    //蒙特卡洛
    public int MonteCarlo(int[][] data) {
        //初始化
        setData(data);
        setTempData();

        //开始模拟计算
        NextStepPrediction();

        stepVerify++;

        if (stepVerify > 50 && nextStepScore[0] == nextStepScore[1] && nextStepScore[1] == nextStepScore[2]
                && nextStepScore[2] == nextStepScore[3]) {
            return stepVerify % 4;
        }

        //返回期望最高者
        int temp = 0;
        int direction = 0;
        for (int i = 0; i < nextStepScore.length; i++) {
            if (nextStepScore[i] > temp) {
                temp = nextStepScore[i];
                direction = i;
            }
        }


        System.out.printf("R = %-6d, L = %-6d, U = %-6d, D = %-6d\n",
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
            if (motion.isCanNotMovable(Data)) {
                System.out.println("Max step: " + step);
                return;
            }
            int direction = MonteCarlo(Data);
             if (direction == 0) {
                motion.moveRight(Data);
            } else if (direction == 1) {
                motion.moveLeft(Data);
            } else if (direction == 2) {
                motion.moveUp(Data);
            } else if (direction == 3) {
                motion.moveDown(Data);
            }
            step++;
        }
    }

    //AI不停操作
    public void AIMovingNonStop(int[][] Data) {
        int step = 0;
        while (true) {
            int direction = MonteCarlo(Data);
            if (motion.isCanNotMovable(Data)) {
                System.out.println("Max step: " + step);
                return;
            }
            if (direction == 0) {
                motion.moveRight(Data);
            } else if (direction == 1) {
                motion.moveLeft(Data);
            } else if (direction == 2) {
                motion.moveUp(Data);
            } else if (direction == 3) {
                motion.moveDown(Data);
            }
            step++;
        }
    }


}
