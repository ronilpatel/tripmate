package grp16.tripmate.post.model.exception;

public class StartDateBeforeTodayException extends Exception{
    @Override
    public String getMessage() {
        return "Trips can be planned starting tomorrow";
    }
}
