package Main.Controller;

public abstract class AIMotionBasic {
    public abstract void moveUp(int[][] TempData);

    public abstract void moveDown(int[][] TempData);

    public abstract void moveLeft(int[][] TempData);

    public abstract void moveRight(int[][] TempData);

    public abstract boolean isCanNotMovable(int[][] TempData);

    public int SimulateScore;
}
