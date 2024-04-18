package Parsers;

import OtherThings.Pair;

import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public static boolean validParentheses(String input) {
        Matcher matcher = Pattern.compile("\\[\\w+\\s*]").matcher(input);
        return matcher.matches();
    }
    public static boolean containsInText(String string, String[] text)
    {
        Pattern pattern = Pattern.compile("\\w*" + string + "\\w*", Pattern.CASE_INSENSITIVE);
        for (var word : text)
            if (pattern.matcher(word).matches())
                return true;
        return false;
    }
    public static boolean stringMatchesAnyItemOfList(Collection<String> collection, String string, String regex)
    {
        string = string.replaceAll(regex, "");
        collection = collection.stream().map(item -> item.toLowerCase().replaceAll(
                regex, "")).collect(Collectors.toList());
        for (String item : collection)
        {
            Pattern pattern = Pattern.compile(item, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(string).matches())
                return true;
        }
        return false;
    }
    public static boolean anyItemOfListFindsInString(Collection<String> collection, String string)
    {
        for (String item : collection)
        {
            Pattern pattern = Pattern.compile(item + "\\w*", Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(string).find())
                return true;
        }
        return false;
    }
    public static boolean anyItemOfListMatchesAnyItemOfAnotherList(Collection<String> collection,
                                                                   Collection<String> anotherCollection) {
        for (var item : collection)
        {
            Pattern pattern = Pattern.compile(item + "\\w+", Pattern.CASE_INSENSITIVE);
            for (var anotherItem : anotherCollection)
            {
                if (pattern.matcher(anotherItem).find())
                    return true;
            }
        }
        return false;
    }
    public static boolean anyItemOfListFindsInAnotherList(Collection<String> collection,
                                                          Collection<String> anotherCollection) {
        String collectionToString = anotherCollection.toString();
        for (var item : collection)
        {
            Matcher matcher = Pattern.compile(item + "\\w*", Pattern.CASE_INSENSITIVE).matcher(collectionToString);
            if (matcher.find())
                return true;
        }
        return false;
    }
    public static<T> Collection<T> listItemsInString(Collection<T> collection, String string)
    {
        Collection<T> inString = new ArrayList<>();
        for (T item : collection)
        {
            Matcher matcher = Pattern.compile(item + "\\w*", Pattern.CASE_INSENSITIVE).matcher(string);
            if (matcher.find())
                inString.add(item);
        }
        return inString;
    }
    public static<T> Pair<Boolean, T> isClassInList(List<Object> inputList, Class<T> tClass) {
        for (var item : inputList) {
            if (item.getClass() == tClass)
                return new Pair<>(true, (T) item);
        }
        return new Pair<>(false, null);
    }
    public static<T> Pair<Boolean, List<T>> isClassesInList(List<Object> inputList, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        for (var item : inputList)
            if (item.getClass() == tClass)
                list.add((T) item);
        return new Pair<>(!list.isEmpty(), list);
    }
    public static<T> boolean isClassesInListAtOnce(List<Object> inputList, Class<T> tClass) {
        Pair<Boolean, List<T>> pair = isClassesInList(inputList, tClass);
        return pair.getFirst() && (pair.getSecond().size() <= 1);
    }
    public static<T> boolean isOnlyGivenClassesInList(List<Object> inputList, Class<T> tClass) {
        Pair<Boolean, List<T>> pair = isClassesInList(inputList, tClass);
        return pair.getFirst() && pair.getSecond().size() == inputList.size();
    }
    public static <T, F> T parseGeneric(F from)
    {
        return null;
    }
}

