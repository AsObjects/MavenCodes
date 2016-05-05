package com.mccutil;

public class StringUtils {

	public static boolean isBlank(String str){
		if(str==null||str==""){
			return true;
		}
		return false;
	}
}
