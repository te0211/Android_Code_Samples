package com.customtabs.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tejoa on 28/08/2015.
 */
public class ViewUtil {

    /**
     * Will write all bytes from <code>inStream</code> to <code>outStream</code> transferring up to <code>bufSize</code> bytes at
     * a time.
     *
     * @param inStream  contains the input stream to read from.
     * @param outStream contains the output stream to write to.
     * @param bufSize   contains the number of bytes read/written at one time.
     * @throws throws an IOException if an exception occurs while reading/writing.
     */
    public static void writeAllBytes(InputStream inStream, OutputStream outStream, int bufSize) throws IOException {
        if (inStream != null && outStream != null) {
            byte[] buffer = new byte[bufSize];
            int numBytesRead = 0;
            while ((numBytesRead = inStream.read(buffer, 0, bufSize)) != -1) {
                outStream.write(buffer, 0, numBytesRead);
            }
        }
    }

    /**
     * Format a timestamp with null checking
     *
     * @param formatter
     *            A {@link DateFormat} with the desired settings
     * @param cal
     *            The untested {@link Calendar} value to format
     * @return If successful, the timestamp in the proper format. If unsuccesful, a blank string.
     */
    public static String safeFormatCalendar(DateFormat formatter, Calendar cal) {
        String dateString = "";

        if (cal != null) {
            dateString = safeFormatDate(formatter, cal.getTime());
        }

        return dateString;
    }

    /**
     * Format a timestamp with null checking
     *
     * @param formatter
     *            A {@link DateFormat} with the desired settings
     * @param date
     *            The untested {@link Date} value to format
     * @return If successful, the timestamp in the proper format. If unsuccesful, a blank string.
     */
    public static String safeFormatDate(DateFormat formatter, Date date) {
        String dateString = "";

        // fix for MOB-20041
        // MOB-20041 : needs to reopen. This code is not handling localization and UTC timezone very well
        // String localPattern = ((SimpleDateFormat) formatter).toLocalizedPattern();
        // formatter = new SimpleDateFormat(localPattern, Locale.getDefault());
        // formatter.setTimeZone(TimeZone.getTimeZone("UTC")); // MOB-19835 MOB-19844: Fixed timezone issue.

        if (date != null) {
            dateString = formatter.format(date);
        }

        return dateString;
    }
}
