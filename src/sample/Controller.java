package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    //GUI properties
    private int BOARD_TILE_WIDTH = 4;
    private int BOARD_TILE_HEIGHT = 6;
    private double TILE_WIDTH = 270/BOARD_TILE_WIDTH;
    private double TILE_HEIGHT = 400/BOARD_TILE_HEIGHT;
    private Stage stage;
    private Tile[][] tileArray = new Tile[BOARD_TILE_HEIGHT][BOARD_TILE_WIDTH];

    //FXML properties
    @FXML private GridPane board;
    @FXML private TextField neuron;
    @FXML private TextField epoki;

    //Training arrays
    //TODO
    //All training Arrays put into one array
    private Double[][] trainingZero1 = new Double[24][1];
    private Double[][] trainingZero2 = new Double[24][1];
    private Double[][] trainingZero3 = new Double[24][1];

    private Double[][] trainingOne1 = new Double[24][1];
    private Double[][] trainingOne2 = new Double[24][1];
    private Double[][] trainingOne3 = new Double[24][1];

    private Double[][] trainingTwo1 = new Double[24][1];
    private Double[][] trainingTwo2 = new Double[24][1];
    private Double[][] trainingTwo3 = new Double[24][1];

    private Double[][] trainingThree1 = new Double[24][1];
    private Double[][] trainingThree2 = new Double[24][1];
    private Double[][] trainingThree3 = new Double[24][1];

    private Double[][] trainingFour1 = new Double[24][1];
    private Double[][] trainingFour2 = new Double[24][1];
    private Double[][] trainingFour3 = new Double[24][1];

    private Double[][] trainingFive1 = new Double[24][1];
    private Double[][] trainingFive2 = new Double[24][1];
    private Double[][] trainingFive3 = new Double[24][1];

    private Double[][] trainingSix1 = new Double[24][1];
    private Double[][] trainingSix2 = new Double[24][1];
    private Double[][] trainingSix3 = new Double[24][1];

    private Double[][] trainingSeven1 = new Double[24][1];
    private Double[][] trainingSeven2 = new Double[24][1];
    private Double[][] trainingSeven3 = new Double[24][1];

    private Double[][] trainingEight1 = new Double[24][1];
    private Double[][] trainingEight2 = new Double[24][1];
    private Double[][] trainingEight3 = new Double[24][1];

    private Double[][] trainingNine1 = new Double[24][1];
    private Double[][] trainingNine2 = new Double[24][1];
    private Double[][] trainingNine3 = new Double[24][1];


    private Double[][] binaryZero = new Double[4][1];
    private Double[][] binaryOne = new Double[4][1];
    private Double[][] binaryTwo = new Double[4][1];
    private Double[][] binaryThree = new Double[4][1];
    private Double[][] binaryFour = new Double[4][1];
    private Double[][] binaryFive = new Double[4][1];
    private Double[][] binarySix = new Double[4][1];
    private Double[][] binarySeven = new Double[4][1];
    private Double[][] binaryEight = new Double[4][1];
    private Double[][] binaryNine = new Double[4][1];

    // AI properties
    private Double[][] inputArray = new Double[24][1];
    private int inputLayer = 14;
    private int iterations = 100;
    private Double lambda = 1.0;

    private Double[][] W1Array = new Double[inputLayer][24];
    private Double[][] B1Array = new Double[inputLayer][1];
    private Double[][] NET1 = new Double[inputLayer][1];
    private Double[][] Y1Array = new Double[inputLayer][1];

    private Double[][] W2Array = new Double[4][inputLayer];
    private Double[][] B2Array = new Double[4][1];
    private Double[][] NET2 = new Double[4][1];
    private Double[][] Y2Array = new Double[4][1];

    private Double[][] EOut = new Double[NET2.length][NET2[0].length];
    private Double[][] EIn = new Double[NET1.length][NET1[0].length];

    public void setGrid(){
        for(int i = 0; i< BOARD_TILE_HEIGHT; i++){
            for(int j = 0; j< BOARD_TILE_WIDTH; j++){
                Tile tile = new Tile(new Pane(), 0.0);
                tileArray[i][j] = tile;
                tileArray[i][j].getPane().setStyle("-fx-background-color: lightgrey;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
                tileArray[i][j].getPane().setPrefWidth(TILE_WIDTH);
                tileArray[i][j].getPane().setPrefHeight(TILE_HEIGHT);
                GridPane.setConstraints(tileArray[i][j].getPane(),j,i);
                board.getChildren().add(tileArray[i][j].getPane());
            }
        }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setMouseClickedListener(){
        board.addEventHandler(MouseEvent.MOUSE_CLICKED,
                me -> {
                    if(me.getX()>=0 && me.getX()<270 && me.getY()>=0 && me.getY()<400) {
                        if (me.getButton().equals(MouseButton.PRIMARY)) {
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].getPane().setStyle("-fx-background-color: red;" +
                                    "-fx-border-color: black;" +
                                    "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setState(1.0);
                        }
                        if (me.getButton().equals(MouseButton.SECONDARY)) {
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].getPane().setStyle("-fx-background-color: lightgrey;" +
                                    "-fx-border-color: black;" +
                                    "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setState(0.0);
                        }
                    }
                });
    }

    public void setMouseDraggedListener(){
        board.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                me -> {
                    if(me.getX()>=0 && me.getX()<264 && me.getY()>=0 && me.getY()<400){
                        if(me.isPrimaryButtonDown()) {
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].getPane().setStyle("-fx-background-color: red;" +
                                    "-fx-border-color: black;" +
                                    "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setState(1.0);
                        }
                        if(me.isSecondaryButtonDown()) {
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].getPane().setStyle("-fx-background-color: lightgrey;" +
                                    "-fx-border-color: black;" +
                                    "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setState(0.0);
                        }
                    }
                });
    }

    public void setTrainingArrays(){

        //TODO
        //Load training arrays from text file
        binaryZero = new Double[][]{
                {0.0},
                {0.0},
                {0.0},
                {0.0},};
        binaryOne = new Double[][]{
                {0.0},
                {0.0},
                {0.0},
                {1.0},};
        binaryTwo = new Double[][]{
                {0.0},
                {0.0},
                {1.0},
                {0.0},};
        binaryThree = new Double[][]{
                {0.0},
                {0.0},
                {1.0},
                {1.0},};
        binaryFour = new Double[][]{
                {0.0},
                {1.0},
                {0.0},
                {0.0},};
        binaryFive = new Double[][]{
                {0.0},
                {1.0},
                {0.0},
                {1.0},};
        binarySix = new Double[][]{
                {0.0},
                {1.0},
                {1.0},
                {0.0},};
        binarySeven = new Double[][]{
                {0.0},
                {1.0},
                {1.0},
                {1.0},};
        binaryEight = new Double[][]{
                {1.0},
                {0.0},
                {0.0},
                {0.0},};
        binaryNine = new Double[][]{
                {1.0},
                {0.0},
                {0.0},
                {1.0},};

        trainingZero1 = new Double[][]{
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
        };

        trainingZero2 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingZero3 = new Double[][]{
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingOne1 = new Double[][]{
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0}};

        trainingOne2 = new Double[][]{
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
        };

        trainingOne3 = new Double[][]{
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
        };

        trainingTwo1 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0}};

        trainingTwo2 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingTwo3 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingThree1 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
        };

        trainingThree2 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingThree3 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
        };

        trainingFour1 = new Double[][]{
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
        };

        trainingFour2 = new Double[][]{
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
        };

        trainingFour3 = new Double[][]{
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
        };

        trainingFive1 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
        };

        trainingFive2 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingFive3 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingSix1 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingSix2 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingSix3 = new Double[][]{
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
        };

        trainingSeven1 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
        };

        trainingSeven2 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
        };

        trainingSeven3 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
        };

        trainingEight1 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingEight2 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
        };

        trainingEight3 = new Double[][]{
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
        };

        trainingNine1 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
        };

        trainingNine2 = new Double[][]{
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
        };

        trainingNine3 = new Double[][]{
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
        };

    }

    public <T> void showArray(T[][] array){
        System.out.print("{");
        for(int i=0 ; i<array.length ; i++){
            System.out.print("{");
            for(int j=0 ; j<array[0].length ; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.print("},");
            System.out.println();
        }
        System.out.print("};");
        System.out.println();
        System.out.println();
    }

    public void setInputArray(){
        List<Double> tmpList = new ArrayList<>();
        for(int i = 0; i< BOARD_TILE_HEIGHT; i++){
            for(int j = 0; j< BOARD_TILE_WIDTH; j++){
                tmpList.add(tileArray[i][j].getState());
            }
        }

        for(int i=0 ; i<tmpList.size() ; i++){
            inputArray[i][0] = tmpList.get(i);
        }
    }

    public void checkClick(MouseEvent mouseEvent) {
        setInputArray();

        NET1 = countNet(W1Array,inputArray,B1Array);
        Y1Array = sigBiFun(NET1);
        NET2 = countNet(W2Array,Y1Array,B2Array);
        Y2Array = sigBiFun(NET2);

        System.out.println("--------------------------------------------------------------------------------------------");

        interpret(Y2Array);
    }

    public void trainClick(MouseEvent mouseEvent){

        if(!neuron.getText().equals("") && !epoki.getText().equals("")){
            inputLayer = Integer.parseInt(neuron.getText());
            iterations = Integer.parseInt(epoki.getText());

            W1Array = new Double[inputLayer][24];
            B1Array = new Double[inputLayer][1];
            NET1 = new Double[inputLayer][1];
            Y1Array = new Double[inputLayer][1];
            W2Array = new Double[4][inputLayer];
            EIn = new Double[NET1.length][NET1[0].length];
        }

        W1Array = randomizeMatrix(W1Array);
        W2Array = randomizeMatrix(W2Array);
        B1Array = randomizeMatrix(B1Array);
        B2Array = randomizeMatrix(B2Array);

        for(int i=0 ; i<iterations ; i++){
            learn(trainingZero1,binaryZero);
            learn(trainingZero2,binaryZero);
            learn(trainingZero3,binaryZero);

            learn(trainingOne1,binaryOne);
            learn(trainingOne2,binaryOne);
            learn(trainingOne3,binaryOne);

            learn(trainingTwo1,binaryTwo);
            learn(trainingTwo2,binaryTwo);
            learn(trainingTwo3,binaryTwo);

            learn(trainingThree1,binaryThree);
            learn(trainingThree2,binaryThree);
            learn(trainingThree3,binaryThree);

            learn(trainingFour1,binaryFour);
            learn(trainingFour2,binaryFour);
            learn(trainingFour3,binaryFour);

            learn(trainingFive1,binaryFive);
            learn(trainingFive2,binaryFive);
            learn(trainingFive3,binaryFive);

            learn(trainingSix1,binarySix);
            learn(trainingSix2,binarySix);
            learn(trainingSix3,binarySix);

            learn(trainingSeven1,binarySeven);
            learn(trainingSeven2,binarySeven);
            learn(trainingSeven3,binarySeven);

            learn(trainingEight1,binaryEight);
            learn(trainingEight2,binaryEight);
            learn(trainingEight3,binaryEight);

            learn(trainingNine1,binaryNine);
            learn(trainingNine2,binaryNine);
            learn(trainingNine3,binaryNine);
        }

        System.out.println("GO");
    }

    public void learn(Double[][] learn, Double[][] binaryLearn){
        NET1 = countNet(W1Array,learn,B1Array);
        Y1Array = sigBiFun(NET1);
        NET2 = countNet(W2Array,Y1Array,B2Array);
        Y2Array = sigBiFun(NET2);

        EOut = countEOut(Y2Array, binaryLearn);
        EIn = countEIn();


        W1Array = updateW(W1Array,lambda,EIn,learn);
        B1Array = updateB(B1Array,EIn);

        W2Array = updateW(W2Array,lambda,EOut,Y1Array);
        B2Array = updateB(B2Array,EOut);

    }

    public Double[][] updateW(Double[][] W, Double lambda, Double[][] E, Double[][] trainingArr){

        Double[][] retArr = new Double[W.length][W[0].length];

        Double[][] update = new Double[trainingArr.length][trainingArr[0].length];

        for(int i=0 ; i<E.length ; i++){
            Double x = lambda * E[i][0];

            for(int j = 0 ; j<trainingArr.length ; j++){
                update[j][0] = x * trainingArr[j][0];
            }

            for(int j=0 ; j<W[0].length ; j++){
                retArr[i][j] = round((W[i][j] + update[j][0]),4);
            }
        }

        return retArr;
    }

    public Double[][] updateB(Double[][] B, Double[][] E){

        Double[][] retArr = new Double[B.length][B[0].length];

        for(int i=0 ; i<B.length ; i++){
            retArr[i][0] = round((B[i][0] + (lambda * E[i][0])),4);
        }

        return retArr;
    }

    public Double[][] countEIn(){
        Double[][] retArr = new Double[Y1Array.length][Y1Array[0].length];

        for(int i=0 ; i<Y1Array.length ; i++){
            for(int j=0 ; j<Y1Array[0].length ; j++) {
                Double sum = 0.0;
                //Double sum = Y1Array[i][j] * EOut[0][0];
                //sum = sum + Y1Array[i][j] * EOut[1][0];
                //sum = sum + Y1Array[i][j] * EOut[2][0];
                //sum = sum + Y1Array[i][j] * EOut[3][0];


                sum = sum + (W2Array[0][i] * EOut[0][0]);
                sum = sum + (W2Array[1][i] * EOut[1][0]);
                sum = sum + (W2Array[2][i] * EOut[2][0]);
                sum = sum + (W2Array[3][i] * EOut[3][0]);

                retArr[i][j] = round( sum * (lambda/2) * ((1 - Math.pow(Y1Array[i][j],2))),4);
            }
        }

        return retArr;
    }

    public Double[][] countEOut(Double[][] out, Double[][] train){

        Double[][] retArr = new Double[out.length][out[0].length];

        for(int i=0 ; i<out.length ; i++){
            for(int j=0 ; j<out[0].length ; j++){
                retArr[i][j] = round((train[i][j] - out[i][j]) * (lambda/2) * ((1 - Math.pow(out[i][j],2))),4);

            }
        }

        return retArr;
    }

    public Double[][] randomizeMatrix(Double[][] matrix){
        Random r = new Random();
        for(int i=0 ; i<matrix.length ; i++){
            for(int j=0 ; j<matrix[0].length ; j++){
                matrix[i][j] = round(-1 + 2 * r.nextDouble(),4);
            }
        }
        return matrix;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public Double[][] multiplyMatrix(Double[][] A, Double[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        Double[][] C = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                    C[i][j] = round(C[i][j] , 4);
                }
            }
        }

        return C;
    }

    public Double[][] substractMatrix(Double[][] A, Double[][] B){
        Double[][] returnArray = new Double[A.length][A[0].length];

        for(int i=0 ; i<A.length ; i++){
            for (int j=0 ; j<A[0].length ; j++){
                returnArray[i][j] = round(A[i][j] - B[i][j],4);
            }
        }
        return returnArray;
    }

    public Double[][] countNet(Double[][] W, Double[][] X, Double[][] B){

        Double[][] net = multiplyMatrix(W,X);
        net = substractMatrix(net,B);

        return net;
    }

    public Double[][] sigBiFun(Double[][] net){
        Double[][] rnet = new Double[net.length][net[0].length];
        for(int i=0 ; i<net.length ; i++){
            for (int j=0 ; j<net[0].length ; j++){
                Double x = Math.pow(Math.E,((-lambda)*net[i][j]));
                rnet[i][j] = round((2/(1+x))-1,4);
            }
        }

        return rnet;
    }

    public void interpret(Double[][] net){

        Double[][] inte = new Double[net.length][net[0].length];

        for(int i=0 ; i<net.length ; i++){
            for (int j=0 ; j<net[0].length ; j++){
                if(net[i][j]>=0.5){
                    inte[i][j] = 1.0;
                }
                else if(net[i][j]<0.5){
                    inte[i][j] = 0.0;
                }
            }
        }

        int res = 0;

        if(inte[0][0] == 1.0){
            res += 8;
        }
        if(inte[1][0] == 1.0){
            res += 4;
        }
        if(inte[2][0] == 1.0){
            res += 2;
        }
        if(inte[3][0] == 1.0){
            res += 1;
        }

        if(res>9){
            System.out.println("Nieznana liczba");
        }
        else {
            System.out.println("Ta liczba to: " + res);
        }
    }
}
