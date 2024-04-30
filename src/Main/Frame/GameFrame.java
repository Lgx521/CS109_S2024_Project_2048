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

    //模式选择
    JButton _64 = new JButton("64");
    JButton _128 = new JButton("128");
    JButton _256 = new JButton("256");
    JButton _512 = new JButton("512");
    JButton _1024 = new JButton("1024");
    JButton _2048 = new JButton("2048");

    JLabel backgroundImage = new JLabel();

    Container ImageContainer = new Container();

    private final String ImagePath = "src/Main/Resources/";

    private String NumImagePath;
    ImagePathEnum img;


    //重新加载游戏
    public void replayGame() {
        img = ImagePathEnum.DEFAULT;
        NumImagePath = img.getPath();
        data = new InitialGrids().setup();
        motion = new CellMotion();
        setImages(ImagePath + "GameFrameBackground", data);
    }

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

        _64.addActionListener(this);
        _128.addActionListener(this);
        _256.addActionListener(this);
        _512.addActionListener(this);
        _1024.addActionListener(this);
        _2048.addActionListener(this);

        this.setJMenuBar(jMenuBar);

    }

    //搭建图片
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
                imageLable.setBorder(new BevelBorder(BevelBorder.RAISED));
                this.ImageContainer.add(imageLable);
            }
        }
        //背景图
        ImageIcon Image = new ImageIcon(ImagePath + ".png");
        backgroundImage.setIcon(Image);
        backgroundImage.setSize(800, 550);
        backgroundImage.setBounds(0, 23, 800, 550);

        //步数显示
        JLabel steplable = new JLabel(motion.getSteps() + "");
        steplable.setSize(60, 40);
        steplable.setBounds(710, 200, 60, 40);
        steplable.setFont(new Font("Console", Font.ITALIC, 18));
        steplable.setForeground(Color.WHITE);

        //分数显示
        JLabel scorelable = new JLabel(motion.getScore(motion.getSteps()) + "");
        scorelable.setSize(60, 40);
        scorelable.setBounds(710, 168, 60, 40);
        scorelable.setFont(new Font("Console", Font.ITALIC, 18));
        scorelable.setForeground(Color.WHITE);

        //目标显示
        JLabel targetLable = new JLabel(motion.getTarget() + "");
        targetLable.setSize(100, 50);
        targetLable.setBounds(480, 188, 100, 50);
        targetLable.setFont(new Font("Console", Font.ITALIC, 34));
        targetLable.setForeground(Color.WHITE);

        //背景图片
        ImageIcon ImageBack = new ImageIcon("src/Main/Resources/ImageBack.png");
        JLabel backImage = new JLabel(ImageBack);
        backImage.setSize(800, 580);
        backImage.setBounds(0, 0, 800, 580);

        this.ImageContainer.add(steplable);
        this.ImageContainer.add(scorelable);
        this.ImageContainer.add(targetLable);
        this.ImageContainer.add(backgroundImage);
        this.ImageContainer.add(backImage);
        this.setContentPane(ImageContainer);
        this.ImageContainer.repaint();

    }

    //实现按钮鼠标进入效果
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

    //设置目标块大小对话框
    private void setTargetDialog() {
        JDialog targetSet = new JDialog();
        targetSet.setLayout(null);
        targetSet.setLocationRelativeTo(null);
        targetSet.setSize(280, 270);
        targetSet.setTitle("Set Your Target");
        _64.setSize(60, 25);
        _128.setSize(60, 25);
        _256.setSize(60, 25);
        _512.setSize(60, 25);
        _1024.setSize(60, 25);
        _2048.setSize(60, 25);
        _64.setBounds(180, 30, 60, 25);
        _128.setBounds(180, 60, 60, 25);
        _256.setBounds(180, 90, 60, 25);
        _512.setBounds(180, 120, 60, 25);
        _1024.setBounds(180, 150, 60, 25);
        _2048.setBounds(180, 180, 60, 25);
        JLabel a = new JLabel("Try the Rules?");
        a.setSize(120, 25);
        JLabel b = new JLabel("A little Harder?");
        b.setSize(120, 25);
        JLabel c = new JLabel("Not that Easy...");
        c.setSize(120, 25);
        JLabel d = new JLabel("Push...Push...!");
        d.setSize(120, 25);
        JLabel e = new JLabel("One Step to Success...");
        e.setSize(150, 25);
        JLabel f = new JLabel("Ultimate Fantasy!");
        f.setSize(120, 25);
        a.setBounds(40, 30, 120, 25);
        b.setBounds(40, 60, 120, 25);
        c.setBounds(40, 90, 120, 25);
        d.setBounds(40, 120, 120, 25);
        e.setBounds(40, 150, 150, 25);
        f.setBounds(40, 180, 120, 25);
        targetSet.add(a);
        targetSet.add(b);
        targetSet.add(c);
        targetSet.add(d);
        targetSet.add(e);
        targetSet.add(f);
        targetSet.add(_64);
        targetSet.add(_128);
        targetSet.add(_256);
        targetSet.add(_512);
        targetSet.add(_1024);
        targetSet.add(_2048);
        targetSet.setVisible(true);
    }

    //进行重做操作
    private void performUNDO() {
        System.out.println("undo");
        motion.UNDO(data);
        setImages(ImagePath + "GameFrameBackground", data);
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
            this.dispose();
            new LoginFrame().setup();
        } else if (obj == music_1) {
            System.out.println("Play Music 1");
        } else if (obj == music_2) {
            System.out.println("Play Music 2");
        } else if (obj == music_3) {
            System.out.println("Play Music 3");
        } else if (obj == _64) {
            System.out.println("target: 64");
            motion.setTarget(64);
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (obj == _128) {
            System.out.println("target: 128");
            motion.setTarget(128);
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (obj == _256) {
            System.out.println("target: 256");
            motion.setTarget(256);
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (obj == _512) {
            System.out.println("target: 512");
            motion.setTarget(512);
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (obj == _1024) {
            System.out.println("target: 1024");
            motion.setTarget(1024);
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (obj == _2048) {
            System.out.println("target: 2048");
            motion.setTarget(2048);
            setImages(ImagePath + "GameFrameBackground", data);
        }
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
            setTargetDialog();
        } else if (obj == Default) {
            System.out.println("Change theme to: default");
            img = ImagePathEnum.DEFAULT;
            NumImagePath = img.getPath();
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (obj == forest) {
            System.out.println("Change theme to: forest");
            img = ImagePathEnum.FOREST;
            NumImagePath = img.getPath();
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (obj == ocean) {
            System.out.println("Change theme to: ocean");
            img = ImagePathEnum.OCEAN;
            NumImagePath = img.getPath();
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (obj == flames) {
            System.out.println("Change theme to: flames");
            img = ImagePathEnum.FLAME;
            NumImagePath = img.getPath();
            setImages(ImagePath + "GameFrameBackground", data);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 37) {
            System.out.println("left");
            if (motion.status == 0) {
                motion.moveBeforeWin(motion.LEFT, data);
            } else if (motion.status == 1) {
                motion.moveAfterWin(motion.LEFT, data);
            } else if (motion.status == 2) {
                replayGame();
            }
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (code == 38) {
            System.out.println("up");
            if (motion.status == 0) {
                motion.moveBeforeWin(motion.UP, data);
            } else if (motion.status == 1) {
                motion.moveAfterWin(motion.UP, data);
            } else if (motion.status == 2) {
                replayGame();
            }
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (code == 39) {
            System.out.println("right");
            if (motion.status == 0) {
                motion.moveBeforeWin(motion.RIGHT, data);
            } else if (motion.status == 1) {
                motion.moveAfterWin(motion.RIGHT, data);
            } else if (motion.status == 2) {
                replayGame();
            }
            setImages(ImagePath + "GameFrameBackground", data);
        } else if (code == 40) {
            System.out.println("down");
            if (motion.status == 0) {
                motion.moveBeforeWin(motion.DOWN, data);
            } else if (motion.status == 1) {
                motion.moveAfterWin(motion.DOWN, data);
            } else if (motion.status == 2) {
                replayGame();
            }
            setImages(ImagePath + "GameFrameBackground", data);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}