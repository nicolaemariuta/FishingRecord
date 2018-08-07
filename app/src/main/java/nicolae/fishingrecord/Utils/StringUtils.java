package nicolae.fishingrecord.Utils;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Locale;

public class StringUtils {

    public static String toString(DateTime date) {
        return date.toString(ISODateTimeFormat.dateTime().withLocale(Locale.ENGLISH).withZoneUTC());
    }


    public static DateTime fromString(String dateStr) {
        return ISODateTimeFormat.dateTime().withLocale(Locale.ENGLISH).withZoneUTC().parseDateTime(dateStr);
    }

    public static boolean isEmpty(String text) {
        if (text == null) {
            return true;
        }

        return text.trim().length() == 0;

    }

}
