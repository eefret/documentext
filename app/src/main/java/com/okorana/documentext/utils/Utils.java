package com.okorana.documentext.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.Log;
import android.util.Patterns;

import com.okorana.documentext.R;
import com.okorana.documentext.phonebookdata.CharacterSets;
import com.okorana.documentext.phonebookdata.EncodedStringValue;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eefret on 5/7/16.
 */
public class Utils {

    private static int mAliasRuleMinChars = 2;
    private static int mAliasRuleMaxChars = 48;

    public static final Pattern NAME_ADDR_EMAIL_PATTERN =
            Pattern.compile("\\s*(\"[^\"]*\"|[^<>\"]+)\\s*<([^<>]+)>\\s*");
    private static String[] sNoSubjectStrings;
    /**
     * MMS address parsing data structures
     */
    // allowable phone number separators
    private static final char[] NUMERIC_CHARS_SUGAR = {
            '-', '.', ',', '(', ')', ' ', '/', '\\', '*', '#', '+'
    };
    private static HashMap numericSugarMap = new HashMap(NUMERIC_CHARS_SUGAR.length);
    static {
        for (int i = 0; i < NUMERIC_CHARS_SUGAR.length; i++) {
            numericSugarMap.put(NUMERIC_CHARS_SUGAR[i], NUMERIC_CHARS_SUGAR[i]);
        }
    }

    public static String describeSqliteTable(Context ctx, Uri tableUri){
        Cursor c = ctx.getContentResolver().query(tableUri, null, null, null, null);
        if(c!= null){
            c.moveToFirst();
            StringBuilder sb = new StringBuilder();
            for(String name: c.getColumnNames()){
                sb.append(name);
                sb.append("(");
                switch (c.getType(c.getColumnIndex(name))){
                    case Cursor.FIELD_TYPE_BLOB:
                        sb.append("blob");
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        sb.append("float");
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        sb.append("string");
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        sb.append("integer");
                        break;
                    default:
                        sb.append("null");
                }
                sb.append(")");
            }
            c.close();
            return sb.toString();
        }
        return "Invalid Query";
    }

    public static boolean isPhoneNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }

        Matcher match = Patterns.PHONE.matcher(number);
        return match.matches();
    }

    public static boolean isEmailAddress(String address) {
        if (TextUtils.isEmpty(address)) {
            return false;
        }

        String s = extractAddrSpec(address);
        Matcher match = Patterns.EMAIL_ADDRESS.matcher(s);
        return match.matches();
    }

    public static String extractAddrSpec(String address) {
        Matcher match = NAME_ADDR_EMAIL_PATTERN.matcher(address);

        if (match.matches()) {
            return match.group(2);
        }
        return address;
    }

    public static String normalizeNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int len = phoneNumber.length();
        for (int i = 0; i < len; i++) {
            char c = phoneNumber.charAt(i);
            // Character.digit() supports ASCII and Unicode digits (fullwidth, Arabic-Indic, etc.)
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                sb.append(digit);
            } else if (sb.length() == 0 && c == '+') {
                sb.append(c);
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                return normalizeNumber(PhoneNumberUtils.convertKeypadLettersToDigits(phoneNumber));
            }
        }
        return sb.toString();
    }

    /** An alias (or commonly called "nickname") is:
    * Nickname must begin with a letter.
    * Only letters a-z, numbers 0-9, or . are allowed in Nickname field.
     * */
    public static boolean isAlias(String string) {
        int len = string == null ? 0 : string.length();
        if (len < mAliasRuleMinChars || len > mAliasRuleMaxChars) {
            return false;
        }
        if (!Character.isLetter(string.charAt(0))) {    // Nickname begins with a letter
            return false;
        }
        for (int i = 1; i < len; i++) {
            char c = string.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '.')) {
                return false;
            }
        }
        return true;
    }

    /**
     * parse the input address to be a valid MMS address.
     * - if the address is an email address, leave it as is.
     * - if the address can be parsed into a valid MMS phone number, return the parsed number.
     * - if the address is a compliant alias address, leave it as is.
     */
    public static String parseMmsAddress(String address) {
        // if it's a valid Email address, use that.
        if (isEmailAddress(address)) {
            return address;
        }
        // if we are able to parse the address to a MMS compliant phone number, take that.
        String retVal = parsePhoneNumberForMms(address);
        if (retVal != null && retVal.length() != 0) {
            return retVal;
        }
        // if it's an alias compliant address, use that.
        if (isAlias(address)) {
            return address;
        }
        // it's not a valid MMS address, return null
        return null;
    }


    /**
     * Given a phone number, return the string without syntactic sugar, meaning parens,
     * spaces, slashes, dots, dashes, etc. If the input string contains non-numeric
     * non-punctuation characters, return null.
     */
    private static String parsePhoneNumberForMms(String address) {
        StringBuilder builder = new StringBuilder();
        int len = address.length();
        for (int i = 0; i < len; i++) {
            char c = address.charAt(i);
            // accept the first '+' in the address
            if (c == '+' && builder.length() == 0) {
                builder.append(c);
                continue;
            }
            if (Character.isDigit(c)) {
                builder.append(c);
                continue;
            }
            if (numericSugarMap.get(c) == null) {
                return null;
            }
        }
        return builder.toString();
    }

    /**
     * Replaces all unicode(e.g. Arabic, Persian) digits with their decimal digit equivalents.
     *
     * @param number the number to perform the replacement on.
     * @return the replaced number.
     */
    public static String replaceUnicodeDigits(String number) {
        StringBuilder normalizedDigits = new StringBuilder(number.length());
        for (char c : number.toCharArray()) {
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                normalizedDigits.append(digit);
            } else {
                normalizedDigits.append(c);
            }
        }
        return normalizedDigits.toString();
    }

    /**
     * cleanseMmsSubject will take a subject that's says, "<Subject: no subject>", and return
     * a null string. Otherwise it will return the original subject string.
     * @param context a regular context so the function can grab string resources
     * @param subject the raw subject
     * @return
     */
    public static String cleanseMmsSubject(Context context, String subject) {
        if (TextUtils.isEmpty(subject)) {
            return subject;
        }
        if (sNoSubjectStrings == null) {
            sNoSubjectStrings =
                    context.getResources().getStringArray(R.array.empty_subject_strings);
        }
        final int len = sNoSubjectStrings.length;
        for (int i = 0; i < len; i++) {
            if (subject.equalsIgnoreCase(sNoSubjectStrings[i])) {
                return null;
            }
        }
        return subject;
    }

    public static String extractEncStrFromCursor(Cursor cursor,
                                                 int columnRawBytes, int columnCharset) {
        String rawBytes = cursor.getString(columnRawBytes);
        int charset = cursor.getInt(columnCharset);
        if (TextUtils.isEmpty(rawBytes)) {
            return "";
        } else if (charset == CharacterSets.ANY_CHARSET) {
            return rawBytes;
        } else {
            return new EncodedStringValue(charset, getBytes(rawBytes)).getString();
        }
    }

    /**
     * Unpack a given String into a byte[].
     */
    public static byte[] getBytes(String data) {
        try {
            return data.getBytes(CharacterSets.MIMENAME_ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            // Impossible to reach here!
            Log.e("UTILS", "ISO_8859_1 must be supported!", e);
            return new byte[0];
        }
    }

    public static String formatTimeStampString(Context c, long when, boolean fullFormat){
        Time then = new Time();
        then.set(when);
        Time now = new Time();
        now.setToNow();

        int formatFlags = DateUtils.FORMAT_NO_NOON_MIDNIGHT|
                DateUtils.FORMAT_ABBREV_ALL |
                DateUtils.FORMAT_CAP_AMPM;
        if(then.year != now.year){
            formatFlags |= DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE;
        } else if(then.yearDay != now.yearDay){
            formatFlags |= DateUtils.FORMAT_SHOW_DATE;
        }else {
            formatFlags |= DateUtils.FORMAT_SHOW_TIME;
        }

        if(fullFormat){
            formatFlags |= (DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
        }
        return DateUtils.formatDateTime(c, when, formatFlags);
    }
}
