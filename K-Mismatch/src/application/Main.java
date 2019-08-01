package application;
	
import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

// Running GUI Main
public class Main extends Application {
	@Override
	
	public void start(Stage primaryStage) {
		
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			Scene scene = new Scene(root,820,530);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/*
		int form_size = 4;
		String text = "abckeabcabcdefgjijangh";	
		String word = "abc";
		int index;
		*/
		//Set<String> mcs_set = MCS.generate_set(form_size, word.length(), 1);
		//Set<Form> form_set = MCS.generate_map(mcs_set, text, form_size);
		
		//System.out.print(MCS.generate_subwords("abcdef", 4));

/*
Set<String> mcs_set = MCS.generate_set(form_size, word.length(), 1);  
System.out.print("\ntext = "+text + "\n");
System.out.print("word = "+ word + "\n\n");
Set<Form> form_set = MCS.generate_map(mcs_set, text, form_size);
Set<Integer> pos_set = MCS.search(word, form_set, text);
System.out.println(pos_set);
*/
		/*
		Set<String> mcs_set = MCS.generate_set(form_size,word.length(),2);//generate_set(form_size, word size, threshold)
		System.out.print("\ntext = "+text + "\n");
		System.out.print("word = "+ word + "\n\n");
			
		
		//maps
		for (Iterator<Form> it = forms_set.iterator(); it.hasNext(); ){
			System.out.print(it.next().map);
			System.out.print("\n");
		}
		System.out.print("\n");
		Set<Integer> pos_set = MCS.search(word, forms_set, text);
		
		
		*/
		// search word
		/*
		Set<Integer> location_set = new HashSet<Integer>();
		for(Map<Integer,String> map : map_list) { 	
			for(int i = 0; i < map.size(); i++) {
				String temp = map.get(i);
				if(temp.equals(word))
					location_set.add(i);		
			}
		}
		System.out.print(location_set);
		*/
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}