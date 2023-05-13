package grp16.tripmate.post.model;

import grp16.tripmate.post.model.exception.MinAgeGreaterThanMaxAgeException;
import grp16.tripmate.post.model.exception.StartDateAfterEndDateException;
import grp16.tripmate.post.model.exception.StartDateBeforeTodayException;
import grp16.tripmate.post.model.factory.IPostFactory;
import grp16.tripmate.post.model.factory.PostFactory;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class PostValidatorTest {

    IPostFactory factory = PostFactory.getInstance();
    PostValidator validator = factory.makePostValidator();

    @Test
    void isStarDateBeforeTodayThrows() throws ParseException {
        Post post = (Post) factory.makeNewPost();
        String startDate = "2022-12-05";
        String endDate = "2030-12-05";
        post.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        post.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        assertThrows(StartDateBeforeTodayException.class, () -> post.validatePost(validator));
    }

    @Test
    void isStarDateBeforeTodayDoesNotThrow() throws ParseException {
        Post post = (Post) factory.makeNewPost();
        String startDate = "2030-12-05";
        String endDate = "2040-12-05";
        post.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        post.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        assertDoesNotThrow(() -> post.validatePost(validator));
    }

    @Test
    void isStartDateBeforeEndDateThrows() throws ParseException {
        Post post = (Post) factory.makeNewPost();
        String startDate = "2030-12-31";
        String endDate = "2030-12-01";
        post.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        post.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        assertThrows(StartDateAfterEndDateException.class, () -> post.validatePost(validator));
    }

    @Test
    void isStartDateBeforeEndDateDoesNotThrow() throws ParseException {
        Post post = (Post) factory.makeNewPost();
        String startDate = "2025-12-31";
        String endDate = "2030-12-01";
        post.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        post.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        assertDoesNotThrow(() -> post.validatePost(validator));
    }

    @Test
    void isMinAgeLessThanMaxAgeThrows() throws ParseException {
        Post post = (Post) factory.makeNewPost();
        post.setMinAge(5);
        post.setMaxAge(4);
        String startDate = "2030-12-31";
        String endDate = "2031-12-31";
        post.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        post.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        assertThrows(MinAgeGreaterThanMaxAgeException.class, () -> post.validatePost(validator));
    }

    @Test
    void isMinAgeLessThanMaxAgeDoesNotThrow() throws ParseException {
        Post post = (Post) factory.makeNewPost();
        post.setMinAge(10);
        post.setMaxAge(15);
        String startDate = "2030-12-31";
        String endDate = "2031-12-31";
        post.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        post.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        assertDoesNotThrow(() -> post.validatePost(validator));
    }
}