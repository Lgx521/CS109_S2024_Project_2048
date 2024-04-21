package Main.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrameSetup extends JFrame implements ActionListener {

    //创建setup方法供外界访问
    public void setup() {
        initialGameFrame();

        this.setVisible(true);
    }





    private void initialGameFrame() {
        this.getContentPane().removeAll();
        this.setTitle("2048");
        this.setSize(700,500);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);

        //设置菜单栏
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.setSize(700,25);

        JMenu game = new JMenu("GamePlay");
        JMenu users = new JMenu("Users");
        JMenuItem exit = new JMenuItem("Exit");

        JMenuItem replay = new JMenuItem("Replay");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem mode = new JMenuItem("Game Mode");

        JMenuItem switchUsers = new JMenuItem("Switch Users");
        JMenuItem logOut = new JMenuItem("Log Out");
        JMenuItem logOff = new JMenuItem("Log Off");

        jMenuBar.add(game);
        jMenuBar.add(users);

        game.add(mode);
        game.add(load);
        game.add(save);
        game.add(replay);
        game.add(exit);

        users.add(switchUsers);
        users.add(logOut);
        users.add(logOff);

        this.getContentPane().add(jMenuBar);


    }






    @Override
    public void actionPerformed(ActionEvent e) {

    }
}



