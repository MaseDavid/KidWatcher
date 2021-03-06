package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Base64;
import android.util.Log;

public final class FirebaseInstanceIdReceiver extends WakefulBroadcastReceiver {
    public int m15623a(Context context, String str, Intent intent) {
        if (!FirebaseInstanceIdInternalReceiver.m15622a(context)) {
            return C3606g.m15695a().m15699a(context, str, intent);
        }
        if (isOrderedBroadcast()) {
            setResultCode(-1);
        }
        FirebaseInstanceIdInternalReceiver.m15621a(context, str).m15654a(intent, goAsync());
        return -1;
    }

    public void onReceive(Context context, Intent intent) {
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        String stringExtra = intent.getStringExtra("gcm.rawData64");
        if (stringExtra != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra, 0));
            intent.removeExtra("gcm.rawData64");
        }
        stringExtra = intent.getStringExtra("from");
        if ("google.com/iid".equals(stringExtra) || "gcm.googleapis.com/refresh".equals(stringExtra)) {
            stringExtra = "com.google.firebase.INSTANCE_ID_EVENT";
        } else if ("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            stringExtra = "com.google.firebase.MESSAGING_EVENT";
        } else {
            Log.d("FirebaseInstanceId", "Unexpected intent");
            stringExtra = null;
        }
        int i = -1;
        if (stringExtra != null) {
            i = m15623a(context, stringExtra, intent);
        }
        if (isOrderedBroadcast()) {
            setResultCode(i);
        }
    }
}
