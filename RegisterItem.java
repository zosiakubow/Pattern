package Pattern;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterItem {

    private JPanel mainPanel;
    private JPanel pForm;
    private JPanel pButton;
    private JButton btnInsert;
    private JButton btnLogin;
    private JButton btnClear;
    private JTextField tfName;
    private JTextField tfSurname;
    private JTextField tfLogin;
    private JTextField tfPassword;
    private JComboBox cmbCity;
    private JLabel lCity;
    private JLabel lName;
    private JLabel lSurname;
    private JLabel lLogin;
    private JLabel lPassword;

    Window window;
    ConnectionToInternal connection = null;
    private DefaultComboBoxModel cityModel;

    public RegisterItem() {

        window = new Window ();
        window.setContentPane(mainPanel);

        GetDictionaries();
        PopulateDataToForm();
        window.setVisible(true);

        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfLogin.setText(" ");
                tfPassword.setText(" ");
                tfName.setText(" ");
                tfSurname.setText(" ");
            }
        });
    }
    //---------------------------constructor---------------------------

    private void registerUser() {
        String name = tfName.getText();
        String surname= tfSurname.getText();
        String login= tfLogin.getText();
        String password=tfPassword.getText();

        CityItem selectedCityItem = ((CityItem) cmbCity.getModel().getSelectedItem());
        Integer cityId = selectedCityItem.getId();
        String cityName= selectedCityItem.getDescription();

        if(name.isEmpty()||surname.isEmpty()||login.isEmpty()||password.isEmpty()){
            return;
        }
        user=addUserToDatabase(name,surname,login, password, cityId);

    }
    public User user;
    private User addUserToDatabase(String name, String surname, String login, String password, int cityId){
        ConnectionToInternal settings = new ConnectionToInternal();
        Integer newId = null;

        try {
            Connection conn = DriverManager.getConnection(settings.url, settings.user, settings.pwd);
            String sqlQuery = "INSERT INTO register(r_name, r_surname, r_login, r_password,r_city)"+"VALUES (?,?,?,?,?)";
            PreparedStatement statement= conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, login);
            statement.setString(4, password);
            statement.setInt(5, cityId);


            int addRows= statement.executeUpdate();

            if(addRows>0    ){
                Object[] row = {
                        newId,
                        name,
                        surname,
                        login,
                        password,
                        cityId


                };
                JOptionPane.showMessageDialog(mainPanel,  "Success");
            }

            conn.close();

        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    private void PopulateDataToForm() {
        cmbCity.setModel(cityModel);

    }
    private void GetDictionaries()  {
        ConnectionToInternal settings = new ConnectionToInternal();
        cityModel = new DefaultComboBoxModel();

        try {
            Connection conn = DriverManager.getConnection(settings.url, settings.user, settings.pwd);
            Statement sta= conn.createStatement();

            ResultSet rsta_city = sta.executeQuery("SELECT c_id, c_name FROM city");


            while(rsta_city.next()){
                cityModel.addElement(new CityItem(rsta_city.getInt("c_id"), rsta_city.getString("c_name")));
            }
            conn.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
