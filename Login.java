package Pattern;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends Window {
    private JPanel panel1;
    private JLabel pPassword;
    private JLabel lLogin;
    private JButton btnLogin;
    private JButton CLEARButton;
    private JTextField tfLogin;
    private JTextField tfPassword;
    private JPanel mainPanel;

    public Login() {
        super();
        setContentPane(mainPanel);
        setVisible(true);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = tfLogin.getText();
                String password = tfPassword.getText();
                user = getUser(login, password);
            }

            User user;

            private User getUser(String login, String password) {
                User user = null;
                ConnectionToInternal settings = new ConnectionToInternal();
                try {
                    Connection connection = DriverManager.getConnection(settings.url, settings.user, settings.pwd);

                    PreparedStatement statement = connection.prepareStatement("SELECT u_login, u_password FROM login WHERE u_login=? AND u_password=?");
                    statement.setString(1, login);
                    statement.setString(2, password);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        user = new User();
                        user.login = resultSet.getString("u_login");
                        user.password = resultSet.getString("u_password");
                        JOptionPane.showMessageDialog(btnLogin, "You have successfully logged in");


                    } else {
                        JOptionPane.showMessageDialog(btnLogin, "Wrong Username & Password");
                    }
                    statement.close();
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                return user;
            }
        });


        CLEARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               tfLogin.setText(" ");
               tfPassword.setText(" ");
            }
        });
    }
}


