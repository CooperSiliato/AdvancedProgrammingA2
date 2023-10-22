package main;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * The Analyser class provides the functionality needed to manage orders and checkout.
 */
public class Analyser {

    private String name;
    private PostManager postManager;

    public Analyser(String name) {
        this.name = name;
        this.setPostManager(new PostManager());
        try {
			getPostManager().loadPostsFromFile("csvfiles/posts.csv");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }


	/**
     * The menu to operate the analyser
     */
    public void run() {
    	boolean exit = false;
    	do {
			printMenu(this.name);

			String stringInput = readUserInput();

			// Check the user input and continue with the next iteration
			// if no input is provided
			if (stringInput.isEmpty()) {
				System.out.println("Please select a valid menu option.");
				continue;
			}

			char input = stringInput.charAt(0);

			switch (input) {
				case '1':
					this.addPost();
					break;
				case '2':
					this.deletePost();
					break;
				case '3':
					this.retrievePost();
					break;
				case '4':
					this.retrieveLikedPost();
					break;
				case '5':
					this.retrieveSharedPost();
					break;
				case '6':
					exit = true;
					break;
				default:
					System.out.println("Please select a valid menu option.");
					break;
			}
		} while (!exit);
    }

	/**
     * The utility method to print menu options.
     */
	public static void printMenu(String name){
		String banner = new String(new char[50]).replace('\u0000', '-');
		System.out.println("\n" + "Welcome to " + name);
		System.out.println(banner + "\n"  + "> Select from main menu" + "\n" + banner);
		System.out.printf("   %s%n", "1) Add a social media post");
		System.out.printf("   %s%n", "2) Delete an existing social media post");
		System.out.printf("   %s%n", "3) Retrieve a social media post");
		System.out.printf("   %s%n", "4) Retrieve the top N posts with most likes");
		System.out.printf("   %s%n", "5) Retrieve the top N posts with most shares");
		System.out.printf("   %s%n", "6) Exit");
		System.out.print("Please select: ");
	}

	/**
     * The utility method to read user input.
     */
    public static String readUserInput() {
	    Scanner sc = new Scanner(System.in);
	    return sc.nextLine();
	}

    /**
     * The method to place orders.
     */
    public void addPost() {
        System.out.println("-- Add a post --");

        int postID = -1;
        while (postID == -1) {
            System.out.println("Enter post ID: ");
            try {
                postID = Integer.parseInt(readUserInput());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid post ID.");
            }
        }

        System.out.println("Enter post content: ");
        String postContent = readUserInput();
        System.out.println("Enter post author: ");
        String postAuthor = readUserInput();

        int postLikes = -1;
        while (postLikes == -1) {
            System.out.println("Enter number of post likes: ");
            try {
                postLikes = Integer.parseInt(readUserInput());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number of likes.");
            }
        }

        int postShares = -1;
        while (postShares == -1) {
            System.out.println("Enter number of post shares: ");
            try {
                postShares = Integer.parseInt(readUserInput());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number of shares.");
            }
        }

        System.out.println("Enter date and time of post format DD/MM/YYYY HH:MM: ");
        String postDateTime = readUserInput();
        String postOwner = readUserInput();

        Post post = new Post(postID, postContent, postAuthor, postLikes, postShares, postDateTime, postOwner);

        getPostManager().addPost(post);
        System.out.println("Post added successfully.");
    }


    /**
     * The method to manage checkout.
     */
    public void deletePost() {
    	System.out.println("-- Delete a post --\nEnter post ID: ");
    	int postID = Integer.parseInt(readUserInput());

        if (getPostManager().deletePost(postID)) {
            System.out.println("Post with ID " + postID + " has been deleted.");
        } else {
            System.out.println("Post with ID " + postID + " not found.");
        }
    }

    public void retrievePost() {
    	System.out.println("-- Retrieve a post --\nEnter post ID: ");
        int postID = Integer.parseInt(readUserInput());

        Post retrievedPost = getPostManager().retrievePost(postID);

        if (retrievedPost != null) {
            System.out.println("Post ID: " + retrievedPost.getId());
            System.out.println("Content: " + retrievedPost.getContent());
            System.out.println("Author: " + retrievedPost.getAuthor());
            System.out.println("Likes: " + retrievedPost.getLikes());
            System.out.println("Shares: " + retrievedPost.getShares());
            System.out.println("Date and Time: " + retrievedPost.getDateTime());
        } else {
            System.out.println("Post with ID " + postID + " not found.");
        }
    }

    public void retrieveLikedPost() {
    	System.out.println("-- Retrieve the top N posts with most likes --\nPlease specify the number of posts to retrieve: ");

    	int n = 10;

        Map<Integer, Post> topLikedPosts = getPostManager().getTopNPostsByLikes(n);

        if (!topLikedPosts.isEmpty()) {
            System.out.println("The top-" + n + " posts with the most likes are:");
            int rank = 1;
            for (Post post : topLikedPosts.values()) {
                System.out.println(rank + ") " + post.getId() + " | " + post.getContent() + " | " + post.getLikes());
                rank++;
            }
        } else {
            System.out.println("No posts available.");
        }
    }

    public void retrieveSharedPost() {
    	System.out.println("-- Retrieve the top N posts with most likes --\nPlease specify the number of posts to retrieve: ");

    	int n = Integer.parseInt(readUserInput());

        Map<Integer, Post> topSharedPosts = getPostManager().getTopNPostsByShares(n);

        if (!topSharedPosts.isEmpty()) {
            System.out.println("The top-" + n + " posts with the most shares are:");
            int rank = 1;
            for (Post post : topSharedPosts.values()) {
                System.out.println(rank + ") " + post.getId() + " | " + post.getContent() + " | " + post.getShares());
                rank++;
            }
        } else {
            System.out.println("No posts available.");
        }
    }


	public PostManager getPostManager() {
		return postManager;
	}


	public void setPostManager(PostManager postManager) {
		this.postManager = postManager;
	}

}
