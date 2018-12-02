package csecau.capstone.homeseek;

import android.os.Parcel;
import android.os.Parcelable;

public class board_list implements Parcelable {
    private String board, id, title, detail;

    public String getBoard(){return board;}
    public void setBoard(String board){this.board = board;}
    public String getId(){return id;}
    public void setId(String id){this.id= id;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}
    public String getDetail() { return detail; }
    public void setDetail(String detail){this.detail = detail;}

    public board_list(String board, String id, String title, String detail){
        this.board = board;
        this.id = id;
        this.title = title;
        this.detail = detail;
    }

    public board_list(Parcel in){
        this.board = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.detail = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.board);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.detail);
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public posting_list createFromParcel(Parcel in){
            return new posting_list(in);
        }
        @Override
        public posting_list[] newArray(int size){
            return new posting_list[size];
        }
    };

}
