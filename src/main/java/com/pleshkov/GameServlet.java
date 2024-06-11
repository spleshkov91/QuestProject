package com.pleshkov;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class GameServlet extends HttpServlet {
    private Game game;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            game = new Game();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String initialMessage = game.startGame();
        request.setAttribute("message", initialMessage);
        request.setAttribute("options", game.getCurrentOptions());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String answer = request.getParameter("answer");
        if ("true".equals(request.getParameter("reset"))) {
            game = new Game();
            answer = null;
        }

        if (answer != null) {
            String nextQuestionOrResult = game.processAnswer(answer);
            request.setAttribute("message", nextQuestionOrResult);
            List<String> options = game.getCurrentOptions();
            request.setAttribute("options", options);

            if (game.isGameOver()) {
                request.setAttribute("showRestartButton", true);
                if (game.isDefeated()) {
                    game.resetGame();
                } else if (game.isVictory()) {
                    game.resetGame();
                }
            }
        } else {
            request.setAttribute("message", "Вы потеряли память. Принять вызов НЛО?");
            List<String> options = game.getCurrentOptions();
            request.setAttribute("options", options);
        }

        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }
}
