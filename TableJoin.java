

import Pattern.ConnectionToInternal;
import Pattern.Window;

//import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class TableJoin {
 /*   private JPanel mainPanel;
 /*   private JTable tData;

    private Window window;
    public DefaultTableModel model;
   String[] header = {"Id","Name","Surname","Login","Password","City"};
    public TableJoin () {
        window=new Window();
        window.setContentPane(mainPanel);
        bindDataWithModel();
        getDataFromDataBase();
        window.setVisible(true);
    }


    private void bindDataWithModel() {
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

            ResultSet rsta=sta.executeQuery("SELECT u.u_id, u.u_name, u.u_surname, u.u_login, u.u_password, p.pub_name FROM student s JOIN city c ON c.c_id=s.s_cid");

            while(rsta.next()){
                Object[] row = {
                        rsta.getInt("s_id"),
                        rsta.getString("s_name"),
                        rsta.getString("s_surname"),
                        rsta.getString("s_login"),
                        rsta.getString("s_password"),
                        rsta.getString("c_name")
                };
                model.addRow(row);
                //dispose();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/
}
