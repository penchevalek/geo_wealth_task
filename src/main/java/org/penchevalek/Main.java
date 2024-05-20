package org.penchevalek;

public class Main {

    public static void main(String[] args) {
        WordsGame wordsGame = new WordsGame();
        int result = wordsGame.checkAllWords();
        System.out.println("Founded: " + result + " words which met the criteria.");
    }
}