package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javafx.scene.text.Text;

// Class for MCS , Map and Enhanced k-mismatch search engine with indels. 
public class MCS {
    // Function for algorithm to create MCS 
	public static Set<String> generate_set(int form_size, int comb_size, int mismatches){
		
        // Update '0' at positions at array
		int[] pos;
		
		// One array of '0' and '1'(By word length)
		char[] original_array = new char[comb_size];
		
		// Few arrays of '0' and '1'(All MCS)
		ArrayList<char[]> array = new ArrayList();
		
		// Update '0' at array
		pos = new int[mismatches];
		
		int pos_sum = 0, curr_sum = 0, pointer, sum = 0, isSingle = 0, matches = comb_size - mismatches;
		boolean succeed = false;
		
		// MCS set of strings
		Set<String> mcs_set = new HashSet<String>(); 
		
		char[] char_temp_comb;
		String string_temp_comb;

		// initialize pos_sum
		for(int i=0; i<mismatches; i++) {
			pos_sum += comb_size - 1 - i;
		}
		// initialize array
		for(int i=0; i<comb_size; i++) {
			original_array[i] = '1';
		}

		// create pos
		for(int i=0; i<mismatches; i++) {
			pos[i] = i + 1;
			//System.out.print(pos[i] + " ");
		}

		array.add(original_array);
		char_temp_comb = update_form(original_array, pos);
		array.add(char_temp_comb);
		string_temp_comb = String.copyValueOf(char_temp_comb);
		
		// add insertions
		for(int i=1; i<matches; i++) {
			array.add(add_insertion(string_temp_comb, i));
		}
		
		// add deletions
		for(int i=0; i<matches; i++) {
			array.add(add_deletion(string_temp_comb, i));
		}
		 
		while(curr_sum < pos_sum) { 

			pointer = mismatches - 1; // point to end of pos_array
			while(!succeed) {
				if(pointer < 0) break;
				if(pos[pointer] < comb_size - mismatches + pointer) {
					pos[pointer]++;	
					while(pointer < mismatches - 1) //update the rest
						pos[++pointer] = pos[pointer - 1] + 1;
					succeed = true;
				}
				else
					pointer--;	
			}
			succeed = false;
			char_temp_comb = update_form(original_array, pos);
			array.add(char_temp_comb);
			string_temp_comb = String.copyValueOf(char_temp_comb);
			
			// add insertions
			for(int i=1; i<matches; i++) {
				array.add(add_insertion(string_temp_comb, i));
			}
			
			// add deletions
			for(int i=0; i<matches; i++) {
				array.add(add_deletion(string_temp_comb, i));
			}
			
			// update current sum
			curr_sum = 0;
			for(int i=0; i<mismatches; i++) {
				curr_sum += pos[i];
			}
		}
		String temp_comb;
		print_array(array);
		System.out.print("\n");
		/*
		// normalize set to 0 and 1
		temp_comb = new String();
		char[] temp_char_comb;
		for (int i = 0; i < array.size(); i++) {
			temp_comb = String.valueOf(array.get(i));
			if(temp_comb.indexOf(" ") != -1) {
				temp_comb = temp_comb.replace(" ", "");
				temp_comb = temp_comb.replace("3", "1");
				array.set(i, temp_comb.toCharArray());
			}
			else if(temp_comb.indexOf("2") != -1) {
				temp_comb = temp_comb.replace("2", "1");
				array.set(i, temp_comb.toCharArray());
			}
				
		}
		*/
		//print_array(array);
		//System.out.print("\n");

		// create set
		String test = "";
		for (int i = 0; i < form_size; i++) {
			test += "1";
		}
		mcs_set.add(test);
		String temp_form = new String();

		for (int i = 0; i < array.size(); i++) {

			 temp_comb = String.valueOf(array.get(i));
			 for (java.util.Iterator<String> it = mcs_set.iterator(); it.hasNext(); ) {
				
				if(temp_comb.contains(it.next()))
					break;
				else
					continue;			
			 }	
			 
			 
			// add to set
			temp_form = "";
			sum = 0;
			for(int k = 0; sum < form_size && k < (temp_comb.length()); k++) {
				temp_form += temp_comb.substring(k,k+1);
				if(!temp_comb.substring(k,k+1).equals("0")) // if comb[i] != 0
					sum++;
			}
			//if(sum == form_size)
				mcs_set.add(temp_form);	
		}
		
	//System.out.println(mcs_set + "\n");
	
	// reduce set
		/*
	for (java.util.Iterator<String> it = mcs_set.iterator(); it.hasNext(); ) {
		temp_form = it.next();
		isSingle = 0;
		// check if there is single confirmation from set
		for (int i = 0; i < array.size(); i++) {
			
			temp_comb = String.valueOf(array.get(i));
			if(temp_comb.contains(temp_form))
				if(single_confirmation(temp_comb, mcs_set)) {
					isSingle = 1;
					break; 
				}
				 else
					 continue;
			else
				continue;
		}
		if(isSingle == 0)
			it.remove();  // delete current form
	}
	*/
	
	//System.out.println("reduced set:\n");
	System.out.println(mcs_set);
	return mcs_set;

	}
    // Print array
	private static void print_array(ArrayList<char[]> arr) {
		
		for(int i=0; i<arr.size(); i++) {
			for(int j=0; j<arr.get(i).length; j++)
				System.out.print(arr.get(i)[j] + " ");
			System.out.print("\n");
		}
		System.out.print("\n");
	}
    // Update form on MCS
	private static char[] update_form(char[] arr, int[] pos) {		

		char[] temp = new char[arr.length];

		for(int i=0; i<arr.length; i++) 
			temp[i] = '1';

		for(int i=0; i<pos.length; i++)
			temp[pos[i]] = '0';

		return temp;

	}
    // Check for one form at MCS
	private static boolean single_confirmation(String comb, Set<String> set) {
	
	int sum = 0;
	
	for (java.util.Iterator<String> it = set.iterator(); it.hasNext(); ) {
		if(comb.contains(it.next()))
			sum++;
	}	
	if(sum != 1)
		return false;
	else
		return true;
		
	}
    // Count matches on each form at MCS
	private static int count_matches(String s) {
		
		int count = 0;
		
		for(int i=0; i<s.length(); i++) {
			if(s.substring(i, i+1) == "1")
				count++;
		}
		return count;
	}
    // Convert form to word
	public static ArrayList<String> form_to_word(String form, String text) {
		
		String str = new String();
		str = "";
		ArrayList<String> words_array = new ArrayList();
		//int size = text.length() <  form.length() ? text.length() : form.length();
		while(form.length() <= text.length()) {
			for(int i = 0; i < form.length(); i++) {
				if(!form.substring(i, i+1).equals("0"))
					str += text.substring(i,i+1);
			}
			words_array.add(str);
			text = text.substring(1);
			str = "";
		}
			
		return words_array;
	}
	// Function for algorithm to create map
	public static Set<Form> generate_map(Set<String> mcs_set, String text, int form_size) {
		
		String temp_text, temp_word;
		Form temp_form;
		Set<Form> mcs_Form_set = new HashSet();
		
		for (java.util.Iterator<String> it = mcs_set.iterator(); it.hasNext(); ) {
			mcs_Form_set.add(new Form(it.next(), new HashMap<String, ArrayList<Integer>>()));
		}
		
		for(int j = 0; j < text.length() - form_size - 1; j++ ) {
		
			// Search the relevant word on the text
			for (Iterator<Form> it = mcs_Form_set.iterator(); it.hasNext(); ) {
				temp_form = it.next();
				if((j + temp_form.form.length()) < text.length())
					temp_text = text.substring(j, j + temp_form.form.length());
				else
					break;
				
				// Add word form to the relevant form set
				ArrayList<String> temp_word_array = form_to_word(temp_form.form, temp_text);
				for(int i = 0; i < temp_word_array.size(); i++){
				
					temp_word = temp_word_array.get(i); //MCS.form_to_word(temp_form.form, temp_text);
					if(temp_form.map.get(temp_word) == null) {
						temp_form.map.put(temp_word, new ArrayList<Integer>());
					}		
					// Add the place of the word at the text
					temp_form.map.get(temp_word).add(j);
					}
		} //for
			
	} //for
	
		return mcs_Form_set;
		//return map;
		
}

	public static ArrayList<String> form_to_word2(String form, String word) {
	
	String str = new String();
	str = "";
	ArrayList<String> words_array = new ArrayList();
	//int size = text.length() <  form.length() ? text.length() : form.length();
	while(form.length() <= word.length()) {
		for(int i = 0; i < form.length(); i++) {
			if(form.substring(i, i+1).equals("1"))
				str += word.substring(i,i+1);
			else if(form.substring(i, i+1).equals("2"))
				str += word.substring(i-1,i);
			else if(form.substring(i, i+1).equals("3")) {
				if(i <= word.length() - 2)
					str += word.substring(i+1,i+2);
			}
		}
		words_array.add(str);
		word = word.substring(1);
		str = "";
	}
		
	return words_array;
}
	
	public static Set<String> generate_subwords(String word, int size,  Set<Form> set) {
		
		String s = "";
		String s2 = "";
		//ArrayList<String> comb_array = new ArrayList();
		//ArrayList<String> words_array = new ArrayList();
		Set<String> words_set = new HashSet();
		ArrayList<String> temp_words_array;
		
		for (java.util.Iterator<Form> it = set.iterator(); it.hasNext(); ) {
			temp_words_array = form_to_word2(it.next().form, word);
			for(String temp : temp_words_array) {
				words_set.add(temp);
			}
		}
	
		return words_set;
		
	}

	// Function for enhanced k-mismatch search engine with indels algorithm
	public static Set<Integer> search(String word, Set<Form> set, String text, int form_size, int mismatch) {
		
		Set<Integer> pos_set = new HashSet();
		String temp_word;
		int pos, end_pos;
		Form temp_form;
		int diff = word.length() - form_size;
		
		Set<String> temp_word_array = generate_subwords(word, form_size, set);
		
		for (java.util.Iterator<Form> it = set.iterator(); it.hasNext(); ) {
			
			temp_form = it.next();
			for (java.util.Iterator<String> it2 = temp_word_array.iterator(); it2.hasNext(); ){
				
				temp_word = it2.next();
				ArrayList<Integer> map_pos_list = temp_form.map.get(temp_word);   // search all forms
				if(map_pos_list == null) continue;
				for (java.util.Iterator<Integer> it3 = map_pos_list.iterator(); it3.hasNext(); ) {
					pos = it3.next();
					
					for(int position = (pos - diff) >= 0 ? (pos - diff) : 0; position <= pos + diff; position++) {
						if(position+word.length() < text.length()) {
							if(edit_distance(word, text.substring(position, position+word.length())) <= mismatch)
								pos_set.add(position);
							else if(edit_distance(word, text.substring(position, position+1+word.length())) <= mismatch + 1) // check insertion
								pos_set.add(position);
							else if(edit_distance(word, text.substring(position, position-1+word.length())) <= mismatch + 1) // check deletion
								pos_set.add(position);
						}
						else {
							if(edit_distance(word, text.substring(position)) <= mismatch)
								pos_set.add(position);
						}
					}
				}
			}
		}
		System.out.println("positions:");
		System.out.print(pos_set + "\n");
		
		return pos_set;
	}
	
	// Dynamic programming compare
	public static int dynamic_compare(String word, String text_substring) {
		
		if(word.equals(text_substring))
			return 0;
		else
			return 1;
		
	}
	// Reduce positions to final positions of searched word on text
	public static Set<Integer> final_search(Set<Integer> pos_set, String text, String word) {
		
		Set<Integer> final_pos_set = new HashSet();
		
		// compare word in relevant positions
		for (java.util.Iterator<Integer> it = pos_set.iterator(); it.hasNext(); ) {
			int pos = it.next();
			String temp_text = text.substring(pos, pos + word.length());
			if(word.equals(temp_text))
				final_pos_set.add(pos);		
		}
		
		System.out.println("final set:");
		System.out.print(final_pos_set + "\n");
		return final_pos_set;
		
	}
	// Add insertion
	public static char[] add_insertion(String comb, int indel_pos) {
		
		char[] new_char_comb = new char[comb.length() + 1];
		String new_comb = "";
		int curr_pos;
		for(curr_pos = 0; curr_pos < indel_pos; curr_pos++) {
			new_comb += comb.substring(curr_pos, curr_pos + 1);
		}
		new_comb += "0";  // insert indel
		
		while(curr_pos < comb.length())
		{
			if(comb.substring(curr_pos, curr_pos + 1).equals("1"))
				new_comb += "2";
			else
				new_comb += "0";
			
			curr_pos++;
		}
		new_char_comb = new_comb.toCharArray();
		return new_char_comb;
	}
    // Add deletion
	public static char[] add_deletion(String comb, int indel_pos) {
		
		char[] new_char_comb = new char[comb.length()];
		String new_comb = "";
		int curr_pos;
		for(curr_pos = 0; curr_pos < indel_pos; curr_pos++) {
			new_comb += comb.substring(curr_pos, curr_pos + 1);
		}
		
		while(curr_pos < comb.length())
		{
			if(comb.substring(curr_pos, curr_pos + 1).equals("1"))
				new_comb += "3";
			else
				new_comb += "0";
			
			curr_pos++;
		}
		new_char_comb = new_comb.toCharArray();
		return new_char_comb;
	}
	// Edit distance
	public static int edit_distance(String word1, String word2) {
		
		int[][] cost = new int[word1.length() + 1][word2.length() + 1]; 
		int min;
		
		for(int i = 1; i < word1.length() + 1; i++)
			cost[i][0] = i;
		
		for(int j = 1; j < word2.length() + 1; j++)
			cost[0][j] = j;
		
		for(int i = 1; i < word1.length() + 1; i++) {
			for(int j = 1; j < word2.length() + 1; j++) {		
				cost[i][j] = min(cost[i-1][j] + 1, cost[i][j-1] + 1, cost[i-1][j-1] + eq(word1.substring(i-1, i), word2.substring(j-1,j)));		
			}
		}
		/*
		for(int i = 0; i < word1.length() + 1; i++) {
			for(int j = 0; j < word2.length() + 1; j++) {		
				System.out.print(cost[i][j] + " ");
			}
			System.out.print("\n");
		}
		*/
		
		min = cost[word1.length()][word2.length()];
		/*
		for(int i = 1; i < word1.length() + 1; i++)
			if(min > cost[i][word2.length()])
				min = cost[i][word2.length()];
		
		for(int i = 1; i < word2.length() + 1; i++)
			if(min > cost[word1.length()][i])
				min = cost[word1.length()][i];
		*/
		return min;
	}
	// Finding minimum
	public static int min(int a, int b, int c) {
		
		if(a <= b && a <= c)
			return a;
		else if(b <= a && b <= c)
			return b;
		else
			return c;
	}
	// Equal between two strings
	public static int eq(String t1, String t2) {
		
		if(t1.equals(t2))
			return 0;
		else
			return 1;
	}
}