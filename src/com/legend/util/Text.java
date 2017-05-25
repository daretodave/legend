package com.legend.util;

public class Text {
	
	public static String strip(String name) {
		if(name.lastIndexOf('.') > 0) {
			return  name.substring(0, name.lastIndexOf('.'));
		}
		return "";
	}
	
	public static String extenstion(String name) {
		if(name.lastIndexOf('.') > 0) {
			return  name.replace(name.substring(0, name.lastIndexOf('.')+1), "").toLowerCase();
		}
		return "";
	}

}
