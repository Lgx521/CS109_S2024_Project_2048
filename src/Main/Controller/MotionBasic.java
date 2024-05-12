package Main.Controller;

import Main.Features.EffectMusicPlayer;

import javax.swing.*;
import java.util.Random;

public abstract class MotionBasic extends JFrame{

    //抽象方法
    public abstract void moveBeforeWin(int issue, int[][] data);
    public abstract void moveAfterWin(int issue, int[][] data);
    public abstract int getTarget();
    public abstract void setTarget(int target);
    public abstract void isEnding(int[][] data);
    public abstract void moveRight(int[][] data);
    public abstract void moveLeft(int[][] data) ;
    public abstract void moveUp(int[][] data);
    public abstract void moveDown(int[][] data) ;
    public abstract boolean isCanNotMovable(int[][] data);

    //游戏状态常量
    /* status == 0: 正常游戏
     * status == 1: 胜利后继续游玩
     * status == 2: 胜利后重新开始游戏 */
    public int status = 0;

    Random r = new Random();

    Undo undo = new Undo();

    EffectMusicPlayer effectMusicPlayer = new EffectMusicPlayer();


    //游戏进程常量
    public final int IN_PROGRESS = -1;
    public final int YOU_WIN_CAN_PLAY = 0;
    public final int YOU_WIN_CANNOT_PLAY = 1;
    public final int YOU_LOST = 2;

    //移动方向常量
    public final int RIGHT = 0;
    public final int LEFT = 1;
    public final int UP = 2;
    public final int DOWN = 3;


    private int[] score = {0, 0, 0, 0};

    public int flagOfIsMovable = IN_PROGRESS;

    //获取步数
    public int getSteps() {
        return undo.getSteps();
    }

    //用于在出现有效格点融合后增加分数
    public void setScore(int step, int scoreIncreasing) {
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
    public int getScoreByIndex(int step) {
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

    //获取分数数组
    public int[] getScoreArr() {
        return score;
    }

    //设置分数数组
    public void setScoreArr(int[] score) {
        System.arraycopy(score, 0, this.score, 0, score.length);
    }

    //在GameFrame中调用的声音播放
    public void EffectSoundPlayer(int soundConstant){
        effectMusicPlayer.playEffectSound(soundConstant);
    }

    //在GameFrame中开关效果声
    public void setEffectSoundStatus() {
        effectMusicPlayer.EffectSoundStatus++;
        String[] arr = {"ON", "OFF"};
        System.out.printf("Effect Sound Status is %s.\n", arr[effectMusicPlayer.EffectSoundStatus % 2]);
    }

    //取对称矩阵
    public int[][] SymmetryTransformation(int[][] data) {
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

    //UNDO
    public void UNDO(int[][] data) throws InterruptedException {
        effectMusicPlayer.playEffectSound(effectMusicPlayer.UNDO_SOUND);
        undo.UNDO(data);
    }

    //调用提示框
    public void EndingNotice(int issue) {
        String content = "initial";
        if (status != 1) {
            if (issue == YOU_WIN_CAN_PLAY) {
                content = """
                        You Win!
                        Do you want to continue your play?
                        Choose "Yes" to Continue
                        Choose "No" to Restart game in next step""";
                int option = JOptionPane.showConfirmDialog(this, content, "Notice", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    //继续游玩
                    status = 1;
                } else {
                    //重新开始游戏
                    status = 2;
                }
            }
        }
        if (issue == YOU_WIN_CANNOT_PLAY) {
            content = "You Win!\n" +
                    "Game Over!";
            JOptionPane.showMessageDialog(this, content, "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
        if (issue == YOU_LOST) {
            content = "You Lost,\n" +
                    "Game Over!";
            JOptionPane.showMessageDialog(this, content, "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //在控制台输出当前棋盘
    public void printData(int[][] data) {
        for (int[] datum : data) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.printf("%-4d", datum[j]);
            }
            System.out.println();
        }
        System.out.println();
    }

}
