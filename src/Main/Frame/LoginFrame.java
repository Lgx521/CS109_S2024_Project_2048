package Main.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginFrame extends JFrame implements ActionListener, ItemListener {

    JTextField loginUserName = new JTextField();
    JPasswordField userPassword = new JPasswordField();
    Checkbox showPwd = new Checkbox("Display");
    JButton commitJbt = new JButton("Log in");
    JButton signinJtb = new JButton("Sign In");
    JButton guest = new JButton("Guest Mode");
    JCheckBox a = new JCheckBox("test");


    //创建setup方法供外界访问
    public void setup() {
        initialLoginFrame();
        this.setVisible(true);
    }

    //初始化主界面
    private void initialLoginFrame() {
        this.getContentPane().removeAll();
        this.setTitle("Log in - Welcome to play 2048");
        this.setSize(450, 320);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);


        //背景图片
        ImageIcon backgroundImage = new ImageIcon("src/Main/Resources/background.png");
        JLabel background = new JLabel(backgroundImage);
        background.setSize(450, 320);
        background.setBounds(0, 0, 450, 320);

        //输入文本框提示
        JLabel UserNameTip = new JLabel("User Name");
        JLabel UserPasswordTip = new JLabel(" Password");

        UserNameTip.setSize(70, 25);
        UserPasswordTip.setSize(70, 25);

        UserNameTip.setBounds(55, 95, 70, 25);
        UserPasswordTip.setBounds(55, 135, 70, 25);

        //输入文本框
        loginUserName.setSize(200, 25);
        loginUserName.setBounds(130, 95, 200, 25);

        userPassword.setSize(200, 25);
        userPassword.setBounds(130, 135, 200, 25);

        //密码显示明文
        showPwd.setSize(80, 25);
        showPwd.setBounds(327, 135, 80, 25);
        showPwd.addItemListener(this);
        showPwd.setBackground(new Color(198, 218, 217));

        //选择按钮
        commitJbt.setSize(80, 30);
        commitJbt.setBounds(178, 175, 100, 30);
        commitJbt.addActionListener(this);

        //Guest访客模式
        JLabel guestTips = new JLabel("Try Log Free Guest Mode?   -->");
        guestTips.setSize(205, 25);
        guestTips.setBounds(35, 225, 205, 25);
        guest.setSize(100, 25);
        guest.setBounds(237, 225, 100, 25);
        guest.addActionListener(this);

        //跳转到注册界面
        JLabel tips = new JLabel("Don't have a Account now?   -->");
        tips.setSize(210, 25);
        tips.setBounds(35, 250, 210, 25);
        signinJtb.setSize(80, 25);
        signinJtb.setBounds(247, 250, 80, 25);
        signinJtb.addActionListener(this);


        this.getContentPane().add(loginUserName);
        this.getContentPane().add(userPassword);
        this.getContentPane().add(UserNameTip);
        this.getContentPane().add(UserPasswordTip);
        this.getContentPane().add(commitJbt);
        this.getContentPane().add(signinJtb);
        this.getContentPane().add(tips);
        this.getContentPane().add(guestTips);
        this.getContentPane().add(guest);
        this.getContentPane().add(showPwd);
        this.getContentPane().add(background);

    }

    private final int NONE_INPUT = 0;
    private final int NONE_PASSWORD = 1;
    private final int WRONG_PASSWORD = 2;
    private final int INVALID_USERNAME = 3;

    //登陆失败提示框初始化
    private void initialLoginFailure(int issue) {
        String content = "initial";
        if (issue == WRONG_PASSWORD) {
            content = "Wrong Password!";
        } else if (issue == INVALID_USERNAME) {
            content = "This User doesn't exist.\nPlease Sign In!\nOr you can try Guest mode";
        } else if (issue == NONE_INPUT) {
            content = "Please input your Info!\nOr you can try Guest mode";
        } else if (issue == NONE_PASSWORD) {
            content = "Please input your password!";
        }
        JOptionPane.showMessageDialog(null, content, "Caution", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == commitJbt) {
            System.out.println("Log in");
            //用户名以及密码输入测试
            System.out.println("The User clicked the log in Button");
            System.out.print("User Name: " + loginUserName.getText() + "\nUser Password: ");
            for (int i = 0; i < userPassword.getPassword().length; i++) {
                System.out.print(userPassword.getPassword()[i]);
            }
            System.out.println();
            //密码验证逻辑
            if (loginUserName.getText().isEmpty()) {
                initialLoginFailure(NONE_INPUT);
            } else {
                //todo:用户存在
                if (loginUserName.getText().equals("gan")) {
                    //todo:用户存在逻辑
                } else {
                    initialLoginFailure(INVALID_USERNAME);
                    return;
                }
                //todo:密码判断
                if (userPassword.getPassword().length == 0) {
                    initialLoginFailure(NONE_PASSWORD);
                } else {
                    if (userPassword.getPassword()[0] != '0') {
                        initialLoginFailure(WRONG_PASSWORD);
                    } else {
                        //todo: 开始游戏
                    }
                }
            }
        } else if (obj == signinJtb) {
            System.out.println("Sign in");
            this.dispose();
            new SigninFrame().setup();
        } else if (obj == guest) {
            System.out.println("Try Guest Mode");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            userPassword.setEchoChar((char) 0);
            System.out.println("Show Password");
        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
            userPassword.setEchoChar((char) 9679);
            System.out.println("Hide password");
        }
    }
}