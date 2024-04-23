package Main.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SigninFrame extends JFrame implements ActionListener, ItemListener {

    JTextField loginUserName = new JTextField();
    JPasswordField userPassword = new JPasswordField();
    JPasswordField userPasswordCommit = new JPasswordField();
    Checkbox showPwd = new Checkbox("Display");
    Checkbox showPwd2 = new Checkbox("Display");
    JButton signIn = new JButton("Sign in");
    JButton guest = new JButton("Guest Mode");


    //创建setup方法供外界访问
    public void setup() {
        initialSigninFrame();
        this.setVisible(true);
    }

    //初始化主界面
    private void initialSigninFrame() {
        this.getContentPane().removeAll();
        this.setTitle("Sign In - Create Your Account");
        this.setSize(450, 320);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);

        //标题
//        JLabel title = new JLabel("");


        //背景图片
        ImageIcon backgroundImage = new ImageIcon("src/Main/Resources/background.png");
        JLabel background = new JLabel(backgroundImage);
        background.setSize(450, 320);
        background.setBounds(0, 0, 450, 320);


        //输入文本框提示
        JLabel UserNameTip = new JLabel("User Name");
        JLabel UserPasswordTip = new JLabel("Input Password");
        JLabel CommitPwdTip = new JLabel("Commit Password");

        UserNameTip.setSize(70, 25);
        UserPasswordTip.setSize(140, 25);
        CommitPwdTip.setSize(140, 25);

        UserNameTip.setBounds(58, 95, 70, 25);
        UserPasswordTip.setBounds(32, 135, 140, 25);
        CommitPwdTip.setBounds(15, 175, 140, 25);


        //输入文本框
        loginUserName.setSize(200, 25);
        loginUserName.setBounds(135, 95, 200, 25);

        userPassword.setSize(200, 25);
        userPassword.setBounds(135, 135, 200, 25);

        userPasswordCommit.setSize(200, 25);
        userPasswordCommit.setBounds(135, 175, 200, 25);


        showPwd.setSize(80, 25);
        showPwd.setBounds(335, 135, 80, 25);
        showPwd.addItemListener(this);
        showPwd.setBackground(new Color(198, 218, 217));

        showPwd2.setSize(80, 25);
        showPwd2.setBounds(335, 175, 80, 25);
        showPwd2.addItemListener(this);
        showPwd2.setBackground(new Color(155, 195, 207));


        //选择按钮
        signIn.setSize(80, 30);
        signIn.setBounds(176, 225, 100, 30);
        signIn.addActionListener(this);


        this.getContentPane().add(loginUserName);
        this.getContentPane().add(userPassword);
        this.getContentPane().add(UserNameTip);
        this.getContentPane().add(UserPasswordTip);
        this.getContentPane().add(userPasswordCommit);
        this.getContentPane().add(signIn);
        this.getContentPane().add(CommitPwdTip);
        this.getContentPane().add(guest);
        this.getContentPane().add(showPwd);
        this.getContentPane().add(showPwd2);
        this.getContentPane().add(background);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == signIn) {
            System.out.println("Commit SignIn");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object obj = e.getSource();
        if (obj == showPwd) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                userPassword.setEchoChar((char) 0);
                System.out.println("Show Password 1");
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                userPassword.setEchoChar((char) 9679);
                System.out.println("Hide Password 1");
            }
        } else if (obj == showPwd2) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                userPasswordCommit.setEchoChar((char) 0);
                System.out.println("Show Commited Password");
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                userPasswordCommit.setEchoChar((char) 9679);
                System.out.println("Hide Commited Password");
            }
        }


    }
}


