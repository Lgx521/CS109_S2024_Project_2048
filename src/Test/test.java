package Test;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Main.UserOperation.encrypt;

public class test extends JFrame {
    public static void main(String[] args) throws Exception {

        String a = "abc123456";

        encrypt er = new encrypt();
        String b = er.encryptBASE64(a);

        System.out.println(b);

        String c = er.decryptBASE64("MTIzNDU2");
        String d = er.decryptBASE64("WzEsIDIsIDMsIDQsIDUsIDZd");

        System.out.println(c);
        System.out.println(d);














//
//        String DataOfThis = "UserID=0&UserName=gan&Password=123";
//
//        String DetectorRegex = "UserID=.+&UserName=\\w+&Password=.+";
//        String UserNameRegex = "UserName=\\w+";
//
//        Pattern overall = Pattern.compile(DetectorRegex);
//        Pattern name = Pattern.compile(UserNameRegex);
//
//        Matcher overallMatcher = overall.matcher(DataOfThis);
//        Matcher nameMatcher = name.matcher(DataOfThis);
//
//        System.out.println(overallMatcher.find());
//        if (nameMatcher.find()){
//            System.out.println(nameMatcher.group().substring(9));
//        }
//        System.out.println(overallMatcher.find());



//
//        JFrame a = new JFrame();
//        a.setSize(600,300);
//        a.setLocationRelativeTo(null);
//        a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        a.setVisible(true);

//        Time time = new Time();
//        new Thread(time).start();

    }

}


//private Timer time;
//
////时间显示
//private JLabel getTimelabel() {
//    if (timelabel == null) {
//        timelabel = new JLabel("");
//        timelabel.setBounds(5, 65, 200, 20);
//        timelabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
//        timelabel.setForeground(new Color(182, 229, 248));
//        time = new Timer(1000,new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                timelabel.setText(new SimpleDateFormat("yyyy年MM月dd日 EEEE hh:mm:ss").format(new Date()));
//            }
//        }
//        time.start();
//    }
//    return timelabel;
//}
