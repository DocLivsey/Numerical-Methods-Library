package Parsers;

import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputStreamParser {
    public static int parseChoiceOfTwo(String firstMessage, String secondMessage)
    {
        System.out.println("Input: " + firstMessage + " or " + secondMessage);
        Scanner scan = new Scanner(System.in);
        String message = scan.nextLine();
        if (firstMessage.equalsIgnoreCase(message.trim()))
            return 1;
        else if (secondMessage.equalsIgnoreCase(message.trim()))
            return 2;
        return -1;
    }
    public static boolean containsInText(String string, String[] text)
    {
        Pattern pattern = Pattern.compile("^#*" + string + "\\w*", Pattern.CASE_INSENSITIVE);
        for (var word : text)
        {
            Matcher matcher = pattern.matcher(word);
            if (matcher.matches())
                return true;
        }
        return false;
    }
}

