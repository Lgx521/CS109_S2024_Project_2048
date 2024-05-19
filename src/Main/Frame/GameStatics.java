package Main.Frame;

import Main.UserOperation.LoginAndSignIn;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameStatics extends JFrame {

    //JLabel
    JLabel column_1 = new JLabel();
    JLabel column_2 = new JLabel();
    JLabel column_3 = new JLabel();
    JLabel column_4 = new JLabel();
    JLabel column_5 = new JLabel();
    JLabel column_6 = new JLabel();

    JLabel colume_1_user = new JLabel();
    JLabel colume_2_user = new JLabel();
    JLabel colume_3_user = new JLabel();
    JLabel colume_4_user = new JLabel();
    JLabel colume_5_user = new JLabel();
    JLabel colume_6_user = new JLabel();


    private String maxTile_2 = "0";
    private String userName_MaxTile_2 = "";

    private String maxTile_3 = "0";
    private String userName_MaxTile_3 = "";

    private String minTime_2048 = "100000";
    private String userName_minTime_2048 = "";

    private String minTime_1024 = "100000";
    private String userName_minTime_1024 = "";

    private String minTime_729 = "100000";
    private String userName_minTime_729 = "";

    private String minTime_243 = "100000";
    private String userName_minTime_243 = "";


    LoginAndSignIn userOperator = new LoginAndSignIn();

    //外部访问
    public void setUpGameStatics() throws IOException {
        initialFrame();
        this.setVisible(true);
    }

    //初始化界面
    private void initialFrame() throws IOException {
        this.setSize(640, 448);
        this.setLocationRelativeTo(null);
        this.setTitle("2048 - Game Statistics");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);

        JLabel tips = new JLabel("Still confuse about this game?  " +
                " Check the game instructor here!  --->");
        tips.setSize(500, 30);
        tips.setForeground(Color.WHITE);
        tips.setBounds(60, 388, 500, 30);

        addTipButton();

        ImageIcon img = new ImageIcon("src/Main/Resources/statistics.png");
        JLabel backgroundImage = new JLabel(img);
        backgroundImage.setSize(655, 420);
        backgroundImage.setBounds(0, 0, 640, 420);

        this.getContentPane().add(tips);

        setJLabel();

        addContent();

        this.getContentPane().add(backgroundImage);

    }

    //File reader
    private void reader() throws IOException {

        File file_2_tile = new File("src/Main/Data/Records/statisticsData_2_tile.txt");
        File file_2_time = new File("src/Main/Data/Records/statisticsData_2_time.txt");
        File file_3_tile = new File("src/Main/Data/Records/statisticsData_3_tile.txt");
        File file_3_time = new File("src/Main/Data/Records/statisticsData_3_time.txt");

        //md5校验
        boolean tile_2 = userOperator.md5Verification(file_2_tile, "src/Main/Data/Records/md5/tile_2_md5.txt");
        boolean tile_3 = userOperator.md5Verification(file_3_tile, "src/Main/Data/Records/md5/tile_3_md5.txt");
        boolean time_2 = userOperator.md5Verification(file_2_time, "src/Main/Data/Records/md5/time_2_md5.txt");
        boolean time_3 = userOperator.md5Verification(file_3_time, "src/Main/Data/Records/md5/time_3_md5.txt");


        try {
            file_2_time.exists();
            file_3_time.exists();
            file_2_tile.exists();
            file_3_tile.exists();
        } catch (Exception e) {
            System.out.println("File doesn't exist");
        }

        //maxTile匹配
        String TileDetectorRegex = "PowerOf[2-3]&tile=[0-9]+";
        Pattern maxTilePattern = Pattern.compile(TileDetectorRegex);

        //minTime匹配
        String DetectorRegex_time = "PowerOf[2-3]&TimeTo_[0-9]{3,4}=[0-9]+";
        Pattern minTimePattern = Pattern.compile(DetectorRegex_time);

        //用户名匹配
        String UserNameRegex = "UserName=(\\w)+";
        Pattern userNameGetter = Pattern.compile(UserNameRegex);

        //文件是否被修改
        boolean modified = false;


        //第一次遍历file2tile，得到maxTile_2
        if (tile_2) {
            for (int i = 0; i < userOperator.getTotalLines(file_2_tile); i++) {
                String DataOfThis = userOperator.ReadAppointedLine(file_2_tile, i);

                Matcher nameMatcher = userNameGetter.matcher(DataOfThis);
                Matcher maxTileMatcher = maxTilePattern.matcher(DataOfThis);

                //匹配字符串
                if (nameMatcher.find() && maxTileMatcher.find()) {
                    String maxTileStr = maxTileMatcher.group().substring(14);
                    String userNameStr = nameMatcher.group().substring(9);

                    //比较判断是否改变最大块变量
                    if (stringCompare(maxTileStr, maxTile_2)) {
                        maxTile_2 = maxTileStr;
                        userName_MaxTile_2 = userNameStr;
                    }

                }
            }
        } else {
            modified = true;
        }

        //第二次遍历file3tile，得到maxTile_3
        if (tile_3) {
            for (int i = 0; i < userOperator.getTotalLines(file_3_tile); i++) {
                String DataOfThis = userOperator.ReadAppointedLine(file_3_tile, i);

                Matcher nameMatcher = userNameGetter.matcher(DataOfThis);
                Matcher maxTileMatcher = maxTilePattern.matcher(DataOfThis);

                //匹配字符串
                if (nameMatcher.find() && maxTileMatcher.find()) {
                    String maxTileStr = maxTileMatcher.group().substring(14);
                    String userNameStr = nameMatcher.group().substring(9);

                    //比较判断是否改变最大块变量
                    if (stringCompare(maxTileStr, maxTile_3)) {
                        maxTile_3 = maxTileStr;
                        userName_MaxTile_3 = userNameStr;
                    }

                }
            }
        } else {
            modified = true;
        }

        //第三次遍历file2time，得到minTime2048以及1024
        if (time_2) {
            for (int i = 0; i < userOperator.getTotalLines(file_2_time); i++) {
                String DataOfThis = userOperator.ReadAppointedLine(file_2_time, i);

                Matcher nameMatcher = userNameGetter.matcher(DataOfThis);
                Matcher minTimeMatcher = minTimePattern.matcher(DataOfThis);

                if (nameMatcher.find() && minTimeMatcher.find()) {

                    String userNameStr = nameMatcher.group().substring(9);
                    String minTime = minTimeMatcher.group().substring(21);

                    String tileNum = minTimeMatcher.group().substring(16, 20);

                    if (tileNum.equals("2048")) {
                        if (stringCompare(minTime_2048, minTime)) {
                            minTime_2048 = minTime;
                            userName_minTime_2048 = userNameStr;
                        }
                    } else if (tileNum.equals("1024")) {
                        if (stringCompare(minTime_1024, minTime)) {
                            minTime_1024 = minTime;
                            userName_minTime_1024 = userNameStr;
                        }
                    }
                }
            }
        } else {
            modified = true;
        }

        //第四次遍历file3time，得到minTime729以及243
        if (time_3) {
            for (int i = 0; i < userOperator.getTotalLines(file_3_time); i++) {
                String DataOfThis = userOperator.ReadAppointedLine(file_3_time, i);

                Matcher nameMatcher = userNameGetter.matcher(DataOfThis);
                Matcher minTimeMatcher = minTimePattern.matcher(DataOfThis);

                if (nameMatcher.find() && minTimeMatcher.find()) {
                    String userNameStr = nameMatcher.group().substring(9);
                    String minTime = minTimeMatcher.group().substring(20);

                    String tileNum = minTimeMatcher.group().substring(16, 19);

                    if (tileNum.equals("729")) {
                        if (stringCompare(minTime_729, minTime)) {
                            minTime_729 = minTime;
                            userName_minTime_729 = userNameStr;
                        }
                    } else if (tileNum.equals("243")) {
                        if (stringCompare(minTime_243, minTime)) {
                            minTime_243 = minTime;
                            userName_minTime_243 = userNameStr;
                        }
                    }
                }
            }
        } else {
            modified = true;
        }
//        System.out.println(maxTile_2);
//        System.out.println(maxTile_3);
//        System.out.println(minTime_2048);
//        System.out.println(minTime_1024);
//        System.out.println(minTime_729);
//        System.out.println(minTime_243);

        if (modified) {
            JOptionPane.showMessageDialog(this, "Statistics data stock file is modified!\n" +
                    "Some information can't be displayed.");
        }

    }

    //JLabelSetter
    private void setJLabel() throws IOException {
        reader();

        Font font = new Font("Arial", Font.ITALIC, 18);

        column_1.setText(maxTile_2);
        column_2.setText(maxTile_3);
        colume_1_user.setText(userName_MaxTile_2);
        colume_2_user.setText(userName_MaxTile_3);

        if (!minTime_2048.equals("100000")) {
            column_3.setText(minTime_2048 + "\t seconds");
            colume_3_user.setText(userName_minTime_2048);
        } else {
            column_3.setText("No Records");
            colume_4_user.setText("–––");
        }
        if (!minTime_1024.equals("100000")) {
            column_4.setText(minTime_1024 + "\t seconds");
            colume_4_user.setText(userName_minTime_1024);
        } else {
            column_4.setText("No Records");
            colume_4_user.setText("–––");
        }
        if (!minTime_729.equals("100000")) {
            column_5.setText(minTime_729 + " \t seconds");
            colume_5_user.setText(userName_minTime_729);
        } else {
            column_5.setText("No Records");
            colume_5_user.setText("–––");
        }
        if (!minTime_243.equals("100000")) {
            column_6.setText(minTime_243 + " \t seconds");
            colume_6_user.setText(userName_minTime_243);
        } else {
            column_6.setText("No Records");
            colume_6_user.setText("–––");
        }


        column_1.setFont(font);
        column_2.setFont(font);
        column_3.setFont(font);
        column_4.setFont(font);
        column_5.setFont(font);
        column_6.setFont(font);


        column_1.setBounds(265, 145, 165, 50);
        column_2.setBounds(265, 185, 165, 50);
        column_3.setBounds(228, 227, 165, 50);
        column_4.setBounds(228, 267, 165, 50);
        column_5.setBounds(228, 308, 165, 50);
        column_6.setBounds(228, 347, 165, 50);


        colume_1_user.setFont(font);
        colume_2_user.setFont(font);
        colume_3_user.setFont(font);
        colume_4_user.setFont(font);
        colume_5_user.setFont(font);
        colume_6_user.setFont(font);

        colume_1_user.setBounds(390, 145, 165, 50);
        colume_2_user.setBounds(390, 185, 165, 50);
        colume_3_user.setBounds(390, 227, 165, 50);
        colume_4_user.setBounds(390, 267, 165, 50);
        colume_5_user.setBounds(390, 308, 165, 50);
        colume_6_user.setBounds(390, 347, 165, 50);

        this.getContentPane().add(column_1);
        this.getContentPane().add(column_2);
        this.getContentPane().add(column_3);
        this.getContentPane().add(column_4);
        this.getContentPane().add(column_5);
        this.getContentPane().add(column_6);

        this.getContentPane().add(colume_1_user);
        this.getContentPane().add(colume_2_user);
        this.getContentPane().add(colume_3_user);
        this.getContentPane().add(colume_4_user);
        this.getContentPane().add(colume_5_user);
        this.getContentPane().add(colume_6_user);

    }

    //比较块大小
    private boolean stringCompare(String a, String b) {
        int A = Integer.parseInt(a);
        int B = Integer.parseInt(b);
        return A > B;
    }

    //增加元素
    private void addContent() {
        JLabel title_1 = new JLabel("Achievements");
        JLabel title_2 = new JLabel("Records");
        JLabel title_3 = new JLabel("Gainer");

        Font titleFont = new Font("Arial", Font.BOLD, 22);
        title_1.setBounds(52, 100, 150, 44);
        title_1.setFont(titleFont);
        title_2.setBounds(240, 100, 120, 44);
        title_2.setFont(titleFont);
        title_3.setBounds(390, 100, 100, 44);
        title_3.setFont(titleFont);

        JLabel column_1 = new JLabel("Max Tile in 2^n Mode");
        JLabel column_2 = new JLabel("Max Tile in 3^n Mode");
        JLabel column_3 = new JLabel("Minimum time to 2048");
        JLabel column_4 = new JLabel("Minimum time to 1024");
        JLabel column_5 = new JLabel("Minimum time to 729");
        JLabel column_6 = new JLabel("Minimum time to 243");

        column_1.setBounds(55, 145, 165, 50);
        column_2.setBounds(55, 185, 165, 50);
        column_3.setBounds(55, 225, 165, 50);
        column_4.setBounds(55, 267, 165, 50);
        column_5.setBounds(55, 308, 165, 50);
        column_6.setBounds(55, 345, 165, 50);

        this.getContentPane().add(column_1);
        this.getContentPane().add(column_2);
        this.getContentPane().add(column_3);
        this.getContentPane().add(column_4);
        this.getContentPane().add(column_5);
        this.getContentPane().add(column_6);

        this.getContentPane().add(title_1);
        this.getContentPane().add(title_2);
        this.getContentPane().add(title_3);
    }

    //game instructor //todo
    private void addTipButton() {
        JButton button = new JButton("Tips");
        button.setLocation(508, 388);
        button.setSize(100, 30);
        add(button);
        button.addActionListener(e -> {
            String[] manualSections = {
                    "---> Slide and Merge:\n" +
                            "   When sliding the grid, all tiles shift towards the\n" +
                            "chosen direction until they hit the edge or another \n" +
                            "immovable tile.\n\n" +
                            "   If two adjacent tiles have the same number, they will\n" +
                            "merge into one tile with the sum of their values after the\n" +
                            "movement stops and this new merged tile will also shift\n" +
                            "along the movement direction until it can't move further.\n\n" +
                            "   If there exist three identically valued tiles that are\n" +
                            "adjacent to one another, the two blocks nearest to the \n" +
                            "end of the sliding direction will merge together.\n" +
                            "                                  Next Page --> New Tile Generation",
                    "---> New Tile Generation:\n\n" +
                            "   Following every valid slide (where at least one tile \n" +
                            "changes position), the game will generate a new 2 or 4\n" +
                            "tile randomly in any empty space on the grid.\n\n\n\n\n\n\n\n\n" +
                            "                                            Next Page --> Save and Load",
                    "---> Save and Load: \n" +
                            "   If you are registered users, you can perform save and\n" +
                            "Operation anytime in your game progress.\n\n" +
                            "   There are 3 prepared Archive slots, where you can save\n" +
                            "and load your game. Additionally, you can save your game\n" +
                            "data to your appointed directory by clicking the button \n" +
                            "'Directory' in the save panel and load your game data from\n" +
                            "appointed file by clicking the button 'File Browser' in\n" +
                            "load panel.\n\n" +
                            "   If you load your files, please ensure that this file does\n" +
                            "belong to you, or the file won't be loaded.\n" +
                            "                                                     Next Page --> UNDO",
                    "---> UNDO: \n\n" +
                            "   UNDO allows the player undo the most recent action,\n" +
                            "you can perform UNDO for no more than 3 times.\n\n" +
                            "   Once you load previous game, undo can't be performed\n" +
                            "immediately.\n\n\n\n\n\n\n" +
                            "                                      Next Page --> Sound and Music",
                    "---> Sound and Music\n\n" +
                            "   This game has effect sound, you can choose to disable\n" +
                            " or enable sound by clicking the button 'Sound On/Off'\n\n" +
                            "   This game has background music, you can turn on the\n" +
                            "music player by clicking the button 'Music 1/2/3'.\n\n" +
                            "   Close the music by clicking 'Background Music Off'.\n" +
                            "\n\n\n\n" +
                            "                                           Next Page --> Game Props",

                    "---> Game Props\n" +
                            " > Hammer:\n" +
                            "   Hammer allow players clear a tile or change the \n" +
                            "chosen tile's number.\n" +
                            "   You can perform this prop by clicking the button\n" +
                            "'Hammer' on game panel and choose a tile on the game\n" +
                            "board, the input a valid number to operate.\n\n" +
                            " > Artificial Intelligence:\n" +
                            "   AI is based on Monte Carlo's algorithm, which can\n" +
                            "automatically move the tiles before the game is over.\n" +
                            "   You can click button 'AI' to perform this prop.\n\n" +
                            "                                           Next Page --> Game Modes",
                    "---> Game Modes\n" +
                            " > 2^n: \n" +
                            "   Classic 2048 Game.\n" +
                            " > 3^n: \n" +
                            "   Change the game rule to 3 grid with same number can\n" +
                                    "merge to a new grid with the number of 3 times before.\n" +
                            " > Count Down: \n" +
                            "   You can select a time limit to challenge your limit.\n" +
                            "When time limit is exceeded, you can't do any operation\n" +
                            "or restart the game\n" +
                            " > Target: \n" +
                            "   You can select the target Tile to reach.\n" +
                            "   You can continue to play after you reach your goal.\n" +
                            "                                           Next Page --> Save and Load",
                    "---> Save and Load\n" +
                            "   We allow you to save you game progress.\n" +
                            "   You have 3 save slots, additionally, you can save\n" +
                            "to a single game file (*.2048) in your laptop.\n" +
                            "   You can load your game from your archive slots or\n" +
                            "from you previous save file. Please ensure that the game\n" +
                            "file does belongs to you, or that file won't be loaded!\n\n\n\n" +
                            "Enjoy your journey in this game playing!"
            };

            AtomicInteger currentIndex = new AtomicInteger(0);

            // 创建文本区域并设置初始内容
            JTextArea textArea = new JTextArea(14, 31);
            textArea.setText(manualSections[currentIndex.get()]);
            textArea.setEditable(false); // 禁止编辑

            // 将文本区域放到滚动窗格中
            JScrollPane scrollPane = new JScrollPane(textArea);

            // 创建"下一条"按钮
            JButton nextButton = new JButton("Next Page");
            nextButton.addActionListener(f -> {
                // 切换到下一条内容
                int index = currentIndex.incrementAndGet();
                if (index < manualSections.length) {
                    textArea.setText(manualSections[index]);
                } else {
                    // 显示完所有内容后关闭弹窗
                    JOptionPane.getRootFrame().dispose();
                }
            });

//            //创建“上一条”按钮
//
//            //todo: debug
//            JButton lastButton = new JButton("Previous Page");
//            lastButton.addActionListener(f -> {
//                // 切换到上一条内容
//                int index = currentIndex.decrementAndGet();
//                if (index >= 0) {
//                    textArea.setText(manualSections[index]);
//                }
//            });

            // 创建面板，将文本区域和按钮放置在一起
            JPanel panel = new JPanel();
            panel.add(scrollPane);
//            panel.add(lastButton);
            panel.add(nextButton);
            // 显示弹窗
            JOptionPane.showMessageDialog(this, panel, "2048 Instructor", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
