package application;

import java.sql.Connection;
import java.sql.DriverManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the login.fxml file using FXMLLoader
            Parent root = FXMLLoader.load(Main.class.getResource("/application/login.fxml"));
            primaryStage.setTitle("Data Analytics Hub");

            // Set the scene with the loaded FXML content
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection con = null;
        try {
            // Attempt to establish a connection to the MySQL database
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3307/users", "root", "admin");

            if (con != null) {
                System.out.println("Connected");
            }
        } catch (Exception e) {
            // Handle connection failure
            System.out.println("Not connected");
        }

        // Launch the JavaFX application
        launch(args);
    }
}
