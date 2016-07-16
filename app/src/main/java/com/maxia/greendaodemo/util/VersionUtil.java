package com.maxia.greendaodemo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.zip.ZipFile;

/**
 * Created by kurt on 11/19/15.
 */
public class VersionUtil {


    private static Info _cachedInfo;

    public static Info getInfo(Context paramContext) {
        if (_cachedInfo == null) {
            try {
                PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
                _cachedInfo = new Info(localPackageInfo.versionCode, localPackageInfo.versionName);
                return _cachedInfo;
            } catch (PackageManager.NameNotFoundException e) {
                _cachedInfo = new Info(1, "1.0.0");
            }
        }
        return _cachedInfo;
    }

    public static class Info {
        public final int versionCode;
        public final String versionName;

        private Info(int paramInt, String paramString) {
            this.versionCode = paramInt;
            this.versionName = paramString;
        }
    }

}
