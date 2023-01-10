package GameMechanics;
import java.lang.Math;

public class Competitor {
    private competitorTypes type;
    private int gridPosition;
    private int previousGridPosition;
    private int previousMoveDirection = 0;
    private int gridSizeX;
    private int gridSizeY;
    private Competitor mostRecentTarget;
    private int mostRecentTargetGridLocation;

    Competitor(competitorTypes givenType, int givenGridPosition, int givenGridSizeX, int givenGridSizeY){
        type = givenType;
        gridPosition = givenGridPosition;
        gridSizeX = givenGridSizeX;
        gridSizeY = givenGridSizeY;
    }

    public Competitor mostRecentTarget(){
        return mostRecentTarget();
    }

    public int getMostRecentTargetGridLocation(){
        return mostRecentTargetGridLocation;
    }

    public competitorTypes getCompetitorType(){
        return type;
    }

    public int getGridPosition(){
        return gridPosition;
    }

    public int getPreviousMoveDirection(){
        return previousMoveDirection;
    }

    public int getPreviousGridPosition(){
        return previousGridPosition;
    }

    public int getXCoordinate(){
       int xcoordinate = gridPosition % gridSizeX;
       return xcoordinate;
    }

    public int getYCoordinate(){
        int yCoordinate = (gridPosition / gridSizeX) + 1;
        return yCoordinate;
    }

    public boolean setCompetitorType(competitorTypes newType){
        type = newType;
        return true;
    }

    public boolean setPreviousMoveDirection(int newDirection){
        previousMoveDirection = newDirection;
        return true;
    }

    public boolean moveToNewGridLocation(int newGridPosition){
        previousGridPosition = gridPosition;
        gridPosition = newGridPosition;
        
        return true;
    }

    public void setNewTarget(Competitor newTarget){
        mostRecentTarget = newTarget;
    }

    public void setNewTargetGridLocation(int newGridLocation){
        mostRecentTargetGridLocation = newGridLocation;
    }

    public int getnewDesiredGridLocation(){
        while (true){

            int newDesiredDirection = chooseNewDirection(previousMoveDirection);
            int newDesiredGridLocation = calculateNewGridPosition(newDesiredDirection);
            
            if (gridPositionIsValid(newDesiredGridLocation, newDesiredGridLocation, newDesiredDirection)){
                return newDesiredGridLocation;
            }
        }
    }

    private int calculateNewGridPosition(int desiredDirection){
        int desiredGridLocation = -1;
        
        switch(desiredDirection){
            case 0:
                return gridPosition;

            case 1:
                desiredGridLocation = (gridPosition - gridSizeX) - 1;
                return desiredGridLocation;

            case 2:
                desiredGridLocation = (gridPosition - gridSizeX);
                return desiredGridLocation;
                                 
            case 3:
                desiredGridLocation = (gridPosition - gridSizeX) + 1;
                return desiredGridLocation;
        
            case 4:
                desiredGridLocation = (gridPosition + 1);
                return desiredGridLocation;
                
            case 5:
                desiredGridLocation = (gridPosition + gridSizeX) + 1;
                return desiredGridLocation;
                
            case 6:
                desiredGridLocation = (gridPosition + gridSizeX);
                return desiredGridLocation;
                
            case 7:
                desiredGridLocation = (gridPosition + gridSizeX) - 1;
                return desiredGridLocation;

            case 8:
                desiredGridLocation = (gridPosition - 1);
                return desiredGridLocation;
        }

        return desiredGridLocation;
    }

    boolean gridPositionIsValid(int givenGridTovalidate, int originalGridPosition, int givenDirection){
        // top wall
        if (givenGridTovalidate < 0){
            return false;
        }

        // bottom wall
        else if (givenGridTovalidate >= ((gridSizeX * gridSizeY) -1)){
            return false;
        }

        // left wall
        else if (originalGridPosition % gridSizeX == 0){
            if (givenDirection == 1 || givenDirection == 8 || givenDirection == 7){
                return false;
            }
        }

        //right wall
        else if (originalGridPosition % (gridSizeX - 1) == 0 ){
            if (givenDirection == 3 || givenDirection == 4|| givenDirection == 5){
                return false;
            }
        }    
        return true;
    }

    public int chooseNewDirection(int givenDirection){
        while (true){
        int newDirection = pickRandomInt();
            if (isValidDirection(newDirection)){
                this.setPreviousMoveDirection(newDirection);
                return this.getPreviousMoveDirection();
            }
        }
    }

    private boolean isValidDirection(int newDirectionToValidate){
        if (newDirectionToValidate != previousMoveDirection){
                previousMoveDirection = newDirectionToValidate;
                return true;
        }

        return false;
    }

    private int pickRandomInt(){
        int randomInt = (int) (Math.random() * 10 % 9);
        return randomInt;
    }


}
