package csecau.capstone.homeseek;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class posting_list implements Parcelable {

    private int profile_image;
    private String title, homeid, address, detailaddress, explain, deposit, monthly, term;
    private String washing, refrigerator, desk, bed, microwave, closet;
    private String imageone, imagetwo, imagethree;
    private String phoneNum;

    public int getProfile_image() {
        return profile_image;
    }
    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getHomeid() {return homeid;}
    public void setHomeid(String homeid){this.homeid = homeid;}
    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}
    public String getDetailaddress(){return detailaddress;}
    public void setDetailaddress(String detailaddress){this.detailaddress = detailaddress;}
    public String getExplain(){return explain;}
    public void setExplain(String explain){this.explain = explain;}
    public String getDeposit(){return deposit;}
    public void setDeposit(String deposit){this.deposit = deposit;}
    public String getMonthly(){return monthly;}
    public void setMonthly(String monthly){this.monthly = monthly;}
    public String getTerm(){return term;}
    public void setTerm(String term){this.term = term;}

    public String getWashing(){return washing;}
    public void setWashing(String washing){this.washing = washing;}
    public String getRefrigerator(){return refrigerator;}
    public void setRefrigerator(String refrigerator){this.refrigerator = refrigerator;}
    public String getDesk(){return desk;}
    public void setDesk(String desk){this.desk = desk;}
    public String getBed(){return bed;}
    public void setBed(String bed){this.bed = bed;}
    public String getMicrowave(){return microwave;}
    public void setMicrowave(String microwave){this.microwave = microwave;}
    public String getCloset(){return closet;}
    public void setCloset(String closet){this.closet= closet;}

    public String getImageone(){return imageone;}
    public void setImageone(String imageone){this.imageone = imageone;}
    public String getImagetwo(){return imagetwo;}
    public void setImagetwo(String imagetwo){this.imagetwo = imagetwo;}
    public String getImagethree(){return imagethree;}
    public void setImagethree(String imagethree){this.imagethree = imagethree;}

    public String getPhoneNum(){return phoneNum;}
    public void setPhoneNum(String phoneNum){this.phoneNum = phoneNum;}


    public posting_list(int profile_image, String title, String homeid, String address, String detailaddress, String explain, String deposit, String monthly, String term,
                        String washing, String refrigerator, String desk, String bed, String microwave, String closet,
                        String imageone, String imagetwo, String imagethree, String phoneNum) {
        this.profile_image = profile_image;
        this.title = title;
        this.homeid = homeid;
        this.address = address;
        this.detailaddress = detailaddress;
        this.explain = explain;
        this.deposit = deposit;
        this.monthly = monthly;
        this.term = term;

        this.washing =washing;
        this.refrigerator = refrigerator;
        this.desk = desk;
        this.bed = bed;
        this.microwave = microwave;
        this.closet = closet;

        this.imageone = imageone;
        this.imagetwo = imagetwo;
        this.imagethree = imagethree;

        this.phoneNum = phoneNum;
    }

    public posting_list(Parcel in){
        this.profile_image = in.readInt();
        this.title = in.readString();
        this.address = in.readString();
        this.detailaddress = in.readString();
        this.explain = in.readString();
        this.deposit = in.readString();
        this.monthly = in.readString();
        this.term = in.readString();

        this.washing =in.readString();
        this.refrigerator = in.readString();
        this.desk = in.readString();
        this.bed = in.readString();
        this.microwave = in.readString();
        this.closet = in.readString();

        this.imageone = in.readString();
        this.imagethree = in.readString();
        this.imagetwo = in.readString();

        this.phoneNum = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(this.profile_image);
        dest.writeString(this.title);
        dest.writeString(this.address);
        dest.writeString(this.detailaddress);
        dest.writeString(this.explain);
        dest.writeString(this.deposit);
        dest.writeString(this.monthly);
        dest.writeString(this.term);

        dest.writeString(this.washing);
        dest.writeString(this.refrigerator);
        dest.writeString(this.desk);
        dest.writeString(this.bed);
        dest.writeString(this.microwave);
        dest.writeString(this.closet);

        dest.writeString(this.imageone);
        dest.writeString(this.imagetwo);
        dest.writeString(this.imagethree);

        dest.writeString(this.phoneNum);
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
