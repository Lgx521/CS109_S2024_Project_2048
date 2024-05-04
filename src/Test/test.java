package Test;

import Main.Features.bgmPlayer;


import javax.swing.JFrame;

public class test extends JFrame {
//    public static void main(String[] args) throws Exception {

//        String a = "abc123456";
//
//        encrypt er = new encrypt();
//        String b = er.encryptBASE64(a);
//
//        System.out.println(b);
//
//        String c = er.decryptBASE64("MTIzNDU2");
//        String d = er.decryptBASE64("WzEsIDIsIDMsIDQsIDUsIDZd");
//
//        System.out.println(c);
//        System.out.println(d);


//
//            private static final JPanel square = new JPanel();
//            private static int x = 20;
//
//            public static void createAndShowGUI(){
//                JFrame frame = new JFrame();
//                frame.getContentPane().setLayout(null);
//                frame.setSize(500,500);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//                frame.add(square);
//                square.setBounds(20,200,100,100);
//                square.setBackground(Color.RED);
//
//                Timer timer = new Timer(2,new MyActionListener());
//                timer.start();
//                frame.setVisible(true);
//            }
//
//            public static class MyActionListener implements ActionListener {
//
//                @Override
//                public void actionPerformed(ActionEvent arg0) {
//                    square.setLocation(x++, 200);
//
//                }
//
//            }

//            public static void main(String[] args) {
//                javax.swing.SwingUtilities.invokeLater(new Runnable(){
//                    @Override
//                    public void run(){
//                        createAndShowGUI();
//
//                    }
//                });


    public static void main(String[] args) {

        String path = "src/Main/Resources/Music/pure_imagination.wav";
        bgmPlayer musicObject = new bgmPlayer();
        musicObject.playMusic(path);
        int i = 0;
        while(true) {
            System.out.println(i);
            i++;
        }

    }









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
