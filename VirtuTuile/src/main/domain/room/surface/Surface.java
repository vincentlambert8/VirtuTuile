package domain.room.surface;

import domain.room.Cover;
import domain.room.Tile;
import domain.room.TileType;
import domain.room.pattern.*;
import gui.MainWindow;
import util.UnitConverter;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;

public class Surface implements Serializable {
    private Point2D.Double position;
    private Color color;
    private boolean selectionStatus = false;
    private TileType tileType;
    private Cover cover;
    private Pattern pattern;
    private boolean isCovered = false;
    private boolean mergedStatus = false;
    private boolean haveHole = false;
    private Polygon polygon;
    private MainWindow.MeasurementUnitMode measurementMode;

    private Path2D.Double area;

    private double width;
    private double height;
    private ArrayList<ElementarySurface> wholeSurfaces;
    private ArrayList<ElementarySurface> holes;
    private int numberSummit;
    private boolean isHole;
    private boolean center;

    //Attributs tests
    private double[] xPoints;
    private double[] yPoints;
    private int nPoints;
    private ArrayList<Surface> elementarySurface;
    private double groutWidth;
    private double mismatch = 0.5d;



    public ArrayList<ElementarySurface> getWholeSurfaces() {
        return wholeSurfaces;
    }

    public ArrayList<ElementarySurface> getHoles() {
        return holes;
    }

    public Surface() {
        //Constructeur vide
    }

    public Surface(Surface surfaceToCopy) {
        if(surfaceToCopy.position != null) {
            this.position = new Point2D.Double(surfaceToCopy.position.x, surfaceToCopy.position.y);
        }

        if(surfaceToCopy.color != null) {
            this.color = new Color(surfaceToCopy.color.getRGB());
        }

        this.selectionStatus = new Boolean(surfaceToCopy.selectionStatus);

        if(surfaceToCopy.tileType != null) {
            this.tileType = new TileType(surfaceToCopy.tileType);
        }

        if(surfaceToCopy.cover != null) {
            this.cover = new Cover(surfaceToCopy.cover);
        }

        // TODO make pattern class constructor accessible
        if(surfaceToCopy.pattern != null) {
            if(surfaceToCopy.pattern.name == "Brick") {
                this.pattern = new BrickPattern(surfaceToCopy.pattern);
            }
            else if(surfaceToCopy.pattern.name == "Chevron") {
                this.pattern = new ChevronPattern(surfaceToCopy.pattern);
            }
            else if(surfaceToCopy.pattern.name == "Incline") {
                this.pattern = new InclinePattern(surfaceToCopy.pattern);
            }
            else if(surfaceToCopy.pattern.name == "Square") {
                this.pattern = new SquarePattern(surfaceToCopy.pattern);
            }
            else if(surfaceToCopy.pattern.name == "Straight") {
                this.pattern = new StraightPattern(surfaceToCopy.pattern);
            }
            else if(surfaceToCopy.pattern.name == "VerticalBrick") {
                this.pattern = new VerticalBrickPattern(surfaceToCopy.pattern);
            }
            else if(surfaceToCopy.pattern.name == "Vertical") {
                this.pattern = new VerticalPattern(surfaceToCopy.pattern);
            }

        }

        this.isCovered = new Boolean(surfaceToCopy.isCovered);

        this.mergedStatus = new Boolean(surfaceToCopy.mergedStatus);

        this.haveHole = new Boolean(surfaceToCopy.haveHole);

        if(surfaceToCopy.polygon != null) {
            this.polygon = new Polygon(surfaceToCopy.polygon.xpoints, surfaceToCopy.polygon.ypoints, surfaceToCopy.polygon.npoints);
        }

//        Comment out because measurementMode is never used
//        this.measurementMode = surfaceToCopy.measurementMode;

        if(surfaceToCopy.area != null) {
            this.area = new Path2D.Double(surfaceToCopy.area);
        }

        this.width = new Double(surfaceToCopy.width);

        this.height = new Double(surfaceToCopy.height);

//        Comment out wholeSurfaces and holes because ElementarySurface is never used
        this.wholeSurfaces = surfaceToCopy.wholeSurfaces;
        this.holes = surfaceToCopy.holes;

        this.isHole = new Boolean(surfaceToCopy.isHole);

        this.center = new Boolean(surfaceToCopy.center);

        if(surfaceToCopy.xPoints != null) {
            this.xPoints = new double[surfaceToCopy.xPoints.length];
            int vectorCounter1 = new Integer(0);
            for (Double xpoints : surfaceToCopy.xPoints) {
                Double x = new Double(xpoints);
                this.xPoints[vectorCounter1] = x;
                vectorCounter1++;
            }
        }
        if(surfaceToCopy.yPoints != null) {
            this.yPoints = new double[surfaceToCopy.yPoints.length];
            int vectorCounter2 = new Integer(0);
            for (Double ypoints : surfaceToCopy.yPoints) {
                Double y = new Double(ypoints);
                this.yPoints[vectorCounter2] = y;
                vectorCounter2++;
            }
        }

        this.nPoints = new Integer(surfaceToCopy.nPoints);

        if(surfaceToCopy.elementarySurface != null) {
            this.elementarySurface = new ArrayList<>();
            for (Surface surface : surfaceToCopy.elementarySurface) {
                Surface elementarySurfaceToCopy = new Surface(surface);
                this.elementarySurface.add(elementarySurfaceToCopy);
            }
        }

        this.groutWidth = new Double(surfaceToCopy.groutWidth);

        this.mismatch = new Double(surfaceToCopy.mismatch);
    }

    public Surface(double[] xPoints, double[] yPoints, int nbr_points) {
        Path2D.Double path = new Path2D.Double();
        for (int i = 0; i < nbr_points; i++) {
            if (i == 0) {
                this.position = new Point2D.Double(xPoints[i], yPoints[i]);
                path.moveTo(xPoints[i], yPoints[i]);
            }
            else {
                path.lineTo(xPoints[i], yPoints[i]);
            }
        }
        path.closePath();
        Area areaTest = new Area(path);
        this.area = new Path2D.Double(areaTest);
        //this.area = new Path2D.Double(areaTest);
        this.width = area.getBounds2D().getWidth();
        this.height = area.getBounds2D().getHeight();

        wholeSurfaces = new ArrayList<ElementarySurface>();
        holes = new ArrayList<ElementarySurface>();
        this.pattern = new StraightPattern();
        this.tileType = TileType.createTileWithDefaultParameters();
        this.color = (Color.WHITE);
        this.center = false;

        //Test
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.nPoints = nbr_points;
        this.elementarySurface = new ArrayList<Surface>();
        this.groutWidth = 0d;

        //TODO fait une copie par référence?
        //this.elementarySurface.add(this);
    }

    public Surface(Point2D.Double point) {
        this.position = point;
        wholeSurfaces = new ArrayList<ElementarySurface>();
        holes = new ArrayList<ElementarySurface>();
        this.pattern = new StraightPattern();
        this.tileType = TileType.createTileWithDefaultParameters();
        //this.pattern = new DefaultPattern();
        this.color = (Color.WHITE);
    }

    public Surface(Surface surfaceToCopy, AffineTransform tx) {
        this.xPoints = surfaceToCopy.xPoints.clone();
        this.yPoints = surfaceToCopy.yPoints.clone();
        this.scaleXPoints(tx.getScaleX());
        this.scaleYPoints(tx.getScaleY());
        this.position = new Point2D.Double(this.xPoints[0], this.yPoints[0]);
        this.area = new Path2D.Double(new Area(surfaceToCopy.getArea()));
        this.width = this.area.getBounds2D().getWidth();
        this.height = this.area.getBounds2D().getHeight();
        this.area.transform(tx);
    }

    private void scaleXPoints(double scaleX) {
        for (int i = 0; i < this.xPoints.length; i++) {
            this.xPoints[i] *= scaleX;
        }
    }

    private void scaleYPoints(double scaleY) {
        for (int i = 0; i < this.yPoints.length; i++) {
            this.yPoints[i] *= scaleY;
        }
    }

    public void addCopy(Surface surface) {
        double[] x = surface.getxPoints().clone();
        double[] y = surface.getyPoints().clone();
        int n = surface.getnPoints();
        Surface surfaceCopy = new Surface(x, y, n);
        this.elementarySurface.add(surfaceCopy);
    }

    public double[] getxPoints() {
        return this.xPoints;
    }

    public double[] getyPoints() {
        return this.yPoints;
    }

    public int getnPoints() {
        return this.nPoints;
    }

    public Area getArea() {
        return new Area(this.area);
    }

    public boolean isMerged() {
        return this.mergedStatus;
    }

    public void merge(Surface surface) {
        //TODO Tester les ajouts
        ArrayList<ElementarySurface> wholeSurface = surface.getWholeSurfaces();
        ArrayList<ElementarySurface> holeSurface = surface.getHoles();
        //addAll proposé par IDE
        this.wholeSurfaces.addAll(wholeSurface);
        this.holes.addAll(holeSurface);
        this.mergedStatus = true;

        Area test = new Area(this.area);
        test.add(new Area(surface.getArea()));
        this.area = new Path2D.Double(test);
        this.width = this.area.getBounds2D().getWidth();
        this.height = this.area.getBounds2D().getHeight();
    }

    public Shape getShape(){
        return this.area;
    }

    public void addElementaryWholeSurface(ElementarySurface elementarySurface) {
        wholeSurfaces.add(elementarySurface);
    }

    public void addHole(ElementarySurface elementarySurface) {
        if (!haveHole) {
            haveHole = true;
        }
        holes.add(elementarySurface);
    }

    public void setColor (Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void switchSelectionStatus() {
        this.selectionStatus = !this.selectionStatus;
    }

    public boolean isSelected() {
        return this.selectionStatus;
    }

    public void unselect() {
        this.selectionStatus = false;
    }

    public void setSelectionStatus(boolean selectionStatus) {
        this.selectionStatus = selectionStatus;
    }

    public double getWidth() {
        return this.width;
        //return this.getBoundingRectangle().getWidth();
    }

    public double getHeight() {
        return this.getBoundingRectangle().getHeight();
    }

     public Cover getCover() {
        return this.cover;
    }

    public void setCover(Cover cover){
        this.cover = cover;
    }

    public Polygon getPolygon() {
        return this.polygon;
    }

    public void setMeasurementMode(MainWindow.MeasurementUnitMode mode) {
        // C'est batard en maudit mais c'est pour éviter un bug quand on crée un surface et qu'on switch
        // Live le bug est dans on switch à la même unité (on crée une surface en mode métrique pis on reselectionne
        // le mode métrique = la surface disparait
        // L'alternative est de passer in MeasurementMode en argument quand on crée un objet Surface, pour setter currentMeasurementMode
        // Le problème c'est que c'est laid quel dog pcq faut le faire passer de MainWindow à RoomController à Room à Surface
        if (this.measurementMode == null) {
            if (mode == MainWindow.MeasurementUnitMode.METRIC) {
                this.measurementMode = MainWindow.MeasurementUnitMode.IMPERIAL;
            } else {
                this.measurementMode = MainWindow.MeasurementUnitMode.METRIC;
            }
        }
        if (this.measurementMode == mode) { return; }

        switch (mode) {
            case METRIC:
                this.polygon = UnitConverter.convertPolygonFromInchToMeter(this.polygon);
                this.measurementMode = MainWindow.MeasurementUnitMode.METRIC;
                break;
            case IMPERIAL:
                this.polygon = UnitConverter.convertPolygonFromMeterToInch(this.polygon);
                this.measurementMode = MainWindow.MeasurementUnitMode.IMPERIAL;
                break;
        }
    }

    public Rectangle getBoundingRectangle() {
        return this.area.getBounds();
    }

    public Point2D.Double getPosition() {
        return this.position;
    }

    public boolean isHole() {
        return this.isHole;
    }

    public TileType getTileType(){
        return this.tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Pattern getPattern(){
        return this.pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
        this.isCovered = true;
    }

    public void setNotCovered() {
        this.isCovered = false;
    }

    public boolean isCovered() {
        return this.isCovered;
    }


    public void setCoverCenter(boolean bool){
        this.center = bool;
    }

    public boolean getCoverCenter(){
        return this.center;
    }

    public void translate(double deltaX, double deltaY) {
        this.translatePolygon(deltaX, deltaY);
    }

    private void translatePolygon(double deltaX, double deltaY) {
        if (this.area.getBounds2D().getX() + deltaX >= 0 && this.area.getBounds2D().getY() + deltaY >= 0) {
            //this.polygon.translate((int)deltaX, (int)deltaY);
            AffineTransform at = new AffineTransform(1, 0, 0, 1, deltaX, deltaY);

            for (ElementarySurface wholeSurface : wholeSurfaces) {
                wholeSurface.translate(deltaX, deltaY);
            }

            for (ElementarySurface holeSurface : holes) {
                holeSurface.translate(deltaX, deltaY);
            }

            if(getPattern().getVirtualTileList() != null) {
                for (Tile tile : getPattern().getVirtualTileList()) {
                    tile.transform(at);
                }
            }
            this.area.transform(at);
            this.position.setLocation(this.position.getX() + deltaX, this.position.getY() + deltaY);

            for (int i = 0; i < elementarySurface.size(); i++){
                Surface elem = elementarySurface.get(i);
                elem.translate(deltaX, deltaY);
            }
            this.translateInitialPoints(deltaX, deltaY);
        }
    }

    public void translateInitialPoints(double deltaX, double deltaY) {
        double[] newXPosition = new double[this.nPoints];
        double[] newYPosition = new double[this.nPoints];
        for (int i = 0; i < nPoints; i++){
            newXPosition[i] = this.xPoints[i] + deltaX;
            newYPosition[i] = this.yPoints[i] + deltaY;
        }
        this.xPoints = newXPosition;
        this.yPoints = newYPosition;

    }

    public void setWidth(double enteredWidth) {
        double deltaX = enteredWidth / this.width;
        this.width = enteredWidth;

        AffineTransform at = new AffineTransform(deltaX, 0, 0, 1, 0, 0);
        double initialXPosition = this.area.getBounds().getX();
        this.area.transform(at);
        double newXPosition = this.area.getBounds().getX();
        double deltaPosition = (newXPosition - initialXPosition);

        AffineTransform atPosition = new AffineTransform(1, 0, 0, 1, -deltaPosition, 0);
        this.area.transform(atPosition);

        for (Surface elem : this.elementarySurface) {
            elem.setWidth(enteredWidth);
        }
    }

    public void setHeight(double height) {
        double deltaY = height / this.height;
        this.height = height;

        AffineTransform at = new AffineTransform(1, 0, 0, deltaY, 0, 0);
        double initialXPosition = this.area.getBounds().getY();
        this.area.transform(at);
        double newXPosition = this.area.getBounds().getY();
        double deltaPosition = (newXPosition - initialXPosition);

        AffineTransform atPosition = new AffineTransform(1, 0, 0, 1, 0, -deltaPosition);
        this.area.transform(atPosition);
    }

    public Dimension getDimensions() {
        Dimension dimension = new Dimension();
        dimension.setSize(this.getWidth(), this.getHeight());
        return dimension;
    }

    //Inspiré de: https://stackoverflow.com/questions/31209541/convert-from-java-awt-geom-area-to-java-awt-polygon
    public boolean intersect(Area otherArea) {
        Area testArea = new Area(this.area);
        testArea.intersect(otherArea);
        return !testArea.isEmpty();
    }

    public void setArea(Area area) {
        this.area = new Path2D.Double(area);
    }

    public int getNumberOfSummit() {
        int counter = 0;
        PathIterator pathIterator = this.area.getPathIterator(null);
        float[] floats = new float[6];
        while (!pathIterator.isDone()) {
            int type = pathIterator.currentSegment(floats);
            int x = (int) floats[0];
            int y = (int) floats[1];
            if (type != PathIterator.SEG_CLOSE) {
                counter += 1;
            }
            pathIterator.next();
        }
        return counter;
    }

    public void setIsHole() {
        this.isHole = true;
    }

    public void setIsHoleAsFalse() {
        this.isHole = false;
    }

    public void setHole(Surface surface) {
        this.wholeSurfaces.addAll(surface.getWholeSurfaces());
        this.holes.addAll(surface.getHoles());
        Area test = new Area(this.area);
        test.subtract(new Area(surface.getArea()));
        this.area = new Path2D.Double(test);

    }

    public void setGroutWidth(double width) {
        this.groutWidth = width;
        //this.getPattern().setGroutWidth(width);
    }

    public double getGroutWidth() {
        return this.groutWidth;
    }

    public Point2D getBottomMostPoint() {
        Point2D[] points = this.getPoints();
        Point2D topMostPoint = new Point2D.Double();
        double maxHeight = 0.0;

        for (Point2D point : points) {
            double y = point.getY();
            if (y > maxHeight) {
                maxHeight = y;
                topMostPoint = point;
            }
        }
        return topMostPoint;
    }

    public Point2D getTopMostPoint() {
        Point2D[] points = this.getPoints();
        Point2D bottomMostPoint = new Point2D.Double();
        double minHeight = Double.POSITIVE_INFINITY;

        for (Point2D point : points) {
            double y = point.getY();
            if (y < minHeight) {
                minHeight = y;
                bottomMostPoint = point;
            }
        }
        return bottomMostPoint;
    }

    public Point2D getLeftMostPoint() {
        Point2D[] points = this.getPoints();
        Point2D leftMostPoint = new Point2D.Double();
        double minWidth = Double.POSITIVE_INFINITY;

        for (Point2D point : points) {
            double x = point.getX();
            if (x < minWidth) {
                minWidth = x;
                leftMostPoint = point;
            }
        }
        return leftMostPoint;
    }

    public Point2D getRightMostPoint() {
        Point2D[] points = this.getPoints();
        Point2D rightMostPoint = new Point2D.Double();
        double maxWidth = 0.0;

        for (Point2D point : points) {
            double x = point.getX();
            if (x > maxWidth) {
                maxWidth = x;
                rightMostPoint = point;
            }
        }
        return rightMostPoint;
    }

    public Point2D[] getPoints() {
        PathIterator iter = area.getPathIterator(null);
        float[] floats = new float[6];
        Point2D[] points = new Point2D[this.getNumberOfSummit()];
        int i = 0;

        while (!iter.isDone()) {
            int type = iter.currentSegment(floats);
            float x = floats[0];
            float y = floats[1];
            if (type != PathIterator.SEG_CLOSE) {
                points[i] = new Point2D.Float(x, y);
            }
            i++;
            iter.next();
        }
        return points;
    }

    public void boundingRectangleSnapToPoint(Point2D closestCorner) {
        double[] deltaArray = boundingRectangleDeltasFromPoint(closestCorner);
        this.translatePolygon(deltaArray[0], deltaArray[1]);
    }

    private double[] boundingRectangleDeltasFromPoint(Point2D closestCorner) {
        Point2D topLeftPoint = getTopLeftPoint();

        double deltaX = closestCorner.getX() - getBoundingRectangle().getMinX();
        double deltaY = closestCorner.getY() - getBoundingRectangle().getMinY();

        return new double[]{deltaX, deltaY};
    }

    public void snapToPoint(Point2D closestCorner) {
        double[] deltaArray = getDeltasFromPoint(closestCorner);
        this.translatePolygon(deltaArray[0], deltaArray[1]);
    }

    private double[] getDeltasFromPoint(Point2D closestCorner) {
        Point2D topLeftPoint = getTopLeftPoint();

        double deltaX = closestCorner.getX() - topLeftPoint.getX();
        double deltaY = closestCorner.getY() - topLeftPoint.getY();

        return new double[]{deltaX, deltaY};
    }

    public Point2D getTopLeftPoint() {
        PathIterator iter = area.getPathIterator(null);
        float[] floats = new float[6];
        int type = iter.currentSegment(floats);
        return new Point2D.Double(floats[0], floats[1]);
    }

    public double getDistanceFromPoint(Point2D point) {
        double[] deltas = getDeltasFromPoint(point);
        return Math.sqrt(Math.pow(deltas[0], 2) + Math.pow(deltas[1], 2));
    }

    public void addMergeSurfaceToList(Surface surfaceToAdd) {
        this.elementarySurface.add(surfaceToAdd);
    }

    public ArrayList<Surface> getElementarySurface() {
        return this.elementarySurface;
    }

    public boolean isToTheLeft(Surface otherSurface) {
        Rectangle thisSurfaceRectangle = this.getBoundingRectangle();
        Point2D thisMiddlePoint = new Point2D.Double(thisSurfaceRectangle.getCenterX(), thisSurfaceRectangle.getCenterY());
        Rectangle otherSurfaceRectangle = otherSurface.getBoundingRectangle();
        Point2D otherMiddlePoint = new Point2D.Double(otherSurfaceRectangle.getCenterX(), otherSurfaceRectangle.getCenterY());

        return (thisMiddlePoint.getX() < otherMiddlePoint.getX());
    }

    public boolean leftMostCorner(Surface otherSurface) {
        Point2D thisTopLeftPoint = new Point2D.Double(this.getBoundingRectangle().getMinX(), this.getBoundingRectangle().getMinY());
        Point2D otherTopLeftPoint = new Point2D.Double(otherSurface.getBoundingRectangle().getMinX(), otherSurface.getBoundingRectangle().getMinY());

        return (thisTopLeftPoint.getX() < otherTopLeftPoint.getX());
    }

    public boolean rightMostCorner(Surface otherSurface) {
        Point2D thisBottomRightPoint = new Point2D.Double(this.getBoundingRectangle().getMaxX(), this.getBoundingRectangle().getMaxY());
        Point2D otherBottomRightPoint = new Point2D.Double(otherSurface.getBoundingRectangle().getMaxX(), otherSurface.getBoundingRectangle().getMaxY());

        return (thisBottomRightPoint.getX() > otherBottomRightPoint.getX());
    }

//    public boolean isToTheRight(Surface otherSurface) {
//        Point2D thisRightMostPoint = this.getRightMostPoint();
//        Point2D otherRightMostPoint = otherSurface.getRightMostPoint();
//
//        return (thisRightMostPoint.getX() > otherRightMostPoint.getX());
//    }

    public boolean isBeneath(Surface otherSurface) {
        Rectangle thisSurfaceRectangle = this.getBoundingRectangle();
        Point2D thisMiddlePoint = new Point2D.Double(thisSurfaceRectangle.getCenterX(), thisSurfaceRectangle.getCenterY());
        Rectangle otherSurfaceRectangle = otherSurface.getBoundingRectangle();
        Point2D otherMiddlePoint = new Point2D.Double(otherSurfaceRectangle.getCenterX(), otherSurfaceRectangle.getCenterY());

        return (thisMiddlePoint.getY() > otherMiddlePoint.getY());
    }

    public boolean topMostCorner(Surface otherSurface) {
        Point2D thisTopLeftPoint = new Point2D.Double(this.getBoundingRectangle().getMinX(), this.getBoundingRectangle().getMinY());
        Point2D otherTopLeftPoint = new Point2D.Double(otherSurface.getBoundingRectangle().getMinX(), otherSurface.getBoundingRectangle().getMinY());

        return (thisTopLeftPoint.getY() < otherTopLeftPoint.getY());
    }

    public boolean bottomMostCorner(Surface otherSurface) {
        Point2D thisBottomRightPoint = new Point2D.Double(this.getBoundingRectangle().getMaxX(), this.getBoundingRectangle().getMaxY());
        Point2D otherBottomRightPoint = new Point2D.Double(otherSurface.getBoundingRectangle().getMaxX(), otherSurface.getBoundingRectangle().getMaxY());

        return (thisBottomRightPoint.getY() > otherBottomRightPoint.getY());
    }

    public void setMismatch(double mismatch) {
        this.mismatch = mismatch;
        this.pattern.setMismatch(mismatch);
    }

    public double getMismatch() {
        return this.mismatch;
    }

    public void translatePattern(double x, double y) {
        this.pattern.setOffset(x, y);
        this.tileType.setxOffset(x);
        this.tileType.setyOffset(y);
    }

    public Point2D getMiddlePoint() {
        Rectangle rec = new Rectangle(this.area.getBounds());
        return new Point2D.Double(rec.getCenterX(), rec.getCenterY());
    }

    public void translatePointsTest(double x, double y) {
        AffineTransform tx = new AffineTransform();
        tx.setToTranslation(x, y);
        this.area.transform(tx);
    }

    public Point2D getCenterOfMass() {
        double xCenter = this.getAverage(this.xPoints);
        double yCenter = this.getAverage(this.yPoints);
        return new Point2D.Double(xCenter, yCenter);
    }

    private double getAverage(double[] numbers) {
        double sum = 0;
        for (double number : numbers) { sum += number; }
        return sum / numbers.length;
    }

    public int getNumberOfTiles() {
        int sum = 0;
        if (this.getPattern().getVirtualTileList() != null) {
            for (Tile tile : this.getPattern().getVirtualTileList()) {
                if (!tile.isEmpty()) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public double getNumberOfBoxes() {
        return (double) getNumberOfTiles() / getTileType().getNbrTilesPerBox();
    }

    public boolean getIsCenter() {
        return this.center;
    }

    public void startWithFullTile() {
        this.center = false;
        this.pattern.initOffset();
    }
}


