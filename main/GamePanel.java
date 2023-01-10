package main;

import java.util.*;
import GameMechanics.Competitor;
import GameMechanics.competitorTypes;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Inputs.KeyboardInputs;

public class GamePanel extends JPanel{
    private final int Competitor_SIZES = 15;

    private int frames = 0;
    private long lastCheck = 0;
    private boolean listsUpdated = false;

    private boolean onWelcomeScreen = true;
    private boolean gameRunning = false;
    private boolean anyKeyPressed = false;

    private ArrayList<Competitor> scissorsList;
    private ArrayList<Competitor> rocksList;
    private ArrayList<Competitor> paperList;
    
    private boolean victorDeclared = false;
    private competitorTypes victor;

    private BufferedImage rockPicture;
    private BufferedImage paperPicture;
    private BufferedImage scissorsPicture;
    
    private int totalScissorWins = 0;
    private int totalRockWins = 0;
    private int totalPaperWins = 0;

    public GamePanel(){
        importRockImage();
        importPaperImage();
        importScissorsImage();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(null);
    }

    public void updateTotalWins(int scissorWins, int rockwins, int paperwins){
        totalPaperWins = paperwins;
        totalRockWins = rockwins;
        totalScissorWins = scissorWins;
    }

    
    public void paintComponent(Graphics g){
        super.paintComponent(g);        
        if (onWelcomeScreen){
            paintWelcomeScreen(g);
        }
        
        if (gameRunning){
           paintAllLists(g); 
        }

        if (victorDeclared){
            declareVictor(g, victor);
            paintTotalVictories(g);
            paintReplayScreen(g);
        }

        frames++;

        if (System.currentTimeMillis() - lastCheck >= 1000){
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + frames);
            frames = 0;
        }
    }

    public void paintTotalVictories(Graphics g){
        g.setColor(Color.BLACK);
        g.drawString("Rock Wins: " + totalRockWins, 205, 210);
        g.drawString("Paper Wins: " + totalPaperWins, 205, 220);
        g.drawString("Scissor Wins: " + totalScissorWins, 205, 230);
    
    }

    public void restartGame(){
        victorDeclared = false;
    }


    public void anyKeyPressed(){
        anyKeyPressed = true;
    }

    public boolean hasAnyKeyBeenPressed(){
        boolean hasbeenPressed = anyKeyPressed;
        anyKeyPressed = false;
        return hasbeenPressed;
    }

    public void welComeScreenOn(){
        onWelcomeScreen = true;
    }

    public void welcomeScreenOff(){
        onWelcomeScreen = false;
    }

    public void startGame(){
        gameRunning = true;
    }

    public void endGame(){
        gameRunning = false;
    }

    public void paintWelcomeScreen(Graphics g){
        paintWelcomeMessage(g);
    }

    public void paintWelcomeMessage(Graphics g){
        g.setColor(Color.BLACK);
        g.drawString("Welcome to Roshambo! \n\t Press any key to begin.", 200, 200);
    }

    public void paintReplayScreen(Graphics g){
        paintReplayMessage(g);
    }

    public void paintReplayMessage(Graphics g){
        g.setColor(Color.BLACK);
        declareVictor(g, victor);
        paintTotalVictories(g);
        g.drawString("Play Again?", 300, 300);
    }
            
    private void importRockImage(){
        InputStream is = getClass().getResourceAsStream("/Resources/Images/rockImage.jpg");
        
        try{
            rockPicture = ImageIO.read(is);
        }
        catch (Exception e) {
            throw new IllegalAccessError();
        }
    
    }

    private void importScissorsImage(){
        InputStream is = getClass().getResourceAsStream("/Resources/Images/scissorImage.png");
        
        try{
            scissorsPicture = ImageIO.read(is);
        }
        catch (Exception e) {
            throw new IllegalAccessError();
        }
    
    }

    private void importPaperImage(){
        InputStream is = getClass().getResourceAsStream("/Resources/Images/paperImage.jpg");
        
        try{
            paperPicture = ImageIO.read(is);
        }
        catch (Exception e) {
            throw new IllegalAccessError();
        }
    
    }

    public void updateVictor(competitorTypes givenVictor){
        victorDeclared = true;
        victor = givenVictor;
    }

    public void declareVictor(Graphics g, competitorTypes victor){
        g.setColor(Color.BLACK);
        g.drawString(victor.toString() + " wins!", 200, 200);
    }

    public void updateAllCompetitorLists(ArrayList<Competitor> newScissorsList, ArrayList<Competitor> newRocksList, ArrayList<Competitor> newPaperList){
        scissorsList = newScissorsList;
        rocksList = newRocksList;
        paperList = newPaperList;
        listsUpdated = true;
    }

    private void paintAllLists(Graphics g){
        if (listsUpdated){
            paintScissorsList(g);
            paintRocksList(g);
            paintPaperList(g);
        }
    }

    private void paintScissorsList (Graphics g){
        g.setColor(Color.RED);
        for (Competitor Competitor: scissorsList){
            g.drawImage(scissorsPicture, Competitor.getXCoordinate() * Competitor_SIZES, Competitor.getYCoordinate() * Competitor_SIZES, null);
            
        }
    }

    private void paintRocksList (Graphics g){
        g.setColor(Color.BLUE);
        for (Competitor Competitor: rocksList){
            g.drawImage(rockPicture, Competitor.getXCoordinate() * Competitor_SIZES, Competitor.getYCoordinate() * Competitor_SIZES, null);
        }
    }
    
    private void paintPaperList (Graphics g){
        g.setColor(Color.GREEN);
        for (Competitor Competitor: paperList){
            g.drawImage(paperPicture, Competitor.getXCoordinate() * Competitor_SIZES, Competitor.getYCoordinate() * Competitor_SIZES, null);
        }
    }

}


