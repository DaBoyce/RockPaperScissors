package GameMechanics;
import java.lang.Math;
import java.util.ArrayList;

public class GameGrid {
    private Competitor[] mainGrid;
    private ArrayList<Competitor> CompetitorList;
    
    private int totalGridSize;
    private int gridSizeX = 25;
    private int gridSizeY = 25;

    private int totalCompetitors = 30;
    private int totalScissors = totalCompetitors / 3; 
    private int totalRocks = totalCompetitors / 3; 
    private int totalPaper = totalCompetitors / 3; 

    private ArrayList<Competitor> scissorsList;
    private ArrayList<Competitor> rocksList;
    private ArrayList<Competitor> paperList;



///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public GameGrid(){
        mainGrid = new Competitor[gridSizeX * gridSizeY];
        totalGridSize = gridSizeX * gridSizeY;
        CompetitorList = new ArrayList<Competitor>();

        initializeCompetitors();        
    }

    boolean initializeCompetitors(){
        initializeScissors();
        initializeRock();
        initializePaper();

        return true;
    }

    private void initializeScissors(){
        for (int i = 0; i < totalScissors; i++){
            createNewCompetitor(competitorTypes.Scissors, getRandomEmptyGridPosition());
        }
    }

    private void initializePaper(){
        for (int i = 0; i < totalPaper; i++){
            createNewCompetitor(competitorTypes.Paper, getRandomEmptyGridPosition());
        }
    }

    private void initializeRock(){
        for (int i = 0; i < totalRocks; i++){
            createNewCompetitor(competitorTypes.Rock, getRandomEmptyGridPosition());
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

    private boolean createNewCompetitor(competitorTypes CompetitorType, int gridPosition){
        Competitor newCompetitor = new Competitor(CompetitorType, gridPosition, gridSizeX, gridSizeY);
        updateGridPosition(gridPosition, newCompetitor);
        CompetitorList.add(newCompetitor);

        return true;
    }

    private boolean updateGridPosition(int position, Competitor CompetitorToUpdate){
        mainGrid[position] = CompetitorToUpdate;
        
        return true;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean updateCompetitorLists(){
        scissorsList = new ArrayList<Competitor>();
        rocksList = new ArrayList<Competitor>();
        paperList = new ArrayList<Competitor>();
        
        for (Competitor Competitor: CompetitorList){
            if (Competitor.getCompetitorType() == competitorTypes.Scissors){
                scissorsList.add(Competitor);
            }
            if (Competitor.getCompetitorType() == competitorTypes.Rock){
                rocksList.add(Competitor);
            }
            if (Competitor.getCompetitorType() == competitorTypes.Paper){
                paperList.add(Competitor);
            }
        }

        return true;
    }


    public ArrayList<Competitor> getScissorsList(){
        return  scissorsList;
    }
    public ArrayList<Competitor> getRocksList(){
        return  rocksList;
    }
    
    public ArrayList<Competitor> getPaperList(){
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
        for (Competitor Competitor: CompetitorList){
            int desiredNewGridLocation = Competitor.getnewDesiredGridLocation();
            if (newGridLocationIsempty(desiredNewGridLocation)){
                moveCompetitorToNewGridLocation(Competitor, desiredNewGridLocation);
            }
            else resolveConflict(mainGrid[desiredNewGridLocation], Competitor);
        }

        updateCompetitorLists();

    }

    private boolean moveCompetitorToNewGridLocation(Competitor Competitor, int newGridLocation){
        updateGridPosition(newGridLocation, Competitor);
        Competitor.moveToNewGridLocation(newGridLocation);
        updateGridPosition(Competitor.getPreviousGridPosition(), null);     
        
        return true;
    }

    void resolveConflict(Competitor Attacker, Competitor Defender){
        competitorTypes attackerType = Attacker.getCompetitorType();
        competitorTypes defenderType = Defender.getCompetitorType();
        
        if (attackerType == defenderType){
            return;
        }

        if (attackerType == competitorTypes.Rock){
            if (defenderType == competitorTypes.Paper){
                AttackerDefeat(Attacker, Defender);
                totalRocks--;
                totalPaper++;
                return;
            }
            else if (defenderType == competitorTypes.Scissors){
                AttackerVictory(Attacker, Defender);
                totalRocks++;
                totalScissors--;
                return;
            }
        }
        else if (attackerType == competitorTypes.Paper){
            if (defenderType == competitorTypes.Scissors){
                AttackerDefeat(Attacker, Defender);
                totalPaper--;
                totalScissors++;
                return;
            }
            else if (defenderType == competitorTypes.Rock){
                AttackerVictory(Attacker, Defender);
                totalPaper++;
                totalRocks--;
                return;
            }
        }
        else if (attackerType == competitorTypes.Scissors){
            if (defenderType == competitorTypes.Rock){
                AttackerDefeat(Attacker, Defender);
                totalScissors--;
                totalRocks++;
                return;
            }
            else if (defenderType == competitorTypes.Paper){
                AttackerVictory(Attacker, Defender);
                totalScissors++;
                totalPaper--;
                return;
            }
        }
    }

    boolean AttackerDefeat(Competitor Attacker, Competitor Defender){
        Attacker.setCompetitorType(Defender.getCompetitorType());
        return true;
    }

    boolean AttackerVictory(Competitor Attacker, Competitor Defender){
        Defender.setCompetitorType(Attacker.getCompetitorType());
        return true;
    }
}
