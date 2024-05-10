package Main;

import Main.Controller.CellMotion;
import Main.Controller.InitialGrids;
import Main.Frame.GameFrame;
import Main.Frame.GameStatics;
import Main.Frame.LoginFrame;
import Main.Frame.SigninFrame;

public class GameStart {
    public static void main(String[] args) {
//        new LoginFrame().setup();
        new GameFrame().setup();
//        new GameStatics().setUpGameStatics();
    }
}
