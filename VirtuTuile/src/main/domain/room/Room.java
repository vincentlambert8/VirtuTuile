package domain.room;

import domain.room.pattern.*;
import domain.room.surface.ElementarySurface;
import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;
import gui.MainWindow;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;


public class Room {

    private ArrayList<Surface> surfaceList;
    private ArrayList<Surface> surfaceProjectionList;
    private ArrayList<TileType> tileTypeList;

    public Room() {
        surfaceList = new ArrayList<Surface>();
        surfaceProjectionList = new ArrayList<Surface>();
        tileTypeList = new ArrayList<TileType>();
    }

    public void addSurfaceToList(Surface surface) {
        this.surfaceList.add(surface);
    }

    public void addSurfaceToProjectionList(Surface surface) {
        this.surfaceProjectionList.add(surface);
    }

    public void addRectangularProjection(Point point, double[] xPoints, double[] yPoints) {
        Surface surface = new Surface(xPoints, yPoints, 4);
        this.addSurfaceToProjectionList(surface);
    }
    /*
    public void addRectangularProjection(Point point, int[] xPoints,int[] yPoints) {
        RectangularSurface rectangularSurfaceProjection = new RectangularSurface(point, xPoints, yPoints);
        Surface surfaceProjection = new Surface(point);
        surfaceProjection.addElementaryWholeSurface(rectangularSurfaceProjection);
        surfaceProjection.updatePolygon();
        this.addSurfaceToProjectionList(surfaceProjection);
    }

     */

    public void addRectangularSurface(Point point, double[] xPoints, double[] yPoints) {
        Surface surface = new Surface(xPoints, yPoints, 4);
        this.addSurfaceToList(surface);
    }
/*
    public void addRectangularSurface(Point point, int[] xPoints, int[] yPoints) {
        RectangularSurface rectangularSurface = new RectangularSurface(point, xPoints, yPoints);
        Surface surface = new Surface(point);
        surface.addElementaryWholeSurface(rectangularSurface);
        surface.updatePolygon();
        this.addSurfaceToList(surface);
    }

 */

/*
    public void addRectangularSurface(Point point, int width, int height) {
        RectangularSurface rectangularSurface = new RectangularSurface(point, width, height);
        Surface surface = new Surface(point);
        surface.addElementaryWholeSurface(rectangularSurface);
        this.addSurfaceToList(surface);
    }

 */

    public void addIrregularSurface(Point point, int[] xPoints, int[] yPoints, int number_of_edges) {
        //TODO Code pour ajouter une surface irrégulière
    }

    public boolean isEmpty() {
        return surfaceList.isEmpty();
    }

    public ArrayList<Surface> getSurfaceList() {
        return surfaceList;
    }

    public ArrayList<Surface> getSurfaceProjectionList() {
        return surfaceProjectionList;
    }

    public void clearSurfaceProjectionList() {
        this.surfaceProjectionList.clear();
    }

    public int getNumberOfSurfaces() {
        return surfaceList.size();
    }


    void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        for (int i = surfaceList.size() - 1; i >= 0; i--) {
            if (surfaceList.get(i).getAreaTest().contains(x, y)) {
                this.switchSelectionStatusIfContains(x, y, isShiftDown, surfaceList.get(i));
                break;
            }
            else {
                surfaceList.get(i).unselect();
            }
        }
        /*
        for (Surface surfaceInRoom : this.surfaceList) {
            this.switchSelectionStatusIfContains(x, y, isShiftDown, surfaceInRoom);
        }
         */
    }

    private void switchSelectionStatusIfContains(double x, double y, boolean isShiftDown, Surface surfaceInRoom) {
        Point2D.Double point = new Point2D.Double(x, y);
        //TODO changer le OR pour une meilleure condition
        if (surfaceInRoom.getAreaTest().contains(point)) {
            surfaceInRoom.switchSelectionStatus();
        }
        else if (!isShiftDown){
            surfaceInRoom.unselect();
        }
    }

    public void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        for (Surface surfaceInRoom : this.surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfaceInRoom.translate(deltaX, deltaY);
            }
        }
    }

    /*
    public void updateSelectedSurfacesPositions(double deltaX, double deltaY, double pixelX, double pixelY) {
        for (Surface surfaceInRoom : this.surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfaceInRoom.translate(deltaX, deltaY, pixelX, pixelY);
            }
        }
    }

     */
    // Refactored par updateSelectedSurfacesPositions() en haut

/*
    private void updateSurfacePositions(double deltaX, double deltaY, Surface surface) {
            int[] x = surface.getPolygon().xpoints;
            int[] y = surface.getPolygon().ypoints;

            for (int i = 0; i < x.length; i++) {
                x[i] = (int)(x[i] + deltaX);
                y[i] = (int)(y[i] + deltaY);
            }

            for (ElementarySurface elementarySurface : surface.getWholeSurfaces()) {
                int[] xPoints = elementarySurface.xpoints;

                for (int i = 0; i < xPoints.length; i++) {
                    elementarySurface.xpoints[i] = (int)(x[i] + deltaX);
                    elementarySurface.ypoints[i] = (int)(y[i] + deltaY);
                }
                elementarySurface.updateElementarySurface();
            }
            surface.updateSurface();
    }
*/

    public boolean surfaceSelecte(){
        boolean auMoinsUne = false;
        for(Surface surface: surfaceList){
            if(surface.isSelected()){
                auMoinsUne = true;
                break;
            }
        }
        return auMoinsUne;
    }


    void setPatternToSelectedSurfaces(Pattern pattern) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.getCover().setPattern(pattern);
            }
        }
    }

    void addCoverToSelectedSurfaces(Cover cover){
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.setCover(cover);
            }
        }
    }


    public double[] getSelectedRectangularSurfaceDimensions() {
        double[] dimensionList = new double[2];
        for (Surface surface : this.surfaceList){
            if (surface.isSelected()){
                dimensionList[0] = surface.getWidth();
                dimensionList[1] = surface.getHeight();
            }
        }
        return dimensionList;
    }

    /*

    public void setSelectedRectangularSurfaceDimensions(double[] dimensions) {
        for (Surface surface: this.surfaceList) {
            if (surface.isSelected()){
                double deltaW = dimensions[0] - surface.getWidth();
                double deltaH = dimensions[1] - surface.getHeight();

                surface.getPolygon().xpoints[1] += deltaW;
                surface.getPolygon().xpoints[2] += deltaW;
                surface.getPolygon().ypoints[2] += deltaH;
                surface.getPolygon().ypoints[3] += deltaH;

                surface.updateSurface();
            }
        }
    }

     */


    public void deleteSurface(){
        for(int i = this.surfaceList.size() - 1; i >= 0; i--){
            if(this.surfaceList.get(i).isSelected()){
                surfaceList.remove(i);
            }
        }
    }

    public void setGroutToSelectedSurfaces(Grout grout) {
        for (Surface surface : this.surfaceList) {
            surface.getCover().setGrout(grout);
        }
    }

    public void setTileToSelectedSufaces(float width, float height, Color color, String name, int nbrTilesPerBox) {
        TileType tileType = new TileType(color, width, height, name, nbrTilesPerBox);
        for (Surface surface : this.surfaceList) {
            surface.setTileType(tileType);
        }
    }

    public void setMeasurementMode(MainWindow.MeasurementUnitMode mode) {
        for (Surface surface : surfaceList) {
            surface.setMeasurementMode(mode);
        }
    }
    public void setGroutColor(Color color){
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected() && surface.isCovered()) {
                surface.setColor(color);
            }
        }
    }


    public void setSelectedSurfaceColor(Color color) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.setColor(color);
            }
        }
    }

    public Color getSelectedSurfaceColor() {
        Color color = Color.WHITE;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : this.surfaceList) {
                if (surface.isSelected()) {
                    color = surface.getColor();
                }
            }
        }
        return color;
    }

    public void setSelectedSurfaceWidth(double enteredWidth) {
        int counterOfSelectedSurfaces = 0;
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                counterOfSelectedSurfaces += 1;
                if (counterOfSelectedSurfaces > 1) {
                    break;
                }
            }
        }
        if (counterOfSelectedSurfaces == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected()) {
                    surfaceInRoom.setWidth(enteredWidth);
                }
            }
        }
    }

    public void setSelectedSurfaceHeight(double height) {
        int counterOfSelectedSurfaces = 0;
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                counterOfSelectedSurfaces += 1;
                if (counterOfSelectedSurfaces > 1) {
                    break;
                }
            }
        }
        if (counterOfSelectedSurfaces == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected()) {
                    surfaceInRoom.setHeight(height);
                }
            }
        }
    }

    public Dimension getSelectedSurfaceDimensions() {
        Dimension dimension = new Dimension(0,0);
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected()) {
                    dimension =  surfaceInRoom.getDimensions();
                }
            }
        }
        return dimension;
    }

    public int getNumberOfSelectedSurfaces(){
        int count = 0;
        for(Surface surface:surfaceList){
            if(surface.isSelected()){
                count++;
            }
        }
        return count;
    }

    public boolean surfaceInTouch(){
        boolean areIntersect = true;
        ArrayList<Surface> surfacesToCombine = getSurfaceToCombine();
        Surface baseSurface = surfacesToCombine.get(0);
        surfacesToCombine.remove(0);

        for (Surface surface : surfacesToCombine) {
            if (!baseSurface.intersect(surface.getAreaTest())) {
                areIntersect = false;
            }
        }
        return areIntersect;
    }

    public void combineSelectedSurface() {
        ArrayList<Surface> surfacesToCombine = getSurfaceToCombine();

        Surface baseSurface = surfacesToCombine.get(0);
        surfacesToCombine.remove(0);

        for (Surface surface : surfacesToCombine) {
            if (baseSurface.intersect(surface.getAreaTest())) {
                if(surface.isHole()) {
                    System.out.println("ABFJS");
                    baseSurface.setHole(surface);
                    surfaceList.remove(surface);
                }
                else {
                    baseSurface.merge(surface);
                    surfaceList.remove(surface);
                }
            }
        }
    }

    private ArrayList<Surface> getSurfaceToCombine() {
        ArrayList<Surface> surfacesToCombine = new ArrayList<Surface>();
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfacesToCombine.add(surfaceInRoom);
            }
        }
        return surfacesToCombine;
    }

    public void createTileFromUserInput(Color color, float width, float height, String name, int nbrTilesPerBox) {
        TileType tileType = new TileType(color, width, height, name, nbrTilesPerBox);
        tileTypeList.add(tileType);
    }

    public ArrayList<TileType> getTileList() {
        return tileTypeList;
    }





    public void setSelectedTileToSelectedSurface(TileType selectedTileType) {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfaceInRoom.setTileType(selectedTileType);
            }

            if (surfaceInRoom.isCovered()) {
                surfaceInRoom.getPattern().generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest());
            }
        }
    }

    public void setStraightPatternToSelectedSurface() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                StraightPattern straightPattern = new StraightPattern();
                straightPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest());
                surfaceInRoom.setPattern(straightPattern);
            }
        }
    }

    public void setHorizontalPatternToSelectedSurface() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                BrickPattern brickPattern = new BrickPattern();
                brickPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest());
                surfaceInRoom.setPattern(brickPattern);
            }
        }
    }

    public void setVerticalPatternToSelectedSurface() {

        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                VerticalPattern verticalPattern = new VerticalPattern();
                verticalPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest());
                surfaceInRoom.setPattern(verticalPattern);
            }
        }
    }

    public void setVerticalBrickPatternToSelectedSurface() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                VerticalBrickPattern verticalBrickPattern = new VerticalBrickPattern();
                verticalBrickPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest());
                surfaceInRoom.setPattern(verticalBrickPattern);
            }
        }
    }

    public void setSelectedSurfaceAsHole() {
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected())
                    surfaceInRoom.setIsHole();
            }
        }
    }

    public void setSelectedSurfaceAsWhole() {
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected())
                    surfaceInRoom.setIsHoleAsFalse();
            }
        }
    }

    public boolean getIfSelectedSurfaceIsAHole() {
        boolean isAHole = false;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : this.surfaceList) {
                if (surface.isSelected()) {
                    isAHole = surface.isHole();
                }
            }
        }
        return isAHole;
    }

    public boolean checkIfMouseAboveTile(int xPos, int yPos) {
        boolean mouseIsAboveTile = false;
        for (Surface surface : surfaceList) {
            for (Tile tile : surface.getPattern().getVirtualTileList()) {
                if (tile.contains(xPos, yPos)) {
                    mouseIsAboveTile = true;
                }
            }
        }
        return mouseIsAboveTile;
    }

    public ArrayList<Double> getTileDimensions(int xPos, int yPos) {
        ArrayList<Double> array = new ArrayList<Double>();
        for (Surface surface : surfaceList) {
            for (Tile tile : surface.getPattern().getVirtualTileList()) {
                if (tile.contains(xPos, yPos)) {
                    array.add(0, tile.getWidth());
                    array.add(1, tile.getHeight());
                }
            }
        }
        return array;
    }

    public void setSelectedSurfaceGroutWidth(double width) {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfaceInRoom.setGroutWidth(width);
            }
        }
    }

    public double getSelectedSurfaceGroutWidth() {
        double width = 0d;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                if (surface.isSelected()) {
                    width = surface.getGroutWidth();
                }
            }
        }
        return width;
    }

    public void snapSelectedSurfaceToGrid(double gridGap) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                double[] topLeftCornerPos = surface.getTopLeftPos();
                int horizontalGridSquare = (int) (topLeftCornerPos[0] / gridGap);
                int verticalGridSquare = (int) (topLeftCornerPos[1] / gridGap);

                Point2D[] gridSquareCorners = getGridSquareCorners(horizontalGridSquare, verticalGridSquare, gridGap);
                Point2D closestCorner = getClosestCorner(surface, gridSquareCorners);

                surface.snapToPoint(closestCorner);

            }
        }
    }

    private Point2D[] getGridSquareCorners(double horizontalGridSquare, double verticalGridSquare, double gridGap) {
        double[] gridSquarePos = getGridSquarePos(horizontalGridSquare, verticalGridSquare, gridGap);

        Point2D topLeft = new Point2D.Double(gridSquarePos[0], gridSquarePos[2]);
        Point2D topRight = new Point2D.Double(gridSquarePos[1], gridSquarePos[2]);
        Point2D bottomRight = new Point2D.Double(gridSquarePos[1], gridSquarePos[3]);
        Point2D bottomLeft = new Point2D.Double(gridSquarePos[0], gridSquarePos[3]);

        return new Point2D[]{topLeft, topRight, bottomRight, bottomLeft};
    }

    private double[] getGridSquarePos(double horizontalGridSquare, double verticalGridSquare, double gridGap) {
        double firstX = horizontalGridSquare * gridGap;
        double secondX = (horizontalGridSquare  + 1) * gridGap;
        double firstY = (verticalGridSquare + 1) * gridGap;
        double secondY = verticalGridSquare * gridGap;

        return new double[]{firstX, secondX, firstY, secondY};
    }

    private Point2D getClosestCorner(Surface surface, Point2D[] gridSquareCorners) {
        double[] distanceFromSurfaceCorner = new double[4];
        for (int i = 0; i < 4; i++) {
            distanceFromSurfaceCorner[i] = surface.getDistanceFromPoint(gridSquareCorners[i]);
        }
        int closestCornerIndex = getMinimumElementIndex(distanceFromSurfaceCorner);
        return gridSquareCorners[closestCornerIndex];
    }

    private int getMinimumElementIndex(double[] unsortedArray) {
        double[] sortedArray = new double[4];
        System.arraycopy(unsortedArray, 0, sortedArray, 0, 4);
        Arrays.sort(sortedArray);
        for (int i = 0; i < sortedArray.length; i++) {
            if (unsortedArray[i] == sortedArray[0]) {
                return i;
            }
        }
        return 0;
    }

    public float getCurrentTileWidth() {
        float tileWidth = 0;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                tileWidth = surface.getTileType().getWidth();
            }
        }
        return tileWidth;
    }

    public float getCurrentTileHeight() {
        float tileHeight = 0;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                tileHeight = surface.getTileType().getHeight();
            }
        }
        return tileHeight;
    }

    public String getCurrentTileName() {
        String name = "";
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                name = surface.getTileType().getName();
            }
        }
        return name;
    }

    public Color getCurrentTileColor() {
        Color color = Color.WHITE;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                color = surface.getTileType().getColor();
            }
        }
        return color;
    }

    public int getCurrentTileNumberPerBox() {
        int nbrOfTilePerBox = 0;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                nbrOfTilePerBox = surface.getTileType().getNbrTilesPerBox();
            }
        }
        return nbrOfTilePerBox;
    }
}
