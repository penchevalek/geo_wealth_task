package org.penchevalek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WordsGame {

    private static final String ALL_WORDS_URL =
            "https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt";

    private static final int ALLOWED_SIZE = 9;
    private static final String A = "A";
    private static final String I = "I";

    private List<String> allWords;
    private List<String> validWords;

    public WordsGame() {
        try {
            allWords = loadAllWords();
        } catch (IOException e) {
            System.out.println("Sorry, cannot start the game");
        }
        validWords = new ArrayList<>();
    }

    public int checkAllWords() {
        System.out.println("Start checking words");
        long startTime = System.currentTimeMillis();
        allWords.parallelStream()
                .filter(word -> word.length() == ALLOWED_SIZE &&
                               (word.contains(A) ||
                                word.contains(I)))
                .filter(this::checkWord)
                .forEach(word -> validWords.add(word));
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time: " + totalTime);
        System.out.println(validWords);
        return validWords.size();
    }

    private boolean checkWord(String word) {
        if (word.equals(A) || word.equals(I)) {
            return true;
        }

        for (int i = 0; i < word.length(); i++) {
            StringBuilder wordToCheck = new StringBuilder(word);

            wordToCheck.deleteCharAt(i);

            if ((wordToCheck.indexOf(A) != -1 || wordToCheck.indexOf(I) != -1) &&
                    (wordToCheck.length() == 1 || allWords.contains(wordToCheck.toString()))) {
                if (checkWord(wordToCheck.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<String> loadAllWords() throws IOException {
        URL wordsUrl = new URL(ALL_WORDS_URL);

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(wordsUrl.openConnection().getInputStream()))) {
            return br.lines().toList();
        }
    }
}
