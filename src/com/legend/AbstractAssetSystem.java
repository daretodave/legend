package com.legend;

public interface AbstractAssetSystem {
	
	public void grabAssetsFromClasspath(String classpath, Cache dest) throws Exception;
	
	public void grabAssetsFromDirectory(String directory, Cache dest) throws Exception;

}
