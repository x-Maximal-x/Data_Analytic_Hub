package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;





public class LoggedInController implements Initializable {
	
	//private String currentUser;
	
	
	
	
	
	Map<Integer, Post> postMap = loadFile("posts.csv");
	
    // Load the posts from the csv file
	public static Map<Integer, Post> loadFile(String csvFilePath) {
        Map<Integer, Post> postMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
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

                    Post post = new Post(id, content, author, likes, shares, dateTime);
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
    
    @FXML
    private Button findButton;
    
    @FXML
    private TextField numberOf;
    
    @FXML
    private TextField deleteId;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button exportButton;
    
    @FXML
    private TextField exportId;
    
    
    @FXML
    private TextField getpostId;
    
    @FXML
    private  Button getpostButton;
    
    @FXML
    private  Button piechartviewButton;
    
    @FXML
    private  Button updradeButton;
    
    @FXML
    private  Button importButton;
    
    @FXML
    private  Button settingsButton;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	


    	logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "login.fxml", "LogIn", null, null, null);
				
			}
		});
    	
    	
    	settingsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "settings.fxml", "Settings", null, null, null);
				
			}
		});
    	
    	 importButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 FileChooser fileChooser = new FileChooser();
                 fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
                 File selectedFile = fileChooser.showOpenDialog(null);

                 if (selectedFile != null) {
                     Map<Integer, Post> importedPosts = loadFile(selectedFile.getAbsolutePath());

                     // Add the imported posts to the existing postMap
                     postMap.putAll(importedPosts);

                     showAlert(AlertType.INFORMATION, "Import Successful", "Data from the selected CSV file has been imported.");
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
    	 
    	
    	
    
    	piechartviewButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent event) {
    	        
    	            int low = 0;
    	            int med = 0;
    	            int high = 0;
    	            
    	            // Iterate through the postMap to categorize shares
    	            for (Post post : postMap.values()) {
    	                int shares = post.getShares();
    	                if (shares >= 1000) {
    	                    high++;
    	                } else if (shares >= 100) {
    	                    med++;
    	                } else {
    	                    low++;
    	                }
    	            }
    	            
    	            try {
    	                FXMLLoader loader = new FXMLLoader(getClass().getResource("vipAccess.fxml"));
    	                Parent root = loader.load();
    	                
    	                // Get the controller instance
    	                vipAccessController vipaccesscontroller = loader.getController();
    	                
    	                // Call the method on the controller
    	                vipaccesscontroller.setPieChartContent(low, med, high);

    	                Scene scene = new Scene(root);
    	                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	                currentStage.setScene(scene);
    	                currentStage.setTitle("VIP ACCESS");
    	                currentStage.show();
    	            } catch (IOException e) {
    	                e.printStackTrace();
    	            }
    	        }
    	    });

    	
    	
    	
    	getpostButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent event) {
    	        try {
    	            int id = Integer.parseInt(getpostId.getText());
    	            if (postMap.containsKey(id)) {
    	                Object postContent = postMap.get(id);
    	                showAlert(AlertType.INFORMATION, "Post Content", postContent.toString());
    	            } else {
    	                showAlert(AlertType.ERROR, "Post Not Found", "Sorry, the post does not exist in the Collection!");
    	            }
    	        } catch (NumberFormatException e) {
    	            showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid post ID.");
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

    	
    
    	
    	

		postButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        try {
		            int id = Integer.parseInt(postId.getText());
		            int likes = Integer.parseInt(postLikes.getText());
		            int shares = Integer.parseInt(postShares.getText());
		            String content = postContent.getText();
		            String author = postAuthor.getText();
		            String dateTime = postDate.getText();
		
		            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		            dateFormat.setLenient(false);
		
		            try {
		                Date parsedDate = dateFormat.parse(dateTime);
		                String result = "Input date and time is valid: " + parsedDate;
		
		                // Create and add the new post to the map
		                Post newPost = new Post(id, content, author, likes, shares, dateTime);
		                postMap.put(id, newPost);
		
		                result += "\nPost added: ID=" + id + ", Content=" + content + ", Author=" + author + ", Likes=" + likes + ", Shares=" + shares + ", Date/Time=" + dateTime;
		
		                showAlert(AlertType.INFORMATION, "Post Added", result);
		            } catch (ParseException e) {
		                showAlert(AlertType.ERROR, "Invalid Date/Time", "Invalid date and time format. Please use yyyy-MM-dd HH:mm.");
		            }
		        } catch (NumberFormatException e) {
		            showAlert(AlertType.ERROR, "Invalid Input", "Please enter valid numeric values for ID, Likes, and Shares.");
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
    	
    	
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        try {
		            int id = Integer.parseInt(deleteId.getText());
		            if (postMap.containsKey(id)) {
		                postMap.remove(id);
		                showAlert(AlertType.INFORMATION, "Post Deleted", "Post with ID " + id + " has been deleted successfully.");
		            } else {
		                showAlert(AlertType.ERROR, "Post Not Found", "Post with ID " + id + " does not exist in the Collection.");
		            }
		        } catch (NumberFormatException e) {
		            showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid post ID.");
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


    	
		exportButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        try {
		            int postIdToExport = Integer.parseInt(exportId.getText());
		            Post postToExport = postMap.get(postIdToExport);

		            if (postToExport != null) {
		                FileChooser fileChooser = new FileChooser();
		                fileChooser.setTitle("Save Post as CSV");
		                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

		                // Show the save file dialog and get the selected file
		                File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

		                if (file != null) {
		                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
		                        String csvData = String.format("%d,%s,%s,%d,%d,%s%n",
		                                postToExport.getId(), postToExport.getContent(),
		                                postToExport.getAuthor(), postToExport.getLikes(),
		                                postToExport.getShares(), postToExport.getDateTime());

		                        writer.write(csvData);

		                        String successMessage = "Post with ID " + postIdToExport + " has been exported to " + file.getAbsolutePath();
		                        
		                      
		                        showAlert(AlertType.INFORMATION, "Export Successful", successMessage);
		                    } catch (IOException e) {
		                        System.err.println("Error writing to the file: " + e.getMessage());
		                    }
		                } else {
		                    System.out.println("Export canceled by the user.");
		                }
		            } else {
		                String errorMessage = "Post with ID " + postIdToExport + " not found.";
		                
		               
		                showAlert(AlertType.ERROR, "Export Failed", errorMessage);
		            }
		        } catch (NumberFormatException e) {
		            String errorMessage = "Invalid input in the Post ID field.";
		            
		          
		            showAlert(AlertType.ERROR, "Export Failed", errorMessage);
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

    	
   
    	
    	
    	findButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent event) {
    	    
    	    	
    	    	
    	        try {
    	            int n = Integer.parseInt(numberOf.getText());
    	            StringBuilder Content = new StringBuilder();
    	            for (int i = 0; i < n; i++) {
    	                int max = 0;
    	                int id = 0;
    	                for (Map.Entry<Integer, Post> entry : postMap.entrySet()) {
    	                    if (entry.getValue().getLikes() > max) {
    	                        max = entry.getValue().getLikes();
    	                        id = entry.getValue().getId();
    	                    }
    	                }
    	                Content.append(postMap.get(id)).append("\n\n");
    	                postMap.remove(id);
    	            }
 
    	            try {
    	                FXMLLoader loader = new FXMLLoader(getClass().getResource("toplikes.fxml"));
    	                Parent root = loader.load();
    	            
    	            toplikesController toplikescontroller = loader.getController();
    	            toplikescontroller.setResultContent(Content.toString());

    	            // Show the new page
    	            Scene scene = new Scene(root);
    	            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	            currentStage.setScene(scene);
    	            currentStage.setTitle("Top Likes");
    	            currentStage.show();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	        } catch (NumberFormatException e) {
    	            
    	            System.err.println("Invalid input in numberOf: " + numberOf.getText());
    	        }
    	    }
    	});


    }

    public void setUserInformation(String username) {
        welcomeLabel.setText("Welcome " + username + "!");
    }
   
    

    
    
}
