package csecau.capstone.homeseek;

import android.os.Parcel;
import android.os.Parcelable;

public class comment_list implements Parcelable{
    private String board, id, detail, datetime;

    public String getBoard(){return board;}
    public void setBoard(String board){this.board = board;}
    public String getId(){return id;}
    public void setId(String id){this.id= id;}
    public String getDetail() { return detail; }
    public void setDetail(String detail){this.detail = detail;}
    public String getDatetime(){return datetime;}
    public void setDatetime(String datetime){this.datetime = datetime;}

    public comment_list(String board, String id, String detail, String datetime){
        this.board = board;
        this.id = id;
        this.detail = detail;
        this.datetime = datetime;
    }

    public comment_list(Parcel in){
        this.board = in.readString();
        this.id = in.readString();
        this.detail = in.readString();
        this.datetime = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.board);
        dest.writeString(this.id);
        dest.writeString(this.detail);
        dest.writeString(this.datetime);
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public comment_list createFromParcel(Parcel in){
            return new comment_list(in);
        }
        @Override
        public comment_list[] newArray(int size){
            return new comment_list[size];
        }
    };
}
