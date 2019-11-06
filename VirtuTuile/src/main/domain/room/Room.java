package domain.room;

import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Room {

    private ArrayList<Surface> surfaceList;

    public Room() {
        surfaceList = new ArrayList<Surface>();
    }


    public void addSurfaceToList(Surface surface) {
        this.surfaceList.add(surface);
    }

    public void addRectangularSurface(Point point, int[] xPoints, int[] yPoints, int number_of_summits) {
        RectangularSurface surface = new RectangularSurface(point, xPoints, yPoints, number_of_summits);
        this.addSurfaceToList(surface);

    }

    public void addRectangularSurface(Point point, int width, int height) {
        RectangularSurface surface = new RectangularSurface(point, width, height);
        this.addSurfaceToList(surface);
    }

    public void addIrregularSurface(Point point, int[] xPoints, int[] yPoints, int number_of_edges) {
        //TODO Code pour ajouter une surface irrégulière
    }

    public boolean isEmpty() {
        return surfaceList.isEmpty();
    }

    public ArrayList<Surface> getSurfaceList() {return surfaceList;}

    public int getNumberOfSurfaces() {
        return surfaceList.size();
    }

    void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        for (Surface item : this.surfaceList) {
            Point2D.Double point = new Point2D.Double(x, y);
            if (item.contains(point)) {
                item.switchSelectionStatus();
            } else if (!isShiftDown){
                item.unselect();
            }
        }
    }

    /*
    void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        for (Surface surface : this.surfaceList) {
            if(surface.isSelected()) {
                int[] x = surface.getxCoord();
                int[] y = surface.getyCoord();

                for (int i = 0; i < x.length; i++) {
                    surface.setxCoord((int) (x[i] + deltaX), i);
                    surface.setyCoord((int) (y[i] + deltaY), i);
                }
            }
            surface.updateSurface();
        }
    }

     */

    void setPatternToSelectedSurfaces(Cover.Pattern pattern) {
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

    public void setSelectedRectangularSurfaceDimensions(double[] dimensions) {
        for (Surface surface: this.surfaceList) {
            if (surface.isSelected()){
                surface.setDimensions(dimensions);
            }
        }
    }

    public void setGroutToSelectedSurfaces(Grout grout) {
        for (Surface surface : this.surfaceList) {
            surface.getCover().setGrout(grout);
        }
    }

    public void setTileToSelectedSufaces(Tile tile) {
        for (Surface surface : this.surfaceList) {
            surface.getCover().setTile(tile);
        }
    }
}
