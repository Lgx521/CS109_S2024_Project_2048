package Main.UserOperation;

import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginAndSignIn {

    encrypt encoder = new encrypt();

    //保存用户信息
    public void saveUserAccount(String userName, char[] password) throws IOException {
        StringBuilder password_str = new StringBuilder();
        for (int i = 0; i < password.length; i++) {
            password_str.append(password[i]);
        }
        String passwordEncrypted;
        try {
            passwordEncrypted = encoder.encryptBASE64(password_str.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int userID = getTotalLines(new File("src/Main/Data/UserData.txt"));
        String saver = String.format("\nUserID=%s&UserName=%s&Password=%s", userID, userName, passwordEncrypted);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Main/Data/UserData.txt", true));
            writer.write(saver);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("File Save Failed");
        }
        //保存MD5校验码
        fileVerificationSaver(new File("src/Main/Data/UserData.txt"));
    }

    //查询用户是否存在
    /* 存在：返回其UserID
     * 不存在：返回-1
     * 文件损坏：返回-2
     * */
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

    //读取文件查询用户
    public boolean loadUserAccount(int UserID, String userName, char[] userPassword) throws Exception {

        File UserData = new File("src/Main/Data/UserData.txt");

        //先进行文件完整性校验
        if (!md5Verification(UserData)) {
            JOptionPane.showMessageDialog(null, "Data file is modified!\nCan't read.");
            System.out.println("md5 verify failed!");
            return false;
        }

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

            System.out.println("RealUserName: " + RealUserName);

            if (RealUserName.equals(userName)) {
                //用户存在
                StringBuilder sb_pswd = new StringBuilder();
                for (int i = 0; i < userPassword.length; i++) {
                    sb_pswd.append(userPassword[i]);
                }
                //对输入密码进行加密操作
                String userPasswordEncrypted_str = encoder.encryptBASE64(sb_pswd.toString());
                System.out.println("RealUserPassword_Encrypted: " + userPasswordEncrypted_str);
                char[] userPasswordEncrypted = userPasswordEncrypted_str.toCharArray();
                if (RealUserPassword.length != userPasswordEncrypted.length) {
                    return false;
                }
                for (int i = 0; i < RealUserPassword.length; i++) {
                    if (RealUserPassword[i] != userPasswordEncrypted[i]) {
                        return false;
                    }
                }
                return true;
            } else {
                //用户不存在
                return false;
            }
        } else {
            //文件损坏
            return false;
        }
    }

    //获得某行数据
    public String ReadAppointedLine(File file, int targetLine) throws IOException {
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
    public int getTotalLines(File file) throws IOException {
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

    //md5校验
    public boolean md5Verification(File fileToVerify) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/Main/Data/UserData_VerifacationCode.txt"));
        String code = "";
        code = sc.nextLine();
        String rawCode = md5HashCode(fileToVerify.getAbsolutePath());
        System.out.println("MD-5 code: " + code);
        System.out.println("raw MD-5 Code: " + rawCode);
        return rawCode.equals(code);
    }

    //md5校验码保存
    public void fileVerificationSaver(File file) {
        File file_code = new File("src/Main/Data/UserData_VerifacationCode.txt");
        String code = md5HashCode(file.getAbsolutePath());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Main/Data/UserData_VerifacationCode.txt"));
            writer.write(code);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("MD5 Verification code file save Failed");
        }

    }

    //获取文件md5值
    private String md5HashCode(String filePath) {
        try {
            InputStream fis = new FileInputStream(filePath);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            byte[] md5Bytes = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);
            return bigInt.toString(16);
        } catch (Exception e) {
            System.out.println("MD5 code getting error");
            throw new RuntimeException(e);
        }
    }


}