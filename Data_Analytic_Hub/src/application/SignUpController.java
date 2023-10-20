package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private Button signupButton;

    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Event handler for the Signup button
        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!username.getText().trim().isEmpty() && !password.getText().isEmpty() && !firstname.getText().isEmpty()
                        && !lastname.getText().isEmpty()) {
                    signup(event, username.getText(), password.getText(), firstname.getText(), lastname.getText());
                } else {
                    System.out.println("Please enter the details!!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please provide all the required information");
                    alert.show();
                }
            }
        });

        // Event handler for the Login button
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", "Login!", null, null, null);
            }
        });
    }

    @SuppressWarnings("resource")
	private void signup(ActionEvent event, String tfusername, String tfpassword, String tffirstname, String tflastname) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/users", "root", "admin");

            // Check if the username already exists
            preparedStatement = connection.prepareStatement("SELECT password FROM login WHERE username = ?");
            preparedStatement.setString(1, tfusername);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Username already exists");
                alert.showAndWait();
            } else {
                // Insert a new user
                preparedStatement = connection.prepareStatement("INSERT INTO login(username, password, firstname, lastname) VALUES(?,?,?,?)");
                preparedStatement.setString(1, tfusername);
                preparedStatement.setString(2, tfpassword);
                preparedStatement.setString(3, tffirstname);
                preparedStatement.setString(4, tflastname);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("User created successfully");
                    alert.showAndWait();
                    DBUtils.changeScene(event, "login.fxml", "Login", null, null, null);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to create user");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
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
