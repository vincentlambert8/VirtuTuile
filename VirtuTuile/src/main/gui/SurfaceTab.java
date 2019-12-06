package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SurfaceTab extends JPanel{
    private MainWindow mainWindow;
    private JPanel surfaceTab;
    private JFormattedTextField widthField;
    private JFormattedTextField heightField;
    private JButton combineButton;
    private JButton surfaceColorButton;
    private JPanel dimensionPanel;
    private JCheckBox holeCheckBox;
    private JLabel surfaceTitle;
    private JLabel dimensionLabel;
    private JPanel optionsPanel;
    private JLabel dispositionLabel;
    private JButton chromaticButton;
    private JButton separateButton;
    private JButton horizontallyAlignButton;
    private JButton verticallyAlignButton;
    private JButton leftAlignButton;
    private JButton downButton;
    private JButton topAlignButton;
    private JButton rightButton;
    private Color surfaceColor;

    public SurfaceTab(MainWindow mainWindow) throws IOException {
        this.mainWindow = mainWindow;
        this.add(surfaceTab);
        widthField.setValue(0d);
        heightField.setValue(0d);
        surfaceTitle.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        holeCheckBox.setFocusPainted(true);

        Dimension tabButtonDimesion = new Dimension(20,20);


        surfaceColorButton.setPreferredSize(new Dimension(50, 20));
        chromaticButton.setPreferredSize(new Dimension(20, 20));

        BufferedImage chromImage = ImageIO.read(this.getClass().getResourceAsStream("/image/chromatic.png"));
        Icon chromIcon = new ImageIcon(chromImage.getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        chromaticButton.setIcon(chromIcon);

        surfaceColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", surfaceColor);
                setButtonColor(color);
                setSurfaceColor(color);
            }
        });

        widthField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnteredWidthSurfaceDimensions();
            }
        });

        heightField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnteredHeightSurfaceDimensions();
            }
        });

        combineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                combineSelectedSurface();

            }
        });

        //TODO Peut-être à changer pour ActionListener
        holeCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    setSelectedSurfaceAsHole();
                }
                else if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
                    setSelectedSurfaceAsWhole();
                }
            }
        });

        chromaticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", surfaceColor);
                setButtonColor(color);
                setSurfaceColor(color);
            }
        });

        separateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                separateSelectedSurface();
            }
        });

        verticallyAlignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                verticallyAlignSelectedSurfaces();
            }
        });

        horizontallyAlignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                horizontallyAlignSelectedSurfaces();
            }
        });

        //Align Center icon icon by Icons8
        //Align Bottom icon icon by Icons8
        //Align Top icon icon by Icons8

        //Merge Docunemts icon icon by Icons8
        BufferedImage mergeImage = ImageIO.read(this.getClass().getResourceAsStream("/image/merge.png"));
        Icon mergeIcon = new ImageIcon(mergeImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        combineButton.setIcon(mergeIcon);

        //Separate Document icon icon by Icons8
        BufferedImage separateImage = ImageIO.read(this.getClass().getResourceAsStream("/image/separate.png"));
        Icon separateIcon = new ImageIcon(separateImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        separateButton.setIcon(separateIcon);

        //Align Left icon icon by Icons8
        BufferedImage leftImage = ImageIO.read(this.getClass().getResourceAsStream("/image/left.png"));
        Icon leftIcon = new ImageIcon(leftImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        leftAlignButton.setIcon(leftIcon);
        //leftAlignButton.setPreferredSize(tabButtonDimesion);

        //Align Top icon icon by Icons8
        BufferedImage upImage = ImageIO.read(this.getClass().getResourceAsStream("/image/up.png"));
        Icon upIcon = new ImageIcon(upImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        topAlignButton.setIcon(upIcon);
        //topAlignButton.setPreferredSize(tabButtonDimesion);

        //Align Bottom icon icon by Icons8
        BufferedImage downImage = ImageIO.read(this.getClass().getResourceAsStream("/image/down.png"));
        Icon downIcon = new ImageIcon(downImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        downButton.setIcon(downIcon);

        //Align Right icon icon by Icons8
        BufferedImage rightImage = ImageIO.read(this.getClass().getResourceAsStream("/image/right.png"));
        Icon rightIcon = new ImageIcon(rightImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        rightButton.setIcon(rightIcon);

        //Merge Horizontal icon icon by Icons8
        BufferedImage verticallyAlignImage = ImageIO.read(this.getClass().getResourceAsStream("/image/collerVert.png"));
        Icon collerVertIcon = new ImageIcon(verticallyAlignImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        verticallyAlignButton.setIcon(collerVertIcon);

        //Merge Horizontal icon icon by Icons8
        BufferedImage horizontalAlignImage = ImageIO.read(this.getClass().getResourceAsStream("/image/collerHor.png"));
        Icon collerHorIcon = new ImageIcon(horizontalAlignImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        horizontallyAlignButton.setIcon(collerHorIcon);







    }

    private void horizontallyAlignSelectedSurfaces() {
        mainWindow.horizontallyAlignSelectedSurfaces();
    }

    private void verticallyAlignSelectedSurfaces() {
        mainWindow.verticallyAlignSelectedSurfaces();
    }

    public void setEnteredWidthSurfaceDimensions(){
        double enteredWidth = (double)widthField.getValue();
        this.mainWindow.setSelectedSurfaceWidth(enteredWidth);
    }

    public void setEnteredHeightSurfaceDimensions() {
        double enteredHeight = (double)heightField.getValue();
        this.mainWindow.setSelectedSurfaceHeight(enteredHeight);
    }

    public void combineSelectedSurface() {
        this.mainWindow.combineSelectedSurfaces();
    }

    public void setButtonColor(Color color) {
        this.surfaceColor = color;
        surfaceColorButton.setBackground(color);
        surfaceColorButton.setOpaque(true);

    }

    public void setSurfaceColor(Color color){
        mainWindow.setSelectedSurfaceColor(color);
    }

    private Color getSurfaceColor() {
        return this.surfaceColor;
    }

    public void setSurfaceDimensionField(Dimension dimension) {
        this.widthField.setValue(dimension.getWidth());
        this.heightField.setValue(dimension.getHeight());
    }


    public void setSelectedSurfaceAsWhole() {
        mainWindow.setSelectedSurfaceAsWhole();
    }

    public void setSelectedSurfaceAsHole() {
        mainWindow.setSelectedSurfaceAsHole();
    }

    public void setHoleCheckBox(boolean isSelectedSurfaceAHole, int numberOfSelectedSurfaces) {
        if (numberOfSelectedSurfaces == 1) {
            holeCheckBox.setEnabled(true);
            if (isSelectedSurfaceAHole) {
                holeCheckBox.setSelected(true);
            }
            else{
                holeCheckBox.setSelected(false);
            }
        }
        else  {
            holeCheckBox.setEnabled(false);
        }
    }

    public void separateSelectedSurface() {
        mainWindow.separateSelectedSurface();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
