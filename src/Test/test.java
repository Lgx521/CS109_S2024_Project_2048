package Test;

import javax.swing.*;

public class test extends JFrame {
    public static void main(String[] args) {

        JFrame a = new JFrame();
        a.setSize(600,300);
        a.setLocationRelativeTo(null);
        a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        a.setVisible(true);

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
