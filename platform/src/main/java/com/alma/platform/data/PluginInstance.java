package com.alma.platform.data;

/**
 * This class represent an instance of a plugin.
 * 
 */
public class PluginInstance {

	public final static int TYPE_APP = 0;
	public final static int TYPE_EXT = 1;

	private int type;
	private String name;
	private Object instance;

	public PluginInstance(int type, String name, Object instance) {
		this.type = type;
		this.name = name;
		this.instance = instance;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

}
