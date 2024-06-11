package com.pleshkov;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private Map<String, String> questions;
    private String nextQuestion;
    private boolean defeated;
    private boolean victory;

    public boolean isVictory() {
        return victory;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public Game() throws IOException {
        initializeQuestions();
        startGame();
    }

    public void initializeQuestions() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String pathJsonFile = "C:\\java_projects\\JavaRushProjects\\QuestProject\\src\\main\\resources\\game.json";
        questions = mapper.readValue(new File(pathJsonFile),
                new TypeReference<Map<String, String>>(){});

    }

    public String startGame() {
        nextQuestion = "gameStart";
        return questions.get(nextQuestion);
    }

    public List<String> getCurrentOptions() {
        List<String> currentOptions = new ArrayList<>();

        switch (nextQuestion) {
            case "gameStart":
                currentOptions.add("Принять вызов НЛО");
                currentOptions.add("Отклонить вызов НЛО");
                break;
            case "acceptUFOCall":
                currentOptions.add("Подняться к капитану");
                currentOptions.add("Отказаться подниматься");
                break;
            case "climbToCaptain":
                currentOptions.add("Рассказать правду о себе");
                currentOptions.add("Солгать о себе");
                break;
        }
        return currentOptions;
    }


    public String processAnswer(String answer) {
        if (answer == null) {
            return "Ты потерял память. Принять вызов НЛО?";
        }
        switch (nextQuestion) {
            case "gameStart":
                if (answer.equalsIgnoreCase("Принять вызов НЛО")) {
                    nextQuestion = "acceptUFOCall";
                } else {
                    nextQuestion = "declineUFOCall";
                    defeated = true;
                }
                break;
            case "acceptUFOCall":
                if (answer.equalsIgnoreCase("Подняться к капитану")) {
                    nextQuestion = "climbToCaptain";
                } else {
                    nextQuestion = "refuseCaptain";
                    defeated = true;
                }
                break;
            case "climbToCaptain":
                if (answer.equalsIgnoreCase("Рассказать правду о себе")) {
                    nextQuestion = "tellTruth";
                    victory = true;
                } else {
                    nextQuestion = "tellLie";
                    defeated = true;
                }
                break;
        }
        return questions.get(nextQuestion);
    }

    public void resetGame() {
        nextQuestion = "gameStart";
        defeated = false;
        victory = false;
    }

    public boolean isGameOver() {
        return nextQuestion.equals("tellTruth") || nextQuestion.equals("declineUFOCall")
                || nextQuestion.equals("refuseCaptain") || nextQuestion.equals("tellLie");
    }
}

