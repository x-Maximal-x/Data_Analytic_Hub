package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

//import Application.PostMethods;
//import application.Post;

public class LoggedInController implements Initializable {
	
	
	Map<Integer, Post> postMap = loadFile("posts.csv");
    // Load the posts from the csv file
    public static Map<Integer, Post> loadFile(String csvFile) {
    	
        Map<Integer, Post> postMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    int id = Integer.parseInt(data[0].trim());
                    String content = data[1].trim();
                    String author = data[2].trim();
                    int likes = Integer.parseInt(data[3].trim());
                    int shares = Integer.parseInt(data[4].trim());
                    String dateTime = data[5].trim();

                    Post post = new Post(id,content,author,likes,shares,dateTime);
                    postMap.put(id, post);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return postMap;

    }

    @FXML
    private Button logoutButton;

    @FXML
    private Label welcomeLabel;
    
    @FXML
    private TextField postId;
    
    @FXML
    private TextField postContent;
    
    @FXML
    private TextField postAuthor;
    
    @FXML
    private TextField postLikes;
    
    @FXML
    private TextField postShares;
    
    @FXML
    private TextField postDate;
    
    @FXML
    private Button postButton;
    
    
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "login.fxml", "LogIn", null, null, null);
				
			}
		});
    	
    	postButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//DBUtils.changeScene(event, "login.fxml", "LogIn", null, null, null);
				//PostMethods add = new PostMethods(); 
			       //add.addPost(postMap);
			}
		});
    }

    public void setUserInformation(String firstname, String lastname) {
        welcomeLabel.setText("Welcome " + firstname + lastname+ "!");
    }

}
