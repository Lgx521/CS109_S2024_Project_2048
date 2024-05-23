package Main.Controller;

import javax.swing.*;

public class CellMotion_2 extends MotionBasic {

    //当前游戏目标
    private int target = 2048;

    //用于读取游戏的构造器
    public CellMotion_2(int steps, int[] score, int status, int target) {
        this.status = status;
        this.target = target;
        undo.setSteps(steps);
        undo.setZeroUndoTime();
        setScoreArr(score);
    }

    //空参构造
    public CellMotion_2() {
    }

    //分数记录解释
    /*
     * [0][0][2][2]  --->  [0][0][0][4]  分数 +4
     * 每合成一次新的格子，分数增加这个新格子的大小
     * */

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

    /*  移动方式解释
     *  每一次都进行移动，如果遇到相同的就进行融合
     *  最后把0过掉
     *  以向右移动为例，从右侧开始扫描，左边前三个数同时开始向右移动，
     *  若2处元素能与3处融合，则移动成功，0索引处补0
     *  然后再使左边两个数同时开始向右移动，
     *  如果1索引处能与2索引处融合，则移动成功，0索引处补0
     *  然后再使左边第一个数向右移动
     *  如果0索引处能与1索引处融合，则移动成功，0索引处补0
     *
     *  step1:
     *  [4][4][8][8]  -->  [0][4][4][16]
     *
     *  step2:
     *  [0][4][4][16]  --> [0][0][8][16]
     *
     *  step3:
     *  (Only Valid for both of the steps above can't operate)
     *  [2][2][4][8]  -->  [0][4][4][8]
     * */
    //上下移动调用上下移动的逻辑方式
    /*
     *进行向上移动，我们只需要给矩阵取对称，然后利用向左移动逻辑，再进行逆变换即可
     *进行向下移动，我们只需要给矩阵取对称，然后利用向右移动逻辑，再进行逆变换即可
     * */

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
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][3] = 2 * data[i][3];
                    setScore(undo.getSteps() + 1, data[i][3]);
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][1] && data[i][1] != 0) {
                    data[i][2] = 2 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][0] && data[i][0] != 0) {
                    data[i][1] = 2 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
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
                if (data[i][0] == data[i][1] && data[i][0] != 0) {
                    data[i][0] = 2 * data[i][0];
                    setScore(undo.getSteps() + 1, data[i][0]);
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][2] && data[i][1] != 0) {
                    data[i][1] = 2 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][2] = 2 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
                    data[i][3] = 0;
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
                if (data[i][0] == data[i][1] && data[i][0] != 0) {
                    data[i][0] = 2 * data[i][0];
                    setScore(undo.getSteps() + 1, data[i][0]);
                    data[i][1] = data[i][2];
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][2] && data[i][1] != 0) {
                    data[i][1] = 2 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
                    data[i][2] = data[i][3];
                    data[i][3] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][2] = 2 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
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
                if (data[i][2] == data[i][3] && data[i][2] != 0) {
                    data[i][3] = 2 * data[i][3];
                    setScore(undo.getSteps() + 1, data[i][3]);
                    data[i][2] = data[i][1];
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][2] == data[i][1] && data[i][1] != 0) {
                    data[i][2] = 2 * data[i][2];
                    setScore(undo.getSteps() + 1, data[i][2]);
                    data[i][1] = data[i][0];
                    data[i][0] = 0;
                    flag = true;
                }
                if (data[i][1] == data[i][0] && data[i][0] != 0) {
                    data[i][1] = 2 * data[i][1];
                    setScore(undo.getSteps() + 1, data[i][1]);
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
        for (int[] datum : data) {
            if (isRightMovable(datum) || isRightHaveZero(datum)) {
                return true;
            }
        }
        return false;
    }

    //判断所有行能否向左移动
    private boolean isFlagLeft(int[][] data) {
        for (int[] datum : data) {
            if (isLeftMovable(datum) || isLeftHaveZero(datum)) {
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
        //如果存在相邻相等非零元素，return true
        for (int i = 0; i < dataLine.length - 1; i++) {
            for (int j = i + 1; j < dataLine.length; j++) {
                if (dataLine[i] == dataLine[j] && dataLine[i] != 0) {
                    if (j == i + 1) {
                        return true;
                    } else if (j == i + 2) {
                        if (dataLine[j - 1] == 0) {
                            return true;
                        }
                    } else if (j == i + 3) {
                        if (dataLine[i + 1] == 0 && dataLine[j - 1] == 0) {
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
        //如果存在相邻相等非零元素，return true
        for (int i = 0; i < dataLine.length - 1; i++) {
            for (int j = i + 1; j < dataLine.length; j++) {
                if (dataLine[i] == dataLine[j] && dataLine[i] != 0) {
                    if (j == i + 1) {
                        return true;
                    } else if (j == i + 2) {
                        if (dataLine[j - 1] == 0) {
                            return true;
                        }
                    } else if (j == i + 3) {
                        if (dataLine[i + 1] == 0 && dataLine[j - 1] == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //移动后在空白位置增加一个新的2或4
    private void RandomAddingCell(int[][] data) {
        boolean flag = false;
        for (int[] datum : data) {
            for (int j = 0; j < data[0].length; j++) {
                if (datum[j] == 0) {
                    flag = true;
                    break;
                }
            }
        }

        if (flag) {
            int[] selectable = {2, 4};
            int pos = r.nextInt(10);
            //90%产生2
            int newCell = pos < 9 ? 0 : 1;
            int i = r.nextInt(data.length);
            int j = r.nextInt(data[0].length);
            if (data[i][j] == 0) {
                data[i][j] = selectable[newCell];
            } else {
                RandomAddingCell(data);
            }
        }


    }

    //判断游戏是否已经不能移动了
    @Override
    public boolean isCanNotMovable(int[][] data) {
        for (int[] datum : data) {
            if (isLeftMovable(datum) || isRightMovable(datum)
                    || isLeftHaveZero(datum) || isRightHaveZero(datum)) {
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

    //判断是否胜利
    private boolean ifYouWin(int[][] data) {
        int target = getTarget();
        for (int[] datum : data) {
            for (int j = 0; j < data[0].length; j++) {
                if (datum[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    //目标getter
    @Override
    public int getTarget() {
        return target;
    }

    //目标值setter
    @Override
    public void setTarget(int target) {
        this.target = target;
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


}