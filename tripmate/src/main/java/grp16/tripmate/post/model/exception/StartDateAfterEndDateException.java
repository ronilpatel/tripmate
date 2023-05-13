package grp16.tripmate.post.model.exception;

public class StartDateAfterEndDateException extends Exception{

    @Override
    public String getMessage() {
        return "Start Date cannot be later than End Date";
    }
}
