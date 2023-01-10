package GameMechanics;

import java.util.ArrayList;

public class GameLogic {
    private GameGrid gameGrid;
    private int scissorWins = 0;
    private int paperWins = 0;
    private int rockWins = 0;

    public GameLogic(){
        gameGrid = new GameGrid();
    }

    public void restartGame(){
        gameGrid = new GameGrid();
    }

    public boolean runGame(){
        gameGrid.cycleGrid();
        return true;
    }

    public int getScissorWins(){
        return scissorWins;
    }

    public int getPaperWins(){
        return paperWins;
    }

    public int getRockWins(){
        return rockWins;
    }

    public boolean updateTotalWins(competatorTypes victor){
        if (victor == competatorTypes.Scissors){
            scissorWins++;
            return true;
        }
        else if (victor == competatorTypes.Rock){
            rockWins++;
            return true;
        }
        else if (victor == competatorTypes.Paper){
            paperWins++;
            return true;
        }
        return false;
    }

    public ArrayList<Competator> getScissorsList(){
        return gameGrid.getScissorsList();
    }

    public ArrayList<Competator> getRocksList(){
        return gameGrid.getRocksList();
    }

    public ArrayList<Competator> getPaperList(){
        return gameGrid.getPaperList();
    }

    public competatorTypes GameEndCondtions(){
        if (gameGrid.getTotalPaper() != 0 && gameGrid.getTotalRocks() == 0 && gameGrid.getTotalScissors() == 0){
            return competatorTypes.Paper;
        }
        else if (gameGrid.getTotalPaper() == 0 && gameGrid.getTotalRocks() != 0 && gameGrid.getTotalScissors() == 0){
            return competatorTypes.Rock;
        }
        else if (gameGrid.getTotalPaper() == 0 && gameGrid.getTotalRocks() == 0 && gameGrid.getTotalScissors() != 0){
            return competatorTypes.Scissors;
        }
        return null;

    }

    public void updateVictories(competatorTypes victor){
        if (gameGrid.getTotalPaper() != 0 && gameGrid.getTotalRocks() == 0 && gameGrid.getTotalScissors() == 0){
            updateTotalWins(competatorTypes.Paper);
        }
        else if (gameGrid.getTotalPaper() == 0 && gameGrid.getTotalRocks() != 0 && gameGrid.getTotalScissors() == 0){
            updateTotalWins(competatorTypes.Rock);
        }
        else if (gameGrid.getTotalPaper() == 0 && gameGrid.getTotalRocks() == 0 && gameGrid.getTotalScissors() != 0){
            updateTotalWins(competatorTypes.Scissors);
        }

    }
}