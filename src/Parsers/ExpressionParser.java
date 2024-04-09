package Parsers;

import java.util.*;
import MathModule.*;
import MathModule.LinearAlgebra.Point2D;
import OtherThings.*;


public class ExpressionParser {
    /*------------------------------------------------------------------
     * PARSER RULES
     *------------------------------------------------------------------*/
//    analyseFunction : NAME '(' (analyseExpression (',' analyseExpression)+)? ')'
//
//    analyseExpression : analysePlusMinus * EOF ;
//
//    analysePlusMinus: analyseMultiDiv ( ( '+' | '-' ) analyseMultiDiv )* ;
//
//    analyseMultiDiv : analyseFactor ( ( '*' | '/' ) analyseFactor )* ;
//
//    analyseFactor : function | unary | NUMBER | VARIABLE | '(' expression ')' ;
//
//    unary : '-' analyseFactor

    public static HashMap<String, MathFunction> functionMap = getFunctionMap();
    public static HashMap<String, Double> variableMap;

    public static HashMap<String, MathFunction> getFunctionMap()
    {
        HashMap<String, MathFunction> functionTable = new HashMap<>();
        functionTable.put("pow", arguments -> {
            if (arguments.size() != 2)
                throw new RuntimeException(PrettyOutput.ERROR + "ОШИБКА. " +
                        "ожидаемое число аргументов функции: 2\n" + "Получено: " + arguments.size());
            return new Point2D(arguments.get(0), Math.pow(arguments.get(0), arguments.get(1)));
        });
        functionTable.put("sin", arguments -> {
            if (arguments.size() != 1)
                throw new RuntimeException(PrettyOutput.ERROR + "ОШИБКА. " +
                        "ожидаемое число аргументов функции: 1\n" + "Получено: " + arguments.size());
            return new Point2D(arguments.get(0), Math.sin(arguments.get(0)));
        });
        functionTable.put("cos", arguments -> {
            if (arguments.size() != 1)
                throw new RuntimeException(PrettyOutput.ERROR + "ОШИБКА. " +
                        "ожидаемое число аргументов функции: 1\n" + "Получено: " + arguments.size());
            return new Point2D(arguments.get(0), Math.cos(arguments.get(0)));
        });
        functionTable.put("tan", arguments -> {
            if (arguments.size() != 1)
                throw new RuntimeException(PrettyOutput.ERROR + "ОШИБКА. " +
                        "ожидаемое число аргументов функции: 1\n" + "Получено: " + arguments.size());
            return new Point2D(arguments.get(0), Math.tan(arguments.get(0)));
        });
        functionTable.put("exp", arguments -> {
            if (arguments.size() != 1)
                throw new RuntimeException(PrettyOutput.ERROR + "ОШИБКА. " +
                        "ожидаемое число аргументов функции: 1\n" + "Получено: " + arguments.size());
            return new Point2D(arguments.get(0), Math.exp(arguments.get(0)));
        });
        functionTable.put("sqrt", arguments -> {
            if (arguments.size() != 1)
                throw new RuntimeException(PrettyOutput.ERROR + "ОШИБКА. " +
                        "ожидаемое число аргументов функции: 1\n" + "Получено: " + arguments.size());
            return new Point2D(arguments.get(0), Math.sqrt(arguments.get(0)));
        });
        return functionTable;
    }

    public static HashMap<String, Double> setVariableMap() {
        return null;
    }

    public enum LexemeType {
        LEFT_BRACKET, RIGHT_BRACKET,
        OP_PLUS, OP_MINUS, OP_MUL, OP_DIV,
        NUMBER, VARIABLE, NAME, COMMA,
        EOF;
    }
    public static class Lexeme {
        LexemeType type;
        String value;
        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }
        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }
        @Override
        public String toString() {
            return "Lexeme{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
    public static class LexemeBuffer {
        private int pos;

        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }
    public static List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos < expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    continue;
                case ',':
                    lexemes.add(new Lexeme(LexemeType.COMMA, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            if (c >= 'a' && c <= 'z'
                                    || c >= 'A' && c <= 'Z') {
                                StringBuilder sb = new StringBuilder();
                                do {
                                    sb.append(c);
                                    pos++;
                                    if (pos >= expText.length())
                                        break;
                                    c = expText.charAt(pos);
                                } while (c >= 'a' && c <= 'z'
                                        || c >= 'A' && c <= 'Z');

                                if (functionMap.containsKey(sb.toString())) {
                                    lexemes.add(new Lexeme(LexemeType.NAME, sb.toString()));
                                } else if (c != '(') {
                                    lexemes.add(new Lexeme(LexemeType.VARIABLE, sb.toString()));
                                } else {
                                    throw new RuntimeException("Unexpected character: " + c);
                                }
                            }
                        } else {
                            pos++;
                        }
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    public static double analyseExpression(LexemeBuffer lexemes, double variable) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return 0;
        } else {
            lexemes.back();
            return analysePlusMinus(lexemes, variable);
        }
    }

    public static double analysePlusMinus(LexemeBuffer lexemes, double variable) {
        double value = analyseMultiDiv(lexemes, variable);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_PLUS:
                    value += analyseMultiDiv(lexemes, variable);
                    break;
                case OP_MINUS:
                    value -= analyseMultiDiv(lexemes, variable);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case COMMA:
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
            }
        }
    }

    public static double analyseMultiDiv(LexemeBuffer lexemes, double variable) {
        double value = analyseFactor(lexemes, variable);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_MUL:
                    value *= analyseFactor(lexemes, variable);
                    break;
                case OP_DIV:
                    value /= analyseFactor(lexemes, variable);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case COMMA:
                case OP_PLUS:
                case OP_MINUS:
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
            }
        }
    }

    public static double analyseFactor(LexemeBuffer lexemes, double variable) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case NAME:
                lexemes.back();
                return analyseFunction(lexemes, variable);
            case OP_MINUS:
                double value = analyseFactor(lexemes, variable);
                return -value;
            case NUMBER:
                return Integer.parseInt(lexeme.value);
            case LEFT_BRACKET:
                value = analysePlusMinus(lexemes, variable);
                lexeme = lexemes.next();
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
                }
                return value;
            case VARIABLE:
                return variable;
            default:
                throw new RuntimeException("Unexpected token: " + lexeme.value
                        + " at position: " + lexemes.getPos());
        }
    }

    public static double analyseFunction(LexemeBuffer lexemeBuffer, double variable) {
        String name = lexemeBuffer.next().value;
        Lexeme lexeme = lexemeBuffer.next();

        if (lexeme.type != LexemeType.LEFT_BRACKET) {
            throw new RuntimeException("Wrong function call syntax at " + lexeme.value);
        }

        ArrayList<Double> args = new ArrayList<>();

        lexeme = lexemeBuffer.next();
        if (lexeme.type != LexemeType.RIGHT_BRACKET) {
            lexemeBuffer.back();
            do {
                args.add(analyseExpression(lexemeBuffer, variable));
                lexeme = lexemeBuffer.next();

                if (lexeme.type != LexemeType.COMMA && lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Wrong function call syntax at " + lexeme.value);
                }

            } while (lexeme.type == LexemeType.COMMA);
        }
        return functionMap.get(name).function(args).getY();
    }
    public static class AnalyseString {
        public static boolean isDigit(String string)
        { return string.matches("[-+]?\\d+"); }
        public static String findNotDigitCharPos(String string)
        {
            StringBuilder notDigitCharsMessage = new StringBuilder();
            char[] charsOfString = string.toCharArray();
            for (var ch : charsOfString)
                if (!isDigit(ch + ""))
                    notDigitCharsMessage.append("not digit char: ").append(ch).append(" at: ").
                            append(string.indexOf(ch)).append("\n");
            return notDigitCharsMessage.toString();
        }
        public static boolean isLetter(String string)
        { return !string.toLowerCase().equals(string.toUpperCase()); }
        public static String findNotLetterCharPos(String string)
        {
            StringBuilder notLetterCharsMessage = new StringBuilder();
            char[] charsOfString = string.toCharArray();
            for (var ch : charsOfString)
                if (!isLetter(ch + ""))
                    notLetterCharsMessage.append("not letter char: ").append(ch).append(" at: ").
                            append(string.indexOf(ch)).append("\n");
            return notLetterCharsMessage.toString();
        }
    }
}
