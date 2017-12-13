package sweeper;
class Flag {
    private Matrix flagMap;
    private Coord flagedToBox;
    private int countOfClosedBoxes;
    void start(){
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y ;
    }

    Box get(Coord coord){
        return flagMap.get(coord);
    }

    public void setOpenedToBox(Coord openedToBox) {
        flagMap.set(openedToBox, Box.OPENED);
        countOfClosedBoxes --;
    }

    public void setFlagedToBox(Coord flagedToBox) {
        flagMap.set(flagedToBox, Box.FLAGED);
    }

    public void taggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)){
            case FLAGED: setClosedToBox(coord); break;
            case CLOSED: setFlagedToBox(coord); break;
        }
    }

    public void setClosedToBox(Coord closedToBox) {
        flagMap.set(closedToBox, Box.CLOSED);
    }

    int getCountOfClosetBoxes() {
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    public void setOpenedToClosedBombBox(Coord coord1) {
        if (flagMap.get(coord1) == Box.CLOSED)
            flagMap.set(coord1, Box.OPENED);
    }

    public void setNoBombToFlagedSafeBox(Coord coord1) {
        if (flagMap.get(coord1) == Box.FLAGED)
            flagMap.set(coord1, Box.NOBOMB);
    }

    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord)){
            if (flagMap.get(around) == Box.FLAGED){
                count ++;
            }
        }
        return count;
    }
}
