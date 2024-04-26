package Main.Frame;

import Main.Controller.CellMotion;
import Main.Controller.InitialGrids;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame implements ActionListener, MouseListener, KeyListener {

    private int[][] data;

    //创建setup方法供外界访问
    public void setup() {
        initialGameFrame();
        replayGame();
        addMouseListener(this);
        addKeyListener(this);
        this.setVisible(true);
    }

    //产生motion对象，实现每次重启游戏步数清零重计
    CellMotion motion;

    //todo：改变主题后，以改变后的主题启动
    private void replayGame() {
        data = new InitialGrids().setup();
        motion = new CellMotion();
        setImages(ImagePath + "GameFrameBackground", data);
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

    Container ImageContainer = new Container();


    private final String ImagePath = "src/Main/Resources/";
    private final String NumImagePath = "src/Main/Resources/Cells/";


    //初始化界面
    private void initialGameFrame() {
        this.getContentPane().removeAll();
        this.setTitle("2048");
        this.setSize(800, 630);
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

        this.setJMenuBar(jMenuBar);

    }


    private void setImages(String ImagePath, int[][] data) {
        this.ImageContainer.removeAll();
        this.setLayout(null);

        this.ImageContainer.add(undo);
        this.ImageContainer.add(hammer);
        this.ImageContainer.add(ai);
        this.ImageContainer.add(_2N);
        this.ImageContainer.add(_3N);
        this.ImageContainer.add(countdown);
        this.ImageContainer.add(target);
        this.ImageContainer.add(Default);
        this.ImageContainer.add(forest);
        this.ImageContainer.add(ocean);
        this.ImageContainer.add(flames);

        //添加数字图片
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                JLabel imageLable = new JLabel();
                ImageIcon image = new ImageIcon(NumImagePath + data[i][j] + ".png");
                imageLable.setIcon(image);
                imageLable.setSize(100, 100);
                imageLable.setBounds(32 + 100 * j, 98 + 100 * i, 100, 100);
                imageLable.setBorder(new BevelBorder(BevelBorder.LOWERED));
                this.ImageContainer.add(imageLable);
            }
        }
        //背景图
        ImageIcon Image = new ImageIcon(ImagePath + ".png");
        backgroundImage.setIcon(Image);
        backgroundImage.setSize(800, 550);
        backgroundImage.setBounds(0, 23, 800, 550);
        this.ImageContainer.add(backgroundImage);

        this.setContentPane(ImageContainer);
        this.ImageContainer.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == exit) {
            System.exit(0);
        } else if (obj == replay) {
            System.out.println("Replay Game");
            replayGame();
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

    //进行重做操作
    private void performUNDO() {
        System.out.println("undo");
        motion.UNDO(data);
        setImages(ImagePath + "GameFrameBackground", data);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == undo) {
            performUNDO();
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
    public void mouseEntered(MouseEvent e) {
        changeBackgroundImage(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setImages(ImagePath + "GameFrameBackground", data);
    }

    private void changeBackgroundImage(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == undo) {
            setImages(ImagePath + "Click/GameFrameBackground_UNDO", data);
        } else if (obj == hammer) {
            setImages(ImagePath + "Click/GameFrameBackground_HAMMER", data);
        } else if (obj == ai) {
            setImages(ImagePath + "Click/GameFrameBackground_AI", data);
        } else if (obj == _2N) {
            setImages(ImagePath + "Click/GameFrameBackground_2n", data);
        } else if (obj == _3N) {
            setImages(ImagePath + "Click/GameFrameBackground_3n", data);
        } else if (obj == countdown) {
            setImages(ImagePath + "Click/GameFrameBackground_COUNTDOWN", data);
        } else if (obj == target) {
            setImages(ImagePath + "Click/GameFrameBackground_TARGET", data);
        } else if (obj == Default) {
            setImages(ImagePath + "Click/GameFrameBackground_DEFAULT", data);
        } else if (obj == forest) {
            setImages(ImagePath + "Click/GameFrameBackground_FOREST", data);
        } else if (obj == ocean) {
            setImages(ImagePath + "Click/GameFrameBackground_OCEAN", data);
        } else if (obj == flames) {
            setImages(ImagePath + "Click/GameFrameBackground_FLAMES", data);
        }
        this.getContentPane().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 37) {
            System.out.println("left");
            motion.moveLeft(data);
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (code == 38) {
            System.out.println("up");
            motion.moveUp(data);
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (code == 39) {
            System.out.println("right");
            motion.moveRight(data);
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (code == 40) {
            System.out.println("down");
            motion.moveDown(data);
            setImages(ImagePath + "GameFrameBackground", data);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}