package application;

import javafx.application.HostServices;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

// GUI main for create MCS 
public class MainController implements Initializable{
	
	@FXML
	private Button btn1;
	
	@FXML 
	private ComboBox<String> NumMismatch;
	
	@FXML
	private ListView textFile;
	
	@FXML
	private ComboBox<String> SizeWordComboBox;
	
	@FXML
	private ComboBox<String> sizeFormComboBox;
	
	@FXML
	private Button btnAlgorithm;
	
	@FXML
	private Button Help;
	
	int mcsBtn = -1;
	int numBtn = -1;
	int wordBtn = -1;
	String text = "abckeabcdfgj";	
	String word = "abcd";
	String content = "";
	
	//ObservableList<String> list1 = FXCollections.observableArrayList("0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20");
	ObservableList<String> list1 = FXCollections.observableArrayList("0","1","2","3","4","5");
	ObservableList<String> list2 = FXCollections.observableArrayList("2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20");
	
	// Help system - Explain menu screen 
	public void Help(ActionEvent event) throws IOException{
		Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.CANCEL);
		alert.setTitle("Help system - Explain menu screen");
		alert.setHeaderText("The user had 5 options: \r\n\n" + 
				"Define the search parameters:\r\n" + 
				"•	Browse a File: \r\n" + 
				"     Here we choose a txt file to get the text - we will search the word in that text through our algorithm. \r\n\n" + 
				"Define the MCS parameters:\r\n" + 
				"•	Select size of form: \r\n" + 
				"     Here we will choose the size of form that will define the size of each form based on mismatch.\r\n" + 
				"•	Select size of word: \r\n" + 
				"     Here we will choose the size of word that will define the size of form based on match and mismatch.\r\n" +  
				"•	Select size of mismatches: \r\n" + 
				"     Here we will choose the size of mismatches that will be on one form.\r\n\n" + 
				"Button:\r\n" + 
				"•	Run MCS: \r\n" + 
				"     Here we execute the creation of MCS with indels using the size of form , size of word and size of mismatches. It will create the MCS with indels."
				+ "   After that we will execute the creation of map that we will use to search the word through MCS , text and size form.\r\n" + 
				"");
		alert.showAndWait();
		return;
	}
	
	// Running browse file by pressing browse for file button
	public void Button1Action(ActionEvent event) throws IOException {
		
		File file = null;
		textFile.getItems().clear();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File ("C:\\"));
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setTitle("Open Resource File");
		File selectedFile = fileChooser.showOpenDialog(null);
		StringBuffer contents = new StringBuffer();
		FileReader reader = null;
		
		if(selectedFile != null) {
			reader = new FileReader(selectedFile);
		    char[] chars = new char[(int) selectedFile.length()];
		    reader.read(chars);
		    content = new String(chars);
	
		    reader.close();
			textFile.getItems().add(content);
		    numBtn = 1;
		}
		
		else {
			Alert alert = new Alert(AlertType.ERROR, "", ButtonType.CANCEL);
			alert.setTitle("File is not selected");
			alert.setHeaderText("You did not select a file.");
			alert.showAndWait();
			return;
		}
	}
	
	// Move variables that user choice to enhanced k-mismatch search engine with indels controller
	public void BasicAlgorithm(ActionEvent event) throws Exception{
		
		if(content.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "", ButtonType.CANCEL);
			alert.setTitle("Cannot access");
			alert.setHeaderText("The text is empty - Please add another text");
			alert.showAndWait();
			return;
		}
		
		else if(SizeWordComboBox.getValue() == null || numBtn == -1 || NumMismatch.getValue() == null || sizeFormComboBox.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR, "", ButtonType.CANCEL);
			alert.setTitle("Cannot access");
			alert.setHeaderText("Please check missing");
			alert.showAndWait();
			return;
		}
		
		else {
			
		    FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(getClass().getResource("/application/BasicAlgorithm.fxml"));
		    
		    try { 
			    loader.load();
		    }  catch (IOException e) {
				e.printStackTrace();
			}	
		    String stringTextWordSize = SizeWordComboBox.getValue();
		    String stringNumMismatch = NumMismatch.getValue();
		    String stringFormComboBox = sizeFormComboBox.getValue();
		    int intTextWordSize = Integer.parseInt(stringTextWordSize);
			int intNumMismatch = Integer.parseInt(stringNumMismatch);
			int intFormSize = Integer.parseInt(stringFormComboBox);
			//Scene scene = new Scene(root,550,530);
			KMismatchAlgorithmController Kmismatch = loader.getController();
			Kmismatch.mcsGeneration(intTextWordSize, content, intFormSize, intNumMismatch);
			Parent p = loader.getRoot();
			Stage primaryStage = new Stage();
			primaryStage.setScene(new Scene(p));
			primaryStage.showAndWait();
			}
						
		}
	
	// Initialize ComboBoxes with list1 and list2
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		NumMismatch.setItems(list1);
		SizeWordComboBox.setItems(list2);
		sizeFormComboBox.setItems(list2);
	}	

}