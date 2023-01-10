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

    public boolean updateTotalWins(competitorTypes victor){
        if (victor == competitorTypes.Scissors){
            scissorWins++;
            return true;
        }
        else if (victor == competitorTypes.Rock){
            rockWins++;
            return true;
        }
        else if (victor == competitorTypes.Paper){
            paperWins++;
            return true;
        }
        return false;
    }

    public ArrayList<Competitor> getScissorsList(){
        return gameGrid.getScissorsList();
    }

    public ArrayList<Competitor> getRocksList(){
        return gameGrid.getRocksList();
    }

    public ArrayList<Competitor> getPaperList(){
        return gameGrid.getPaperList();
    }

    public competitorTypes GameEndCondtions(){
        if (gameGrid.getTotalPaper() != 0 && gameGrid.getTotalRocks() == 0 && gameGrid.getTotalScissors() == 0){
            return competitorTypes.Paper;
        }
        else if (gameGrid.getTotalPaper() == 0 && gameGrid.getTotalRocks() != 0 && gameGrid.getTotalScissors() == 0){
            return competitorTypes.Rock;
        }
        else if (gameGrid.getTotalPaper() == 0 && gameGrid.getTotalRocks() == 0 && gameGrid.getTotalScissors() != 0){
            return competitorTypes.Scissors;
        }
        return null;

    }

    public void updateVictories(competitorTypes victor){
        if (gameGrid.getTotalPaper() != 0 && gameGrid.getTotalRocks() == 0 && gameGrid.getTotalScissors() == 0){
            updateTotalWins(competitorTypes.Paper);
        }
        else if (gameGrid.getTotalPaper() == 0 && gameGrid.getTotalRocks() != 0 && gameGrid.getTotalScissors() == 0){
            updateTotalWins(competitorTypes.Rock);
        }
        else if (gameGrid.getTotalPaper() == 0 && gameGrid.getTotalRocks() == 0 && gameGrid.getTotalScissors() != 0){
            updateTotalWins(competitorTypes.Scissors);
        }

    }
}