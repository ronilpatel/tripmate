package grp16.tripmate.post.model.exception;

public class MinAgeGreaterThanMaxAgeException extends Exception{
    @Override
    public String getMessage() {
        return "Min Age cannot be greater than Max Age";
    }
}
