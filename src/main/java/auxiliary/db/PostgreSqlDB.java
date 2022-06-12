package auxiliary.db;


import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import auxiliary.DefinedTimeStamp;

public class PostgreSqlDB {

	public java.sql.Connection con = null;

	
	// **ConnectionUrl Definition. Private for PostgreSQL class**
	    private String connectionUrl(){
	    	System.out.println("*** Calling [PostgreSql][connectionUrl()]");
	    	final String url = "jdbc:postgresql://";
	        final String serverName= "127.0.0.1";
	        final String databaseName= "whois";     
	        return url+serverName+"/"+databaseName;
	    	}


	    
	// **openConnection. Private for PostgreSQL class**
	    public java.sql.Connection openConnection(){
	    	System.out.println("*** Calling [PostgreSQL][openConnection()]");
	    	try{
		        final String user= "postgres";
		        final String password= "wosp14";  
	    		con = DriverManager.getConnection(connectionUrl(), user, password);	    	    
	    	    if(con!=null) 
	    	    	System.out.println(DefinedTimeStamp.currentUtcTimeDate()+" [PostgreSql][openConnection()] Database Connection Successful.");
	    		}
	    	catch(Exception e){
	             e.printStackTrace();
	             System.out.println(DefinedTimeStamp.currentUtcTimeDate()+" [PostgreSql][openConnection()] Error Trace in openConnection() : " + e.getMessage());
	             System.out.println(DefinedTimeStamp.currentUtcTimeDate()+" [PostgreSql][openConnection()] Database Connection Failed!");
	    		}
	        return con;
	    	}   

	    
	    
	// **closeConnection. Private for PostgreSQL class**
	    public void closeConnection(){
	    	System.out.println("*** Calling [PostgreSql][closeConnection()]");
	        try{
	        	if(con!=null)
	                  con.close();
	        	con=null;
	            System.out.println(DefinedTimeStamp.currentUtcTimeDate()+" [PostgreSql][closeConnection] Database Connection Closed.");
	        	}
	        catch(Exception e){
	             e.printStackTrace();
	             System.out.println(DefinedTimeStamp.currentUtcTimeDate()+" [PostgreSql][closeConnection] Failed to Close DataBase Connection!");
	             }
	        }	 
	    

	    
	// **Display the driver properties, database details**
	    public void displayDbProperties(){
	    	System.out.println("*** Calling [PostgreSql][displayDbProperties()]");
	    	java.sql.DatabaseMetaData dm = null;
	    	java.sql.ResultSet rs = null;
	    	try{
	    		if(con!=null){
	    			dm = con.getMetaData();
	    			System.out.println("---------------------------------------------");
	    			System.out.println("Driver Information");
	    			System.out.println("\tDriver Name: "+ dm.getDriverName());
	    			System.out.println("\tDriver Version: "+ dm.getDriverVersion ());
	    			System.out.println("Database Information ");
	    			System.out.println("\tDatabase Name: "+ dm.getDatabaseProductName());
	    			System.out.println("\tDatabase Version: "+ dm.getDatabaseProductVersion());
	    			System.out.println("Avalilable Catalogs ");
	    			rs = dm.getCatalogs();
	    			while(rs.next()){
	    				System.out.println("\tcatalog: "+ rs.getString(1));
	              		} 
	    			System.out.println("---------------------------------------------");
	    			rs.close();
	    			rs = null;
	        		}
	    		else System.out.println(DefinedTimeStamp.currentUtcTimeDate()+" [PostgreSql][displayDbProperties] Error: No active Connection");
	    		}
	    	catch(Exception e){
	    		e.printStackTrace();
	    		}
	    	dm=null;
	    	}

	    
	    
	// **** Test connection, display properties, database details ****   
		public void dbConnectionTest(){
			System.out.println("*** Calling [PostgreSql][dbConnectionTest()]");
			PostgreSqlDB DB = new PostgreSqlDB();
			DB.openConnection();
			DB.displayDbProperties();
			DB.closeConnection();
		}    
	
	    
	// **** SelectDataFromDB into one dimension array - call values by using array.get(x) **** // trzeba zmianic String na parametry
		public ArrayList<ArrayList<String>> selectDataFromDb(String selectQuery,Connection con){
			System.out.println("*** Calling [PostgreSql][selectDataFromDb]");
			ArrayList<ArrayList<String>> rowValues = new ArrayList<ArrayList<String>>();
			try {
				Statement stmt = con.createStatement();
	            ResultSet rs = stmt.executeQuery(selectQuery);
	            if(con!=null){
	             	// Ilosc kolumn
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
	        	System.out.println(DefinedTimeStamp.currentUtcTimeDate()+" [PostgreSql][selectDataFromDb] SQL exception :" + e.getMessage());
	            e.printStackTrace();
	        	}
	        finally {    	
	        	}
	        return rowValues;
	    	}

		public ArrayList<String> selectDataFromDbOneColumnOnly(String selectQuery,Connection con){
			System.out.println("*** Calling [PostgreSql][selectDataFromDbOneColumnOnly]");
			ArrayList<String> ColumnValues = new ArrayList<String>();
			try {
				//System.out.println(TimeStamp.currentUtcTimeDate()+" [PostgreSql][selectDataFromDbOneColumnOnly] Query : "+selectQuery);
				Statement stmt = con.createStatement();
	            ResultSet rs = stmt.executeQuery(selectQuery);
	            if(con!=null){
	             	// Do momentu az SQL zwraca wiersze wykonuj
	             	int kolumna = 1; //kolumna
	             	int wiersz = 0; //wiersze
	             	while(rs.next()) {
	             		//System.out.println("Buduje "+wiersz+" wiersz macierzy");          	
	             		//Dla kazdej kolumny wpisz dane w kolejnym polu macierzy aktualnego wiersza
	             		String strng ;
	                    strng = rs.getString(kolumna); // poniewaz jedna kolumna zawsze bedzie 0
	                    //System.out.println("Odczytuje dane z wiersza "+wiersz+". Dane: "+strng);
	                    ColumnValues.add(strng);
	                    wiersz++;
	                 	}
	             	rs.close();
	             	stmt.close();
	            	}     
	        	} 
			catch (SQLException e) {
	        	System.out.println(DefinedTimeStamp.currentUtcTimeDate()+" [PostgreSql][selectDataFromDbOneColumnOnly] SQL exception :" + e.getMessage());
	            e.printStackTrace();
	        	}
	        finally {    	
	        	}
	        return ColumnValues;
	    	}
		
}
