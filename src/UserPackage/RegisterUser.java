package UserPackage;
import Login.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class RegisterUser {
	public static void display() throws ClassNotFoundException, SQLException {
		System.out.println("Enter Your Username");
		Scanner sc = new Scanner(System.in);
		String UserName = sc.next();
		
		System.out.println("Enter Your Password");
		String Password = sc.next();
		
		System.out.println("Enter your Name");
		String Name = sc.next();
		
		System.out.println("Enter you gender");
		String Gender = sc.next();
		
		
		Connection connection = Main.getConnection();
		String sql = "INSERT INTO user (UserName , Password ,Name , Gender ) VALUES (?,?,?,?)" ;
		PreparedStatement ps = connection.prepareStatement(sql);
		
		ps.setString(1, UserName);
		ps.setString(2, Password);
		ps.setString(3, Name);
		ps.setString(4, Gender);
		
		int rowsInserted = ps.executeUpdate();
		
		if (rowsInserted >0) {
			System.out.println("New user registered");
			System.out.println(" ");
			LoginUser.display();
		}
		
	}

}
