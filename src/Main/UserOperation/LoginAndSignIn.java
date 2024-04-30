package Main.UserOperation;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginAndSignIn {

    public void saveUserAccount(String userName, char[] password) throws IOException {
        StringBuilder password_str = new StringBuilder();
        for (int i = 0; i < password.length; i++) {
            password_str.append(password[i]);
        }
        int userID = getTotalLines(new File("src/Main/Data/UserData.txt"));
        String saver = String.format("\nUserID=%s&UserName=%s&Password=%s",userID,userName, password_str.toString());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Main/Data/UserData.txt",true));
            writer.write(saver);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //todo:显示警告框
            System.out.println("File Save Failed");
        }
    }

    //查询用户是否存在
    /* 存在：返回其UserID
       不存在：返回-1
       文件损坏：返回-2 */
    public int isUserConsistent(String userName) throws IOException {
        File UserData = new File("src/Main/Data/UserData.txt");

        String DetectorRegex = "UserID=(.)+&UserName=(\\w)+&Password=(.)+";
        String UserNameRegex = "UserName=(\\w)+";

        Pattern overall = Pattern.compile(DetectorRegex);
        Pattern name = Pattern.compile(UserNameRegex);

        for (int i = 0; i < getTotalLines(UserData); i++) {
            String DataOfThis = ReadAppointedLine(UserData, i);
            Matcher overallMatcher = overall.matcher(DataOfThis);
            Matcher nameMatcher = name.matcher(DataOfThis);
            if (overallMatcher.find() && nameMatcher.find()) {
                if (nameMatcher.group().substring(9).equals(userName)) {
                    return i;
                }
            } else {
                //文件损坏
                return -2;
            }
        }
        //用户不存在
        return -1;
    }


    public boolean loadUserAccount(int UserID, String userName, char[] userPassword) throws IOException {

        File UserData = new File("src/Main/Data/UserData.txt");

        String DetectorRegex = "UserID=(.)+&UserName=(\\w)+&Password=(.)+";
        String UserNameRegex = "UserName=(\\w)+";
        String UserPasswordRegex = "Password=(.)+";

        Pattern overall = Pattern.compile(DetectorRegex);
        Pattern name = Pattern.compile(UserNameRegex);
        Pattern password = Pattern.compile(UserPasswordRegex);

        String DataOfThis = ReadAppointedLine(UserData, UserID);

        Matcher overallMatcher = overall.matcher(DataOfThis);
        Matcher nameMatcher = name.matcher(DataOfThis);
        Matcher pwdMatcher = password.matcher(DataOfThis);

        boolean ifTrue = overallMatcher.find() && nameMatcher.find() && pwdMatcher.find();

        if (ifTrue) {

            String RealUserName = nameMatcher.group().substring(9);
            char[] RealUserPassword = pwdMatcher.group().substring(9).toCharArray();

            System.out.println(RealUserName);
            for (int i = 0; i < RealUserPassword.length; i++) {
                System.out.print(RealUserPassword[i]);
            }
            System.out.println();

            if (RealUserName.equals(userName)) {
                //用户存在
                for (int i = 0; i < RealUserName.length(); i++) {
                    if (RealUserPassword.length != userPassword.length) {
                        return false;
                    }
                    if (RealUserPassword[i] != userPassword[i]) {
                        return false;
                    }
                    return true;
                }
            } else {
                //用户不存在
                return false;
            }
        } else {
            //文件损坏
            return false;
        }
        return false;
    }




    //获得某行数据
    private String ReadAppointedLine(File file, int targetLine) throws IOException {
        Scanner sc = new Scanner(file);
        String result = null;
        int lineNumber = 0;
        while (sc.hasNextLine()) {
            result = sc.nextLine();
            if (lineNumber == targetLine) {
                return result;
            }
            lineNumber++;
        }
        return null;
    }

    //获得文件总行数
    private int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader lineReader = new LineNumberReader(in);
        int totalLine = 0;
        while (lineReader.readLine() != null) {
            totalLine++;
        }
        in.close();
        lineReader.close();
        return totalLine;
    }




}
