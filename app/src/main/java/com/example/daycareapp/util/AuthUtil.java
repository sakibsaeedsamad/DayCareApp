package com.example.daycareapp.util;

import android.content.Context;

public class AuthUtil {
    private Context context;
    private SharedRefs sharedRefs;

    public AuthUtil(Context context, SharedRefs sharedRefs) {
        this.context = context;
        this.sharedRefs = sharedRefs;
    }

    public boolean isLogin() {
        return sharedRefs.has(sharedRefs.ACCESS_TOKEN);
    }
}
