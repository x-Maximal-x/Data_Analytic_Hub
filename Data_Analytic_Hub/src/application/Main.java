package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage)throws Exception {
		try {
			Parent root = FXMLLoader.load(Main.class.getResource("/application/login.fxml"));
			primaryStage.setTitle("Data Analytics Hub");
			
			primaryStage.setScene(new Scene(root, 600,400));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
		Connection con = null;
		try {
			con = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3307/users", "root", "admin");
//			preparedStatement = con.prepareStatement("SELECT * FROM login");
//			resultSet = preparedStatement.executeQuery();
//			System.out.println(resultSet);
			if(con!=null) {
				System.out.println("COnnected");
				
			}
			
		}
		catch(Exception e) {
			System.out.println("Not connected");
		}
		launch(args);
		
	}
}
