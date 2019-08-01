package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


//GUI controller class for enhanced k-mismatch search engine with indels 
public class KMismatchAlgorithmController implements Initializable{
    
	@FXML
	private Button ReturnButton;
	
	@FXML
	private Button MCSGenerationButton;
	
	@FXML
	private ListView textIndels;

	@FXML
	private TextField WordText;
	
	@FXML
	private Button Help;
	
	@FXML
	private Label status;
	
	@FXML
	private ProgressBar progressbar;
	
	int mismatch;
	int num1 = 0;
	Set<String> mcs_set;
	Set<Form> forms_set;
	String text;
	int textWordSize;
	int textFormSize;
	Integer[] pos_setOrdered1;
	
	// Help system - Explain enhanced k-mismatch search engine with indels screen 
	public void Help(ActionEvent event) throws IOException{
		Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.CANCEL);
		alert.setTitle("Help system - Explain enhanced k-mismatch search engine with indels screen");
		alert.setHeaderText("The user had 3 options: \n\n" + 
				"Define the search parameters:\n" + 
				"•	Add a word for search in text: \n" + 
				"Here we add a word for search in text - we will search that word on the text through our algorithm.\n" + 
				"We will see the results through bigger and bold letters on the text.\n" +
				"Button:\n" + 
				"•	Run our algorithm: \n" + 
				"     Here we will search the word on text using the map , word and text. We will show the results of searching in the text area.\n" + 
				"•	Return to main: \n" + 
				"     Here we return to main.\n" + 
				"");
		alert.showAndWait();
		return;
	}
	// Return back to menu by pressing button action
	public void GoMenuAlgorithm(ActionEvent event) throws Exception{
		  Stage stage = (Stage) ReturnButton.getScene().getWindow();
		  stage.close();
	}
	
	// Running search to enhanced k-mismatch search engine with indels by pressing run algorithm
	public void RunningSearch(ActionEvent event) throws Exception{
		int i;
		String data = WordText.getText().trim();
		String error = "";
		StringBuilder textMessage = new StringBuilder(text);
		String regularText;
		String boldText;
		String boldSamePlaceText;
		String nextText;
		String messageCal;
		int pos = 0;
		int checknum;
		TextFlow flow = new TextFlow();
		TextFlow flowSearching = new TextFlow();
		int num = 0;
		Font font1 = new Font(14);
		Font font2 = new Font(20);
		int checkIt = 0;
		int checkFinal = 0;
		Text textFont;
		Text boldTextFont = null;
		Text boldTextFontBefore = null;
		Text boldSamePlaceTextFont = null;
		
		textIndels.getItems().clear();
		
		if(data.length() != 0) {
			
			boldText = "Searching:";
			boldTextFont = new Text(boldText);
			boldTextFont.setFont(font1.font("Arial",FontWeight.BOLD, 18));
			flowSearching.getChildren().addAll(boldTextFont);
			textIndels.getItems().add(flowSearching);
			ReturnButton.setDisable(true);
			MCSGenerationButton.setDisable(true);
			long start = System.currentTimeMillis();
		    Set<Integer> pos_set = MCS.search(data, forms_set, text, textFormSize, mismatch);
		    long end = System.currentTimeMillis();
		    System.out.println("Time:" + (end - start) + " Milli second");
		    
		    pos_setOrdered1 = pos_set.toArray(new Integer[pos_set.size()]);
		    
		    if(pos_setOrdered1.length != 0) {
		        bubbleSort(pos_setOrdered1);
		        checkIt = 1;
		    }
		    
		    /*List<Integer> myList = new ArrayList<Integer>();
		    myList.add(6);
		    myList.add(7);
		    myList.add(8);
		    pos_setOrdered1 = myList.toArray(new Integer[myList.size()]);*/
		    
		    if(checkIt == 1) {
			    for(i = 0;i < pos_setOrdered1.length;i++) { 
			    	pos = pos_setOrdered1[i];
			    		if(num > pos) {
			    			if(pos + data.length() < textMessage.length()) {
								boldText = text.substring(num, pos + data.length());
								boldTextFont = new Text(boldText);
								boldTextFont.setFont(font1.font("Arial",FontWeight.BOLD, 18));
								flow.getChildren().addAll(boldTextFont);
			    			}
			    			else
			    				break;
				    	}
				    	else {
							regularText = text.substring(num, pos);
						    textFont = new Text(regularText);
							boldText = text.substring(pos, pos + data.length());
							boldTextFont = new Text(boldText);
							boldTextFont.setFont(font1.font("Arial",FontWeight.BOLD, 18));
							flow.getChildren().addAll(textFont, boldTextFont);
				    	}
				    	num = pos + data.length();
			    }
			          
			          if(pos + data.length() < textMessage.length()) {
						    textIndels.getItems().add("Text:");
						    regularText = text.substring(num, textMessage.length());
						    textFont = new Text(regularText);
						    flow.getChildren().addAll(textFont);
			            	textIndels.getItems().add(flow);
			          }
			          else {
			        	    textIndels.getItems().add("Text:");
			        	    boldText = text.substring(pos_setOrdered1[i - 1] + data.length(), textMessage.length());
							boldTextFont = new Text(boldText);
							boldTextFont.setFont(font1.font("Arial",FontWeight.BOLD, 18));
							flow.getChildren().addAll(boldTextFont);
			            	textIndels.getItems().add(flow);
			          }
		    }
		    
            if(checkIt == 0) {
            	textIndels.getItems().add("Results:");
            	textIndels.getItems().add("The word '" + data + "' is not found on the text:");
            	textIndels.getItems().add(textMessage);
            }
            else {
            	textIndels.getItems().add("Results:");
            	textIndels.getItems().add("Positions of the word '" + data + "' on text:" + Arrays.toString(pos_setOrdered1));
            }
            
            MCSGenerationButton.setDisable(false);
		    ReturnButton.setDisable(false);
		    
		}
		else {
            error = "Please enter a word";
			Alert alert = new Alert(AlertType.ERROR, "", ButtonType.CANCEL);
			alert.setTitle("There is no word to search");
			alert.setHeaderText(error);
			alert.showAndWait();
			return;
		}
		
    }
	
	// Create MCS by pressing run MCS on main	
	public void mcsGeneration(int textWordSize, String text, int textFormSize, int mismatch) {
				
		
		if(num1 == 0) {
			this.mismatch = mismatch;
			this.textFormSize = textFormSize;
			this.textWordSize = textWordSize;
            this.text = text;
			this.mcs_set = MCS.generate_set(textFormSize,textWordSize,mismatch);//generate_set(form_size, word size, threshold)
			String [] stringMCS = mcs_set.toArray(new String[mcs_set.size()]);
			/*
			for(int i=0; i<stringMCS.length; i++) {
				System.out.println(stringMCS[i]);
			}
			*/
			
			forms_set = MCS.generate_map(mcs_set, text, textFormSize);
/*
			for (Iterator<Form> it = forms_set.iterator(); it.hasNext(); ){
				System.out.print(it.next().map);
				System.out.print("\n");
			}
*/
			System.out.print("\n");	
		}
		
		num1 = 1;
		
		
	}
    
	// Bubble sort algorithm for position 
	static void bubbleSort(Integer[] pos_setOrdered1) {  
		int n = pos_setOrdered1.length;
	    int temp = 0;

	    for (int i = 0; i < n; i++) {
	        for (int j = 1; j < (n - i); j++) {

	            if (pos_setOrdered1[j - 1] > pos_setOrdered1[j]) {
	                temp = pos_setOrdered1[j - 1];
	                pos_setOrdered1[j - 1] = pos_setOrdered1[j];
	                pos_setOrdered1[j] = temp;
	            }

	        }
	    }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
