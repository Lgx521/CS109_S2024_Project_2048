package Main.Data;

import Main.UserOperation.LoginAndSignIn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatisticsDataStock {

    LoginAndSignIn userOperation = new LoginAndSignIn();

    private String userName;

    private int userID;

    private int maxTile_2 = 0;
    private int maxTile_3 = 0;
    private long timeTo_2048 = 36000L;
    private long timeTo_1024 = 36000L;
    private long timeTo_729 = 36000L;
    private long timeTo_243 = 36000L;


    private final String filePathForPowerOf2 = "src/Main/Data/Records/statisticsData_2_tile.txt";
    private final String filePathForPowerOf2_time = "src/Main/Data/Records/statisticsData_2_time.txt";
    private final String filePathForPowerOf3 = "src/Main/Data/Records/statisticsData_3_tile.txt";
    private final String filePathForPowerOf3_time = "src/Main/Data/Records/statisticsData_3_time.txt";

    private final String userDataFile = "src/Main/Data/UserData.txt";

    //get userName
    private void setUserNameAccordingToFile(int userID) {
        int totalLine = 0;
        String data = "";
        File file = new File("src/Main/Data/UserData.txt");
        try {
            totalLine = userOperation.getTotalLines(file);
            if (userID <= totalLine) {
                data = userOperation.ReadAppointedLine(file, userID);
            }
        } catch (IOException e) {
            //todo
            System.out.println("userData file error");
            throw new RuntimeException(e);
        }

        String UserNameRegex = "UserName=(\\w)+";
        Pattern name = Pattern.compile(UserNameRegex);
        Matcher nameMatcher = name.matcher(data);

        if (nameMatcher.find()) {
            this.userName = nameMatcher.group().substring(9);
        } else {
            System.out.println("Error");
        }

    }

    //syncer
    public void syncStatistics_2(int maxTile_2, long timeTo_2048, long timeTo_1024, int userID) {
        setUserNameAccordingToFile(userID);
        if (maxTile_2 > this.maxTile_2) {
            this.maxTile_2 = maxTile_2;
            System.out.println("maxTile: " + maxTile_2);
            writeMaxTileData_2(maxTile_2, userName);
        }
        if (timeTo_2048 < this.timeTo_2048 && timeTo_2048 != 0) {
            this.timeTo_2048 = timeTo_2048;
            System.out.println("time to 2048: " + timeTo_2048);
            writeMinTimeTo_2048(timeTo_2048, userName);
        }
        if (timeTo_1024 < this.timeTo_1024 && timeTo_1024 != 0) {
            this.timeTo_1024 = timeTo_1024;
            System.out.println("time to 2048: " + timeTo_1024);
            writeMinTimeTo_1024(timeTo_1024, userName);
        }
        this.userID = userID;
    }

    public void syncStatistics_3(int maxTile_3, long timeTo_729, long timeTo_243, int userID) {
        setUserNameAccordingToFile(userID);
        if (maxTile_3 > this.maxTile_3) {
            this.maxTile_3 = maxTile_3;
            System.out.println("maxTile: " + maxTile_3);
            writeMaxTileData_3(maxTile_3, userName);
        }
        if (timeTo_729 < this.timeTo_729 && timeTo_729 != 0) {
            this.timeTo_729 = timeTo_729;
            System.out.println("time to 729: " + timeTo_729);
            writeMinTimeTo_729(timeTo_729, userName);
        }
        if (timeTo_243 < this.timeTo_243 && timeTo_243 != 0) {
            this.timeTo_243 = timeTo_243;
            System.out.println("time to 243: " + timeTo_243);
            writeMinTimeTo_243(timeTo_243, userName);
        }
        this.userID = userID;
    }


    //writer
    private void writeMaxTileData_2(int tileNum, String userName) {

        String saver = String.format("\nPowerOf2&tile=%s&UserName=%s", tileNum, userName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForPowerOf2, true));
            writer.write(saver);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //todo
            System.out.println("File error");
        }
    }

    private void writeMaxTileData_3(int tileNum, String userName) {
        String saver = String.format("\nPowerOf3&tile=%s&UserName=%s", tileNum, userName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForPowerOf3, true));
            writer.write(saver);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //todo
            System.out.println("File error");
        }
    }

    private void writeMinTimeTo_2048(long timeTo_2048, String userName) {
        String saver = String.format("\nPowerOf2&TimeTo_2048=%s&UserName=%s", timeTo_2048, userName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForPowerOf2_time, true));
            writer.write(saver);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //todo
            System.out.println("File error");
        }
    }

    private void writeMinTimeTo_1024(long timeTo_1024, String userName) {
        String saver = String.format("\nPowerOf2&TimeTo_1024=%s&UserName=%s", timeTo_1024, userName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForPowerOf2_time, true));
            writer.write(saver);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //todo
            System.out.println("File error");
        }
    }

    private void writeMinTimeTo_243(long timeTo_243, String userName) {
        String saver = String.format("\nPowerOf3&TimeTo_243=%s&UserName=%s", timeTo_243, userName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForPowerOf3_time, true));
            writer.write(saver);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //todo
            System.out.println("File error");
        }
    }

    private void writeMinTimeTo_729(long timeTo_729, String userName) {
        String saver = String.format("\nPowerOf3&TimeTo_729=%s&UserName=%s", timeTo_729, userName);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePathForPowerOf3_time, true));
            writer.write(saver);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //todo
            System.out.println("File error");
        }
    }


}
