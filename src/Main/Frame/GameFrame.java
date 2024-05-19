package Main.Frame;

import Main.Controller.CellMotion_2;
import Main.Controller.CellMotion_3;
import Main.Controller.InitialGrids;
import Main.Controller.MotionBasic;
import Main.Data.GameDataStock;
import Main.Data.StatisticsDataStock;
import Main.Features.AI;
import Main.Features.Props;
import Main.Features.bgmPlayer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class GameFrame extends JFrame implements ActionListener, MouseListener, KeyListener {

    //Attributes --------------------

    //游戏board大小记录
    private int[][] data;

    //产生AI对象
    AI ai_prop = new AI();

    JMenu game = new JMenu("GamePlay");
    JMenu users = new JMenu("Users");
    JMenu music = new JMenu("Select BGM");
    JMenuItem exit = new JMenuItem("Exit");

    JMenuItem replay = new JMenuItem("Replay Game");
    JMenuItem load = new JMenuItem("Load Game");
    JMenuItem save = new JMenuItem("Save Game");
    JMenuItem sound = new JMenuItem("Background Music Off");
    JMenuItem effectOff = new JMenuItem("Effect Sound On/Off");

    JMenuItem music_1 = new JMenuItem("Music 1");
    JMenuItem music_2 = new JMenuItem("Music 2");
    JMenuItem music_3 = new JMenuItem("Music 3");

    JMenuItem login = new JMenuItem("Log in");
    JMenuItem logout = new JMenuItem("Log out");
    JMenuItem signIn = new JMenuItem("Sign In");

    JMenuItem statistics = new JMenuItem("Game Statistics");

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
    JButton _64 = new JButton();
    JButton _128 = new JButton();
    JButton _256 = new JButton();
    JButton _512 = new JButton();
    JButton _1024 = new JButton();
    JButton _2048 = new JButton();

    //创建Hammer选择图层
    JLabel a1 = new JLabel();
    JLabel a2 = new JLabel();
    JLabel a3 = new JLabel();
    JLabel a4 = new JLabel();
    JLabel b1 = new JLabel();
    JLabel b2 = new JLabel();
    JLabel b3 = new JLabel();
    JLabel b4 = new JLabel();
    JLabel c1 = new JLabel();
    JLabel c2 = new JLabel();
    JLabel c3 = new JLabel();
    JLabel c4 = new JLabel();
    JLabel d1 = new JLabel();
    JLabel d2 = new JLabel();
    JLabel d3 = new JLabel();
    JLabel d4 = new JLabel();

    //创建数组容器便于阅读
    private final JLabel[] labels = {a1, a2, a3, a4, b1, b2, b3, b4, c1, c2, c3, c4, d1, d2, d3, d4};

    //背景图
    JLabel backgroundImage = new JLabel();

    //内容图层版
    Container ImageContainer = new Container();

    //图片基础路径
    private final String ImagePath = "src/Main/Resources/";

    //枚举类：设置主题
    private String NumImagePath;
    ImagePathEnum img;

    //背景音乐播放对象
    bgmPlayer musicObject = new bgmPlayer();

    //游戏数据同步，便于保存
    GameDataStock syncer = new GameDataStock();
    SaveAndLoad saverAndLoader;

    //数据统计对象
    StatisticsDataStock statisticsDataStock = new StatisticsDataStock();

    //时间格式化
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    //data setter
    public void setData(int[][] data) {
        this.data = data;
    }

    //表示是否为登陆用户，用于显示Tips
    private int STATUS;

    //当前游戏玩家ID
    private static int USER_ID;

    //计时相关
    private long seconds;
    private final Timer timeCountDown = new Timer(1000, this);
    private final Timer timeCount = new Timer(1000, this);
    JLabel timeLabel = new JLabel("00:00");

    //退出倒计时按钮
    JButton quitCountDown = new JButton();

    //表示Hammer是否开启
    private boolean isHammerAvailable = false;

    //AI是否可用标签
    private boolean isAiAvailable = false;

    //当前鼠标所在块位置索引
    private int cellIndex;

    //设置目标块大小对话框
    JDialog targetSet;

    //格点生成初始化对象
    InitialGrids initialGrids = new InitialGrids();

    //产生motion对象，实现每次重启游戏步数清零重计
    MotionBasic motion;
    private final int POWER_OF_2 = 0;
    private final int POWER_OF_3 = 1;

    //倒计时label
    JLabel timeCountDownLabel = new JLabel();

    //倒计时选择对话框
    JDialog timeCountSelector;

    //Buttons
    JButton _10sec = new JButton();
    JButton _10min = new JButton();
    JButton _30min = new JButton();
    JButton _60min = new JButton();

    //AI与Hammer是否使用标签
    boolean isAiUsed = false;
    boolean ishammerUsed = false;


    //Methods ---------------------


    //设置当前游戏运行模式
    /*
     * STATUS == 0: LOG_IN.
     * STATUS == 1: GUEST.
     * */
    public void setStatus(int status) {
        this.STATUS = status;
    }

    //2或3模式，默认Power of 2
    public int gameModeSelector = 0;

    //创建setup方法供外界访问
    public void setup() {
        game.add(load);
        game.add(save);
        users.add(statistics);
        initialGameFrame();
        users.add(logout);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setVisible(true);
        replayGame();
    }

    //存储结果加载
    public void loadSetUp() {
        STATUS = 0;
        game.add(load);
        game.add(save);
        users.add(statistics);
        initialGameFrame();
        users.add(logout);
        img = ImagePathEnum.DEFAULT;
        NumImagePath = img.getPath();
        setImages();
        motion.EffectSoundPlayer(4);
        timeCount.restart();
        timeCount();
        addMouseListener(this);
        addKeyListener(this);
        this.setVisible(true);
        this.requestFocus();
    }

    //用于访客模式的外界访问
    public void setupInGuestMode() {
        initialGameFrame();
        users.add(login);
        users.add(signIn);
        STATUS = 1;
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setVisible(true);
        replayGame();
    }

    //重新加载游戏
    public void replayGame() {
        ai_prop.setMotion(gameModeSelector);
        setMotion(gameModeSelector);
        img = ImagePathEnum.DEFAULT;
        NumImagePath = img.getPath();
        setImages();
        motion.EffectSoundPlayer(4);
        isAiAvailable = false;
        isHammerAvailable = false;
        seconds = 0;
        timeCountDown.restart();
        timeCount.restart();
        setTimeLimit(timeLimit);
        timeCount();
        syncStatisticsData();
        this.requestFocus();
    }

    //统计数据同步
    private void syncStatisticsData() {
        //若使用了Hammer，直接return，则不进行统计
        if (ishammerUsed || STATUS == 1) {
            return;
        }
        System.out.println("1");
        if (gameModeSelector == POWER_OF_2) {
            int maxTile = 0;
            long timeTo_2048 = 0;
            long timeTo_1024 = 0;
            for (int[] datum : data) {
                for (int j = 0; j < data[0].length; j++) {
                    if (datum[j] > maxTile) {
                        maxTile = datum[j];
                    }
                    if (datum[j] == 2048) {
                        timeTo_2048 = seconds;
                    }
                    if (datum[j] == 1024) {
                        timeTo_1024 = seconds;
                    }
                }
            }
            statisticsDataStock.syncStatistics_2(maxTile, timeTo_2048, timeTo_1024, USER_ID);
        } else {
            int maxTile = 0;
            long timeTo_729 = 0;
            long timeTo_243 = 0;
            for (int[] datum : data) {
                for (int j = 0; j < data[0].length; j++) {
                    if (datum[j] > maxTile) {
                        maxTile = datum[j];
                    }
                    if (datum[j] == 729) {
                        timeTo_729 = seconds;
                    }
                    if (datum[j] == 243) {
                        timeTo_243 = seconds;
                    }
                }
            }
            statisticsDataStock.syncStatistics_3(maxTile, timeTo_729, timeTo_243, USER_ID);
            System.out.println("243--" + timeTo_243);
        }
    }

    //设置游戏移动逻辑
    private void setMotion(int mode) {
        if (mode == POWER_OF_2) {
            data = initialGrids.setup();
            motion = new CellMotion_2();
        } else if (mode == POWER_OF_3) {
            data = initialGrids.setup_3();
            motion = new CellMotion_3();
        }
    }

    //初始化界面
    private void initialGameFrame() {
        this.getContentPane().removeAll();
        this.setTitle("2048");
        this.setSize(800, 650);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        saverAndLoader = new SaveAndLoad(syncer);

        //设置菜单栏
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.setSize(800, 25);

        jMenuBar.add(game);
        jMenuBar.add(users);

//        game.add(load);
//        game.add(save);
        game.add(replay);
        game.add(sound);
        game.add(effectOff);
        game.add(music);
        game.add(exit);

        music.add(music_1);
        music.add(music_2);
        music.add(music_3);

//        users.add(login);
//        users.add(logout);

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

        for (JLabel label : labels) {
            label.addMouseListener(this);
        }

        //添加监听
        exit.addActionListener(this);
        replay.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        login.addActionListener(this);
        logout.addActionListener(this);
        signIn.addActionListener(this);
        sound.addActionListener(this);
        music_1.addActionListener(this);
        music_2.addActionListener(this);
        music_3.addActionListener(this);
        effectOff.addActionListener(this);

        quitCountDown.addActionListener(this);

        statistics.addActionListener(this);

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

        _10sec.addActionListener(this);
        _10min.addActionListener(this);
        _30min.addActionListener(this);
        _60min.addActionListener(this);

        this.setJMenuBar(jMenuBar);

        //计时
        timeCount.addActionListener(e -> {
            seconds++;
            if (STATUS == 0) {
                if (seconds % 5 == 0) {
                    //每五秒同步数据
                    syncStatisticsData();
                    //每5秒自动同步
                    syncer.sync(USER_ID, data, motion.getSteps(), motion.getScoreArr(), motion.getTarget(), motion.status, gameModeSelector, seconds);
                    saverAndLoader.AutoSave();
                }
            }
            String sdfed = sdf.format(seconds * 1000);
            timeLabel.setText(sdfed);
        });

        //倒计时
        timeCountDown.addActionListener(e -> {
            if (thisTimeLimit > 0) {
                thisTimeLimit--;
            } else {
                int option = JOptionPane.showConfirmDialog(this, "Time out!\nPress 'OK' to retry, 'Cancel' to quit.", "Notice", JOptionPane.OK_CANCEL_OPTION);
                if (option == 0) {
                    replayGame();
                    setTimeLimit(timeLimit);
                    timeCountDownLabel.setForeground(new Color(0, 200, 120));
                } else {
                    setTimeLimit(timeLimit);
                    timeCountDownLabel.setForeground(new Color(0, 200, 120));
                    timeCountDown.stop();
                }
            }
            if (((double) thisTimeLimit / (double) timeLimit) <= 0.5) {
                timeCountDownLabel.setForeground(Color.RED);
            }
            String sdfed = sdf.format(thisTimeLimit * 1000);
            timeCountDownLabel.setText("Time remaining  " + sdfed);
        });

    }

    //设置游戏运行时的用户
    public void setID(int userID) {
        USER_ID = userID;
        System.out.println("Current user's ID: " + USER_ID);
    }

    //获得游戏运行时的用户
    public int getID() {
        return USER_ID;
    }

    //搭建元素
    private void setComponents() {
        this.ImageContainer.removeAll();
        this.setLayout(null);

        this.ImageContainer.add(timeLabel);
        this.ImageContainer.add(timeCountDownLabel);
        this.ImageContainer.add(quitCountDown);

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

        quitCountDown.setSize(140, 30);
        quitCountDown.setBounds(290, 50, 140, 30);
        quitCountDown.setText("Quit Count Down");

        //hammer选择图层
        if (isHammerAvailable) {
            String SelectPath = "src/Main/Resources/Click/selector.png";
            ImageIcon selectImg = new ImageIcon(SelectPath);
            for (int i = 0; i < labels.length; i++) {
                labels[i].setSize(100, 100);
            }
            if (cellIndex != -1) {
                labels[cellIndex].setIcon(selectImg);
            }
            for (int i = 0; i < labels.length; i++) {
                labels[i].setBounds(32 + (i % 4) * 100, 98 + (i / 4) * 100, 100, 100);
                this.ImageContainer.add(labels[i]);
            }
        }

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

        //若在GUEST模式中，显示注册提示
        if (this.STATUS == 1) {
            JLabel tips = new JLabel("Tips: Sign your own Account to Save your game!");
            tips.setSize(500, 25);
            tips.setForeground(Color.WHITE);
            tips.setBounds(464, 555, 500, 25);
            this.ImageContainer.add(tips);
        }

        //步数显示
        JLabel steplable = new JLabel(motion.getSteps() + "");
        steplable.setSize(60, 40);
        steplable.setBounds(705, 200, 60, 40);
        steplable.setFont(new Font("Console", Font.ITALIC, 18));
        steplable.setForeground(Color.WHITE);

        //分数显示
        JLabel scorelable = new JLabel(motion.getScore(motion.getSteps()) + "");
        scorelable.setSize(60, 40);
        scorelable.setBounds(705, 168, 60, 40);
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
        backImage.setSize(800, 600);
        backImage.setBounds(0, 0, 800, 600);

        //添加到图层
        this.ImageContainer.add(steplable);
        this.ImageContainer.add(scorelable);
        this.ImageContainer.add(targetLable);
        this.ImageContainer.add(backgroundImage);
        this.ImageContainer.add(backImage);
        this.setContentPane(ImageContainer);

    }

    //搭建图片
    private void setImages() {
        setComponents();
        //背景图
        ImageIcon Image = new ImageIcon(ImagePath + "GameFrameBackground.png");
        backgroundImage.setIcon(Image);
        backgroundImage.setSize(800, 550);
        backgroundImage.setBounds(0, 23, 800, 550);
        //刷新图层
        this.ImageContainer.repaint();
    }

    //鼠标移入显示实现
    private void setImagesClicked(String name) {
        setComponents();
        //背景图
        ImageIcon Image = new ImageIcon(ImagePath + name + ".png");
        backgroundImage.setIcon(Image);
        backgroundImage.setSize(800, 550);
        backgroundImage.setBounds(0, 23, 800, 550);

        //刷新图层
        this.ImageContainer.repaint();

    }

    //判断显示游戏结束对话框
    private void gameOverDialog() {
        if (motion.flagOfIsMovable != motion.IN_PROGRESS) {
            timeCountDown.stop();
            motion.EndingNotice(motion.flagOfIsMovable);
        }
    }

    //实现当时鼠标进入所在的cell的效果
    private int getSelectedVisible(MouseEvent e) {
        Object obj = e.getSource();
        int result = -1;
        for (int i = 0; i < labels.length; i++) {
            if (obj == labels[i]) {
                result = i;
                break;
            }
        }
        return result;
    }

    //实现按钮鼠标进入右侧按钮效果
    private void changeBackgroundImage(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == undo) {
            setImagesClicked("Click/GameFrameBackground_UNDO");
        } else if (obj == hammer) {
            setImagesClicked("Click/GameFrameBackground_HAMMER");
        } else if (obj == ai) {
            setImagesClicked("Click/GameFrameBackground_AI");
        } else if (obj == _2N) {
            setImagesClicked("Click/GameFrameBackground_2N");
        } else if (obj == _3N) {
            setImagesClicked("Click/GameFrameBackground_3N");
        } else if (obj == countdown) {
            setImagesClicked("Click/GameFrameBackground_COUNTDOWN");
        } else if (obj == target) {
            setImagesClicked("Click/GameFrameBackground_TARGET");
        } else if (obj == Default) {
            setImagesClicked("Click/GameFrameBackground_DEFAULT");
        } else if (obj == forest) {
            setImagesClicked("Click/GameFrameBackground_FOREST");
        } else if (obj == ocean) {
            setImagesClicked("Click/GameFrameBackground_OCEAN");
        } else if (obj == flames) {
            setImagesClicked("Click/GameFrameBackground_FLAMES");
        }
        this.getContentPane().repaint();
    }

    //目标块大小对话框界面搭建
    private void setTargetDialog() {
        targetSet = new JDialog();
        targetSet.setLayout(null);
        targetSet.setLocationRelativeTo(null);
        targetSet.setAlwaysOnTop(true);
        targetSet.setTitle("Set Your Target");
        if (gameModeSelector == POWER_OF_2) {
            targetSet.setSize(270, 260);
            _64.setText("64");
            _128.setText("128");
            _256.setText("256");
            _512.setText("512");
            _1024.setText("1024");
            _2048.setText("2048");
            targetSet.add(_2048);
            JLabel e = new JLabel("One Step to Success...");
            e.setSize(150, 25);
            JLabel f = new JLabel("Ultimate Fantasy!");
            f.setSize(120, 25);
            e.setBounds(40, 150, 150, 25);
            f.setBounds(40, 180, 120, 25);
            targetSet.add(e);
            targetSet.add(f);
        } else {
            targetSet.setSize(270, 230);
            _64.setText("9");
            _128.setText("27");
            _256.setText("81");
            _512.setText("243");
            _1024.setText("729");
            JLabel e = new JLabel("Ultimate Fantasy!");
            e.setSize(150, 25);
            e.setBounds(40, 150, 150, 25);
            targetSet.add(e);
        }
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


        a.setBounds(40, 30, 120, 25);
        b.setBounds(40, 60, 120, 25);
        c.setBounds(40, 90, 120, 25);
        d.setBounds(40, 120, 120, 25);

        targetSet.add(a);
        targetSet.add(b);
        targetSet.add(c);
        targetSet.add(d);

        targetSet.add(_64);
        targetSet.add(_128);
        targetSet.add(_256);
        targetSet.add(_512);
        targetSet.add(_1024);

        targetSet.setVisible(true);
    }

    //进行重做操作
    private void performUNDO() throws InterruptedException {
        System.out.println("undo");
        motion.UNDO(data);
        setImages();
    }

    //Hammer
    private void HammerProp(int row, int col) {
        Props props = new Props();

        int[] arr;
        String content_1;
        String content;
        if (gameModeSelector == POWER_OF_2) {
            arr = new int[]{0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
            content_1 = "Invalid Input!\n" +
                    "You can only input 0 or powers of 2 ( ≤ 2048),\n";
            content = """
                    Prop: Hammer
                    Please input a valid number which is in the range
                    of 2 ~ 2048, to change that tile to this number.
                    You can also input 0 to dispose that tile.
                    """;
        } else {
            arr = new int[]{0, 3, 9, 27, 81, 243, 729};
            content_1 = "Invalid Input!\n" +
                    "You can only input 0 or powers of 3 ( ≤ 729 ),\n";
            content = """
                    Prop: Hammer
                    Please input a valid number which is in the range
                    of 3 ~ 729, to change that tile to this number.
                    You can also input 0 to dispose that tile.
                    """;
        }

        String inputText;
        char[] input;
        try {
            inputText = JOptionPane.showInputDialog(this, content, "Hammer", JOptionPane.QUESTION_MESSAGE);
            input = inputText.toCharArray();
        } catch (NullPointerException e) {
            System.out.println("Clear Input");
            isHammerAvailable = false;
            return;
        }


        //检验输入是否为数字
        String regex = "[0-9]+";
        if (!inputText.matches(regex)) {
            JOptionPane.showMessageDialog(this, content_1 +
                    "Please select again!", "Caution", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            if (toInteger(input) == arr[i]) {
                props.Hammer(this.data, row, col, arr[i]);
                setImages();
                motion.isEnding(data);
                gameOverDialog();
                isHammerAvailable = false;
                return;
            }
        }
        JOptionPane.showMessageDialog(this, content_1 +
                "Please select again!", "Caution", JOptionPane.WARNING_MESSAGE);
    }

    //hammer输入值转为int类型
    private int toInteger(char[] str) {
        int ans = 0;
        int base = 1;
        for (int i = str.length - 1; i >= 0; i--) {
            ans = ans + ((int) str[i] - 48) * base;
            base *= 10;
        }
        return ans;
    }

    //AI
    private void AIRunning() {
        ai_prop.setMotion(gameModeSelector);
        motion.closeEffectSound();
        int direction = ai_prop.MonteCarlo(data);
        if (direction == 0) {
            motion.moveBeforeWin(motion.RIGHT, data);
        } else if (direction == 1) {
            motion.moveBeforeWin(motion.LEFT, data);
        } else if (direction == 2) {
            motion.moveBeforeWin(motion.UP, data);
        } else if (direction == 3) {
            motion.moveBeforeWin(motion.DOWN, data);
        }
        setImages();
        syncStatisticsData();
    }

    //setter for 流逝时间
    public void getSeconds(long seconds) {
        this.seconds = seconds;
    }

    //计时
    private void timeCount() {
        timeLabel.setSize(100, 30);
        timeLabel.setBounds(535, 323, 100, 30);
        timeLabel.setFont(new Font("Arial", Font.ITALIC, 24));
        timeLabel.setForeground(Color.WHITE);
        timeCount.start();
    }

    public long timeLimit = 1000000;
    public long thisTimeLimit = 10;

    //设置倒计时
    private void setTimeLimit(long timeLimit) {
        thisTimeLimit = timeLimit;
    }

    //倒计时时间选择对话框
    private void setTimeCountDownDialog() {
        timeCountSelector = new JDialog();
        timeCountSelector.setLayout(null);
        timeCountSelector.setAlwaysOnTop(true);
        timeCountSelector.setLocationRelativeTo(this);
        timeCountSelector.setTitle("Select limit");
        timeCountSelector.setSize(180, 250);

        JLabel message = new JLabel("Select time limit:");
        message.setSize(150, 30);
        message.setBounds(10, 10, 150, 30);
        _10sec.setSize(100, 30);
        _10sec.setBounds(40, 50, 100, 30);
        _10sec.setText("10 sec");
        _10min.setSize(100, 30);
        _10min.setBounds(40, 90, 100, 30);
        _10min.setText("10 min");
        _30min.setSize(100, 30);
        _30min.setBounds(40, 130, 100, 30);
        _30min.setText("30 min");
        _60min.setSize(100, 30);
        _60min.setBounds(40, 170, 100, 30);
        _60min.setText("60 min");

        timeCountSelector.add(message);
        timeCountSelector.add(_10sec);
        timeCountSelector.add(_10min);
        timeCountSelector.add(_30min);
        timeCountSelector.add(_60min);

        timeCountSelector.setVisible(true);
    }

    //倒计时
    private void timeCountDown() {
        quitCountDown.setVisible(true);
        timeCountDownLabel.setSize(300, 30);
        timeCountDownLabel.setBounds(35, 50, 300, 30);
        timeCountDownLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timeCountDownLabel.setForeground(new Color(0, 200, 120));
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        setTimeLimit(timeLimit);
        timeCountDownLabel.setText("Time remaining  " + sdf.format(thisTimeLimit * 1000));
        timeCountDown.start();
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
            saverAndLoader.LOAD();
            timeCountDown.stop();
            timeCount.stop();
            this.dispose();
        } else if (obj == save) {
            System.out.println("Save Game");
            //同步数据
            syncer.sync(USER_ID, data, motion.getSteps(), motion.getScoreArr(), motion.getTarget(), motion.status, gameModeSelector, seconds);
            //保存
            saverAndLoader.SAVE();
        } else if (obj == sound) {
            System.out.println("Music Turn Off");
            musicObject.stopMusic();
        } else if (obj == login) {
            System.out.println("Login");
            this.dispose();
            new LoginFrame().setup();
        } else if (obj == logout) {
            System.out.println("Logout");
            this.dispose();
            new LoginFrame().setup();
        } else if (obj == music_1) {
            System.out.println("Play Music 1");
            musicObject.stopMusic();
            String path = "src/Main/Resources/Music/Background/pure_imagination.wav";
            musicObject.playMusic(path);
        } else if (obj == music_2) {
            System.out.println("Play Music 2");
            String path = "src/Main/Resources/Music/Background/Body_Pillow.wav";
            musicObject.stopMusic();
            musicObject.playMusic(path);
        } else if (obj == music_3) {
            System.out.println("Play Music 3");
            String path = "src/Main/Resources/Music/Background/Garten.wav";
            musicObject.stopMusic();
            musicObject.playMusic(path);
        } else if (obj == _64) {
            System.out.println("target: 64");
            if (gameModeSelector == POWER_OF_2) {
                motion.setTarget(64);
                targetSet.dispose();
            } else {
                motion.setTarget(9);
                targetSet.dispose();
            }
            setImages();
        } else if (obj == _128) {
            System.out.println("target: 128");
            if (gameModeSelector == POWER_OF_2) {
                motion.setTarget(128);
                targetSet.dispose();
            } else {
                motion.setTarget(27);
                targetSet.dispose();
            }
            setImages();
        } else if (obj == _256) {
            System.out.println("target: 256");
            if (gameModeSelector == POWER_OF_2) {
                motion.setTarget(256);
                targetSet.dispose();
            } else {
                motion.setTarget(81);
                targetSet.dispose();
            }
            setImages();
        } else if (obj == _512) {
            System.out.println("target: 512");
            if (gameModeSelector == POWER_OF_2) {
                motion.setTarget(512);
                targetSet.dispose();
            } else {
                motion.setTarget(243);
                targetSet.dispose();
            }
            setImages();
        } else if (obj == _1024) {
            System.out.println("target: 1024");
            if (gameModeSelector == POWER_OF_2) {
                motion.setTarget(1024);
                targetSet.dispose();
            } else {
                motion.setTarget(729);
                targetSet.dispose();
            }
            setImages();
        } else if (obj == _2048) {
            System.out.println("target: 2048");
            motion.setTarget(2048);
            targetSet.dispose();
            setImages();
        } else if (obj == signIn) {
            this.dispose();
            new SigninFrame().setup();
        } else if (obj == effectOff) {
            //效果声status自增
            motion.setEffectSoundStatus();
        } else if (obj == statistics) {
            System.out.println("Game Statistics");
            try {
                new GameStatics().setUpGameStatics();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (obj == _10sec) {
            System.out.println("Set target time: 10 sec");
            this.timeLimit = 10L;
            timeCountSelector.dispose();
            timeCountDown();
        } else if (obj == _10min) {
            System.out.println("Set target time: 10 min");
            this.timeLimit = 600L;
            timeCountSelector.dispose();
            timeCountDown();
        } else if (obj == _30min) {
            System.out.println("Set target time: 30 min");
            this.timeLimit = 1800L;
            timeCountSelector.dispose();
            timeCountDown();
        } else if (obj == _60min) {
            System.out.println("Set target time: 60 min");
            this.timeLimit = 3600L;
            timeCountSelector.dispose();
            timeCountDown();
        } else if (obj == quitCountDown) {
            setTimeLimit(timeLimit);
            timeCountDownLabel.setText("Time remaining  —:—");
            timeCountDown.stop();
            setImages();
            this.requestFocus();
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
            try {
                performUNDO();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } else if (obj == hammer) {
            System.out.println("use prop hammer");
            int ans = JOptionPane.showConfirmDialog(this, "Prop: Hammer\n" +
                    " > This is Hammer Prop. \n" +
                    " > Choose one tile and click, then enter a valid grid number\n" +
                    "and then you can change the tile to that number.\n" +
                    " > ZERO is a valid grid number, too. You can clear that grid\n" +
                    "by entering '0'.\n" +
                    " > Once you performed Hammer, this game's score won't\n" +
                    " be noted at statistics data!\n" +
                    "   Press 'OK' to confirm using the prop.", "Prop: Hammer", JOptionPane.OK_CANCEL_OPTION);
            if (ans == 0) {
                System.out.println("Confirm to use AI");
                ishammerUsed = true;
                isHammerAvailable = true;
            }
            setImages();
        } else if (obj == ai) {
            int ans = JOptionPane.showConfirmDialog(this, "Prop: AI\n" +
                    " > This AI prop is based on Monte Carlo's algorithm. \n" +
                    " > Every time you press 'space', AI will help you move\n" +
                    "automatically with the best predicted direction.\n" +
                    "   Press 'OK' to confirm using the prop.", "Prop: AI", JOptionPane.OK_CANCEL_OPTION);
            if (ans == 0) {
                System.out.println("Confirm to use AI");
                isAiAvailable = true;
                isAiUsed = true;
            }
        } else if (obj == _2N) {
            System.out.println("2^n mode");
            gameModeSelector = POWER_OF_2;
            replayGame();
        } else if (obj == _3N) {
            System.out.println("3^n mode");
            gameModeSelector = POWER_OF_3;
            replayGame();
        } else if (obj == countdown) {
            System.out.println("countdown mode");
            setTimeCountDownDialog();
        } else if (obj == target) {
            System.out.println("target select mode");
            setTargetDialog();
        } else if (obj == Default) {
            System.out.println("Change theme to: default");
            img = ImagePathEnum.DEFAULT;
            NumImagePath = img.getPath();
            setImages();
        } else if (obj == forest) {
            System.out.println("Change theme to: forest");
            img = ImagePathEnum.FOREST;
            NumImagePath = img.getPath();
            setImages();
        } else if (obj == ocean) {
            System.out.println("Change theme to: ocean");
            img = ImagePathEnum.OCEAN;
            NumImagePath = img.getPath();
            setImages();
        } else if (obj == flames) {
            System.out.println("Change theme to: flames");
            img = ImagePathEnum.FLAME;
            NumImagePath = img.getPath();
            setImages();
        } else if (obj == a1) {
            System.out.println("a1");
            HammerProp(0, 0);
        } else if (obj == a2) {
            System.out.println("a2");
            HammerProp(0, 1);
        } else if (obj == a3) {
            System.out.println("a3");
            HammerProp(0, 2);
        } else if (obj == a4) {
            System.out.println("a4");
            HammerProp(0, 3);
        } else if (obj == b1) {
            System.out.println("b1");
            HammerProp(1, 0);
        } else if (obj == b2) {
            System.out.println("b2");
            HammerProp(1, 1);
        } else if (obj == b3) {
            System.out.println("b3");
            HammerProp(1, 2);
        } else if (obj == b4) {
            System.out.println("b4");
            HammerProp(1, 3);
        } else if (obj == c1) {
            System.out.println("c1");
            HammerProp(2, 0);
        } else if (obj == c2) {
            System.out.println("c2");
            HammerProp(2, 1);
        } else if (obj == c3) {
            System.out.println("c3");
            HammerProp(2, 2);
        } else if (obj == c4) {
            System.out.println("c4");
            HammerProp(2, 3);
        } else if (obj == d1) {
            System.out.println("d1");
            HammerProp(3, 0);
        } else if (obj == d2) {
            System.out.println("d2");
            HammerProp(3, 1);
        } else if (obj == d3) {
            System.out.println("d3");
            HammerProp(3, 2);
        } else if (obj == d4) {
            System.out.println("d4");
            HammerProp(3, 3);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        cellIndex = getSelectedVisible(e);
        setImages();
        changeBackgroundImage(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (cellIndex != -1) {
            this.labels[cellIndex].setIcon(null);
        }
        cellIndex = -1;
        setImages();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println(code);
        if (code == 37) {
            System.out.println("left");
            if (motion.status == 0) {
                motion.moveBeforeWin(motion.LEFT, data);
            } else if (motion.status == 1) {
                motion.moveAfterWin(motion.LEFT, data);
            } else if (motion.status == 2) {
                replayGame();
            }
            setImages();
            syncStatisticsData();
            gameOverDialog();
        } else if (code == 38) {
            System.out.println("up");
            if (motion.status == 0) {
                motion.moveBeforeWin(motion.UP, data);
            } else if (motion.status == 1) {
                motion.moveAfterWin(motion.UP, data);
            } else if (motion.status == 2) {
                replayGame();
            }
            setImages();
            syncStatisticsData();
            gameOverDialog();
        } else if (code == 39) {
            System.out.println("right");
            if (motion.status == 0) {
                motion.moveBeforeWin(motion.RIGHT, data);
            } else if (motion.status == 1) {
                motion.moveAfterWin(motion.RIGHT, data);
            } else if (motion.status == 2) {
                replayGame();
            }
            setImages();
            syncStatisticsData();
            gameOverDialog();
        } else if (code == 40) {
            System.out.println("down");
            if (motion.status == 0) {
                motion.moveBeforeWin(motion.DOWN, data);
            } else if (motion.status == 1) {
                motion.moveAfterWin(motion.DOWN, data);
            } else if (motion.status == 2) {
                replayGame();
            }
            setImages();
            syncStatisticsData();
            gameOverDialog();
        } else if (code == 32 && isAiAvailable) {
            AIRunning();
            gameOverDialog();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}