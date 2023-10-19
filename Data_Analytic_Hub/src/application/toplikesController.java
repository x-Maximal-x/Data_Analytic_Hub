package application;


import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


public class toplikesController implements Initializable {

	
	
	
	@FXML
	private TextArea output;
	
	@FXML
	private Button backButton; 
	


	@Override
	    public void initialize(URL location, ResourceBundle resources) {
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "loggedIn.fxml", "Dashboard", null, null, null);
				
			}
		});
		  
	}
	 public void setResultContent(String content) {
	        output.setText(content);
	    }
	
}
