package grp16.tripmate.post.persistance;

import grp16.tripmate.post.model.IPost;

public interface IPostsQueryGenerator {

    String getCreatePostQuery(IPost post);

    String getAllPosts(int loggedInUser);

    String getPostsByUserId(int userId);

    String getPostByPostId(int postId);

    String getUpdatePostQuery(IPost post);

    String deletePostQuery(int postId);

    String hidePostQuery(int postId);
}
