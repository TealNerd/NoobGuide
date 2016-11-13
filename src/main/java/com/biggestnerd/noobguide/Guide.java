package com.biggestnerd.noobguide;

import org.bukkit.event.Listener;

public abstract class Guide implements Listener {

	private NoobGuide plugin;
	private Config config;
	
	public Guide(NoobGuide plugin, Config config) {
		this.plugin = plugin;
		this.config = config;
	}
}
