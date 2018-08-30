package com.example.jessi.flickrappfromudemy;

class Photo {

    private String mTitle;
    private String mAuthor;
    private String mAuthorID;
    private String mLink;
    private String mTags;
    private String mImage;

    public Photo(String title, String author, String authorID, String link, String tags, String image) {
        mTitle = title;
        mAuthor = author;
        mAuthorID = authorID;
        mLink = link;
        mTags = tags;
        mImage = image;
    }

    String getTitle() {
        return mTitle;
    }

    String getAuthor() {
        return mAuthor;
    }

    String getAuthorID() {
        return mAuthorID;
    }

    String getLink() {
        return mLink;
    }

    String getTags() {
        return mTags;
    }

    String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorID='" + mAuthorID + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
