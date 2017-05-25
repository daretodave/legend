package com.legend;

import java.util.HashMap;
import java.util.logging.Logger;

public class Cache {
	
	private HashMap<String, Object> src = new HashMap<String, Object>();
	
	public void place(String asset, Object data) {
		Object previous = src.put(asset, data);
		if(previous != null) {
			logger.info("Asset:"+asset+", has replaced another asset.");
		}
	}
	
	private static HashMap<String, Cache>    cache = new HashMap<String, Cache>();
	private static Logger logger = Logger.getLogger(Cache.class.getName());
	
	private static boolean debug;
	
	public static void load(String classpath) {
		load(classpath, true);
	}
	
	public static void load(String where, boolean classpath) {
		System.out.println(where);
		String loc = where;
		if(classpath) {
			loc = where.replace("\\.", "/");
		}
		Cache dest = cache.get(loc);
		if(dest == null) {
			dest = new Cache();
			cache.put(loc, dest);
		}
		try {
			if(classpath) {
				system.grabAssetsFromClasspath(where, dest);
			} else {
				system.grabAssetsFromDirectory(where, dest);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T grab(String asset, Class<T> type) {
		return (T) grab(asset);
	}
	
	public static Object grab(String asset) {
		if(!asset.contains("/")) {
			for(Cache cache : Cache.cache.values()) {
				Object search = cache.src.get(asset);
				if(search != null) {
					return search;
				}
			}
			return null;
		}
		int index   = asset.lastIndexOf("/");
		String loc   = asset.substring(0, index);
		Cache resolve = cache.get(loc);
		if(resolve == null) {
			return null;
		}
		asset = asset.substring(index+1, asset.length());
		return resolve.src.get(asset);
	}
	
	private static AbstractAssetSystem system;
	
	protected static void setAssetSystem(AbstractAssetSystem system) {
		Cache.system = system;
	}
	
	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		Cache.debug = debug;
	}

	static {
		try {
			Class<?> found = Class.forName("com.legend.Assets");
			if(found != null) {
				Cache.system = (AbstractAssetSystem) found.newInstance();
				if(debug) {
					logger.info("Found Asset Manager At: com.legend.Assets");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("An asset manager needs to be located at com.legend.Assets extending AbstractAssetManager");
		}
		if(Cache.system == null) {
			throw new RuntimeException("An asset manager needs to be located at com.legend.Assets extending AbstractAssetManager");
		}
	}

}
