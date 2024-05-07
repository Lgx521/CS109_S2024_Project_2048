package Main.Frame;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.awt.AWTEventMulticaster.add;

public class GameStatics extends JFrame {

    public void setUpGameStatics() {
        initialFrame();
    }

    private void initialFrame() {
        this.setSize(550, 420);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
    }






    //todo
    private void addTipButton(){
        JButton button = new JButton("Tips");
        button.setLocation(200, 100);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            //todo
            String[] manualSections = {
                    "匹配规则：玩家通过交换相邻的（不包括对角线相邻）方块的\n位置来进行移动。当三个或更多相同图案的方块在一条直线上\n（水平或垂直）对齐时，它们被清除，并得分。",
                    "Auto mode可以自动完成一个周期",
                    "Hint可以让玩家获得下一步的提示",
                    "Back可以允许玩家悔棋",
                    "Music可切换和暂停音乐",
                    "Motion可打开或关闭动画模式",
                    "Refresh可刷新棋盘，每局有三次机会，Restart可重新开始这关游戏",
                    "Level选择难度并重新开始一轮游戏",
                    "Save和Load保存和加载游戏"
            };
            AtomicInteger currentIndex = new AtomicInteger(0);

            // 创建文本区域并设置初始内容
            JTextArea textArea = new JTextArea(10, 30);
            textArea.setText(manualSections[currentIndex.get()]);
            textArea.setEditable(false); // 禁止编辑

            // 将文本区域放到滚动窗格中
            JScrollPane scrollPane = new JScrollPane(textArea);

            // 创建"下一条"按钮
            JButton nextButton = new JButton("下一条");
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

            // 创建面板，将文本区域和按钮放置在一起
            JPanel panel = new JPanel();
            panel.add(scrollPane);
            panel.add(nextButton);

            // 显示弹窗
            JOptionPane.showMessageDialog(null, panel, "游戏说明书", JOptionPane.INFORMATION_MESSAGE);


        });
    }
}
