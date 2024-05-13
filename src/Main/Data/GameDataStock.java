package Main.Data;

import java.io.Serial;
import java.io.Serializable;

public class GameDataStock implements Serializable {

    @Serial
    private static final long serialVersionUID = -2087760755088145753L;

    /* 特性:
     * All Static Variables
     *
     * 2-D Array Data
     * current user
     * steps
     * score
     *
     * load之后不可以立即重做
     *
     * */

    //重做次数为0

    //初始值为-1，用于未进行游戏时便保存后读取的判断
    private int userID = -1;
    private int[][] gameData = new int[4][4];
    private int steps = -1;
    private int[] score = {0, 0, 0, 0};
    private int target = -1;
    private int motionStatus = -2;
    private int gameModeStatus = -1;
    private long timeLapsed = 0;

    public int getUserID() {
        return userID;
    }

    public int[][] getGameData() {
        return gameData;
    }

    public int getSteps() {
        return steps;
    }

    public int[] getScore() {
        return score;
    }

    public int getTarget() {
        return target;
    }

    public int getMotionStatus() {
        return motionStatus;
    }

    public int getCurrentScore() {
        if (steps % 4 == 0) {
            return score[0];
        } else if (steps % 4 == 1) {
            return score[1];
        } else if (steps % 4 == 2) {
            return score[2];
        } else {
            return score[3];
        }
    }

    public int getGameModeStatus() {
        return this.gameModeStatus;
    }

    public long getTimeLapsed() {
        return timeLapsed;
    }

    //同步数据
    public void sync(int userID, int[][] gameData, int steps, int[] score, int target, int motionStatus, int gameModeStatus, long timeLapsed) {
        this.userID = userID;
        for (int i = 0; i < gameData.length; i++) {
            System.arraycopy(gameData[i], 0, this.gameData[i], 0, gameData.length);
        }
        this.steps = steps;
        System.arraycopy(score, 0, this.score, 0, score.length);
        this.target = target;
        this.motionStatus = motionStatus;
        this.gameModeStatus = gameModeStatus;
        this.timeLapsed = timeLapsed;
//        System.out.printf("Synced, userID: %s, steps: %s, score: %s, target: %s\n", userID, steps, score, target);
    }


}
