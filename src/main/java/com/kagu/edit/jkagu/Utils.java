package com.kagu.edit.jkagu;

public class Utils {

    private Utils()
    {
        // Hidden constructor
    }

    public static String getReplacedString(String s, int occurrenceIndex, String target, String replacement) {
        final String result;
        if(occurrenceIndex != -1)
        {
            String firstPart = s.substring(0, occurrenceIndex);
            String lastPart = s.substring(occurrenceIndex + target.length());
            StringBuilder sb = new StringBuilder(firstPart);
            sb.append(replacement);
            sb.append(lastPart);
            result = sb.toString();
        }
        else
        {
            result = s;
        }
        return result;
    }


    public static boolean isStringNOTEmpty(String s)
    {
        if(s != null && !s.trim().isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
