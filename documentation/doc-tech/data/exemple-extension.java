/* Recuperer tous les plugins implementant l'interface 
 * IHighScore. Ces plugins sont recuperes dans des 
 * objets PluginDescriptor.
 */
List<PluginDescriptor> listHighScore = Platform.getInstance()
                                .getListPlugin(IHighScore.class);

/* Pour chaque plugin de la liste, on lance une instance 
 * de l'extension.
 */
for(PluginDescriptor plugin : listHighScore){
	IHighScore highScore =  Platform.getInstance()
	                            .getPluginInstance(plugin);
}