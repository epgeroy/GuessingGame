package com.codingame.game;
import com.codingame.gameengine.core.AbstractPlayer;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Referee extends AbstractReferee {
    // Uncomment the line below and comment the line under it to create a Solo Game
    // @Inject private SoloGameManager<Player> gameManager;
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;

    private int n;
    private int maxThreshold = 100;
    private ArrayList<ArrayList<String>> playersHistory;

    @Override
    public void init() {
        Random random = new Random();
        this.n = random.nextInt(100);

        gameManager.addToGameSummary(String.format("the secret number is %d", this.n));

        playersHistory = new ArrayList<>(gameManager.getPlayerCount());

        for(int i = 0; i < gameManager.getPlayerCount(); i++){
            playersHistory.add(new ArrayList<>());
        }

        for (ArrayList<String> playerHistory :
                playersHistory) {
            playerHistory.add(String.format("%d", maxThreshold));
        }
    }

    @Override
    public void gameTurn(int turn) {
        int playerInTurnIndex = turn % gameManager.getPlayerCount();
        Player currentPlayer = gameManager.getPlayer(playerInTurnIndex);

        currentPlayer.sendInputLine(playersHistory.get(playerInTurnIndex).get(playersHistory.get(playerInTurnIndex).size() - 1));

        currentPlayer.execute();

        try{
            String output = currentPlayer.getOutputs().get(0);
            int guessedNumber = Integer.parseInt(output);

            gameManager.addToGameSummary(String.format("Player %s guessed %d", currentPlayer.getNicknameToken(), guessedNumber));

            if(guessedNumber == n){
                gameManager.addToGameSummary(String.format("Player %s won!!!", currentPlayer.getNicknameToken()));
                playersHistory.get(playerInTurnIndex).add("0");
                currentPlayer.setScore(1);
                gameManager.endGame();
            }else if(guessedNumber < n){
                gameManager.addToGameSummary(String.format("Player %s guessed to low!!!", currentPlayer.getNicknameToken()));
                playersHistory.get(playerInTurnIndex).add("-1");
            }else {
                gameManager.addToGameSummary(String.format("Player %s guessed to high!!!", currentPlayer.getNicknameToken()));
                playersHistory.get(playerInTurnIndex).add("1");
            }
        }catch (AbstractPlayer.TimeoutException e){
            gameManager.addToGameSummary(gameManager.formatErrorMessage(currentPlayer.getNicknameToken() + " timeout!"));
            currentPlayer.deactivate(currentPlayer.getNicknameToken() + " timeout!");
            currentPlayer.setScore(-1);
            gameManager.endGame();
        }

    }
}
