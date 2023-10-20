package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class loginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    Window window;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Event handler for the Signup button
        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Sign-up.fxml", "SignUp!", null, null, null);
            }
        });

        // Event handler for the Login button
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                login(event, null, username.getText(), password.getText());
            }
        });
    }

    private void login(ActionEvent event, String title, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        ResultSet resultFirstLast = null;
        try {
            // Establish a database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/users", "root", "admin");
            
            // Prepare and execute a SQL query to retrieve the password for the given username
            preparedStatement = connection.prepareStatement("SELECT password FROM login WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                // User not found in the database
                System.out.println("User not found in DB");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect Credentials!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        // Password matches
                        ps = connection.prepareStatement("SELECT firstname, lastname FROM login WHERE password = ?");
                        ps.setString(1, password);
                        resultFirstLast = ps.executeQuery();
                        
                        if (resultFirstLast.next()) {

                            
                            // Redirect to the logged-in page
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("loggedIn.fxml"));
                                Parent root = loader.load();
                                LoggedInController loggedInController = loader.getController();
                                loggedInController.setUserInformation(username.toString());

                                // Show the new page
                                Stage stage = (Stage) loginButton.getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        // Password does not match
                        System.out.println("Password does not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect Password");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (result sets, statements, and the connection) to release them
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
