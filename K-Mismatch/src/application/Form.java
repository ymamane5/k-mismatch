package application;

import java.util.ArrayList;

import java.util.Map;
// Class for form on MCS
public class Form {

	String form;
	Map<String, ArrayList<Integer>> map;
	
	public Form(String form, Map map) {
		this.form = form;
		this.map = map;
	}
	
}
