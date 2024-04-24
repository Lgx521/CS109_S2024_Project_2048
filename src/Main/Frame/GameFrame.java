package Main.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {

    //创建setup方法供外界访问
    public void setup() {
        initialGameFrame();
        this.setVisible(true);
    }


    JMenu game = new JMenu("GamePlay");
    JMenu users = new JMenu("Users");
    JMenuItem exit = new JMenuItem("Exit");

    JMenuItem replay = new JMenuItem("Replay");
    JMenuItem load = new JMenuItem("Load");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem mode = new JMenuItem("Game Mode");

    JMenuItem switchUsers = new JMenuItem("Switch Users");

    //todo: 只在访客模式显示login
    JMenuItem login = new JMenuItem("Login");
    JMenuItem logout = new JMenuItem("Logout");


    //初始化界面
    private void initialGameFrame() {
        this.getContentPane().removeAll();
        this.setTitle("2048");
        this.setSize(800, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);

        //设置菜单栏
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.setSize(800, 25);

//        JMenu game = new JMenu("GamePlay");
//        JMenu users = new JMenu("Users");
//        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(this);
//
//        JMenuItem replay = new JMenuItem("Replay");
        replay.addActionListener(this);
//        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(this);
//        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(this);
//        JMenuItem mode = new JMenuItem("Game Mode");
        mode.addActionListener(this);
//
//        JMenuItem switchUsers = new JMenuItem("Switch Users");
        switchUsers.addActionListener(this);
//        JMenuItem login = new JMenuItem("Login");
        login.addActionListener(this);
//        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(this);

        jMenuBar.add(game);
        jMenuBar.add(users);

        game.add(mode);
        game.add(load);
        game.add(save);
        game.add(replay);
        game.add(exit);

        users.add(switchUsers);
        users.add(login);
        users.add(logout);

        this.getContentPane().add(jMenuBar);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == exit) {
            System.exit(0);
        } else if (obj == replay) {
            System.out.println("Replay Game");
        } else if (obj == load) {
            System.out.println("Load Game");
        } else if (obj == save) {
            System.out.println("Save Game");
        } else if (obj == mode) {
            System.out.println("Game Mode");
        } else if (obj == switchUsers) {
            System.out.println("Switch Users");
        } else if (obj == login) {
            System.out.println("Login");
        } else if (obj == logout) {
            System.out.println("Logout");
        }
    }
}



