package Admin;
import Login.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminLogin {
	public static void performOperations(int AdminChoice) throws ClassNotFoundException, SQLException {
		Connection connection = Main.getConnection();
		Scanner sc = new Scanner(System.in);
		switch(AdminChoice) {
		case 1:
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM bookings");
			ResultSet rs = ps.executeQuery();
			
			System.out.println("these are the booked tickets");
			System.out.println("User" + "\t\t" + "Train no" + "\t\t" + "Seat no");
			while (rs.next()) {
				String Uname = rs.getString("UserName");
				int TrainNo = rs.getInt("tnum");
				int seatno = rs.getInt("sno");
				//System.out.println("UserName"+ "\t" + "Train Number" + "\t" + "Seatno.");
				
				System.out.println(Uname + "\t\t" + TrainNo + "\t\t" + seatno);
				System.out.println(" ");
			}
			display();
		break;	
		case 3:
			System.out.println("Enter the details about the train you want to add");
			System.out.println("Enter train number");
			int trainNoNew = sc.nextInt();
			
			sc.nextLine();
			System.out.println("Enter train name");
			String trainNameNew = sc.nextLine();
			
			System.out.println("Enter total seats");
			int seatsNew = sc.nextInt();
			
			System.out.println("Enter date of departure in YYYY-MM-DD format");
			String dateNew = sc.next();
			
			System.out.println("Enter time of departure in HH:mm:ss format ");
			String timeNew = sc.next();
			
			System.out.println("Enter the starting location of train");
			String startNew = sc.next();
			
			System.out.println("Enter Destination of the train");
			String destinationNew = sc.next();
			
			String sql = "INSERT INTO train (tnum , tname ,seats , date , time , start , destination ) VALUES (?,?,?,?,?,?,?)" ;
			ps = connection.prepareStatement(sql);
			
			ps.setInt(1,trainNoNew);
			ps.setString(2, trainNameNew);
			ps.setInt(3,seatsNew);
			ps.setString(4, dateNew);
			ps.setString(5, timeNew);
			ps.setString(6, startNew);
			ps.setString(7, destinationNew);
			
			int rowsInserted = ps.executeUpdate();
			if (rowsInserted >0) {
				System.out.println("new train updated");
				System.out.println(" ");
			}
			display();
			
			
		break;	
		case 2:
			ps = connection.prepareStatement("SELECT * FROM bookings ");
			ResultSet rs2 = ps.executeQuery();
			while(rs2.next()) {
				String Uname = rs2.getString("UserName");
				int TrainNo = rs2.getInt("tnum");
				int seatno = rs2.getInt("sno");
				System.out.println("User" + "\t\t" + "TrainNo" + "\t\t" + "SeatNo" );
				System.out.println(Uname + "\t\t" + TrainNo + "\t\t" + seatno);
			}
			System.out.println("Choose the User Name ,  Train no. and Seat no. of booking you want to delete");
			String userDelete = sc.next();
			int trainDelete = sc.nextInt();
			int seatDelete = sc.nextInt();
			ps = connection.prepareStatement("DELETE FROM bookings WHERE UserName = ? AND tnum = ? AND sno = ?");
			ps.setString(1,userDelete);
			ps.setInt(2,trainDelete);
			ps.setInt(3, seatDelete);
			int rowsInserted1 = ps.executeUpdate();
			if (rowsInserted1 > 0) {
				ps = connection.prepareStatement("SELECT seats FROM train WHERE tnum = ?");
			    ps.setInt(1,trainDelete);
				ResultSet rs3 = ps.executeQuery();
				int seatU =0;
				while (rs3.next()){
					 seatU = rs3.getInt(1);
				}
				ps = connection.prepareStatement("UPDATE train SET seats = ? WHERE tnum = ?");
				ps.setInt(1,seatU+1 );
				ps.setInt(2, trainDelete);
				ps.executeUpdate();
				System.out.println("Booking has been deleted");
				System.out.println(" ");
			}
			display();
			
			
		break;	
		
		case 4:
			Main.Start();
		break;	
		
		default:System.out.println("invalid input");
		System.out.println("********");
		System.out.println(" ");
		AdminLogin.display();
		}

	}

	public static void display() throws ClassNotFoundException, SQLException {
		Connection connection = Main.getConnection();
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome Admin");
		System.out.println("Choose 1 to view all ticket bookings , 2 to delete ticket bookings , 3 to add a train , 4 to logout");
		int AdminChoice = sc.nextInt();
		performOperations (AdminChoice);
		
		
		
		
		// Connection connection = Main.getConnection();
		// String sql = "INSERT INTO user (UserName , Password ,Name , gender ) VALUES (?,?,?,?)" ;
		// PreparedStatement ps = connection.prepareStatement(sql);
		
		
		
				
	}

}
