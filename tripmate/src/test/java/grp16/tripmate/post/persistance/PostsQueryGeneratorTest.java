package grp16.tripmate.post.persistance;

import grp16.tripmate.post.model.Post;
import grp16.tripmate.post.model.factory.PostFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

class PostsQueryGeneratorTest {

    IPostsQueryGenerator queryGenerator = PostsQueryGenerator.getInstance();

    @Test
    void getCreatePostQuery() throws ParseException {
        String actual = "INSERT INTO Post(  created_by,title,source_location,destination_location,start_ts,end_ts,min_age,max_age,capacity,description)  VALUES ( 0, 'sharshil1299@gmail.com', 'source 2', 'destination 3', '2024-12-14', '2024-12-22', 10, 20, 10, 'description 3' );";
        Post post = (Post) PostFactory.getInstance().makeNewPost();
        post.setTitle("sharshil1299@gmail.com");
        post.setSource("source 2");
        post.setDestination("destination 3");
        post.setStartDate("2024-12-14");
        post.setEndDate("2024-12-22");
        post.setMinAge(10);
        post.setMaxAge(20);
        post.setCapacity(10);
        post.setDescription("description 3");
        post.setId(23);
        Assertions.assertEquals(actual, queryGenerator.getCreatePostQuery(post));
    }

    @Test
    void getAllPosts() {
        String actual = "SELECT  id,  created_by,  title,  source_location,  destination_location,  start_ts,  end_ts,  min_age,  max_age,  capacity,  is_hidden,  description  FROM Post WHERE is_hidden != 1 AND created_by != 17";
        Assertions.assertEquals(actual, queryGenerator.getAllPosts(17));
    }

    @Test
    void getPostsByUserId() {
        String actual = "SELECT  id,  created_by,  title,  source_location,  destination_location,  start_ts,  end_ts,  min_age,  max_age,  capacity,  is_hidden,  description  FROM Post WHERE is_hidden != 1 AND  created_by = 17";
        Assertions.assertEquals(actual, queryGenerator.getPostsByUserId(17));
    }

    @Test
    void getPostByPostId() {
        String actual = "SELECT  id,  created_by,  title,  source_location,  destination_location,  start_ts,  end_ts,  min_age,  max_age,  capacity,  is_hidden,  description  FROM Post WHERE id = 23;";
        Assertions.assertEquals(actual, queryGenerator.getPostByPostId(23));
    }

    @Test
    void getUpdatePostQuery() throws ParseException {
        String actual = "UPDATE Post    SET title='sharshil1299@gmail.com', source_location='source 2', destination_location='destination 3', start_ts='2024-12-14', end_ts='2024-12-22', min_age=10, max_age=20, capacity=10, description='description 3'     WHERE id=23";
        Post post = (Post) PostFactory.getInstance().makeNewPost();
        post.setTitle("sharshil1299@gmail.com");
        post.setSource("source 2");
        post.setDestination("destination 3");
        post.setStartDate("2024-12-14");
        post.setEndDate("2024-12-22");
        post.setMinAge(10);
        post.setMaxAge(20);
        post.setCapacity(10);
        post.setDescription("description 3");
        post.setId(23);
        Assertions.assertEquals(actual, queryGenerator.getUpdatePostQuery(post));
    }

    @Test
    void deletePostQuery() {
        String actual = "DELETE FROM Post WHERE id = 15";
        Assertions.assertEquals(actual, queryGenerator.deletePostQuery(15));
    }

    @Test
    void hidePostQuery() {
        String actual = "UPDATE Post     SET is_hidden=true     WHERE id=23";
        Assertions.assertEquals(actual, queryGenerator.hidePostQuery(23));
    }
}