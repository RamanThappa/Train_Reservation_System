package Login;
import UserPackage.LoginUser;
import Admin.AdminLogin;
import UserPackage.RegisterUser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {
	// DB connection variables
		static Connection connection = null;
		static String databaseName = "trainreservation" ;
		static String url = "jdbc:mysql://localhost:3306/" +databaseName ;
		
		static String username = "root";
		static String password = "Raman@12345";
		
		public static Connection getConnection () throws SQLException, ClassNotFoundException {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username , password);
			return connection;
		}
		
		public static void Start () throws ClassNotFoundException, SQLException {
	       // Connection connection = getConnection();
			
			System.out.println("Select the login type");
			System.out.println("Press A for admin login and U for user login");
			Scanner sc = new Scanner(System.in);
			char userType = sc.next().charAt(0);
			switch (Character.toUpperCase(userType)) {
			case 'A' :
				int EnterPin;
				int pin = 70181;
				System.out.println("Please enter the pin");
				EnterPin = sc.nextInt();
				if (EnterPin == pin) {
					
					AdminLogin.display();
				}
				else {
					System.out.println("wrong pin");System.out.println("********");
					System.out.println(" ");
					Main.Start();
				}
			break;
			case 'U':
				System.out.println("Choose");
				System.out.println("1 - login existing user");
				System.out.println("2 - register new user");
				int userInput ;
				userInput = sc.nextInt();
				if (userInput == 1) {
					LoginUser.display();
				}
				else if (userInput == 2) {
					RegisterUser.display();		
					
					
				}
				else {
					System.out.println("invalid input");
					System.out.println("********");
					System.out.println(" ");
					Main.Start();
				}
				
				
			break;	
			
			default:System.out.println("invalid input");
			System.out.println("********");
			System.out.println(" ");
			Main.Start();
			}

		}
		

		public static void main(String[] args) throws ClassNotFoundException, SQLException {
			
			Start ();
		}


}