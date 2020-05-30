package com.kstepanyan.TurnitInPDFReportProcessor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.FontPosture;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;

public class GUIDirectoryChooser extends Application {
    public static String inputDirPath;
    public static String outputDirPath;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        Scene scene = new Scene(grid, 600, 275);

        Text scenetitle = new Text("TurnitIn: Feedback and Mark Exporter");
        scenetitle.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
        Text instructions = new Text("1. Specify the folder containing all PDF TurnitIn reports files.\n" +
                                     "2. Choose a folder for creating a CSV file containing exported results.\n" +
                                     "3. Specify the name of the CSV file.");
        instructions.setFont(Font.font("Tahoma", FontWeight.NORMAL, FontPosture.ITALIC, 14));



        Label inputLabel = new Label("PDF Input Folder:");
        TextField inputTextField = new TextField();
        Label outputLabel = new Label("CSV Output Folder: ");
        TextField ouputTextBox = new TextField();

        DirectoryChooser inputDirChooser = new DirectoryChooser();
        inputDirChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Button inputDirChooserButton = new Button("Select Input Directory");
        inputDirChooserButton.setOnAction(e -> {
            File selectedDirectory = inputDirChooser.showDialog(primaryStage);
            if(selectedDirectory != null) {
                inputDirPath = selectedDirectory.getAbsolutePath();
                if (inputDirPath != null) {
                    inputTextField.setText(inputDirPath);
                    //System.out.println(inputDirPath);
                }
            }
        });

        DirectoryChooser outputDirChooser = new DirectoryChooser();
        outputDirChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Button outputDirChooserButton = new Button("Select Output Directory");
        outputDirChooserButton.setOnAction(e -> {
            File selectedDirectory = outputDirChooser.showDialog(primaryStage);
            if(selectedDirectory != null) {
                outputDirPath = selectedDirectory.getAbsolutePath();
                if (outputDirPath != null) {
                    ouputTextBox.setText(outputDirPath);
                    //System.out.println(outputDirPath);
                }
            }
        });

        grid.add(scenetitle,0,0,2,1);
        grid.add(instructions,0,1,2,1);
        grid.add(inputLabel,0,2);
        grid.add(inputDirChooserButton, 2,2);
        grid.add(inputTextField, 1,2);
        grid.add(outputLabel,0,3);
        grid.add(ouputTextBox,1,3);
        grid.add(outputDirChooserButton, 2,3);

        grid.setGridLinesVisible(false);

        Button generateCSV = new Button("Generate CSV");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(generateCSV);
        grid.add(hbBtn,1,4);

        final Text actiontarget = new Text();
        grid.add(actiontarget,1,6);

        generateCSV.setOnAction(event ->{
            actiontarget.setFill(Color.FIREBRICK);
            if(inputDirPath != null || outputDirPath != null) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Processing...");
                TurnitInPDFReportProcessor pdfProcessor = new TurnitInPDFReportProcessor(inputDirPath, outputDirPath);
                pdfProcessor.startProcess();
                actiontarget.setFill(Color.DARKGREEN);
                actiontarget.setText("Done!");
            } else {
                actiontarget.setText("Please specify input and output directories.");
            }
        });

        primaryStage.setTitle("TurnitIn General Feedback and Mark Exporter");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}