package Parsers;

import java.util.*;
import java.lang.*;

public class ParseMessage {
    public static int parseChoiceOfTwo(String firstMessage, String secondMessage)
    {
        System.out.println("Input: " + firstMessage + " or " + secondMessage);
        Scanner scan = new Scanner(System.in);
        String message = scan.nextLine();
        if (firstMessage.equalsIgnoreCase(message.trim()))
            return 1;
        else if (secondMessage.equalsIgnoreCase(message.trim()))
            return 2;
        return 0;
    }
}
