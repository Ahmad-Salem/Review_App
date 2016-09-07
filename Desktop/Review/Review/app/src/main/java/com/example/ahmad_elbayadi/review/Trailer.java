package com.example.ahmad_elbayadi.review;

/**
 * Created by Ahmad_Elbayadi on 05/09/2016.
 */
public class Trailer {

    //defining Trailer member variables
    private String Trailer_Id;
    private String Trailer_key;
    private String Trailer_Name;
    private String Trailer_Site;
    private String Trailer_Type;

    //constructor that initailizing member variables
    public Trailer(String Trailer_Id,String Trailer_key,String Trailer_Name,String Trailer_Site,String Trailer_Type)
    {
        this.Trailer_Id=Trailer_Id;
        this.Trailer_key=Trailer_key;
        this.Trailer_Name=Trailer_Name;
        this.Trailer_Site=Trailer_Site;
        this.Trailer_Type=Trailer_Type;
    }

    //setter and getter
    public String getTrailer_Id()
    {
        return this.Trailer_Id;
    }

    public void setTrailer_Id(String Trailer_Id )
    {
        this.Trailer_Id=Trailer_Id;
    }

    public String getTrailer_key()
    {
        return this.Trailer_key;
    }

    public void setTrailer_key(String Trailer_Key )
    {
        this.Trailer_key=Trailer_Key;
    }

    public String getTrailer_Name()
    {
        return this.Trailer_Name;
    }

    public void setTrailer_Name(String Trailer_Name )
    {
        this.Trailer_Name=Trailer_Name;
    }

    public String getTrailer_Site()
    {
        return this.Trailer_Site;
    }

    public void setTrailer_Site(String Trailer_Site)
    {
        this.Trailer_Site=Trailer_Site;
    }

    public String getTrailer_Type()
    {
        return this.Trailer_Type;
    }

    public void setTrailer_Type(String Trailer_Type )
    {
        this.Trailer_Type=Trailer_Type;
    }

}
