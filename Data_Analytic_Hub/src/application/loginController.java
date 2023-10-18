package application;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.stage.Window;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
    	
		signupButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event,"Sign-up.fxml", "SignUp!", null, null, null);
			}
		});
		
		 loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				login(event, null, username.getText(),password.getText());
			}
			
		 });
    	}

			private  void login(ActionEvent event, String title, String username, String password) {
				
				Connection connection = null;
				PreparedStatement preparedStatement = null;
				PreparedStatement ps = null;
				ResultSet resultSet = null;
				ResultSet resultFirstLast = null;
				try {
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/users", "root",
							"admin");
					preparedStatement = connection.prepareStatement("SELECT password FROM login WHERE username = ?");
					preparedStatement.setString(1, username);
					resultSet = preparedStatement.executeQuery();
					//System.out.println(resultSet);
					
					if (!resultSet.isBeforeFirst()) {
						System.out.println("User not found in DB");
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Incorrect Credentials!");
						alert.show();
					
					} else {
						while (resultSet.next()) {
							String retrievedPassword = resultSet.getString("password");
							if (retrievedPassword.equals(password)) {
								ps = connection.prepareStatement("SELECT firstname, lastname FROM login WHERE password = ?");
								ps.setString(1, password);
								resultFirstLast = ps.executeQuery();
								if(resultFirstLast.next()) {
//									String firstname = resultFirstLast.getString("firstname");
//									String lastname = resultFirstLast.getString("lastname");
									DBUtils.changeScene(event, "loggedIn.fxml","Welcome to Dashboard", null , null , null);
									
								} 
								

							} else {
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
	




