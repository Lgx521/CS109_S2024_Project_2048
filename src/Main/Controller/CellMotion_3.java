package Main.Controller;

import javax.swing.*;

public class CellMotion_3 extends MotionBasic {

    //当前游戏目标
    private int target = 729;

    //用于读取游戏的构造器
    public CellMotion_3(int steps, int[] score, int status, int target) {
        this.status = status;
        this.target = target;
        undo.setSteps(steps);
        undo.setZeroUndoTime();
        setScoreArr(score);
    }

    //空参构造
    public CellMotion_3() {
    }

    //在游戏结束之前调用，判断是否出现目标格点
    @Override
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
    @Override
    public void moveAfterWin(int issue, int[][] data) {
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
            JOptionPane.showMessageDialog(null, "Game Over!", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //向右移动
    @Override
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

            while (isRightMovable(data[i])) {
                boolean flag = false;
                /*
                0  1  2  3       0  1  2  3
                [3][9][9][9] --> [0][0][3][27]
                */
                if (data[i][1] == data[i][2] && data[i][2] == data[i][3] && data[i][1] != 0) {
                    data[i][3] = 3 * data[i][3];
                    setScore(undo.getSteps() + 1, data[i][3]);
                    data[i][2] = data[i][0];
                    data[i][1] = 0;
                    data[i][0] = 0;
                    flag = true;
                }
                /*
             0  1  2   3        0  1   2   3
            [9][9][9][81] --> [0][0][27][81]
             */
                if (data[i][2] == data[i][1] && data[i][1] == data[i][0] && data[i][1] != 0) {
                    data[i][2] = 3 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
                    data[i][1] = 0;
                    data[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }

        if (isCanNotMovable(data)) {
            printData(data);
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(data);
            effectMusicPlayer.playEffectSound(effectMusicPlayer.MOTION_SOUND);
            undo.setSteps(undo.getSteps() + 1);
            undo.saveStatus(data);
            undo.setUndoTimes();
            printData(data);
        }
    }

    //向左移动
    @Override
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
            while (isLeftMovable(data[i])) {
                boolean flag = false;
                /*
                 0  1  2  3         0  1  2  3
                [3][3][3][*]  -->  [9][*][0][0]
                 */
                if (data[i][0] == data[i][1] && data[i][1] == data[i][2] && data[i][0] != 0) {
                    data[i][0] = 3 * data[i][0];
                    setScore(undo.getSteps() + 1, data[i][0]);
                    data[i][1] = data[i][3];
                    data[i][2] = 0;
                    data[i][3] = 0;
                    flag = true;
                }
                /*
                 0  1  2  3         0  1  2  3
                [*][3][3][3]  -->  [*][9][0][0]
                 */
                if (data[i][1] == data[i][2] && data[i][2] == data[i][3] && data[i][1] != 0) {
                    data[i][1] = 3 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
                    data[i][2] = 0;
                    data[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }

        if (isCanNotMovable(data)) {
            super.printData(data);
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(data);
            effectMusicPlayer.playEffectSound(effectMusicPlayer.MOTION_SOUND);
            undo.setSteps(undo.getSteps() + 1);
            undo.saveStatus(data);
            undo.setUndoTimes();
            printData(data);
        }
    }

    //向上移动
    @Override
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
            while (isLeftMovable(data[i])) {
                boolean flag = false;
                /*
                 0  1  2  3         0  1  2  3
                [3][3][3][*]  -->  [9][*][0][0]
                 */
                if (data[i][0] == data[i][1] && data[i][1] == data[i][2] && data[i][0] != 0) {
                    data[i][0] = 3 * data[i][0];
                    setScore(undo.getSteps() + 1, data[i][0]);
                    data[i][1] = data[i][3];
                    data[i][2] = 0;
                    data[i][3] = 0;
                    flag = true;
                }
                /*
                 0  1  2  3         0  1  2  3
                [*][3][3][3]  -->  [*][9][0][0]
                 */
                if (data[i][1] == data[i][2] && data[i][2] == data[i][3] && data[i][1] != 0) {
                    data[i][1] = 3 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
                    data[i][2] = 0;
                    data[i][3] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }
        SymmetryTransformation(data);

        if (isCanNotMovable(data)) {
            printData(data);
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(data);
            effectMusicPlayer.playEffectSound(effectMusicPlayer.MOTION_SOUND);
            undo.setSteps(undo.getSteps() + 1);
            undo.saveStatus(data);
            undo.setUndoTimes();
            printData(data);
        }

    }

    //向下移动
    @Override
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
            while (isRightMovable(data[i])) {
                boolean flag = false;
                /*
                0  1  2  3       0  1  2  3
                [3][9][9][9] --> [0][0][3][27]
                */
                if (data[i][1] == data[i][2] && data[i][2] == data[i][3] && data[i][1] != 0) {
                    data[i][3] = 3 * data[i][3];
                    setScore(undo.getSteps() + 1, data[i][3]);
                    data[i][2] = data[i][0];
                    data[i][1] = 0;
                    data[i][0] = 0;
                    flag = true;
                }
                /*
             0  1  2   3        0  1   2   3
            [9][9][9][81] --> [0][0][27][81]
             */
                if (data[i][2] == data[i][1] && data[i][1] == data[i][0] && data[i][1] != 0) {
                    data[i][2] = 3 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
                    data[i][1] = 0;
                    data[i][0] = 0;
                    flag = true;
                }
                if (flag) {
                    break;
                }

            }
        }
        SymmetryTransformation(data);
        if (isCanNotMovable(data)) {
            printData(data);
            System.out.println("Game Over!");
        } else {
            RandomAddingCell(data);
            effectMusicPlayer.playEffectSound(effectMusicPlayer.MOTION_SOUND);
            undo.setSteps(undo.getSteps() + 1);
            undo.saveStatus(data);
            undo.setUndoTimes();
            printData(data);
        }


    }

    //判断所有行能否向右移动
    private boolean isFlagRight(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            if (isRightMovable(data[i]) || isRightHaveZero(data[i])) {
                return true;
            }
        }
        return false;
    }

    //判断所有行能否向左移动
    private boolean isFlagLeft(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            if (isLeftMovable(data[i]) || isLeftHaveZero(data[i])) {
                return true;
            }
        }
        return false;
    }

    //判断给定行能否在右侧有零的情况下移动
    private boolean isRightHaveZero(int[] dataLine) {
        //如果非零元素右边有0，return true
        for (int i = dataLine.length - 1; i > 0; i--) {
            if (dataLine[i] == 0 && dataLine[i - 1] != 0) {
                return dataLine[i - 1] != 1;
            }
        }
        return false;
    }

    //判断给定行能否合并的向右移动
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

    //判断给定行能否在左边有零的情况下移动
    private boolean isLeftHaveZero(int[] dataLine) {
        //如果非零元素左边有0，return true
        for (int i = 0; i < dataLine.length - 1; i++) {
            if (dataLine[i] == 0 && dataLine[i + 1] != 0) {
                return dataLine[i + 1] != 1;
            }
        }
        return false;
    }

    //判断给定行能否合并的向左移动
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

    //移动后在空白位置增加一个新的3
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
            int newCell = r.nextInt(0, 2);
            int i = r.nextInt(data.length);
            int j = r.nextInt(data[0].length);
            if (data[i][j] == 0) {
                data[i][j] = 3;
            } else {
                RandomAddingCell(data);
            }
        } else {
        }


    }

    //判断游戏是否已经不能移动了
    @Override
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

    //判断游戏结束
    @Override
    public void isEnding(int[][] data) {
        if (ifYouWin(data) && !isCanNotMovable(data)) {
            //胜利：胜利并且可以继续移动
            System.out.println("Win and Can play");
            if (flagOfIsMovable != YOU_WIN_CAN_PLAY) {
                effectMusicPlayer.playEffectSound(effectMusicPlayer.VICTORY_SOUND);
            }
            flagOfIsMovable = YOU_WIN_CAN_PLAY;
        } else if (ifYouWin(data) && isCanNotMovable(data)) {
            //胜利：胜利且不能继续移动
            System.out.println("Win and can't play");
            effectMusicPlayer.playEffectSound(effectMusicPlayer.VICTORY_SOUND);
            flagOfIsMovable = YOU_WIN_CANNOT_PLAY;
        } else if (!ifYouWin(data) && !isCanNotMovable(data)) {
            //游戏中：未胜利且可以继续移动
            System.out.println("In progress");
            flagOfIsMovable = IN_PROGRESS;
        } else if (!ifYouWin(data) && isCanNotMovable(data)) {
            //失败：未胜利且不能继续移动
            System.out.println("lost");
            effectMusicPlayer.playEffectSound(effectMusicPlayer.LOST_SOUND);
            flagOfIsMovable = YOU_LOST;
        }
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

    //目标getter
    @Override
    public int getTarget() {
        return this.target;
    }

    //目标值setter
    @Override
    public void setTarget(int target) {
        this.target = target;
    }


}
