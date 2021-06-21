package dtu.softproj.memorizer;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.animation.Animation;

public class Blinking {

    @SuppressLint("WrongConstant")
    public static void manageBlinkEffect(ViewGroup cLayout, String startColor, String endColor, int duration) {
        ObjectAnimator anim = ObjectAnimator.ofInt(cLayout, "backgroundColor",
                Color.parseColor(startColor), Color.parseColor(endColor));
        anim.setDuration(duration);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(1);
        anim.start();
    }
    
}
