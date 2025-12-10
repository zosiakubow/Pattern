package Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterTable {
    private JPanel pTable;
    private JPanel mainPanel;
    private JTable tData;
    private JPanel pForm;
    private JPanel pButton;
    private JButton INSERTButton;
    private JButton UPDATEButton;
    private JButton DELETEButton;
    private JTextField tfID;
    private JTextField tfName;
    private JTextField tfSurname;
    private JTextField tfLogin;
    private JTextField tfPassword;
    private JScrollPane tScroll;

    private Window window;
    private DefaultTableModel model;

    String[] header = {"Id", "Name", "Surname", "Login", "Password"};

    public RegisterTable() {
        window = new Window();
        window.setContentPane(mainPanel);
        bindControlWithModel();
        getDataFromDataBase();
        window.setVisible(true);

        INSERTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                String surname = tfSurname.getText();
                String login = tfLogin.getText();
                String password = tfPassword.getText();

                ConnectionToInternal settings = new ConnectionToInternal();
                Integer newId = null;

                try {
                    Connection conn = DriverManager.getConnection(settings.url, settings.user, settings.pwd);
                    String sqlQuery = "INSERT INTO login (u_name, u_surname, u_login, u_password) VALUES (?, ?, ?,?)";
                    PreparedStatement statement= conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, name);
                    statement.setString(2, surname);
                    statement.setString(3,login);
                    statement.setString(4,password);
                    //  statement.setInt(5, rowId);

                    int affectedRows = statement.executeUpdate();
                    if(affectedRows>0) {
                        try {
                            ResultSet rs = statement.getGeneratedKeys();
                            if (rs.next()) {
                                newId = rs.getInt(1);
                            }
                        } catch( SQLException ex){
                            System.out.println(ex.getMessage());
                        }
                        finally {
                            conn.close();
                        }
                    }

                    if(newId != null) {
                        Object[] row = {
                                newId,
                                name,
                                surname,
                                login,
                                password
                        };
                        model.addRow(row);;
                    }

                    JOptionPane.showMessageDialog(null, "Dane dodane do bazy", "Insert", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Insert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void bindControlWithModel() {
        model = new DefaultTableModel(0, header.length) {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        model.setColumnIdentifiers(header);
        tData.setModel(model);
    }
        private void getDataFromDataBase() {
            ConnectionToInternal settings = new ConnectionToInternal();

            try {
                Connection conn = DriverManager.getConnection(settings.url, settings.user, settings.pwd);
                Statement sta = conn.createStatement();

                ResultSet rsta = sta.executeQuery("SELECT u_id, u_name, u_surname, u_login, u_password FROM login");

                while (rsta.next()) {
                    Object[] row = {
                            rsta.getInt("u_id"),
                            rsta.getString("u_name"),
                            rsta.getString("u_surname"),
                            rsta.getString("u_login"),
                            rsta.getString("u_password"),
                    };
                    model.addRow(row);
                    //dispose();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();

            }}
                private void PopulateDataToForm() {

                    tData.setModel(model);
                }

            }




