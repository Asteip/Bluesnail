package com.alma.platform;

import com.alma.plugin.Plugin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * the parser for configuration file
 * @author 
 */
public class Parser {

    /**
     * a method parsing the file of configuration of extensions
     * @param formatFichier
     * @return 
     * @throws IOException
     */
	public Map<String, Plugin> parseFile(String formatFichier) throws IOException {
        
		String line;
        FileReader fileReader = new FileReader(formatFichier);
		BufferedReader reader = new BufferedReader(fileReader);

        Map<String, Plugin> result = new HashMap<>();

        while (reader.ready()) {
            Properties properties = new Properties();
            Plugin plug = new Plugin();
            line = reader.readLine();

			String[] OneAttributeOneValue = line.split(";");
            for(int c = 0; c < OneAttributeOneValue.length; c++){
				String[] property = OneAttributeOneValue[c].split("=");
                properties.setProperty(property[0], property[1]);
            }
			plug.setProperties(properties);
            result.put(plug.getProperties().getProperty("name"), plug);
		}
		reader.close();
		return result;
	}

    /**
     * loading the configuration file of the platform
     * @param filename
     * @return
     * @throws IOException
     */
    public Properties loadConfig(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        Properties config = new Properties();
        config.load(fileReader);
        return config;
    }

}
