package Parsers;

import java.io.*;
import java.util.*;

public class FileParser {
    public static class ParseSettings {
        public enum Settings {
            ARGUMENTS, CONFIGURATIONS;
        }
        protected HashMap<String, ArrayList<String>> synonyms;
        protected static Settings settings;
        public static void setSettings(String pathToSettingsFile) throws IOException {
            LinkedList<String> linesList = new LinkedList<>();
            Scanner fileScanner = new Scanner(new File(pathToSettingsFile));
            while (fileScanner.hasNextLine())
                linesList.add(fileScanner.nextLine());

        }
        public static HashMap<String, Double> getArgumentsTable()
        {


            return new HashMap<>();
        }
    }

}
