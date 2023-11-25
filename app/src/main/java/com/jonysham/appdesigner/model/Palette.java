package com.jonysham.appdesigner.model;

import java.util.List;

public class Palette {
	private int icon;
	private String name;
	private List<PaletteItem> items;
	
	public Palette(int icon, String name, List<PaletteItem> items) {
		this.icon = icon;
		this.name = name;
		this.items = items;
	}
}