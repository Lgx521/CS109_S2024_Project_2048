package Main;

import Main.Frame.GameFrame;
import Main.Frame.GameStatics;
import Main.Frame.LoginFrame;

import java.io.IOException;

public class GameStart {
    public static void main(String[] args) {
        new LoginFrame().setup();
//        new GameFrame().setup();
//        try {
//            new GameStatics().setUpGameStatics();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
