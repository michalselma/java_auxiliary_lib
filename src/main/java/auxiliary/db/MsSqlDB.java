package auxiliary.db;


import java.sql.*;
import java.util.*;

public class MsSqlDB {
    public java.sql.Connection con = null;


    // **ConnectionUrl Definition. Private for MsSQLDatabase class**
    private String connectionUrl(){
        System.out.println("*** Calling [MsSQLDatabase][connectionUrl]");
        final String url = "jdbc:sqlserver://";
        final String serverName= "127.0.0.1";  // Activate MSSQL TCP/IP connection via Configuration Manager -> SQL Server Network Configuration
        final String databaseName= "whois";
        final String portNumber = "1433";
        final String selectMethod = "cursor"; // Informs the driver to use server a side-cursor, which permits more than one active statement on a connection.
        final String utf8support = "characterSetResults=UTF-8;useUnicode=yes;characterEncoding=UTF-8";
        return url+serverName+":"+portNumber+";databaseName="+databaseName+";selectMethod="+selectMethod+";"+utf8support+";";
    }



    // **openConnection. Private for MsSQLDatabase class**
    public java.sql.Connection openConnection(){
        System.out.println("*** Calling [MsSQLDatabase][openConnection]");
        try{
            final String userName = "sa";
            final String password = "wosp14";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=java.sql.DriverManager.getConnection(connectionUrl(),userName,password);
            if(con!=null)
                System.out.println("[MsSQLDatabase.openConnection] Database Connection Successful.");
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("[MsSQLDatabase.openConnection] Error Trace in openConnection() : " + e.getMessage());
            System.out.println("[MsSQLDatabase.openConnection] Database Connection Failed!");
        }
        return con;
    }



    // **closeConnection. Private for MsSQLDatabase class**
    public void closeConnection(){
        System.out.println("*** Calling [MsSQLDatabase][closeConnection]");
        try{
            if(con!=null)
                con.close();
            con=null;
            System.out.println("[MsSQLDatabase.closeConnection] Database Connection Closed.");
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("[MsSQLDatabase.closeConnection] Failed to Close DataBase Connection!");
        }
    }



    // **Display the driver properties, database details**
    public void displayDbProperties(){
        System.out.println("*** Calling [MsSQLDatabase][displayDbProperties]");
        java.sql.DatabaseMetaData dm = null;
        java.sql.ResultSet rs = null;
        try{
            if(con!=null){
                dm = con.getMetaData();
                System.out.println("Driver Information");
                System.out.println("\tDriver Name: "+ dm.getDriverName());
                System.out.println("\tDriver Version: "+ dm.getDriverVersion ());
                System.out.println("\nDatabase Information ");
                System.out.println("\tDatabase Name: "+ dm.getDatabaseProductName());
                System.out.println("\tDatabase Version: "+ dm.getDatabaseProductVersion());
                System.out.println("Avalilable Catalogs ");
                rs = dm.getCatalogs();
                while(rs.next()){
                    System.out.println("\tcatalog: "+ rs.getString(1));
                }
                rs.close();
                rs = null;
            }
            else System.out.println("[MsSQLDatabase.displayDbProperties] Error: No active Connection");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        dm=null;
    }



    // **** Test connection, display properties, database details ****
    public void DBConnectionTest(){
        System.out.println("*** Calling [MsSQLDatabase][DBConnectionTest]");
        MsSqlDB DB = new MsSqlDB();
        DB.openConnection();
        DB.displayDbProperties();
        DB.closeConnection();
    }




    // **** SelectDataFromDB into one dimension array - call values by using array.get(x) **** // trzeba zmianiæ String na parametry
    public ArrayList<ArrayList<String>> SelectDataFromDB(String selectQuery){
        ArrayList<ArrayList<String>> rowValues = new ArrayList<ArrayList<String>>();
        try {
            openConnection();
            System.out.println("[SelectDataFromDB] Pobieram dane SQL");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            if(con!=null){
                // Iloœæ kolumn
                int numCols = rs.getMetaData().getColumnCount();
                //System.out.println("[SelectDataFromDB] Ilosc kolumn SQL:" +numCols);
                // Do momentu az SQL zwraca wiersze wykonuj
                int wiersz = 0; //wiersze
                while(rs.next()) {
                    // Add an element to each dimension, Dodaj kolejny wiersz macierzy
                    rowValues.add(new ArrayList<String>());
                    //System.out.println("Buduje "+wiersz+" wiersz macierzy");
                    //Dla kazdej kolumny wpisz dane w kolejnym polu macierzy aktualnego wiersza
                    String strng ;
                    for (int i = 1; i <= numCols; i++) {
                        strng = rs.getString(i);
                        rowValues.get(wiersz).add(strng);
                    }
                    wiersz=wiersz+1;
                }
                rs.close();
                stmt.close();
            }
        }
        catch (SQLException e) {
            System.out.println("Sql exception :" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return rowValues;
    }
}
