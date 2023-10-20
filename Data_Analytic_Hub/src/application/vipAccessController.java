package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class vipAccessController implements Initializable {
    @FXML
    private PieChart piechart;

    @FXML
    private Button backButton;
    
   
    
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "loggedIn.fxml", "LogIn", null, null, null);
				
			}
		});   	  	
 	  	
    	
        
    }

    
    
    
    
    public void setPieChartContent(int low, int med, int high) {
        piechart.getData().add(new PieChart.Data("0-99 Shares", low));
        piechart.getData().add(new PieChart.Data("100-999 Shares", med));
        piechart.getData().add(new PieChart.Data("1000+ Shares", high));
    }
}
