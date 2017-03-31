package com.alma.platform.control;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Observer;

import com.alma.platform.data.PluginDescriptor;
import com.alma.platform.data.PluginParser;

/**
 * This class represents the BlueSnail platform and is used to manage the
 * different applications/plugin created by user.
 */
public class Platform {

	/**
	 * Class url of the main plugin interface.
	 */
	public static final String I_MAIN_PLUGIN_PATH = "com.alma.platform.data.IMainPlugin";

	/*
	 * Instance of the platform
	 */
	private static Platform INSTANCE;

	// Monitoring of platform
	private IMonitor monitor;

	// Parser of config file
	private PluginParser parser;

	// List of available plugin
	private List<PluginDescriptor> pluginDescriptor;

	// Class loader of external class
	private ClassLoader classLoader;

	// list of plugins' states
	private Map<PluginDescriptor, Boolean> pluginsMap;

	// --- CONSTRUCTOR

	/**
	 * Creates an instance of a Platform using the singleton pattern.
	 * 
	 * @throws IOException
	 * @throws NoSuchElementException
	 * @throws IllegalArgumentException
	 */
	private Platform() throws IOException, NoSuchElementException, IllegalArgumentException {
		pluginsMap = new HashMap<PluginDescriptor, Boolean>();
		parser = new PluginParser();
		pluginDescriptor = parser.parseFile("config.txt"); // Parse of
															// extensions file

		URL pluginUrls[] = new URL[pluginDescriptor.size()];
		String userDirUrl[] = System.getProperty("user.dir").split("/");
		int cpt = 0;

		/*
		 * Get and treats the absolute path of the plugin directory to create
		 * the ClassLoader
		 */

		for (PluginDescriptor plugin : pluginDescriptor) {

			String pluginUrl = "file:///";

			/*
			 * Note : We don't take the last fragment of the url which is the
			 * directory of the current project. We assume that the plugin
			 * project directory is located next to the current directory
			 * project.
			 */
			for (int i = 1; i < userDirUrl.length - 1; ++i) {
				pluginUrl += userDirUrl[i] + "/";
			}

			// Removes the first "/" if there is one.

			if (plugin.getDirectoryPath().substring(0, 1).equals("/"))
				pluginUrl += plugin.getDirectoryPath().substring(1, plugin.getDirectoryPath().length());
			else
				pluginUrl += plugin.getDirectoryPath();

			pluginUrls[cpt] = new URL(pluginUrl);
			++cpt;
		}
		initPluginsMap(pluginDescriptor);
		classLoader = new URLClassLoader(pluginUrls);

	}

	/**
	 * load the list of available plugins in the pluginsMap
	 * 
	 * @param pluginDescr
	 */
	private void initPluginsMap(List<PluginDescriptor> pluginDescr) {
		for (int i = 0; i < pluginDescr.size(); i++) {
			pluginsMap.put(pluginDescr.get(i), false);
		}
	}

	/*
	 * Checks if the plugin given in parameter implements the IMainPlugin
	 * interface.
	 */
	private boolean checkMainPlugin(PluginDescriptor plugin) throws ClassNotFoundException {

		boolean isMainPlugin = false;
		int i = 0;

		Class<?>[] clPlugin = Class.forName(plugin.getClassName(), true, classLoader).getInterfaces();

		while (i < clPlugin.length && !isMainPlugin) {
			if (clPlugin[i].getName().equals(I_MAIN_PLUGIN_PATH))
				isMainPlugin = true;

			++i;
		}

		return plugin.getInterfaceName().equals(I_MAIN_PLUGIN_PATH) && isMainPlugin;
	}

	// --- PUBLIC METHODS

	/**
	 * Returns the unique instance of the platform.
	 * 
	 * @return The unique platform instance.
	 * @throws NoSuchElementException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static Platform getInstance() throws NoSuchElementException, IllegalArgumentException, IOException {
		if (INSTANCE == null) {
			synchronized (Platform.class) {
				if (INSTANCE == null) {
					INSTANCE = new Platform();
				}
			}
		}

		return INSTANCE;
	}

	/**
	 * Returns the list of available plugin with their description from a client
	 * need.
	 * 
	 * @param need
	 *            The need of the application. The need is represented by an
	 *            interface that the plugin must implement.
	 * @return A list of available plugin that correspond to the client need.
	 */
	public List<PluginDescriptor> getListPlugin(Class<?> need) {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : pluginDescriptor) {
			if (plugin.getInterfaceName().equals(need.getName()))
				result.add(plugin);
		}

		return result;
	}

	/**
	 * Returns the list of autorun plugin from the available plugin, with their
	 * description.
	 * 
	 * @return A list of autorun plugin.
	 * @throws ClassNotFoundException
	 */
	public List<PluginDescriptor> getAutorunPlugin() throws ClassNotFoundException {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		/*
		 * Checks first if the plugin is autorun and then check if it implements
		 * the good interface
		 */
		for (PluginDescriptor plugin : pluginDescriptor) {
			if (plugin.isAutorun() && checkMainPlugin(plugin))
				result.add(plugin);
		}

		return result;
	}

	/**
	 * Returns an instance of a plugin.
	 * 
	 * @param className
	 *            The name of the plugin.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object getPluginInstance(String className)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (monitor != null) {
			updatePluginsMap(className);
			notify(this);
		}
		return Class.forName(className, true, classLoader).newInstance();
	}

	private void notify(Platform platform) {
		/*
		 * for (Observer observer : observers) { observer.update(); }
		 */
		// TODO
		// FIXME
		// https://www.tutorialspoint.com/design_pattern/observer_pattern.htm
	}

	/**
	 * update the state of the loaded plugin
	 * 
	 * @param className
	 */
	private void updatePluginsMap(String className) {
		for (Map.Entry<PluginDescriptor, Boolean> entry : pluginsMap.entrySet()) {
			if (entry.getKey().getClassName() == className) {
				entry.setValue(true);
			}
			break;
		}
	}

	public List<PluginDescriptor> getPluginDescriptor() {
		return pluginDescriptor;
	}

	// FIXME Remove this method : A plugin can't modify the list of loaded
	// plugin !
	public void setPluginDescriptor(List<PluginDescriptor> pluginDescriptor) {
		this.pluginDescriptor = pluginDescriptor;
	}

	public Map<PluginDescriptor, Boolean> getPluginsMap() {
		return pluginsMap;
	}

	// FIXME Idem : A plugin can't modify the states of loaded plugin ! It cant
	// only read it.
	public void setPluginsMap(Map<PluginDescriptor, Boolean> pluginsMap) {
		this.pluginsMap = pluginsMap;
	}

	public IMonitor getMonitor() {
		return monitor;
	}

	/**
	 * 
	 * @param monitor
	 */
	public void setMonitor(IMonitor monitor) {
		this.monitor = monitor;
	}

}
