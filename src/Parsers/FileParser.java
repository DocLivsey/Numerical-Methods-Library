package Parsers;

import OtherThings.PrettyOutput;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.regex.*;

public class FileParser {
    public static class ParseSettings {
        public enum Settings {
            PARAMETERS, CONFIGURATIONS,
            NOT_A_SETTINGS;
        }
        protected static Settings settings;
        protected static HashMap<String, Settings> settingsTable = new HashMap<>(){{
            this.put("config", Settings.CONFIGURATIONS);
            this.put("argument", Settings.PARAMETERS);
            this.put("param", Settings.PARAMETERS);
        }};
        public static void setSettings(String pathToSettingsFile) throws IOException {
            List<String> linesList = Files.readAllLines(Paths.get(pathToSettingsFile));
            Pattern settingLinePattern = Pattern.compile("^#+[\\w\\s]+");
            for (var line : linesList)
            {
                Matcher matchLinePattern = settingLinePattern.matcher(line);
                if (matchLinePattern.matches())
                {
                    for (var key : settingsTable.keySet())
                    {
                        String[] words = line.split("\\s+");
                        if (InputStreamParser.containsInText(key, words))
                        {
                            settings = settingsTable.get(key);
                            break;
                        } else settings = Settings.NOT_A_SETTINGS;
                    }
                    break;
                }
            }
        }
        protected static LinkedList<String> selectSettingsPartOfFile(String pathToSettingsFile) throws IOException {
            LinkedList<String> settingsPart = new LinkedList<>();
            Pattern settingLinePattern = Pattern.compile("^#+[\\w\\s]+");
            List<String> linesList = Files.readAllLines(Paths.get(pathToSettingsFile));
            for (int i = 0; i < linesList.size(); i++)
            {
                Matcher matchLinePattern = settingLinePattern.matcher(linesList.get(i));
                if (matchLinePattern.matches())
                {
                    int j = i + 1;
                    matchLinePattern = settingLinePattern.matcher(linesList.get(j));
                    while (!matchLinePattern.matches())
                    {
                        settingsPart.add(linesList.get(j));
                        j++;
                        if (j >= linesList.size())
                            break;
                        matchLinePattern = settingLinePattern.matcher(linesList.get(j));
                    }
                    break;
                }
            }
            return settingsPart;
        }
        public static HashMap<String, Double> getParametersTable(String pathToSettingsFile) throws IOException {
            LinkedList<String> settingsPart = selectSettingsPartOfFile(pathToSettingsFile);
            HashMap<String, Double> parametersTable = new HashMap<>();
            setSettings(pathToSettingsFile);
            if (settings == Settings.PARAMETERS)
            {
                for (var settingsString : settingsPart)
                {
                    String[] parameters = settingsString.split("[,;]");
                    for (var parameter : parameters)
                    {
                        String name = parameter.split("=")[0].strip();
                        double value = Double.parseDouble(parameter.split("=")[1].strip());
                        parametersTable.put(name, value);
                    }
                }
            } else
                throw new RuntimeException(PrettyOutput.ERROR +
                        "Файл настроек не предназначен для считывания параметров" + PrettyOutput.RESET);
            return parametersTable;
        }
        public void updateSettingsTable(String association, Settings settings)
        {
            boolean exists = true;
            try {
                Settings.valueOf(settings.toString());
            } catch (IllegalArgumentException e) {
                exists = false;
            }
            if (exists)
                settingsTable.put(association, settings);
            else
                throw new RuntimeException(PrettyOutput.ERROR + "There is no such value in enum: " +
                        PrettyOutput.COMMENT + settings + PrettyOutput.RESET);
        } // добавление по значению из Settings нового синонима в список синонимов
    }

}
