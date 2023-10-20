package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class settingsController implements Initializable{
	
	  @FXML
	  private Button backButton;
	  
	  @FXML
	  private  TextField username;
	  
	  @FXML
	  private TextField password;
	  
	  @FXML
	  private TextField newpassword;
	  
	  @FXML
	  private Button updateButton;
	  
	  
	  
	  
	  @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    	
	    	backButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					DBUtils.changeScene(event, "loggedIn.fxml", "LogIn", null, null, null);
					
				}
			});   	  	
	 	  	
	    	updateButton.setOnAction(new EventHandler<ActionEvent>() {
	    	    @Override
	    	    public void handle(ActionEvent event) {
	    	        Connection connection = null;
	    	        PreparedStatement preparedStatement = null;
	    	        
	    	        try {
	    	            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/users", "root", "admin");
	    	            String updatePasswordQuery = "UPDATE login SET password = ? WHERE username = ? AND password = ?";
	    	            preparedStatement = connection.prepareStatement(updatePasswordQuery);
	    	            preparedStatement.setString(1, newpassword.getText());
	    	            preparedStatement.setString(2, username.getText());
	    	            preparedStatement.setString(3, password.getText());

	    	            int rowsUpdated = preparedStatement.executeUpdate(); // Execute the update query

	    	            if (rowsUpdated > 0) {
	    	                // Password updated successfully
	    	                showAlert(AlertType.INFORMATION, "Password Updated", "Password has been updated successfully.");
	    	            } else {
	    	                // Password update failed
	    	                showAlert(AlertType.ERROR, "Password Update Failed", "Invalid username or current password.");
	    	            }
	    	        } catch (SQLException e) {
	    	            e.printStackTrace();
	    	        } finally {
	    	            try {
	    	                if (preparedStatement != null) {
	    	                    preparedStatement.close();
	    	                }
	    	                if (connection != null) {
	    	                    connection.close();
	    	                }
	    	            } catch (SQLException e) {
	    	                e.printStackTrace();
	    	            }
	    	        }
	    	    }
	    	    
	    	    private void showAlert(AlertType alertType, String title, String content) {
	 		        Alert alert = new Alert(alertType);
	 		        alert.setTitle(title);
	 		        alert.setHeaderText(null);
	 		        alert.setContentText(content);
	 		        alert.showAndWait();
	 		    }
	    	});
  
	    	
	        
	    }

}
