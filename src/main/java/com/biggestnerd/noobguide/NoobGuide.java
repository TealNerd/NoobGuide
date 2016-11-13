package com.biggestnerd.noobguide;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;

public class NoobGuide extends JavaPlugin implements Listener {

	private List<Guide> guides;
	private Map<String, Class<?>> pluginGuideMap;
	private ConfigurationSection guideConfigs;
	private boolean debug;
	
	public void onEnable() {
		debug = getConfig().getBoolean("debug", false);
		pluginGuideMap = new HashMap<String, Class<?>>();
		guideConfigs = getConfig().getConfigurationSection("guides");
		try {
			ClassPath getSamplersPath = ClassPath.from(getClassLoader());
			for(ClassPath.ClassInfo info : getSamplersPath.getTopLevelClasses("com.biggestnerd.noobguide.guides")) {
				try {
					Class<?> clazz = info.load();
					if(clazz != null && Guide.class.isAssignableFrom(clazz)) {
						String className = clazz.getSimpleName();
						pluginGuideMap.put(className.substring(0, className.length() - 5), clazz);
					}
				} catch (NoClassDefFoundError e) {
					
				} catch (Exception e) {
					
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	@EventHandler
	public void onPluginLoad(PluginEnableEvent event) {
		try {
			Class<?> clazz = pluginGuideMap.get(event.getPlugin().getName());
			ConfigurationSection guideConfig = guideConfigs.getConfigurationSection(clazz.getSimpleName());
			Config config = new Config(guideConfig);
			if(config != null) {
				Guide guide = null;
				try {
					Constructor<?> constructBasic = clazz.getConstructor(NoobGuide.class, config.getClass());
					guide = (Guide) constructBasic.newInstance(this, config);
				} catch (InvalidConfigException ice) {
					
				} catch (Exception e) {
					
				}
				if(guide == null) {
					
				} else {
					register(guide);
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	public void register(Guide guide) {
		if(guides != null) {
			guides.add(guide);
		}
	}
	
	// ===== debug / logging methods =====

		private static final String debugPrefix = "[DEBUG] ";

		public void debug(String message) {
			if (!debug) return;
			log(Level.INFO, NoobGuide.debugPrefix + message);
		}
		
		public void debug(String message, Object object) {
			if (!debug) return;
			log(Level.INFO, NoobGuide.debugPrefix + message, object);
		}
		
		public void debug(String message, Throwable thrown) {
			if (!debug) return;
			log(Level.INFO, NoobGuide.debugPrefix + message, thrown);
		}
		
		public void debug(String message, Object...objects) {
			if (!debug) return;
			log(Level.INFO, NoobGuide.debugPrefix + message, objects);
		}
			
		public void log(String message) {
			getLogger().log(Level.INFO, message);
		}

		public void log(Level level, String message) {
			getLogger().log(level, message);
		}
		
		public void log(Level level, String message, Throwable thrown) {
			getLogger().log(level, message, thrown);
		}

		public void log(Level level, String message, Object object) {
			getLogger().log(level, message, object);
		}

		public void log(Level level, String message, Object...objects) {
			getLogger().log(level, message, objects);
		}
}
