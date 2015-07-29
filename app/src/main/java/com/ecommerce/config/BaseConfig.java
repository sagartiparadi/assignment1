package com.ecommerce.config;

import android.app.Activity;
import android.graphics.Typeface;

/**
 * Created by sagar on 27/7/15.
 */
public class BaseConfig {
    public static String BOLD_FONT = "fonts/ColabBol.otf";
    public static String REGULAR_FONT = "fonts/ColabReg.otf";

    public static Typeface getFont(String font_name,Activity activity){
        return Typeface.createFromAsset(activity.getAssets(), font_name);
    }
}
