package Pattern;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterNew extends Window{
    private JPanel mainPanel;
    private JTextField tfName;
    private JTextField tfSurname;
    private JTextField tfLogin;
    private JTextField tfPassword;
    private JButton REGISTERButton;
    private JButton CLEARButton;
    private JPanel rSurname;
    private JPanel rLogin;
    private JPanel rName;
    private JPanel rPassword;

    Connection connection=null;

    public RegisterNew(JPanel rLogin) {
        this.rLogin = rLogin;
    }



    public RegisterNew() {


        super();
        setContentPane(mainPanel);
        setVisible(true);

        REGISTERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = tfName.getText();
                String surname=tfSurname.getText();
                String login = tfLogin.getText();
                String password=tfPassword.getText();
                if(name.isEmpty()||surname.isEmpty()||login.isEmpty()||password.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanel, "Enter", "try again", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                user=addUsertoDatabase(name, surname,login,password);

            }

            User user;

            public User addUsertoDatabase(String u_name, String u_surname, String u_login, String u_password) {
                User user= null;
                ConnectionToInternal settings = new ConnectionToInternal();
                try{

                    Connection conn= DriverManager.getConnection(settings.url,settings.user,settings.pwd);
                    Statement stm= conn.createStatement();
                    String sql="INSERT INTO login(u_name, u_surname, u_login, u_password)"+"VALUES (?,?,?,?)";
                    PreparedStatement statement= conn.prepareStatement(sql);
                    statement.setString(1, u_name);
                    statement.setString(2, u_surname);
                    statement.setString(3, u_login);
                    statement.setString(4, u_password);

                    int addRows= statement.executeUpdate();

                    if(addRows>0  ){
                        user= new User();
                        user.name=u_name;
                        user.surname=u_surname;
                        user.login=u_login;
                        user.password=u_password;
                        JOptionPane.showMessageDialog(mainPanel,  "Success");
                    }
                    stm.close();
                    conn.close();

                }
                catch (SQLException throwables){
                    throwables.printStackTrace();
                }

                return user;
            }

        });
        CLEARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }

        });




    }
}
