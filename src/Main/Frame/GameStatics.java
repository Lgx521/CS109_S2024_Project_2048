package Main.Frame;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.awt.AWTEventMulticaster.add;

public class GameStatics extends JFrame {

    public void setUpGameStatics() {
        initialFrame();
        this.setVisible(true);
    }

    private void initialFrame() {
        this.setSize(550, 420);
        this.setLocationRelativeTo(null);
        this.setTitle("2048 - Game Statistics");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        addTipButton();
    }


    //todo
    private void addTipButton() {
        JButton button = new JButton("Tips");
        button.setLocation(200, 100);
        button.setSize(100, 40);
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
                            "",
                    "Save和Load保存和加载游戏"
            };
            AtomicInteger currentIndex = new AtomicInteger(0);

            // 创建文本区域并设置初始内容
            JTextArea textArea = new JTextArea(14, 31);
            textArea.setText(manualSections[currentIndex.get()]);
            textArea.setEditable(false); // 禁止编辑

            // 将文本区域放到滚动窗格中
            JScrollPane scrollPane = new JScrollPane(textArea);

            //中英文切换

            // 创建"下一条"按钮
            JButton nextButton = new JButton("Next Page");
            nextButton.addActionListener(f -> {
                // 切换到下一条内容
                int nextIndex = currentIndex.incrementAndGet();
                if (nextIndex < manualSections.length) {
                    textArea.setText(manualSections[nextIndex]);
                } else {
                    // 显示完所有内容后关闭弹窗
                    JOptionPane.getRootFrame().dispose();
                }
            });

            //创建“上一条”按钮
            JButton lastButton = new JButton("Previous Page");
            lastButton.addActionListener(f -> {
                // 切换到上一条内容
                int lastIndex = currentIndex.decrementAndGet();
                if (lastIndex >= 0) {
                    textArea.setText(manualSections[lastIndex]);
                } else {
                    // 显示完所有内容后关闭弹窗
                    JOptionPane.getRootFrame().dispose();
                }
            });

            // 创建面板，将文本区域和按钮放置在一起
            JPanel panel = new JPanel();
            panel.add(scrollPane);
            panel.add(lastButton);
            panel.add(nextButton);

            // 显示弹窗
            JOptionPane.showMessageDialog(this, panel, "2048 Instructor", JOptionPane.INFORMATION_MESSAGE);


        });
    }
}
