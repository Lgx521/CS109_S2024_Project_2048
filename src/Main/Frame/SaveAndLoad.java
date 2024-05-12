package Main.Frame;

import Main.Controller.CellMotion_2;
import Main.Controller.CellMotion_3;
import Main.Data.GameDataStock;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveAndLoad extends JFrame implements ActionListener, MouseListener {

    private final String FilePath = "src/Main/Data/GameData/User";

    private GameDataStock gameDataStock;

    private int thisUserID = new GameFrame().getID();

    JLabel slot_1 = new JLabel();
    JLabel slot_2 = new JLabel();
    JLabel slot_3 = new JLabel();
    JLabel slot_4 = new JLabel();

    //构造器
    SaveAndLoad(GameDataStock gameDataStock) {
        initialFrame();
        this.gameDataStock = gameDataStock;
    }

    //外界访问的保存方法
    public void SAVE() {
        this.status = STATUS_SAVE;
        setImage(BACKGROUND_0);
        this.setTitle("2048 - Save");
        addMouseListener(this);
        this.setVisible(true);
    }

    //外界访问的载入方法
    public void LOAD() {
        this.status = STATUS_LOAD;
        setImage(BACKGROUND_0);
        this.setTitle("2048 - Load");
        addMouseListener(this);
        this.setVisible(true);
    }

    //文件浏览器
    JButton browser = new JButton();
    JFileChooser browserPanel = new JFileChooser("src/Main/Data/GameData");

    //选择保存文件夹
    JButton saveToDirectory = new JButton();

    //初始化界面
    private void initialFrame() {
        this.setSize(450, 330);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);

        slot_1.setSize(250, 27);
        slot_2.setSize(250, 27);
        slot_3.setSize(250, 27);
        slot_4.setSize(250, 27);

        slot_1.setBounds(100, 110, 250, 29);
        slot_2.setBounds(100, 153, 250, 29);
        slot_3.setBounds(100, 197, 250, 29);
        slot_4.setBounds(100, 241, 250, 29);

        slot_1.addMouseListener(this);
        slot_2.addMouseListener(this);
        slot_3.addMouseListener(this);
        slot_4.addMouseListener(this);

        browser.addMouseListener(this);
        saveToDirectory.addMouseListener(this);
    }

    private final int BACKGROUND_0 = 0;
    private final int BACKGROUND_1 = 1;
    private final int BACKGROUND_2 = 2;
    private final int BACKGROUND_3 = 3;
    private final int BACKGROUND_4 = 4;

    //当为保存页面时，status == 0(STATUS_SAVE)，第四条无法被点击
    private final int STATUS_SAVE = 0;
    private final int STATUS_LOAD = 1;
    private int status = 0;

    private int maxScore = 0;

    //todo: debug
    private int getMaxScore() {
        int maxScore = 0;
        if (gameDataStock.getCurrentScore() > maxScore) {
            maxScore = gameDataStock.getCurrentScore();
            AutoSave();
            System.out.println("Auto saved");
        }
        return maxScore;
    }

    //设置界面元素
    private void setImage(int number) {
        this.getContentPane().removeAll();
        this.setLayout(null);
        if (status == STATUS_LOAD) {
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            browser.setText("File Browser");
            browser.setSize(100, 25);
            browser.setBounds(350, 270, 100, 25);
            this.getContentPane().add(browser);
        } else {
            saveToDirectory.setText("Directory");
            saveToDirectory.setSize(100, 25);
            saveToDirectory.setBounds(350, 270, 100, 25);
            this.getContentPane().add(saveToDirectory);
        }

        //背景图片
        String imagePath = "src/Main/Resources/SaveAndLoad/background_";
        ImageIcon backgroundImage = new ImageIcon(imagePath + number + ".png");
        JLabel background = new JLabel(backgroundImage);
        background.setSize(450, 330);
        background.setBounds(0, -20, 450, 330);

        //三个存档槽文字
        JLabel label1 = new JLabel("Archive slot 1");
        JLabel label2 = new JLabel("Archive slot 2");
        JLabel label3 = new JLabel("Archive slot 3");
        JLabel label4 = new JLabel("Auto Save Archive slot");
        label1.setSize(100, 30);
        label2.setSize(100, 30);
        label3.setSize(100, 30);
        label4.setSize(150, 30);
        label1.setBounds(110, 106, 100, 30);
        label2.setBounds(110, 149, 100, 30);
        label3.setBounds(110, 193, 100, 30);
        label4.setBounds(110, 237, 150, 30);

        if (isSlotEmpty(1)) {
            JLabel label_empty = new JLabel("This slot is empty");
            Font font = new Font("Arial", Font.ITALIC, 12);
            label_empty.setFont(font);
            label_empty.setForeground(Color.GRAY);
            label_empty.setSize(130, 30);
            label_empty.setBounds(210, 107, 130, 30);
            this.getContentPane().add(label_empty);
        }

        if (isSlotEmpty(2)) {
            JLabel label_empty = new JLabel("This slot is empty");
            Font font = new Font("Arial", Font.ITALIC, 12);
            label_empty.setFont(font);
            label_empty.setForeground(Color.GRAY);
            label_empty.setSize(130, 30);
            label_empty.setBounds(210, 150, 130, 30);
            this.getContentPane().add(label_empty);
        }

        if (isSlotEmpty(3)) {
            JLabel label_empty = new JLabel("This slot is empty");
            Font font = new Font("Arial", Font.ITALIC, 12);
            label_empty.setFont(font);
            label_empty.setForeground(Color.GRAY);
            label_empty.setSize(130, 30);
            label_empty.setBounds(210, 194, 130, 30);
            this.getContentPane().add(label_empty);
        }

        if (!isSlotEmpty(4)) {
            JLabel label_saved = new JLabel();
            String content = String.format("Auto Saved When Score is %d.", getMaxScore());
            label_saved.setText(content);
            label_saved.setForeground(Color.WHITE);
            label_saved.setSize(240, 30);
            label_saved.setBounds(110, 267, 240, 30);
            this.getContentPane().add(label_saved);
        }


        this.getContentPane().add(slot_1);
        this.getContentPane().add(slot_2);
        this.getContentPane().add(slot_3);
        this.getContentPane().add(slot_4);

        this.getContentPane().add(label1);
        this.getContentPane().add(label2);
        this.getContentPane().add(label3);
        this.getContentPane().add(label4);
        this.getContentPane().add(background);

        this.getContentPane().repaint();
    }

    //按存档槽保存
    private void saveFile(int slotNum) {
        try {
            String fileName = FilePath + gameDataStock.getUserID() + "_" + slotNum + ".2048";
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(gameDataStock);
            oos.close();
            JOptionPane.showMessageDialog(null, "Game Saved Successfully", "Notice", JOptionPane.INFORMATION_MESSAGE);
            toString(gameDataStock);
            System.out.println("slot_" + slotNum + " is saved");
            this.dispose();
        } catch (IOException e) {
            System.out.println("Save failed");
            JOptionPane.showMessageDialog(null, "File save failed!", "Caution", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    //存储到选定文件夹
    private void saveToAppointedDictionary() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Game"); // 设置对话框标题

        // 设置默认文件名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        Date now = new Date();
        String timeStamp = formatter.format(now);
        String defaultFileName = "game_" + timeStamp + ".2048";
        fileChooser.setSelectedFile(new File(defaultFileName));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileOutputStream fileOut = new FileOutputStream(fileToSave);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(gameDataStock);
                System.out.println("Game has been saved to: " + fileToSave.getAbsolutePath());
                JOptionPane.showMessageDialog(null, "Game Saved Successfully", "Notice", JOptionPane.INFORMATION_MESSAGE);
                toString(gameDataStock);
                this.dispose();
            } catch (IOException e) {
                System.out.println("file save failed");
                JOptionPane.showMessageDialog(null, "Game Save Failed", "Caution", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    //自动保存
    //todo
    public void AutoSave() {
        try {
            String fileName = FilePath + gameDataStock.getUserID() + "_AutoSave.2048";
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(gameDataStock);
            oos.close();
//            toString(gameDataStock);
//            System.out.println("Auto saved");
        } catch (IOException e) {
            System.out.println("Auto save failed");
            JOptionPane.showMessageDialog(null, "Auto save failed!", "Caution", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    //按路径加载游戏
    private GameDataStock loadFileWithPath(String path) {
        File data = new File(path);
        GameDataStock gameDataStock_load;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(data));
            gameDataStock_load = (GameDataStock) ois.readObject();
            toString(gameDataStock_load);
            ois.close();
            System.out.println("loaded");
        } catch (IOException e) {
            System.out.println("File can't be found");
            JOptionPane.showMessageDialog(null, "File doesn't exist!", "Caution", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("File can't be read");
            JOptionPane.showMessageDialog(null, "File load failed!\nPlease choose proper file", "Caution", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(e);
        }
        return gameDataStock_load;
    }

    //按文件加载并开始游戏
    private void loadFileAndStartGame(File file) throws InterruptedException {
        GameDataStock gameDataStock_load;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            gameDataStock_load = (GameDataStock) ois.readObject();
            toString(gameDataStock_load);
            ois.close();
            System.out.println("loaded");
        } catch (IOException e) {
            System.out.println("This isn't the correct game data storage file");
            JOptionPane.showMessageDialog(null, "This isn't the correct game data storage file!\n" +
                    "Please choose proper file (*.2048)", "Caution", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("File can't be read");
            JOptionPane.showMessageDialog(null, "File load failed!\nPlease choose proper file", "Caution", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(e);
        }

        //需要添加判断是否是当前用户读取该文件
        if (gameDataStock_load.getUserID() != thisUserID) {
            System.out.println("This file does NOT Belong to this User!");
            JOptionPane.showMessageDialog(null, "File load failed!\nThis file does NOT Belong to YOU!", "Caution", JOptionPane.INFORMATION_MESSAGE);
        } else {
            this.dispose();
            newGameSetUp(gameDataStock_load);
        }
    }

    //文件过滤器
    FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            if (f.getName().endsWith(".2048")) {
                return true;
            }
            return false;
        }

        @Override
        public String getDescription() {
            return ".2048(Game Data File)";
        }
    };

    //文件浏览器
    private void addFileBrowser() throws InterruptedException {
        browserPanel.setDialogTitle("Please choose game data storage file (*.2048)");
        browserPanel.setVisible(true);
        browserPanel.addChoosableFileFilter(filter);
        browserPanel.setFileFilter(filter);
        browserPanel.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        browserPanel.showDialog(null, "Commit");
        File selected = browserPanel.getSelectedFile();
        loadFileAndStartGame(selected);
    }

    //显示保存信息
    private void toString(GameDataStock gameDataStock) {
        int fileID = gameDataStock.getUserID();
        int fileSteps = gameDataStock.getSteps();
        int[] fileScore = gameDataStock.getScore();
        int fileTarget = gameDataStock.getTarget();
        System.out.printf("ID: %s, Steps: %s, Score: {%s, %s, %s, %s}, Target: %s\n", fileID, fileSteps, fileScore[0], fileScore[1], fileScore[2], fileScore[3], fileTarget);
    }

    //读取数据开始新游戏
    private void newGameSetUp(GameDataStock gameDataStock) throws InterruptedException {
        int[][] gameData = gameDataStock.getGameData();
        int[] score = gameDataStock.getScore();
        int steps = gameDataStock.getSteps();
        int motionStatus = gameDataStock.getMotionStatus();
        int userID = gameDataStock.getUserID();
        int target = gameDataStock.getTarget();

        GameFrame loaded = new GameFrame();

        loaded.gameModeSelector = gameDataStock.getGameModeStatus();

        if (motionStatus == 0) {
            loaded.motion = new CellMotion_2(steps, score, motionStatus, target);
        } else {
            loaded.motion = new CellMotion_3(steps, score, motionStatus, target);
        }

        loaded.setData(gameData);
        loaded.setID(userID);
        loaded.setStatus(1); //设置为有用户已登陆

        loaded.loadSetUp();
        JOptionPane.showMessageDialog(null, "Game Loaded Successfully", "Notice", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }

    //存档槽是否为空
    private boolean isSlotEmpty(int slotNum) {
        if (slotNum == 4) {
            String path = FilePath + thisUserID + "_AutoSave.2048";
            File file = new File(path);
            return !file.exists();
        } else {
            String path = FilePath + thisUserID + "_" + slotNum + ".2048";
            File file = new File(path);
            return !file.exists();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

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
        if (obj == slot_1) {
            if (status == STATUS_LOAD) {
                System.out.println("slot_1 is clicked when status == 1(LOAD)");
                if (isSlotEmpty(1)) {
                    return;
                }
                try {
                    newGameSetUp(loadFileWithPath(FilePath + thisUserID + "_1.2048"));
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.println("slot_1 is clicked when status == 0(SAVE)");
                if (!isSlotEmpty(1)) {
                    int choice = JOptionPane.showConfirmDialog(null, "This will cover the previous slot,\nDo you want to continue?", "Caution", JOptionPane.WARNING_MESSAGE);
                    if (choice == 0) {
                        saveFile(1);
                    } else {
                        System.out.println("Canceled");
                    }
                } else {
                    saveFile(1);
                }
            }
        } else if (obj == slot_2) {
            if (status == STATUS_LOAD) {
                System.out.println("slot_2 is clicked when status = 1(LOAD)");
                if (isSlotEmpty(2)) {
                    return;
                }
                try {
                    newGameSetUp(loadFileWithPath(FilePath + thisUserID + "_2.2048"));
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.println("slot_2 is clicked when status = 0(SAVE)");
                if (!isSlotEmpty(2)) {
                    int choice = JOptionPane.showConfirmDialog(null, "Caution!\nThis will cover the previous slot,\nDo you want to continue?");
                    if (choice == 0) {
                        saveFile(2);
                    } else {
                        System.out.println("Canceled");
                    }
                } else {
                    saveFile(2);
                }
            }
        } else if (obj == slot_3) {
            if (status == STATUS_LOAD) {
                System.out.println("slot_3 is clicked when status = 1(LOAD)");
                if (isSlotEmpty(3)) {
                    return;
                }
                try {
                    newGameSetUp(loadFileWithPath(FilePath + thisUserID + "_3.2048"));
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.println("slot_3 is clicked when status = 0(SAVE)");
                if (!isSlotEmpty(3)) {
                    int choice = JOptionPane.showConfirmDialog(null, "Caution!\nThis will cover the previous slot,\nDo you want to continue?");
                    if (choice == 0) {
                        saveFile(3);
                    } else {
                        System.out.println("Canceled");
                    }
                } else {
                    saveFile(3);
                }
            }
        } else if (obj == slot_4) {
            if (status == STATUS_LOAD) {
                System.out.println("slot_4 is clicked when status = 1(LOAD)");
                if (isSlotEmpty(4)) {
                    return;
                }
                try {
                    newGameSetUp(loadFileWithPath(FilePath + thisUserID + "_autoSave.2048"));
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.println("slot_4 is clicked when status = 0(SAVE)");
            }
        } else if (obj == browser) {
            System.out.println("browser");
            try {
                addFileBrowser();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } else if (obj == saveToDirectory) {
            saveToAppointedDictionary();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object obj = e.getSource();
        if (status == STATUS_SAVE) {
            if (obj == slot_1) {
                setImage(BACKGROUND_1);
            } else if (obj == slot_2) {
                setImage(BACKGROUND_2);
            } else if (obj == slot_3) {
                setImage(BACKGROUND_3);
            }
        } else if (status == STATUS_LOAD) {
            if (obj == slot_1) {
                if (isSlotEmpty(1)) {
                    return;
                }
                setImage(BACKGROUND_1);
            } else if (obj == slot_2) {
                if (isSlotEmpty(2)) {
                    return;
                }
                setImage(BACKGROUND_2);
            } else if (obj == slot_3) {
                if (isSlotEmpty(3)) {
                    return;
                }
                setImage(BACKGROUND_3);
            } else if (obj == slot_4) {
                if (isSlotEmpty(4)) {
                    return;
                }
                setImage(BACKGROUND_4);
            }
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        setImage(BACKGROUND_0);
    }
}
