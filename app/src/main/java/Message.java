/**
 * Created by Keith on 22/04/2015.
 */
public class Message {

    private String message;
    private String date;

    public Message(String message, String date){
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
