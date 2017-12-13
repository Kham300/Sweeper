package sweeper;

public class Game {
    private Bomb bomb;
    private Flag flag;
    private GameState gameState;

    public Game(int cols, int rows, int bombs){
        Ranges.setSize(new Coord(cols, rows));
         bomb = new Bomb(bombs);
         flag = new Flag();
    }

    public void start(){
        bomb.start();
        flag.start();
        gameState = GameState.PLAYED;
    }

    public Box getBox(Coord coord){
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        else
        return  flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) return;
        openBox(coord);
        checkWinner();
    }

    private boolean gameOver() {
        if (gameState == GameState.PLAYED)
            return false;
        start();

        return true;

    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) return;
        flag.taggleFlagedToBox(coord);
    }

    private void checkWinner() {
        if (gameState == GameState.PLAYED)
            if (flag.getCountOfClosetBoxes() == bomb.getTotalBombs())
                gameState = GameState.WINNER;
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)){
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); return;
            case FLAGED: return;
            case CLOSED:
                switch (bomb.get(coord)){
                    case ZERO: openBoxesAround(coord);return;
                    case BOMB: openBombs(coord); return;
                    default  : flag.setOpenedToBox(coord); return;
                }
        }
    }

    void setOpenedToClosedBoxesAroundNumber(Coord coord){
        if (bomb.get(coord) != Box.BOMB)
            if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

    private void openBombs(Coord coord) {
        gameState = GameState.BOMBED;
        flag.setBombedToBox(coord);
        for (Coord coord1 : Ranges.getAllCoords()){
            if (bomb.get(coord1) == Box.BOMB){
                flag.setOpenedToClosedBombBox(coord1);
            } else {
                flag.setNoBombToFlagedSafeBox(coord1);
            }
        }
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around: Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public GameState getGameState() {
        return gameState;
    }
}
