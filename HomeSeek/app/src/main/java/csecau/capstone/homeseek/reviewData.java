package csecau.capstone.homeseek;

public class reviewData {
    private String userID;
    private String message;

    public reviewData(){}

    public reviewData(String userID, String message){
        this.userID = userID;
        this.message = message;
    }

    public String getUserID(){return userID;}
    public void setUserID(String userID){this.userID = userID;}
    public String getMessage(){return message;}
    public void setMessage(String message){this.message = message;}
}
