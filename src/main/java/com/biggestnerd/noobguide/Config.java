package com.biggestnerd.noobguide;

import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class Config {

	private ConfigurationSection base;
	private boolean enabled;
	private Map<String, Message> messages;
	
	public Config(ConfigurationSection base) {
		this.base = base;
		this.enabled = base.getBoolean("enabled", false);
		this.messages = loadMessages(base.getConfigurationSection("messages"));
	}
	
	public Message getMessage(String event) {
		return messages.get(event);
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	private Map<String, Message> loadMessages(ConfigurationSection config) {
		return null;
	}
}
