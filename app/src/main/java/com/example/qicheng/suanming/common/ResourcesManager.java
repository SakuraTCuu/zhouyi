package com.example.qicheng.suanming.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ResourcesManager {
    public static Drawable getDrawable(Context ctx, int id) {
        if (ctx == null) {
            Log.d(ResourcesManager.class.getSimpleName(), "Context is null");
            return null;
        }
        return ctx.getResources().getDrawable(id);
    }
}
