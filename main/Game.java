package main;

import GameMechanics.GameLogic;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 2;
    private final int GAME_CYCLE_TIME = 2;

    private GameLogic gameLogic;

    private boolean onReplayScreen = false;
    private boolean onWelcomeScreen = true;

    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gameLogic = new GameLogic();
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        
        while (true){
            long now = System.nanoTime();
            if (now - lastFrame >= timePerFrame){
                if (onWelcomeScreen){
                    gamePanel.welComeScreenOn();
                    gamePanel.repaint();
                }
    
                if (gamePanel.hasAnyKeyBeenPressed()){
                    endPreviousScreens();
                    gamePanel.restartGame();
                    runOneGame();
                    onReplayScreen = true;

                    gamePanel.updateTotalWins(gameLogic.getScissorWins(), gameLogic.getRockWins(), gameLogic.getPaperWins());
                }
    
                if (onReplayScreen){
                    gamePanel.repaint();
    
                }

                lastFrame = now;
            }
        }
    }

    private void endPreviousScreens(){
        gamePanel.welcomeScreenOff();
        onReplayScreen = false;
        onWelcomeScreen = false;
    }

    private void runOneGame(){
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerCycle = 1000000000.0 / GAME_CYCLE_TIME;
        long lastFrame = System.nanoTime();
        long gameCycle = System.nanoTime();
        long now = System.nanoTime();
        
        gamePanel.startGame();
        gameLogic.restartGame();

        while(true){
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame){
                gamePanel.repaint();
                lastFrame = now;
            }

            if (now - gameCycle >= timePerCycle){
                gameLogic.runGame();
                gamePanel.updateAllCompetitorLists(gameLogic.getScissorsList(), gameLogic.getRocksList(), gameLogic.getPaperList());
                gameCycle = now;
            }

            if (gameEndCondition()){
                gamePanel.repaint();
                break;
            }
        }  

        gamePanel.endGame();

    }

    public boolean gameEndCondition(){
        if (gameLogic.GameEndCondtions() != null){
            gamePanel.updateVictor(gameLogic.GameEndCondtions());
            gameLogic.updateVictories(gameLogic.GameEndCondtions());
            return true;
        }
        return false;

    }

}
