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


        //背景图片
        ImageIcon backgroundImage = new ImageIcon("src/Main/Resources/background.png");
        JLabel background = new JLabel(backgroundImage);
        background.setSize(450, 320);
        background.setBounds(0, 0, 450, 320);


        //输入文本框提示
        JLabel UserNameTip = new JLabel("User Name");
        JLabel UserPasswordTip = new JLabel("Input Password");
        JLabel CommitPwdTip = new JLabel("Verify Password");

        UserNameTip.setSize(70, 25);
        UserPasswordTip.setSize(140, 25);
        CommitPwdTip.setSize(140, 25);

        UserNameTip.setBounds(58, 95, 70, 25);
        UserPasswordTip.setBounds(32, 135, 140, 25);
        CommitPwdTip.setBounds(29, 175, 140, 25);


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

    private final int NOTICE = 0;
    private final int NO_USERNAME_INPUT = 1;
    private final int NO_PASSWORD_INPUT = 2;
    private final int NO_PASSWORD_VERIFICATION_INPUT = 3;
    private final int VERIFICATION_ERROR = 4;


    //notice提示初始化
    private void initialNoticeDialog(int issue) {
        String content = "initial";
        if (issue == NOTICE) {
            content = "Your User Name can only contains\n" +
                    "English letters, Numbers and _";
            JOptionPane.showMessageDialog(null, content, "Notice", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (issue == NO_USERNAME_INPUT) {
            content = "Please input your user name!";
        } else if (issue == NO_PASSWORD_INPUT) {
            content = "No password input!";
        } else if (issue == NO_PASSWORD_VERIFICATION_INPUT) {
            content = "Verify your password!";
        } else if (issue == VERIFICATION_ERROR) {
            content = "Verification Failed!\nYou must input same password";
        }
        JOptionPane.showMessageDialog(null, content, "Notice", JOptionPane.ERROR_MESSAGE);
    }

    private boolean userNameValidation(String texts) {
        String regex = "\\w+";
        return texts.matches(regex);
    }

    private boolean passwordCommiting(char[] raw, char[] commit) {
        if (raw.length != commit.length) {
            return false;
        }
        for (int i = 0; i < raw.length; i++) {
            if (raw[i] != commit[i]) {
                return false;
            }
        }
        return true;
    }

    private void SignInValidation() {
        int passwordInputLength = userPassword.getPassword().length;
        int passwordCommitInputLength = userPasswordCommit.getPassword().length;
        if (!loginUserName.getText().isEmpty()) {
            boolean validation = userNameValidation(loginUserName.getText());
            if (passwordInputLength != 0) {
                if (passwordCommitInputLength != 0) {
                    if (validation) {
                        if (passwordCommiting(userPassword.getPassword(), userPasswordCommit.getPassword())) {
                            System.out.println("Verify SignIn Successfully");
                            this.dispose();
                            new LoginFrame().setup();
                            //todo: 接入文件写入
                        } else {
                            initialNoticeDialog(VERIFICATION_ERROR);
                        }

                    } else {
                        System.out.println("Invalid UserName");
                        initialNoticeDialog(NOTICE);
                    }
                } else {
                    System.out.println("No password Verification");
                    initialNoticeDialog(NO_PASSWORD_VERIFICATION_INPUT);
                }
            } else {
                System.out.println("No password");
                initialNoticeDialog(NO_PASSWORD_INPUT);
            }
        } else {
            System.out.println("No user name input");
            initialNoticeDialog(NO_USERNAME_INPUT);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == signIn) {
            SignInValidation();

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