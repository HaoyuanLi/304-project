package backend;

import backend.Connector;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.Vector;

/**
 * Created by GL on 2017-03-25.
 */
public class RoomType {

    private static Connection con = Connector.getConnection();

    public static DefaultTableModel searchRoomType(boolean typeBox, boolean accommodationBox, boolean sizeBox, boolean rateBox, boolean featuresBox,
                                                   int accommodationDrop, int accommodationVal, int sizeDrop, int sizeVal, int rateDrop, float rateVal,
                                                   boolean kitchenBox, boolean bathroomBox, boolean loungeBox) {
        String select = createSelectString(typeBox, accommodationBox, sizeBox, rateBox, featuresBox);
        String from = "FROM roomtype ";
        String where = createWhereString(accommodationDrop, accommodationVal, sizeDrop, sizeVal, rateDrop, rateVal, kitchenBox, bathroomBox, loungeBox);
        String query = select + from + where;
        System.out.println(query);
        return executeSearchQuery(query);
    }

    public static DefaultTableModel searchRoomType(boolean typeBox, boolean accommodationBox, boolean sizeBox, boolean rateBox, boolean featuresBox) {
        String select = createSelectString(typeBox, accommodationBox, sizeBox, rateBox, featuresBox);
        String from = "FROM roomtype";
        String query = select + from;
        System.out.println(query);
        return executeSearchQuery(query);
    }

    public static Number findAggregate(int aggregationDrop, int attributeDrop) {
        String select = "SELECT ";
        if (aggregationDrop == 0) {
            select = select + "MAX(";
        }
        else if (aggregationDrop == 1) {
            select = select + "MIN(";
        }
        else if (aggregationDrop == 2) {
            select = select + "AVG(";
        }
        else if (aggregationDrop == 3) {
            select = select + "COUNT(";
        }
        //TODO: ADD SUM!!!
        else if(aggregationDrop == 4){
            select = select +"SUM(";
        }

        if (attributeDrop == 0) {
            select = select + "accommodation) ";
        }
        else if (attributeDrop == 1) {
            select = select + "rsize) ";
        }
        else if (attributeDrop == 2) {
            select = select + "rate) ";
        }
        String from = "FROM roomtype";
        String query = select + from;
        System.out.println(query);
        return executeFindQuery(query, attributeDrop);
    }
    //TODO
    public static Number doubleAggregation(){
        String query = "SELECT Temp.rsize, Temp.agg FROM (SELECT rt.rsize, MIN (rt.rate) as agg FROM roomtype rt GROUP BY rt.rsize) as Temp WHERE Temp.agg = (SELECT MAX(Temp.agg) FROM  Temp)";
        String select = "SELECT Temp.rsize, Temp.agg ";
        String from = "FROM (SELECT rt.rsize, MIN (rt.rate) as agg FROM roomtype rt GROUP BY rt.rsize) as Temp ";
        String where = "WHERE Temp.agg = (SELECT MAX(Temp.agg) FROM  Temp)";
        String q = select + from + where;

        return executeDoubleAggQuery(q);
    }

    private static String createSelectString(boolean typeBox, boolean accommodationBox, boolean sizeBox, boolean rateBox, boolean featuresBox) {
        String select = "SELECT ";
        if (typeBox) {
            select = select + "type, ";
        }
        if (accommodationBox) {
            select = select + "accommodation, ";
        }
        if (sizeBox) {
            select = select + "rsize, ";
        }
        if (rateBox) {
            select = select + "rate, ";
        }
        if (featuresBox) {
            select = select + "features, ";
        }
        select = select.substring(0, select.length() - 2) + " ";
        return select;
    }

    private static String createWhereString(int accommodationDrop, int accommodationVal, int sizeDrop, int sizeVal, int rateDrop, float rateVal,
                                            boolean kitchenBox, boolean bathroomBox, boolean loungeBox) {
        String where = "WHERE ";
        if (accommodationVal != -1) {
            where = where + "accommodation " + findSymbolString(accommodationDrop) + " " + accommodationVal + " AND ";
        }
        if (sizeVal != -1) {
            where = where + "rsize " + findSymbolString(sizeDrop) + " " + sizeVal + " AND ";
        }
        if (rateVal != -1) {
            where = where + "rate " + findSymbolString(rateDrop) + " " + rateVal + " AND ";
        }
        if (kitchenBox) {
            where = where + "features LIKE '%kitchen%' AND ";
        }
        if (bathroomBox) {
            where = where + "features LIKE '%bathroom%' AND ";
        }
        if (loungeBox) {
            where = where + "features LIKE '%lounge%' AND ";
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

    private static Number executeFindQuery(String query, int attributeDrop) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String att;
            if (attributeDrop == 2) {
                rs.next();
                return rs.getFloat(1);
            }
            else {
                rs.next();
                return rs.getInt(1);
            }

        }catch (SQLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            System.out.println(exceptionAsString);
        }
        return null;
    }
    private static Number executeDoubleAggQuery(String query) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            return rs.getFloat(1);

        }catch (SQLException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            System.out.println(exceptionAsString);
        }
        return null;
    }

    private static String findSymbolString(int dropIndex) {
        if (dropIndex == 0) {
            return "<";
        }
        else if (dropIndex == 1) {
            return ">";
        }
        else if (dropIndex == 2) {
            return "=";
        }
        else if (dropIndex == 3) {
            return "<=";
        }
        else {
            return ">=";
        }
    }
}
