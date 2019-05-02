package com.example.newagnda.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    private  String title,authors,publisher,publisher_date,description,image,categories,leng;
    public Data(String title, String authors, String publisher, String publisher_date, String description, String image, String categories, String leng) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publisher_date = publisher_date;
        this.description = description;
        this.image = image;
        this.categories = categories;
        this.leng = leng;
    }

    protected Data(Parcel in) {
        title = in.readString();
        authors = in.readString();
        publisher = in.readString();
        publisher_date = in.readString();
        description = in.readString();
        image = in.readString();
        categories = in.readString();
        leng = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher_date() {
        return publisher_date;
    }

    public void setPublisher_date(String publisher_date) {
        this.publisher_date = publisher_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getLeng() {
        return leng;
    }

    public void setLeng(String leng) {
        this.leng = leng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(authors);
        dest.writeString(publisher);
        dest.writeString(publisher_date);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(categories);
        dest.writeString(leng);
    }
}
