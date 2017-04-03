package com.alma.platform;

import com.alma.platform.control.Platform;
import com.alma.platform.data.IMainPlugin;
import com.alma.platform.data.PluginDescriptor;
import com.alma.platform.data.PluginInstance;

/**
 * Main class of the platform. Run all existing autorun plugin.
 */
public class Launcher {

	public static void main(String[] args) {
		try {
			Platform platform = Platform.getInstance();

			// Starting each plugin in autorun mode
			for (PluginDescriptor plugin : platform.getAutorunPlugin()) {
				IMainPlugin mainPlugin = (IMainPlugin) platform.getPluginInstance(plugin);
				platform.addInstance(new PluginInstance(PluginInstance.TYPE_APP, plugin.getPluginName(), mainPlugin));

				if (mainPlugin != null)
					mainPlugin.run();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
