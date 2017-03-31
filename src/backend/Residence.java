package backend;

import javax.swing.table.DefaultTableModel;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.Vector;


/**
 * Created by GL on 2017-03-25.
 */
public class Residence {

    private static Connection con = Connector.getConnection();

    public static DefaultTableModel searchResidence(boolean rnameBox, boolean addressBox, boolean snameBox,
                                                    String rnameText, String addressText, String snameText) {
        String select = createSelectString(rnameBox, addressBox, snameBox);
        String from = "FROM residence ";
        String where = createWhereString(rnameText, addressText, snameText);
        String query = select + from + where;
        System.out.println(query);
        return executeSearchQuery(query);
    }
    public static DefaultTableModel searchResidenceByRoomType(boolean obrBox, boolean tbrBox, boolean fbrBox, boolean sbrBox, boolean studioBox) {
        String select = "SELECT r.rname ";
        String from = "FROM residence r ";
        String nestedWhere = createdNestedWhere(obrBox, tbrBox, fbrBox, sbrBox, studioBox);
        String where = "WHERE NOT EXISTS ((SELECT t.type FROM roomtype t " + nestedWhere + ") MINUS (SELECT m.type FROM room m WHERE r.rname=m.rname))";
        String query = select + from + where;
        System.out.println(query);
        return executeSearchQuery(query);
    }
    public static String createdNestedWhere(boolean obrBox, boolean tbrBox, boolean fbrBox, boolean sbrBox, boolean studioBox){
        String where = "WHERE ";
        if (obrBox) {
            where = where + "t.type='One Bedroom' OR ";
        }
        if (tbrBox) {
            where = where + "t.type='Two Bedrooms Suite' OR ";
        }
        if (fbrBox) {
            where = where + "t.type='Four Bedrooms Suite' OR ";
        }
        if (sbrBox) {
            where = where + "t.type='Six Bedrooms Suite' OR ";
        }
        if (studioBox) {
            where = where + "t.type='Studio' OR ";
        }
        where = where.substring(0, where.length()-4);
        return where;
    }

    public static DefaultTableModel searchResidence(boolean rnameBox, boolean addressBox, boolean snameBox) {
        String select = createSelectString(rnameBox, addressBox, snameBox);
        String from = "FROM residence";
        String query = select + from;
        System.out.println(query);
        return executeSearchQuery(query);
    }

    private static String createSelectString(boolean rnameBox, boolean addressBox, boolean snameBox) {
        String select = "SELECT ";
        if (rnameBox) {
            select = select + "rname, ";
        }
        if (addressBox) {
            select = select + "address, ";
        }
        if (snameBox) {
            select = select + "sname, ";
        }
        select = select.substring(0, select.length() - 2) + " ";
        return select;
    }

    private static String createWhereString(String rnameText, String addressText, String snameText) {
        String where = "WHERE ";
        if (!rnameText.equals("")) {
            where = where + "UPPER(rname) LIKE UPPER('%" + rnameText + "%') AND ";
        }
        if (!addressText.equals("")) {
            where = where + "UPPER(address) LIKE UPPER('%" + addressText + "%') AND ";
        }
        if (!snameText.equals("")) {
            where = where + "UPPER(sname) LIKE UPPER('%" + snameText + "%') AND ";
        }
        where = where.substring(0, where.length()-5);
        return where;
    }

    private static DefaultTableModel executeSearchQuery(String query) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            Vector<String> columnNames = new Vector<String>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnLabel(i));
            }

            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    vector.add(rs.getObject(i));
                }
                data.add(vector);
            }

            return new DefaultTableModel(data, columnNames);

        }catch (SQLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            System.out.println(exceptionAsString);
        }
        return null;
    }
}
