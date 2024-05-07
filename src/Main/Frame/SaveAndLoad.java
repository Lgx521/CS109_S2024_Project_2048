package Main.Frame;

import Main.Controller.CellMotion;
import Main.Data.GameDataStock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class SaveAndLoad extends JFrame implements ActionListener, MouseListener {

    private final String FilePath = "src/Main/Data/GameData/";

    private GameDataStock gameDataStock;

    //构造器
    SaveAndLoad(GameDataStock gameDataStock) {
        initialFrame();
        this.gameDataStock = gameDataStock;
    }

    //保存与加载方法
    public void SAVE() {
        this.status = STATUS_SAVE;
        setImage(BACKGROUND_0);
        this.setTitle("2048 - Save");
        addMouseListener(this);
        this.setVisible(true);
    }
    public void LOAD() {
        this.status = STATUS_LOAD;
        setImage(BACKGROUND_0);
        this.setTitle("2048 - Load");
        addMouseListener(this);
        this.setVisible(true);
    }

    JLabel slot_1 = new JLabel();
    JLabel slot_2 = new JLabel();
    JLabel slot_3 = new JLabel();
    JLabel slot_4 = new JLabel();

    //文件浏览器
//    JButton browser = new JButton();

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
    }

    private final String ImagePath = "src/Main/Resources/SaveAndLoad/background_";

    private final int BACKGROUND_0 = 0;
    private final int BACKGROUND_1 = 1;
    private final int BACKGROUND_2 = 2;
    private final int BACKGROUND_3 = 3;
    private final int BACKGROUND_4 = 4;

    //当为保存页面时，status == 0，第四条无法被点击
    private final int STATUS_SAVE = 0;
    private final int STATUS_LOAD = 1;
    private int status = 0;

    //设置背景图
    private void setImage(int number) {
        this.getContentPane().removeAll();
        this.setLayout(null);

        //背景图片
        ImageIcon backgroundImage = new ImageIcon(ImagePath + number + ".png");
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

//        JLabel FileBrowser = new JLabel("Load your game from file?");
//
//        browser.setSize(100,25);
//        browser.setBounds(350, 270,100,25);
//        browser.setText("File Browser");
//        this.getContentPane().add(browser);

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


    private void saveFile(int slotNum) {

        try {
            String fileName = FilePath + slotNum + ".2048";
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(gameDataStock);
            oos.close();
            JOptionPane.showMessageDialog(null, "Game Saved Successfully", "Notice", JOptionPane.INFORMATION_MESSAGE);
            toString(gameDataStock);
            System.out.println("slot_" + slotNum + "is saved");
        } catch (IOException e) {
            System.out.println("Save failed");
            JOptionPane.showMessageDialog(null, "File save failed!", "Caution", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    private GameDataStock loadFile(String path) {
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

    private void toString(GameDataStock gameDataStock) {
        int fileID = gameDataStock.getUserID();
        int fileSteps = gameDataStock.getSteps();
        int[] fileScore = gameDataStock.getScore();
        int fileTarget = gameDataStock.getTarget();
        System.out.printf("ID: %s, Steps: %s, Score: {%s, %s, %s, %s}, Target: %s\n", fileID, fileSteps, fileScore[0], fileScore[1], fileScore[2], fileScore[3], fileTarget);
    }


    private void newGameSetUp(GameDataStock gameDataStock) {
        int[][] gameData = gameDataStock.getGameData();
        int[] score = gameDataStock.getScore();
        int steps = gameDataStock.getSteps();
        int motionStatus = gameDataStock.getMotionStatus();
        int userID = gameDataStock.getUserID();
        int target = gameDataStock.getTarget();

        GameFrame loaded = new GameFrame();

        loaded.motion = new CellMotion(steps, score, motionStatus, target);
        loaded.setData(gameData);
        loaded.setID(userID);
        loaded.setStatus(1); //设置为有用户已登陆

        loaded.loadSetUp();
        JOptionPane.showMessageDialog(null, "Game Loaded Successfully", "Notice", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
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
                newGameSetUp(loadFile(FilePath + "1.2048"));

            } else {
                System.out.println("slot_1 is clicked when status == 0(SAVE)");
                saveFile(1);
            }
        } else if (obj == slot_2) {
            if (status == STATUS_LOAD) {
                System.out.println("slot_2 is clicked when status = 1(LOAD)");
                newGameSetUp(loadFile(FilePath + "2.2048"));
            } else {
                System.out.println("slot_2 is clicked when status = 0(SAVE)");
                saveFile(2);
            }
        } else if (obj == slot_3) {
            if (status == STATUS_LOAD) {
                System.out.println("slot_3 is clicked when status = 1(LOAD)");
                newGameSetUp(loadFile(FilePath + "3.2048"));
            } else {
                System.out.println("slot_3 is clicked when status = 0(SAVE)");
                saveFile(3);
            }
        } else if (obj == slot_4) {
            if (status == STATUS_LOAD) {
                System.out.println("slot_4 is clicked when status = 1(LOAD)");
                newGameSetUp(loadFile(FilePath + "autoSave.2048"));
            } else {
                System.out.println("slot_4 is clicked when status = 0(SAVE)");
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == slot_1) {
            setImage(BACKGROUND_1);
//            System.out.println("1");
        } else if (obj == slot_2) {
            setImage(BACKGROUND_2);
//            System.out.println("2");
        } else if (obj == slot_3) {
            setImage(BACKGROUND_3);
//            System.out.println("3");
        } else if (obj == slot_4) {
            if (status == 1) {
                setImage(BACKGROUND_4);
//                System.out.println("4");
            }
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        setImage(BACKGROUND_0);
    }
}
