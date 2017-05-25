package com.legend.graphics;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.legend.event.ChangeEvent;
import com.legend.model.State;

public class PalletDefinition {
	
	private HashMap<Integer, Layer> layers = new HashMap<Integer, Layer>();
	
	public Pallet toPallet(ChangeEvent listener, State state) {
		Pallet pallet = new Pallet(listener, state);
		for(Entry<Integer, Layer> entry : layers.entrySet()) {
			pallet.layers.put(entry.getKey(), entry.getValue().copy(listener));
		}
		return pallet;
	}

	public HashMap<Integer, Layer> getLayers() {
		return layers;
	}
	
	private static Pattern blocks   = Pattern.compile("(.*?)\\{(.*?)\\}", Pattern.DOTALL|Pattern.MULTILINE);
	private static Pattern values   = Pattern.compile("(.*?)\\=(.*?)\\;", Pattern.DOTALL|Pattern.MULTILINE);
	private static Pattern template = Pattern.compile("(.*?)\\((.*?)\\)");
	
	private final static Object ASSOCIATION[][] = {
		{"standard", State.STANDARD},
		{"hover",    State.HOVER},
		{"press",    State.PRESSED},
		{"focused",  State.FOCUSED},
		{"checked",  State.CHECKED}
	};
	
	private static final int getAssociation(String raw) {
		String[] division = raw.split("\\|");
		int computed = State.STANDARD;
		for(String s : division) {
			s = s.toLowerCase().trim();
			if(!s.isEmpty()) {
				for(int i = 0; i < ASSOCIATION.length; i++) {
					if(ASSOCIATION[i][0].toString().equalsIgnoreCase(s)) {
						computed |= (Integer)ASSOCIATION[i][1];
					}
				}
			}
		}
		return computed;
	}

	public static PalletDefinition derive(String asString) {
		Matcher matcher = blocks.matcher(asString);
		PalletDefinition defined = new PalletDefinition();
		while(matcher.find()) {
			String context = matcher.group(1);
			Matcher templated = template.matcher(context);
			String template = Template.DEFAULT;
			if(templated.find()) {
				context = templated.group(1);
				template = templated.group(2);
			}
			int depth = getAssociation(context);
			String content = matcher.group(2).trim();
			Layer layer;
			if(!defined.layers.isEmpty()) {
				layer = defined.getLayerClosestTo(depth);
				layer = layer.copy(); 
			} else {
				layer = new Layer();
			}
			if(template != Template.DEFAULT)
				layer.setTemplate(template);
			if(!content.isEmpty()) {
				Matcher submatcher = values.matcher(content);
				while(submatcher.find()) {
					layer.getStyle().interpet(submatcher.group(1).trim(), submatcher.group(2).trim());
				}
			}
			Layer previous = defined.layers.put(depth, layer);
			if(previous != null) {
				throw new RuntimeException("Multiple Definitions For Depth:"+context);
			}
		}
		return defined;
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
			int examine = Pallet.score(
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

}
