package grp16.tripmate.user.model;

public class InvalidUsernamePasswordException extends Exception{
    @Override
    public String getMessage() {
        return "Invalid Username or Password. Please try again";
    }
}
