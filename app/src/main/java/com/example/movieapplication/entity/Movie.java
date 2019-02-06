package com.example.movieapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Movie implements Parcelable {

    private String name;
    private String nameEng;
    private String description;
    private String premiere;
    private String image;

    public Movie(String name, String nameEng, String description, String premiere, String image) {
        this.name = name;
        this.nameEng = nameEng;
        this.description = description;
        this.premiere = premiere;
        this.image = image;
    }

    private Movie(Parcel parcel) {
        String[] data = new String[5];
        parcel.readStringArray(data);
        name = data[0];
        nameEng = data[1];
        description = data[2];
        premiere = data[3];
        image = data[4];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPremiere() {
        return premiere;
    }

    public void setPremiere(String premiere) {
        this.premiere = premiere;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Movie movie = (Movie) object;
        return Objects.equals(getImage(), movie.getImage()) &&
                Objects.equals(getName(), movie.getName()) &&
                Objects.equals(getNameEng(), movie.getNameEng()) &&
                Objects.equals(getPremiere(), movie.getPremiere()) &&
                Objects.equals(getDescription(), movie.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImage(), getName(), getNameEng(), getPremiere(), getDescription());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[]{name, nameEng, description, premiere, image});
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
