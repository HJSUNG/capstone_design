package csecau.capstone.homeseek;

import android.widget.TextView;

import static csecau.capstone.homeseek.navigation_main.ID_textview;
import static csecau.capstone.homeseek.navigation_main.nickname_textview;
import static csecau.capstone.homeseek.navigation_main.phone_textview;
import static csecau.capstone.homeseek.navigation_main.type_textview;


public class User_information {
    public String info_ID = "";
    public String info_nickname = "";
    public String info_phone = "";
    public String info_type = "";

    public User_information (String ID, String nickname, String phone, String type) {
        this.info_ID = ID;
        this.info_nickname = nickname;
        this.info_phone = phone;
        this.info_type = type;
    }

    public void log_in (String ID, String nickname, String phone, String type) {
        this.info_ID = ID;
        this.info_nickname = nickname;
        this.info_phone = phone;
        this.info_type = type;

        ID_textview.setText(this.info_ID);
        nickname_textview.setText(this.info_nickname);
        phone_textview.setText(this.info_phone);
        type_textview.setText(this.info_type);


    }

    public void log_out() {
        this.info_ID = "";
        this.info_nickname = "";
        this.info_phone = "";
        this.info_type = "";

        ID_textview.setText(this.info_ID);
        nickname_textview.setText(this.info_nickname);
        phone_textview.setText(this.info_phone);
        type_textview.setText(this.info_type);

    }
}
