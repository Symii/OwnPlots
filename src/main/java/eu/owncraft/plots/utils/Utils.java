package eu.owncraft.plots.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static boolean isAlphaNumeric(String string)
    {
        return string != null && string.matches("^[a-zA-Z0-9]*$");
    }

    public static String getStringDate(long time)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

}
