package Main;

import Main.Frame.GameFrame;
import Main.Frame.GameStatics;
import Main.Frame.LoginFrame;

public class GameStart {
    public static void main(String[] args) {
//        new LoginFrame().setup();
//        new GameFrame().setup();
        new GameStatics().setUpGameStatics();
    }
}
