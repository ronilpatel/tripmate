package grp16.tripmate.post.model;

import grp16.tripmate.post.model.exception.MinAgeGreaterThanMaxAgeException;
import grp16.tripmate.post.model.exception.StartDateAfterEndDateException;
import grp16.tripmate.post.model.exception.StartDateBeforeTodayException;

import java.text.ParseException;
import java.util.Date;

public class PostValidator {
    public void isStarDateBeforeToday(Post post) throws ParseException, StartDateBeforeTodayException {
        Date startDate = post.getJavaDate(post.getStartDate());
        if(startDate.before(new Date())){
            throw new StartDateBeforeTodayException();
        }
    }

    public void isStartDateBeforeEndDate(Post post) throws ParseException, StartDateAfterEndDateException {
        Date startDate = post.getJavaDate(post.getStartDate());
        Date endDate = post.getJavaDate(post.getEndDate());
        if(startDate.after(endDate)){
            throw new StartDateAfterEndDateException();
        }
    }

    public void isMinAgeLessThanMaxAge(Post post) throws MinAgeGreaterThanMaxAgeException {
        int minAge = post.getMinAge();
        int maxAge = post.getMaxAge();
        if(minAge > maxAge){
            throw new MinAgeGreaterThanMaxAgeException();
        }
    }
}
