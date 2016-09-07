package com.example.ahmad_elbayadi.review;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */
public class Review {
    //review class member varaibles
    private String Review_Id;
    private String Review_Autor;
    private String Review_Content;
    private String Review_URL;

    //constructor that initailizing all of these member variables
    public Review(String Review_Id,String Review_Author,String Review_Content,String Review_URL)
    {
        this.Review_Id=Review_Id;
        this.Review_Autor=Review_Author;
        this.Review_Content=Review_Content;
        this.Review_URL=Review_URL;
    }

    //setter and getter
    public String getReview_Id()
    {
        return Review_Id;
    }

    public void setReview_Id(String Review_Id)
    {
        this.Review_Id=Review_Id;
    }

    public String getReview_Autor()
    {
        return Review_Autor;
    }

    public void setReview_Autor(String Review_Author)
    {
        this.Review_Autor=Review_Author;
    }

    public String getReview_Content()
    {
        return Review_Content;
    }

    public void setReview_Content(String Review_Content)
    {
        this.Review_Content=Review_Content;
    }

    public String getReview_URL()
    {
        return Review_URL;
    }

    public void setReview_URL(String Review_URL)
    {
        this.Review_URL=Review_URL;
    }
}

