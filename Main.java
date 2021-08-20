package readability;

import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            String text = Files.readString(Paths.get(args[0]));
            System.out.println("Words: "+ getWords(text));
            System.out.println("Sentences: " + getSentences(text));
            System.out.println("Characters: " + getCharacters(text));
            System.out.println("Syllables: " + getSyllables(text));
            System.out.println("Polysyllables: " + getPolySyllables(text));
            scoreInterface(getWords(text), getSentences(text), getCharacters(text), getSyllables(text), getPolySyllables(text));
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
    }

    public static void scoreInterface(int words, int sentences, int chars, int syllables, int polySyl) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        double ariScore = getARI(words, sentences, chars);
        double fkScore = getFK(words, sentences, syllables);
        double smogScore = getSMOG(polySyl, sentences);
        double clScore = getCL(words, sentences, chars);
        double average = ((double) readingAge(ariScore) + readingAge(fkScore) + readingAge(smogScore) + readingAge(clScore)) / 4;

        String input = scanner.nextLine();
        switch (input) {
            case "ARI":
                System.out.printf("\nAutomated Readability Index: %.2f (about %s-year-olds).\n", ariScore, readingAge(ariScore));
                break;
            case "FK":
                System.out.printf("Flesch-Kincaid readability tests: %.2f (about %s-year-olds).\n", fkScore, readingAge(fkScore));
                break;
            case "SMOG":
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).\n", smogScore, readingAge(smogScore));
                break;
            case "CL":
                System.out.printf("Coleman-Liau index: %.2f (about %s-year-olds).\n", clScore, readingAge(clScore));
                break;
            case "all":
                System.out.printf("\nAutomated Readability Index: %.2f (about %s-year-olds).\n", ariScore, readingAge(ariScore));
                System.out.printf("Flesch-Kincaid readability tests: %.2f (about %s-year-olds).\n", fkScore, readingAge(fkScore));
                System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds).\n", smogScore, readingAge(smogScore));
                System.out.printf("Coleman-Liau index: %.2f (about %s-year-olds).\n\n", clScore, readingAge(clScore));
                System.out.printf("This text should be understood in average by %.2f-year-olds.", average);
                break;
        }
    }

    public static double getCL(int words, int sentences, int chars) {
        double avgS = (double) sentences / (double) words * 100;
        double avgC = (double) chars / (double) words * 100;
        return 0.0588 * avgC - 0.296 * avgS - 15.8;
    }

    public static double getSMOG(int polySyl, int sentences) {
        return 1.043 * Math.sqrt(polySyl * ((double) 30 / sentences)) + 3.1291;
    }

    public static double getFK(int words, int sentences, int syllables) {
        return 0.39 * ((double) words / (double) sentences) + 11.8 * ((double) syllables / (double) words) - 15.59;
    }

    public static double getARI(int words, int sentences, int chars) {
        return 4.71 * ((double) chars / (double) words) + 0.5 * ((double) words / (double) sentences) - 21.43;
    }

    public static int getSyllables(String text) {
        String[] vowelReplacedString = text.replaceAll("e\\b", "x")
                .replaceAll("[aeiouy]{2,}", "a")
                .replaceAll("\\d*,\\d*", "x")
                .split(" ");
        int count = 0;
        for (String word: vowelReplacedString) {
            int numberOfSyllables = countVowels(word);
            count += numberOfSyllables;
        }
        return count;
    }

    public static int countVowels(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (letter == 'a' || letter == 'A' ||
                    letter == 'e' || letter == 'E' ||
                    letter == 'i' || letter == 'I' ||
                    letter == 'o' || letter == 'O' ||
                    letter == 'u' || letter == 'U' ||
                    letter == 'y' || letter == 'Y') {
                count++;
            }
        }
        return Math.max(1, count);
    }

    public static int getPolySyllables(String text) {
        String[] vowelReplacedString = text.replaceAll("e\\b", "x")
                .replaceAll("[aeiouy]{2}", "x")
                .replaceAll("\\d*,\\d*", "x")
                .split(" ");
        int count = 0;
        for (String word: vowelReplacedString) {
            int numberOfSyllables = countVowels(word);
            if (numberOfSyllables >= 3) {
                count++;
            }
        }
        return count;
    }

    public static int getWords(String text) {
        return text.split("\\s+").length;
    }

    public static int getSentences(String text) {
        int count = 0;
        String[] test = text.split("[!.?]+");
        for (String s : test) {
            count++;
            if (s.isBlank()) {
                count--;
            }
        }
        return count;

    }

    public static int getCharacters(String text) {
        return text.replaceAll("[ \n\t]", "").split("").length;
    }


    public static int readingAge(double score) {
        int toReturn;
        switch ((int) Math.ceil(score)) {
            case 1:
                toReturn = 6;
                break;
            case 2:
                toReturn = 7;
                break;
            case 3:
                toReturn = 8;
                break;
            case 4:
                toReturn = 9;
                break;
            case 5:
                toReturn = 10;
                break;
            case 6:
                toReturn = 11;
                break;
            case 7:
                toReturn = 12;
                break;
            case 8:
                toReturn = 13;
                break;
            case 9:
                toReturn = 14;
                break;
            case 10:
                toReturn = 15;
                break;
            case 11:
                toReturn = 16;
                break;
            case 12:
                toReturn = 17;
                break;
            case 13:
                toReturn = 18;
                break;
            default:
                toReturn = 24;
                break;
        }
        return toReturn;
    }
}
