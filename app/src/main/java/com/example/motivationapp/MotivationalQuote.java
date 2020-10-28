package com.example.motivationapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MotivationalQuote implements Parcelable {

    private String quoteId;
    private String quoteImage;
    private String quoteDescription;
    private boolean isFavourite;



    protected MotivationalQuote(Parcel in) {
        quoteId = in.readString();
        quoteImage = in.readString();
        quoteDescription = in.readString();
        isFavourite = in.readByte() != 0;
    }

    public MotivationalQuote(String quoteId, String quoteImage, String quoteDescription, boolean isFavourite) {
        this.quoteId = quoteId;
        this.quoteImage = quoteImage;
        this.quoteDescription = quoteDescription;
        this.isFavourite = isFavourite;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public String getQuoteImage() {
        return quoteImage;
    }

    public String getQuoteDescription() {
        return quoteDescription;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public static final Creator<MotivationalQuote> CREATOR = new Creator<MotivationalQuote>() {
        @Override
        public MotivationalQuote createFromParcel(Parcel in) {
            return new MotivationalQuote(in);
        }

        @Override
        public MotivationalQuote[] newArray(int size) {
            return new MotivationalQuote[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quoteId);
        dest.writeString(quoteImage);
        dest.writeString(quoteDescription);
        dest.writeByte((byte) (isFavourite ? 1 : 0));
    }
}
