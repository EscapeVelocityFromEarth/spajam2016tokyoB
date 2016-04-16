package com.escapevelocityfromearth.automoderator.util;

import android.util.Log;

/**
 * Created by daitikuwa on 2016/04/16.
 */
public class L {

    public static boolean DEBUG = Const.DBG;

    public static void d(String message) {
        if (DEBUG) {
            Log.d(new Throwable().getStackTrace()[1].getClassName(), message);
        }
    }

    public static void e(String message) {
        if (DEBUG) {
            Log.e(new Throwable().getStackTrace()[1].getClassName(), message);
        }
    }

    public static void outputMethodName() {
        if (DEBUG) {
            Log.d(new Throwable().getStackTrace()[1].getFileName().split("¥¥.")[0], new Throwable().getStackTrace()[1].getMethodName());
        }
    }

    public static void traceCodePlace(String message) {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("code place is ");
            sb.append("(");
            sb.append(new Throwable().getStackTrace()[1].getFileName());
            sb.append(new Throwable().getStackTrace()[1].getLineNumber());
            sb.append(")");

            Log.d(new Throwable().getStackTrace()[1].getClassName(), sb.toString());
        }
    }


}
