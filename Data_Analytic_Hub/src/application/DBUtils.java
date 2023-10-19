package application;

import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class DBUtils {

 

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String firstname, String lastname) {
        Parent root = null;
    			
        System.out.println(firstname + lastname);
        if (firstname != null && lastname != null) {
            try {
            	FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
               
                root = loader.load();
//                LoggedInController loggedincontroller = loader.getController();
//                loggedincontroller.setUserInformation(firstname, lastname);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    
        }
    }
