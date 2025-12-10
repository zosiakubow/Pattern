package Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Table {
    private JPanel panel1;
    private JPanel mainPanel;
    private JTable tData;


    private Window window;
    private DefaultTableModel model;

    String[] header = {"Id","Name","Surname","Login","Password"};
    public Table() {
            window = new Window();
            window.setContentPane(mainPanel);
            bindControlWithModel();
            getDataFromDataBase();
            window.setVisible(true);

        }
    private void bindControlWithModel() {
        model = new DefaultTableModel(0,header.length)
        {
            @Override public Class<?> getColumnClass(int column) {
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
            Statement sta= conn.createStatement();

            ResultSet rsta=sta.executeQuery( "SELECT u_id, u_name, u_surname, u_login, u_password FROM login" );

            while(rsta.next()){
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
        }
    }



}

