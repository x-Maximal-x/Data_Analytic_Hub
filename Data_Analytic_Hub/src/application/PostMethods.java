package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;



public class PostMethods {
	
	private final Scanner sc;
	
	public PostMethods(Scanner scanner) {
		this.sc = scanner;
	}

	//The method to add a post to the collection of posts
	public static void addPost(Map<Integer,Post> postMap, int id, String content, String author, int likes, int shares, String dateTime) {
//		System.out.println("Enter the post id: ");
//        int id = this.sc.nextInt();     
//        this.sc.nextLine();
//        System.out.println("Enter the post content: ");
//        String content = this.sc.nextLine();
//        System.out.println("Enter the post author: ");
//        String author = this.sc.nextLine();
//        System.out.println("Enter the post likes: ");
//        int likes = this.sc.nextInt();
//        System.out.println("Enter the post shares: ");
//        int shares = this.sc.nextInt();
//        this.sc.nextLine();
//        System.out.println("Enter the post date and time(yyyy-MM-dd HH:mm): ");
//        String dateTime = this.sc.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false); 
        
        try {
            Date parsedDate = dateFormat.parse(dateTime);
            System.out.println("Input date and time is valid: " + parsedDate);
        } catch (ParseException e) {
            System.out.println("Invalid date and time format. Please use yyyy-MM-dd HH:mm.");
        }
        Post newpost = new Post(id, content, author, likes, shares, dateTime);
        postMap.put(id, newpost);
        System.out.println("Post added");
	}
	
	//The method to Delete a post in the collection of posts
	public void DeletePost(Map<Integer,Post>postMap) {
		try {
			 	System.out.println("Enter the post id: ");
			 	int id = sc.nextInt();
				if (postMap.containsKey(id)) {
			    postMap.remove(id);
				System.out.println("Post deleted successfully");
				} else {
				System.out.println("Sorry the poost does not exist in the Collection!");
				}
		}catch(Exception e){
			System.out.println("Invalid input!");
			

			}
		 }
	
		
		//The method to  retrieve a post in the collection of posts
		public void RetrievePost(Map<Integer,Post>postMap) {
			System.out.println("Enter the post id: ");
			int id = this.sc.nextInt();
			if (postMap.containsKey(id)) {
			System.out.println(postMap.get(id));
			} else {
			    System.out.println("Sorry the post does not exist in the Collection!");
			    
			}
		}
		//The method to retrieve n posts with maximum shares in the collection of posts
		public void RetrievePostWithMaxShare(Map<Integer,Post>postMap) {
			System.out.println("Please Specify the number of posts to retrieve (N):");
			int qt = this.sc.nextInt();
			for (int i = 0; i < qt; i++) {
			    int max = 0;
			    int id = 0;
			    for (Map.Entry<Integer, Post> entry : postMap.entrySet()) {
			    	Post post = entry.getValue();
			        if (post.getShares() > max) {
			            max = entry.getValue().getShares();
			            id = entry.getValue().getId();
			        }
			    }
			    System.out.println(postMap.get(id));
			    postMap.remove(id);
			}  
		}
		//The method to retrieve n posts with maximum likes in the collection of posts	 
		public void RetrievePostWithMaxLikes(Map<Integer,Post>postMap) {
	        System.out.println("Enter the number of posts you want to retrieve: ");
	        int qt = this.sc.nextInt();
	        for (int i = 0; i < qt; i++) {
	            int max = 0;
	            int id = 0;
	            for (Map.Entry<Integer, Post> entry : postMap.entrySet()) {
	                if (entry.getValue().getLikes() > max) {
	                    max = entry.getValue().getLikes();
	                    id = entry.getValue().getId();
	                }
	            }
	            System.out.println(postMap.get(id));
	            postMap.remove(id);
	        }
		}
	
		
	}

