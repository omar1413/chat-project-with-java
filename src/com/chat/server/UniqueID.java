package com.chat.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniqueID {
	
	private static List<Integer> list = new ArrayList<Integer>();
	private static int index = 0;
	private static final int RANGE = 10000;
	
	static{
		for(int i = 0; i < RANGE; i++){
			list.add(i);
		}
		Collections.shuffle(list);
	}
	
	private UniqueID(){
		
	}
	
	public static int getID(){
		if(index >= RANGE) index = 0;
		
		return list.get(index++);
	}
}
