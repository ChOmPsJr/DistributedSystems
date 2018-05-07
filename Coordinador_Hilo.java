import java.util.*;
import java.sql.*;

public class Coordinador_Hilo implements Runnable{

	Clock_RMI stubA;
	Clock_RMI stubB;
	Clock_RMI stubC;
	int hPrev;
	int cont;
	
	Coordinador_Hilo(Clock_RMI stub1, Clock_RMI stub2, Clock_RMI stub3){
		stubA = stub1;
		stubB = stub2;
		stubC = stub3;
		hPrev = 0;
		cont = 0;
	}
	
	public void run(){
		while(true){
			try{
				Thread.sleep(5000);
			}catch(InterruptedException e){
				return;
			}
			
			try {
				
				int hour1 = stubA.getSeconds_RMI();
				int hour2 = stubB.getSeconds_RMI();
				int hour3 = stubC.getSeconds_RMI();
				
				int promedio = (hour1 + hour2 + hour3) / 3;
				
				System.out.println(hour1 + " " + hour2 + " " + hour3);
				System.out.println("Promedio: " + promedio);
				
				Class.forName("com.mysql.cj.jdbc.Driver"); 
				try (
					 // Step 1: Allocate a database 'Connection' object
					 Connection conn = DriverManager.getConnection(
						   "jdbc:mysql://localhost:3306/SD1?useSSL=false&serverTimezone=UTC", "root", "root");
						   // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
			 
					 // Step 2: Allocate a 'Statement' object in the Connection
					 Statement stmt = conn.createStatement();
				  ) {
					 // Step 3: Execute a SQL SELECT query, the query result
					 //  is returned in a 'ResultSet' object.
					 String strInsert = "insert into HoraCentral (hPrev, hRef) values(" + hPrev + ", " + promedio + ")";
					 //System.out.println("The SQL query is: " + strSelect); // Echo For debugging
					 //System.out.println();
			 
					 PreparedStatement preparedStmt = conn.prepareStatement(strInsert);

					  // execute the preparedstatement
					  preparedStmt.execute();
						
				  } catch(SQLException ex) {
					 ex.printStackTrace();
				  }
				  
				 cont++;
				
				int nuevaH1;
				int ralen1;
				if (hour1 <= promedio){
					stubA.setSpeed_RMI(1000);
					stubA.setSeconds_RMI(promedio);
					ralen1 = 0;
					nuevaH1 = promedio;
				}else{
					stubA.setSpeed_RMI(1100);
					ralen1 = 100;
					nuevaH1 = hour1;
				}
					try (
						 Connection conn = DriverManager.getConnection(
							   "jdbc:mysql://localhost:3306/SD1?useSSL=false&serverTimezone=UTC", "root", "root");
						) {
						 String strInsert = "insert into HoraCentral (IDhSincr, IDEquipo, hEquipo, aEquipo, ralentizar) values(" 
						 + cont	 
						 + ", " 
						 + 1 
						 + ", " 
						 + hour1
						 + ", " 
						 + nuevaH1
						 + ", " 
						 + ralen1
						 + ")";
						 PreparedStatement preparedStmt = conn.prepareStatement(strInsert);

						  preparedStmt.execute();
							
					  } catch(SQLException ex) {
						 ex.printStackTrace();
					  }
				
				
				int nuevaH2;
				int ralen2;
				if (hour2 <= promedio){
					stubB.setSpeed_RMI(1000);
					stubB.setSeconds_RMI(promedio);
					nuevaH2 = promedio;
					ralen2 = 0;
				}else{
					stubB.setSpeed_RMI(1100);
					nuevaH2 = hour2;
					ralen2 = 100;
				}
				
					try (
						 Connection conn = DriverManager.getConnection(
							   "jdbc:mysql://localhost:3306/SD1?useSSL=false&serverTimezone=UTC", "root", "root");
						) {
						 String strInsert = "insert into HoraCentral (IDhSincr, IDEquipo, hEquipo, aEquipo, ralentizar) values(" 
						 + cont	 
						 + ", " 
						 + 2 
						 + ", " 
						 + hour2
						 + ", " 
						 + nuevaH2
						 + ", " 
						 + ralen2
						 + ")";
						 PreparedStatement preparedStmt = conn.prepareStatement(strInsert);

						  preparedStmt.execute();
							
					  } catch(SQLException ex) {
						 ex.printStackTrace();
					  }
				
				
				int nuevaH3;
				int ralen3;
				if (hour3 <= promedio){
					stubC.setSpeed_RMI(1000);
					stubC.setSeconds_RMI(promedio);
					nuevaH3 = promedio;
					ralen3 = 0;
				}else{
					stubC.setSpeed_RMI(1100);
					nuevaH3 = hour3;
					ralen3 = 100;
				}			
			
					try (
						 Connection conn = DriverManager.getConnection(
							   "jdbc:mysql://localhost:3306/SD1?useSSL=false&serverTimezone=UTC", "root", "root");
						) {
						 String strInsert = "insert into HoraCentral (IDhSincr, IDEquipo, hEquipo, aEquipo, ralentizar) values(" 
						 + cont	 
						 + ", " 
						 + 3 
						 + ", " 
						 + hour3
						 + ", " 
						 + nuevaH3
						 + ", " 
						 + ralen3
						 + ")";
						 PreparedStatement preparedStmt = conn.prepareStatement(strInsert);

						  preparedStmt.execute();
							
					  } catch(SQLException ex) {
						 ex.printStackTrace();
					  }
				
				hPrev = promedio;
			
			} catch (Exception e) {
				System.err.println("Client exception: " + e.toString());
				e.printStackTrace();
			}
			
			
		}
	}

}