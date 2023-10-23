package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PostManager {


	public Map<Integer, Post> postsMap;

    public PostManager() {
        postsMap = new HashMap<>();

    }

    public void addPost(Post post) {
        postsMap.put(post.getId(), post);
    }

    public boolean deletePost(int postId) {
        return postsMap.remove(postId) != null;
    }

    public Post retrievePost(int postId) {
        return postsMap.get(postId);
    }

    public Map<Integer, Post> getTopNPostsByLikes(int n) {
        Map<Integer, Post> topLikedPosts = new LinkedHashMap<>();

        for (int i = 0; i < n; i++) {
            int maxLikes = -1;
            int maxLikesPostId = -1;

            for (Map.Entry<Integer, Post> entry : postsMap.entrySet()) {
                int likes = entry.getValue().getLikes();

                if (likes > maxLikes && !topLikedPosts.containsKey(entry.getKey())) {
                    maxLikes = likes;
                    maxLikesPostId = entry.getKey();
                }
            }

            if (maxLikesPostId != -1) {
            	topLikedPosts.put(maxLikesPostId, postsMap.get(maxLikesPostId));
            } else {
                break;
            }
        }

        return topLikedPosts;
    }


    public Map<Integer, Post> getTopNPostsByShares(int n) {
        Map<Integer, Post> topSharedPosts = new LinkedHashMap<>();

        for (int i = 0; i < n; i++) {
            int maxShares = -1;
            int maxSharesPostId = -1;

            for (Map.Entry<Integer, Post> entry : postsMap.entrySet()) {
                int shares = entry.getValue().getShares();

                if (shares > maxShares && !topSharedPosts.containsKey(entry.getKey())) {
                    maxShares = shares;
                    maxSharesPostId = entry.getKey();
                }
            }

            if (maxSharesPostId != -1) {
            	topSharedPosts.put(maxSharesPostId, postsMap.get(maxSharesPostId));
            } else {
                break;
            }
        }

        return topSharedPosts;
    }




    public void loadPostsFromFile(String filePath) throws IOException, NumberFormatException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read and discard the first line (headers)
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String content = parts[1];
                    String author = parts[2];
                    int likes = Integer.parseInt(parts[3]);
                    int shares = Integer.parseInt(parts[4]);
                    String dateTime = parts[5];


                    Post post = new Post(id, content, author, likes, shares, dateTime);
                    postsMap.put(id, post);
                }
            }
        }
    }
    
    public void savePostsToCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write the CSV header
            writer.write("ID,content,author,likes,shares,date-time,owner\n");

            for (Post post : postsMap.values()) {
                writer.write(post.getId() + "," + post.getContent() + "," + post.getAuthor() + ","
                        + post.getLikes() + "," + post.getShares() + "," + post.getDateTime() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}