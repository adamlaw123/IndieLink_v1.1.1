package com.indielink.indielink.Profile;

import android.text.format.Time;

import com.facebook.Profile;
import com.indielink.indielink.Network.GetProfilePicture;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileContent {

    private static HashMap<String,String> User = new HashMap<String,String>();
    private static HashMap<String,Boolean> Instrument = new HashMap<String,Boolean>();
    public static String ProfilePictureURL;

    public static void InitializeProfile(JSONObject object)
    {
        try {
            //get url from Fackbook Graph API
            String url = object.getJSONObject("picture").getJSONObject("data").getString("url");

            String gender = object.get("gender").toString();
            int birth = Integer.parseInt(object.get("birthday").toString().substring(6, 10));
            Time currentTime = new Time();
            currentTime.setToNow();
            String age = String.valueOf(currentTime.year-birth);

            Profile profile = Profile.getCurrentProfile();
            User.put("UserName",profile.getName());
            User.put("UserAge", age);
            User.put("UserGender",gender);

            if(url != ProfilePictureURL && !url.isEmpty())
            {
                ProfilePictureURL = url;
                new GetProfilePicture(ProfileContent.ProfilePictureURL,null).execute();
            }
            return;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String,String> GetUserProfile()
    {
        return User;
    }
}
