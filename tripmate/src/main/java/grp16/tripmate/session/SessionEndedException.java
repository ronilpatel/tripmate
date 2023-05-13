package grp16.tripmate.session;

public class SessionEndedException extends Exception{
    @Override
    public String getMessage() {
        return "Please login to continue";
    }
}
