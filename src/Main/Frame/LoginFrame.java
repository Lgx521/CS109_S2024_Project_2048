package Main.Frame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Scanner;

public class LoginFrame extends JFrame implements ActionListener, ItemListener {

    JTextField loginUserName = new JTextField();
    JPasswordField userPassword = new JPasswordField();
    Checkbox showPwd = new Checkbox("Display");
    JButton commitJbt = new JButton("Log in");


    //创建setup方法供外界访问
    public void setup() {
        initialLoginFrame();
        this.setVisible(true);
    }


    private void initialLoginFrame() {
        this.getContentPane().removeAll();
        this.setTitle("Log in - Welcome to play 2048");
        this.setSize(450,320);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);

        //标题
        JLabel title = new JLabel();


        //输入文本框提示
        JLabel UserNameTip = new JLabel("User Name");
        JLabel UserPasswordTip = new JLabel(" Password");

        UserNameTip.setSize(70,25);
        UserPasswordTip.setSize(70,25);

        UserNameTip.setBounds(40,95,70,25);
        UserPasswordTip.setBounds(40,135,70,25);


        //输入文本框
//        JTextField loginUserName = new JTextField();
//        JPasswordField userPassword = new JPasswordField();

        loginUserName.setSize(200,25);
        loginUserName.setBounds(115, 95, 200, 25);

        userPassword.setSize(200,25);
        userPassword.setBounds(115, 135, 200, 25);



//        Checkbox showPwd = new Checkbox("Display");
        showPwd.setSize(80,25);
        showPwd.setBounds(312,135,80,25);
        showPwd.addItemListener(this);


        //选择按钮
//        JButton commitJbt = new JButton("Log in");
        commitJbt.setSize(80,30);
        commitJbt.setBounds(160,175,100,30);
        commitJbt.addActionListener(this);


        this.getContentPane().add(loginUserName);
        this.getContentPane().add(userPassword);
        this.getContentPane().add(UserNameTip);
        this.getContentPane().add(UserPasswordTip);
        this.getContentPane().add(commitJbt);
        this.getContentPane().add(showPwd);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == commitJbt) {
            System.out.println("The User clicked the log in Button");
            System.out.print("User Name: " + loginUserName.getText() + "\nUser Password: ");
            for (int i = 0; i < userPassword.getPassword().length; i++) {
                System.out.print(userPassword.getPassword()[i]);
            }
            System.out.println();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            userPassword.setEchoChar((char)0);
        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
            userPassword.setEchoChar((char)9679);
        }
    }
}