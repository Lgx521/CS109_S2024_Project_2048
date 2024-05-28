package Test;

import Main.UserOperation.LoginAndSignIn;

import java.io.File;

public class getMD5 {
    public static void main(String[] args) {
        LoginAndSignIn loginAndSignIn = new LoginAndSignIn();

//        System.out.println(loginAndSignIn.md5HashCode("src/Main/Data/UserData.txt"));
        System.out.println(loginAndSignIn.md5HashCode("src/Main/Data/Records/statisticsData_2_tile.txt"));
    }
}
