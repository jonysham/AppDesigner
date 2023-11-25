package com.jonysham.appdesigner.editor.model;

import com.jonysham.appdesigner.editor.value.Value;

import java.util.LinkedHashMap;
import java.util.List;

public class PaletteItem {
	private String name;
	private String className;
	private int icon;
	private LinkedHashMap<Integer, Value> defaultAttributes;
	
	private PaletteItem() {
		defaultAttributes = new LinkedHashMap<>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getClassName() {
		return className;
	}
	
	public int getIcon() {
		return icon;
	}
	
	public LinkedHashMap<Integer, Value> getDefaultAttributes() {
		return defaultAttributes;
	}
	
	
	public static class PaletteItemBuilder {
		private PaletteItem item;
		
		public PaletteItemBuilder() {
			item = new PaletteItem();
		}
		
		public PaletteItemBuilder setName(String name) {
			item.name = name;
			return this;
		}
		
		public PaletteItemBuilder setClassName(String name) {
			item.className = name;
			return this;
		}
		
		public PaletteItemBuilder setIcon(int icon) {
			item.icon = icon;
			return this;
		}
		
		public PaletteItemBuilder addAttribute(int id, Value value) {
			item.defaultAttributes.put(id, value);
			return this;
		}
		
		public PaletteItem build() {
			return item;
		}
	}
}