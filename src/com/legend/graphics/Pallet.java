package com.legend.graphics;

import java.util.HashMap;
import java.util.Map.Entry;

import com.legend.event.ChangeEvent;
import com.legend.model.State;

public class Pallet {
	
	private ChangeEvent changeEvent;
	private State state;
	protected HashMap<Integer, Layer> layers;
	
	public Pallet(ChangeEvent changeEvent, State state) {
		this.changeEvent = changeEvent;
		this.state  = state;
		this.layers = new HashMap<Integer, Layer>();
	}
	
	public Layer getLayer(int...layers) {
		Layer[] resolve = new Layer[layers.length];
		for(int i = 0; i < layers.length; i++) {
			resolve[i] = getLayer(layers[i]);
		}
		
		return new LayerGroup(resolve);
	}
	
	public Layer getLayer(int state) {
		Layer exact = layers.get(state);
		if(exact != null) {
			return exact;
		}
		Layer basis = getLayerClosestTo(state);
		if(basis == null) {
			basis = new Layer();
			basis.setOnChangeListener(changeEvent);
		}
		Layer layer = basis.copy();
		layers.put(state, layer);
		changeEvent.onChange("pallet_addition");
		return layer;
	}
	
	private Layer getLayerClosestTo(int state) {
		Layer exact = layers.get(state);
		if(exact != null) {
			return exact;
		}
		boolean bchecked = (state & State.CHECKED) == State.CHECKED;
		boolean bfocused = (state & State.FOCUSED) == State.FOCUSED;
		boolean bhover   = (state & State.HOVER)   == State.HOVER;
		boolean bpressed = (state & State.PRESSED) == State.PRESSED;
		Layer bestfit = null;
		int score = -1;
		for(Entry<Integer, Layer> entry : layers.entrySet()) {
			Layer layer = entry.getValue();
			int examine = score(
					(entry.getKey() & State.CHECKED) == State.CHECKED,
					(entry.getKey() & State.FOCUSED) == State.FOCUSED,
					(entry.getKey() & State.HOVER)   == State.HOVER,
					(entry.getKey() & State.PRESSED) == State.PRESSED,
					bchecked,
					bfocused, 
					bhover, 
					bpressed
			);
			if( examine > score) {
				bestfit = layer;
				score = examine;
			} 
		}
		return bestfit;
	}
	
	public Layer getLayer() {
		if(layers.size() == 0) {
			throw new RuntimeException("Pallet has no layers to select from!");
		}
		if(layers.size() == 1) {
			return layers.values().iterator().next(); //just return the only one if one is allowed
		}
		int resolve = state.asInt();
		return getLayerClosestTo(resolve);
	}
	
	public static int score(boolean a1, boolean a2, boolean a3, boolean a4, boolean b1, boolean b2, boolean b3, boolean b4) {
		int score = 0;
		if(a1 == b1)
			score++;
		if(a2 == b2) 
			score++;
		if(a3 == b3)
			score++;
		if(a4 == b4)
			score++;
		return score;
	}

	public Layer getAllLayers() {
		Layer[] arr = layers.values().toArray(new Layer[0]);
		return new LayerGroup(arr);
	}

}
