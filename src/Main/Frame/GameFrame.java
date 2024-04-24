package Main.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameFrame extends JFrame implements ActionListener, MouseListener {

    //创建setup方法供外界访问
    public void setup() {
        initialGameFrame();
        setBackgroundImage(ImagePath + "GameFrameBackground");
        addMouseListener(this);
        this.setVisible(true);
    }


    JMenu game = new JMenu("GamePlay");
    JMenu users = new JMenu("Users");
    JMenu music = new JMenu("Select Music");
    JMenuItem exit = new JMenuItem("Exit");

    JMenuItem replay = new JMenuItem("Replay Game");
    JMenuItem load = new JMenuItem("Load Game");
    JMenuItem save = new JMenuItem("Save Game");
    JMenuItem sound = new JMenuItem("Sound On/Off");

    JMenuItem music_1 = new JMenuItem("Music 1");
    JMenuItem music_2 = new JMenuItem("Music 2");
    JMenuItem music_3 = new JMenuItem("Music 3");

    //todo: 只在访客模式显示login
    JMenuItem login = new JMenuItem("Log in");
    JMenuItem logout = new JMenuItem("Log out");

    //图形变化处理
    JLabel undo = new JLabel();
    JLabel hammer = new JLabel();
    JLabel ai = new JLabel();
    JLabel _2N = new JLabel();
    JLabel _3N = new JLabel();
    JLabel countdown = new JLabel();
    JLabel target = new JLabel();
    JLabel Default = new JLabel();
    JLabel forest = new JLabel();
    JLabel ocean = new JLabel();
    JLabel flames = new JLabel();

    JLabel backgroundImage = new JLabel();

    JLabel test = new JLabel();

    private final String ImagePath = "src/Main/Resources/";


    //初始化界面
    private void initialGameFrame() {
        this.getContentPane().removeAll();
        this.setTitle("2048");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);


        //设置菜单栏
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.setSize(800, 25);

        jMenuBar.add(game);
        jMenuBar.add(users);

        game.add(load);
        game.add(save);
        game.add(replay);
        game.add(sound);
        game.add(music);
        game.add(exit);

        music.add(music_1);
        music.add(music_2);
        music.add(music_3);

        users.add(login);
        users.add(logout);

        //图形变化
        undo.setSize(140, 40);
        undo.setBounds(465, 263, 140, 40);
        hammer.setSize(65, 50);
        hammer.setBounds(625, 306, 65, 50);
        ai.setSize(65, 50);
        ai.setBounds(700, 306, 65, 50);
        _2N.setSize(65, 40);
        _2N.setBounds(465, 411, 65, 40);
        _3N.setSize(65, 40);
        _3N.setBounds(540, 411, 65, 40);
        countdown.setSize(70, 40);
        countdown.setBounds(615, 411, 70, 40);
        target.setSize(70, 40);
        target.setBounds(695, 411, 70, 40);
        Default.setSize(65, 40);
        Default.setBounds(465, 505, 65, 40);
        forest.setSize(65, 40);
        forest.setBounds(540, 505, 65, 40);
        ocean.setSize(70, 40);
        ocean.setBounds(615, 505, 70, 40);
        flames.setSize(70, 40);
        flames.setBounds(695, 505, 70, 40);


        //添加监听
        exit.addActionListener(this);
        replay.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        login.addActionListener(this);
        logout.addActionListener(this);
        sound.addActionListener(this);
        music_1.addActionListener(this);
        music_2.addActionListener(this);
        music_3.addActionListener(this);

        undo.addMouseListener(this);
        hammer.addMouseListener(this);
        ai.addMouseListener(this);
        _2N.addMouseListener(this);
        _3N.addMouseListener(this);
        countdown.addMouseListener(this);
        target.addMouseListener(this);
        Default.addMouseListener(this);
        forest.addMouseListener(this);
        ocean.addMouseListener(this);
        flames.addMouseListener(this);

        this.getContentPane().add(jMenuBar);
        this.getContentPane().add(undo);
        this.getContentPane().add(hammer);
        this.getContentPane().add(ai);
        this.getContentPane().add(_2N);
        this.getContentPane().add(_3N);
        this.getContentPane().add(countdown);
        this.getContentPane().add(target);
        this.getContentPane().add(Default);
        this.getContentPane().add(forest);
        this.getContentPane().add(ocean);
        this.getContentPane().add(flames);

    }

    private void setBackgroundImage(String ImagePath) {
        this.getContentPane().remove(backgroundImage);
        this.setLayout(null);
        //背景图
        ImageIcon Image = new ImageIcon(ImagePath + ".png");
        backgroundImage.setIcon(Image);
        backgroundImage.setSize(800, 550);
        backgroundImage.setBounds(0, 23, 800, 550);
        this.getContentPane().add(backgroundImage);
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
        } else if (obj == sound) {
            System.out.println("Sound Turn On/Off");
        } else if (obj == login) {
            System.out.println("Login");
        } else if (obj == logout) {
            System.out.println("Logout");
        } else if (obj == music_1) {
            System.out.println("Play Music 1");
        } else if (obj == music_2) {
            System.out.println("Play Music 2");
        } else if (obj == music_3) {
            System.out.println("Play Music 3");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == undo) {
            System.out.println("undo");
        } else if (obj == hammer) {
            System.out.println("use prop hammer");
        } else if (obj == ai) {
            System.out.println("use AI");
        } else if (obj == _2N) {
            System.out.println("2^n mode");
        } else if (obj == _3N) {
            System.out.println("3^n mode");
        } else if (obj == countdown) {
            System.out.println("countdown mode");
        } else if (obj == target) {
            System.out.println("target select mode");
        } else if (obj == Default) {
            System.out.println("Change theme to: default");
        } else if (obj == forest) {
            System.out.println("Change theme to: forest");
        } else if (obj == ocean) {
            System.out.println("Change theme to: ocean");
        } else if (obj == flames) {
            System.out.println("Change theme to: flames");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        changeBackgroundImage(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackgroundImage(ImagePath + "GameFrameBackground");
        System.out.println("background re-set");
    }

    private void changeBackgroundImage(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == undo) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_UNDO");
        } else if (obj == hammer) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_HAMMER");
        } else if (obj == ai) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_AI");
        } else if (obj == _2N) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_2n");
        } else if (obj == _3N) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_3n");
        } else if (obj == countdown) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_COUNTDOWN");
        } else if (obj == target) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_TARGET");
        } else if (obj == Default) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_DEFAULT");
        } else if (obj == forest) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_FOREST");
        } else if (obj == ocean) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_OCEAN");
        } else if (obj == flames) {
            setBackgroundImage(ImagePath + "Click/GameFrameBackground_FLAMES");
        }
        this.getContentPane().repaint();
    }
}