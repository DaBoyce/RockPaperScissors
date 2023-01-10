package GameMechanics;
import java.lang.Math;
import java.util.ArrayList;

public class GameGrid {
    private Competator[] mainGrid;
    private ArrayList<Competator> CompetatorList;
    
    private int totalGridSize;
    private int gridSizeX = 25;
    private int gridSizeY = 25;

    private int totalCompetators = 30;
    private int totalScissors = totalCompetators / 3; 
    private int totalRocks = totalCompetators / 3; 
    private int totalPaper = totalCompetators / 3; 

    private ArrayList<Competator> scissorsList;
    private ArrayList<Competator> rocksList;
    private ArrayList<Competator> paperList;



///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public GameGrid(){
        mainGrid = new Competator[gridSizeX * gridSizeY];
        totalGridSize = gridSizeX * gridSizeY;
        CompetatorList = new ArrayList<Competator>();

        initializeCompetators();        
    }

    boolean initializeCompetators(){
        initializeScissors();
        initializeRock();
        initializePaper();

        return true;
    }

    private void initializeScissors(){
        for (int i = 0; i < totalScissors; i++){
            createNewCompetator(competatorTypes.Scissors, getRandomEmptyGridPosition());
        }
    }

    private void initializePaper(){
        for (int i = 0; i < totalPaper; i++){
            createNewCompetator(competatorTypes.Paper, getRandomEmptyGridPosition());
        }
    }

    private void initializeRock(){
        for (int i = 0; i < totalRocks; i++){
            createNewCompetator(competatorTypes.Rock, getRandomEmptyGridPosition());
        }
    }

    private int getRandomEmptyGridPosition(){
        while (true){
            int randomNumber = (int) (Math.random() * 100000);
            int gridPosition = randomNumber % (gridSizeX * gridSizeY);
            
            if (newGridLocationIsempty(gridPosition)){
                return gridPosition;
            }
        }
    }

    boolean newGridLocationIsempty(int newGridLocation){
        if (mainGrid[newGridLocation] == null){
            return true;
        }
        return false;
    }

    private boolean createNewCompetator(competatorTypes competatorType, int gridPosition){
        Competator newCompetator = new Competator(competatorType, gridPosition, gridSizeX, gridSizeY);
        updateGridPosition(gridPosition, newCompetator);
        CompetatorList.add(newCompetator);

        return true;
    }

    private boolean updateGridPosition(int position, Competator competatorToUpdate){
        mainGrid[position] = competatorToUpdate;
        
        return true;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean updateCompetatorLists(){
        scissorsList = new ArrayList<Competator>();
        rocksList = new ArrayList<Competator>();
        paperList = new ArrayList<Competator>();
        
        for (Competator competator: CompetatorList){
            if (competator.getCompetatorType() == competatorTypes.Scissors){
                scissorsList.add(competator);
            }
            if (competator.getCompetatorType() == competatorTypes.Rock){
                rocksList.add(competator);
            }
            if (competator.getCompetatorType() == competatorTypes.Paper){
                paperList.add(competator);
            }
        }

        return true;
    }


    public ArrayList<Competator> getScissorsList(){
        return  scissorsList;
    }
    public ArrayList<Competator> getRocksList(){
        return  rocksList;
    }
    
    public ArrayList<Competator> getPaperList(){
        return  paperList;
    }

    public int getTotalRocks(){
        return totalRocks;
    }

    public int getTotalPaper(){
        return totalPaper;
    }

    public int getTotalScissors(){
        return totalScissors;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void cycleGrid(){
        for (Competator competator: CompetatorList){
            int desiredNewGridLocation = competator.getnewDesiredGridLocation();
            if (newGridLocationIsempty(desiredNewGridLocation)){
                moveCompetatorToNewGridLocation(competator, desiredNewGridLocation);
            }
            else resolveConflict(mainGrid[desiredNewGridLocation], competator);
        }

        updateCompetatorLists();

    }

    private boolean moveCompetatorToNewGridLocation(Competator competator, int newGridLocation){
        updateGridPosition(newGridLocation, competator);
        competator.moveToNewGridLocation(newGridLocation);
        updateGridPosition(competator.getPreviousGridPosition(), null);     
        
        return true;
    }

    void resolveConflict(Competator Attacker, Competator Defender){
        competatorTypes attackerType = Attacker.getCompetatorType();
        competatorTypes defenderType = Defender.getCompetatorType();
        
        if (attackerType == defenderType){
            return;
        }

        if (attackerType == competatorTypes.Rock){
            if (defenderType == competatorTypes.Paper){
                AttackerDefeat(Attacker, Defender);
                totalRocks--;
                totalPaper++;
                return;
            }
            else if (defenderType == competatorTypes.Scissors){
                AttackerVictory(Attacker, Defender);
                totalRocks++;
                totalScissors--;
                return;
            }
        }
        else if (attackerType == competatorTypes.Paper){
            if (defenderType == competatorTypes.Scissors){
                AttackerDefeat(Attacker, Defender);
                totalPaper--;
                totalScissors++;
                return;
            }
            else if (defenderType == competatorTypes.Rock){
                AttackerVictory(Attacker, Defender);
                totalPaper++;
                totalRocks--;
                return;
            }
        }
        else if (attackerType == competatorTypes.Scissors){
            if (defenderType == competatorTypes.Rock){
                AttackerDefeat(Attacker, Defender);
                totalScissors--;
                totalRocks++;
                return;
            }
            else if (defenderType == competatorTypes.Paper){
                AttackerVictory(Attacker, Defender);
                totalScissors++;
                totalPaper--;
                return;
            }
        }
    }

    boolean AttackerDefeat(Competator Attacker, Competator Defender){
        Attacker.setCompetatorType(Defender.getCompetatorType());
        return true;
    }

    boolean AttackerVictory(Competator Attacker, Competator Defender){
        Defender.setCompetatorType(Attacker.getCompetatorType());
        return true;
    }
}
