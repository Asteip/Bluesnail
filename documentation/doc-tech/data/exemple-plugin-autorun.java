// Starting each plugin in autorun mode
for (PluginDescriptor plugin : platform.getAutorunPlugin()) {
	IMainPlugin mainPlugin = (IMainPlugin) platform
	                            .getPluginInstance(plugin);

	if (mainPlugin != null)
		mainPlugin.run();
}