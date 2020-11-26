package com.example.loginactivity.theme;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginactivity.R;

public class Utils {
    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_ORANGE = 1;
    public final static int THEME_GREEN = 2;
    public final static int THEME_BROWN = 3;

    public static void changeToTheme(AppCompatActivity activity, int theme) {
        sTheme = theme;

        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.finish();
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.TemaPadrao);
                break;
            case THEME_ORANGE:
                activity.setTheme(R.style.TemaLaranja);
                break;
            case THEME_GREEN:
                activity.setTheme(R.style.TemaVerde);
                break;
            case THEME_BROWN:
                activity.setTheme(R.style.TemaMarrom);
                break;
        }
    }
}

