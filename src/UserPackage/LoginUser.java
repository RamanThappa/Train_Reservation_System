package UserPackage;
import java.sql.Connection;
import Login.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// import com.mysql.cj.jdbc.result.ResultSetMetaData;

import Login.Main;

public class LoginUser {
	@SuppressWarnings("resource")
	public static void display() throws ClassNotFoundException, SQLException {
		System.out.println("Login with your credentials");
		System.out.println("Enter your UserName");
		Scanner sc = new Scanner(System.in);
		String UserNameInput = sc.next();
		
		System.out.println("Enter your Password");
		String PasswordInput = sc.next();
		Connection connection = Main.getConnection();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE UserName = ? and Password = ?" );
		ps.setString(1 , UserNameInput);
		ps.setString(2 , PasswordInput);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			System.out.println("Logged In");
			performUser(UserNameInput , PasswordInput);
		}
		else {
			System.out.println("user does not exist");
			System.out.println("********");
			System.out.println(" ");
			display();
		}
	}
	public static void performUser (String UserNameInput , String Password) throws SQLException, ClassNotFoundException {
		Scanner sc = new Scanner(System.in);
		Connection connection = Main.getConnection();
		
		
		    System.out.println("Choose the operation you want to perform");
			System.out.println("Press 1 for Book tickets");
			System.out.println("Press 2 for View bookings");
			System.out.println("Press 3 for Delete Booking");
			System.out.println("Press 4 for logout");
			int UserChoice = sc.nextInt();
			switch(UserChoice) {
			case 1 :
				PreparedStatement ps = connection.prepareStatement("SELECT * FROM train" );
				ResultSet rs = ps.executeQuery();
				System.out.println("TrainNumber"); 
				while (rs.next()) {
		            
		            int trainNumber = rs.getInt("tnum");
		            String trainName = rs.getString("tname");
		            int seats = rs.getInt("seats");
		            String date = rs.getString("date");
		            String time = rs.getString("time");
		            String startPoint = rs.getString("start");
		            String destination = rs.getString("destination");
		            
		            System.out.println(trainNumber + "\t\t" + trainName + "\t\t" + seats + "\t\t" + date + "\t\t" + time + "\t\t" + startPoint + "\t\t" + destination );
		        }
				System.out.println("Choose Train Number you want to book");
				int TrainNoInput = sc.nextInt();
				// if else for seat no.
			    ps = connection.prepareStatement("SELECT seats from trainreservation.train WHERE tnum = ?" );
			    ps.setInt(1,TrainNoInput);
				ResultSet rs1 = ps.executeQuery();
				
				
				
			int seat = 0;
				if (rs1.next()) {
			        seat = rs1.getInt("seats");
				}
				ps = connection.prepareStatement("INSERT INTO bookings (UserName , tnum , sno) VALUES (? ,? ,?)");
				ps.setString(1 , UserNameInput);
				ps.setInt(2 , TrainNoInput);
				ps.setInt(3, seat );
				ps.executeUpdate();
				ps = connection.prepareStatement("UPDATE train SET seats = ? WHERE tnum = ?");
				ps.setInt(1,seat - 1);
				ps.setInt(2,TrainNoInput);
				ps.executeUpdate();
				System.out.println("Ticket booked successfully");
				System.out.println("**********");
				System.out.println(" ");
				performUser(UserNameInput , Password);
			
			break;	
			case 2 :
				ps = connection.prepareStatement("SELECT * FROM bookings WHERE UserName = ?");
				ps.setString(1, UserNameInput);
				ResultSet rs2 = ps.executeQuery();
				while(rs2.next()) {
					String Uname = rs2.getString("UserName");
					int TrainNo = rs2.getInt("tnum");
					int seatno = rs2.getInt("sno");
					System.out.println("User" + "\t\t" + "Train no" + "\t\t" + "Seat no");
					System.out.println(Uname + "\t\t" + TrainNo + "\t\t" + seatno);
				}
				System.out.println("*********");
				System.out.println(" ");
				performUser(UserNameInput , Password);
		
				
			break;
			case 3 :
				
				ps = connection.prepareStatement("SELECT * FROM bookings WHERE UserName = ?");
				ps.setString(1, UserNameInput);
				ResultSet rs3 = ps.executeQuery();
				while(rs3.next()) {
					String Uname = rs3.getString("UserName");
					int TrainNo = rs3.getInt("tnum");
					int seatno = rs3.getInt("sno");
					System.out.println(Uname + "\t\t" + TrainNo + "\t\t" + seatno);
				}
				System.out.println("Choose the Train no. and Seat no. you want to delete");
				System.out.println("Enter train no");
				int trainDelete = sc.nextInt();
				System.out.println("Enter seat no");
				int seatDelete = sc.nextInt();
				ps = connection.prepareStatement("DELETE FROM bookings WHERE UserName = ? AND tnum = ? AND sno = ?");
				ps.setString(1,UserNameInput);
				ps.setInt(2,trainDelete);
				ps.setInt(3, seatDelete);
				int rowsInserted = ps.executeUpdate();
				if (rowsInserted > 0) {
					ps = connection.prepareStatement("SELECT seats FROM train WHERE tnum = ?");
				    ps.setInt(1,trainDelete);
					ResultSet rs4 = ps.executeQuery();
					int seatU =0;
					while (rs4.next()){
						 seatU = rs4.getInt(1);
					}
					
							
					ps = connection.prepareStatement("UPDATE train SET seats = ? WHERE tnum = ?");
					ps.setInt(1,seatU+1 );
					ps.setInt(2, trainDelete);
					ps.executeUpdate();
					System.out.println("Booking has been deleted");
				}
				System.out.println("*********");
				System.out.println(" ");
				performUser(UserNameInput , Password);
				 
				
			break;
			
			case 4:
				System.out.println("********");
				System.out.println(" ");
				Main.Start();
			break;	
			
			default:System.out.println("invalid input");
			System.out.println("********");
			System.out.println(" ");
			performUser(UserNameInput , Password);
			}
		
		
		
	}

}
