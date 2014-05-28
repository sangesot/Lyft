package com.lyft.InteractionLayer;


// Singleton: http://nkliuliu.iteye.com/blog/980851
public class InteractionLayer {
	private static InteractionLayer instance = null;
	
	private InteractionLayer() {
	}
	
	public static InteractionLayer getInstance() {
		if (instance == null) {
			instance = new InteractionLayer();
		}
		return instance;
	}
	
}
