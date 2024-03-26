package Parsers;

import OtherThings.PrettyOutput;
import OtherThings.Procedure;
import OtherThings.UsefulThings;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.nio.file.*;
import java.util.regex.*;

public class FileParser {
    public static class SettingsParser {
        public enum Settings {
            FIELDS, PARAMETERS, CONFIGURATIONS,
            NOT_A_SETTINGS;
        }
        protected static boolean isSettingsUpload = false;
        protected static LinkedList<Settings> fileSettings = new LinkedList<>();
        protected static HashMap<String, Settings> settingsTable = new HashMap<>(){{
            this.put("param", Settings.PARAMETERS);
            this.put("config", Settings.CONFIGURATIONS);
            this.put("field", Settings.FIELDS);
            this.put("argument", Settings.FIELDS);
        }};
        protected static HashMap<Settings, LinkedList<String>> settingsAssociativeTable = new HashMap<>(){{
            for (var association : settingsTable.entrySet())
            {
                if (this.containsKey(association.getValue()))
                    this.get(association.getValue()).add(association.getKey());
                else
                    this.put(association.getValue(), new LinkedList<>(List.of(association.getKey())));
            }
        }};
        public static LinkedList<Settings> getFileSettings() {
            return fileSettings;
        }
        public static void setSettings(String pathToSettingsFile) throws IOException {
            if (!fileSettings.isEmpty())
                throw new RuntimeException(PrettyOutput.ERROR + "Конфигурация файла " + PrettyOutput.COMMENT +
                        pathToSettingsFile + PrettyOutput.ERROR + " уже установлена" + PrettyOutput.RESET);
            List<String> linesList = Files.readAllLines(Paths.get(pathToSettingsFile));
            Pattern settingLinePattern = Pattern.compile("^#+[\\w\\s]+");
            for (var line : linesList)
            {
                if (settingLinePattern.matcher(line).matches())
                {
                    if (InputStreamParser.anyItemOfListFindsInString(settingsTable.keySet(), line))
                    {
                        Collection<String> inString = InputStreamParser.listItemsInString(settingsTable.keySet(), line);
                        for (var item : inString)
                            fileSettings.add(settingsTable.get(item));
                    } else
                        fileSettings.add(Settings.NOT_A_SETTINGS);
                }
            }
        }
        protected static String selectCertainSettingPartOfFile(
                List<String> textOfFile, Collection<String> settings)
        {
            StringBuilder settingsPart = new StringBuilder();
            LinkedList<String> settingsPartList = new LinkedList<>();
            ListIterator<String> iterator = textOfFile.listIterator();
            while (iterator.hasNext())
            {
                String currString = iterator.next();
                Pattern settingLinePattern = Pattern.compile("^#+[\\w\\s]+");
                if (settingLinePattern.matcher(currString).matches())
                    if (InputStreamParser.anyItemOfListFindsInString(settings, currString))
                    {
                        String nextString = iterator.next();
                        settingsPartList.add(currString);
                        while (!settingLinePattern.matcher(nextString).matches())
                        {
                            settingsPartList.add(nextString);
                            settingsPart.append(nextString);
                            if (iterator.hasNext())
                                nextString = iterator.next();
                            else break;
                        }
                        break;
                    }
            }
            textOfFile.removeAll(settingsPartList);
            return settingsPart.toString();
        }
        protected static HashMap<Settings, String> getSettingsPartTable(
                String pathToSettingsFile) throws IOException {
            HashMap<Settings, String> settingsPartTable = new HashMap<>();
            List<String> fileText = Files.readAllLines(Paths.get(pathToSettingsFile));
            for (Settings setting : fileSettings)
            {
                if (settingsAssociativeTable.containsKey(setting))
                    settingsPartTable.put(setting, selectCertainSettingPartOfFile(
                            fileText, settingsAssociativeTable.get(setting)));
                else
                    settingsPartTable.put(setting, selectCertainSettingPartOfFile(
                            fileText, new LinkedList<>()));
            }
            return settingsPartTable;
        }
        public static HashMap<String, Double> getParametersTable(String pathToSettingsFile) throws IOException {
            try {
                setSettings(pathToSettingsFile);
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
            HashMap<Settings, String> settingsPartTable = getSettingsPartTable(pathToSettingsFile);
            HashMap<String, Double> parametersTable = new HashMap<>();
            if (settingsPartTable.containsKey(Settings.PARAMETERS))
            {
                String parametersPart = settingsPartTable.get(Settings.PARAMETERS);
                String[] parameters = parametersPart.split(";");
                for (var parameter : parameters)
                {
                    if (!parameter.isEmpty())
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
        public static HashMap<String, Object> getFieldsTable(
                String pathToSettingsFile, Object object) throws IOException {
            try {
                setSettings(pathToSettingsFile);
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
            HashMap<Settings, String> settingsPartTable = getSettingsPartTable(pathToSettingsFile);
            HashMap<String, Object> fieldsTable = new HashMap<>();
            List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
            if (settingsPartTable.containsKey(Settings.FIELDS))
            {
                String fieldsPart = settingsPartTable.get(Settings.FIELDS);
                String[] fieldsFromFile = fieldsPart.split(";");
                for (var fieldFromFile : fieldsFromFile)
                {
                    if (!fieldFromFile.isEmpty()) {
                        String name = fieldFromFile.split("=")[0].strip();
                        Object value = Double.parseDouble(fieldFromFile.split("=")[1].strip());
                        if (InputStreamParser.stringMatchesAnyItemOfList(
                                UsefulThings.map(fields, Field::getName), name, "\\s+"))
                            fieldsTable.put(name, value);
                    }
                }
            }
            return fieldsTable;
        }
        public static void updateSettingsTable(String association, Settings settings)
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
