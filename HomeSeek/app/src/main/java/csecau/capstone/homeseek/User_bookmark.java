package csecau.capstone.homeseek;

public class User_bookmark {
    public String[] bookmark= new String[100];
    public int num_bookmark = 0;

    public User_bookmark(String input[]) {
        int num_input= input.length;

        for(int i=0;i<num_input;i++) {
            bookmark[i] = input[i];
        }

        num_bookmark = input.length;
    }
}


