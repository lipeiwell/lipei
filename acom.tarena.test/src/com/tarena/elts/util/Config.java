package com.tarena.elts.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private Properties cfg=new Properties();
	public Config(String file){
		try {
			cfg.load(new FileInputStream(file) );
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	public String getString(String key){
		return cfg.getProperty(key);
	}
	public int getInt(String key){
		return Integer.parseInt(cfg.getProperty(key));
	}
	public double getDouble(String key){
		return Double.parseDouble(cfg.getProperty(key));
	}
	public static void main(String[] args){
		Config config=new Config("client.properties");
		String ip=config.getString("ServerIP");
		System.out.println(ip);
		System.out.println(config.getInt("TimeLimit"));
	}
}
