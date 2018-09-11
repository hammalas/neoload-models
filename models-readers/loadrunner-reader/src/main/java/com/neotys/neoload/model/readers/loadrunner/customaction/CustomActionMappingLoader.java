package com.neotys.neoload.model.readers.loadrunner.customaction;

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.error.YAMLException;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CustomActionMappingLoader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomActionMappingLoader.class);
	private static final String CUSTOM_ACTION_MAPPING_YAML = "custom-action-mapping.yaml";
	
	private static final Yaml YAML = new Yaml();
	
	private static final Supplier<Map<String, ImmutableMappingMethod>> MAPPING = Suppliers.memoize(CustomActionMappingLoader::getCustomActionMapping);

	private CustomActionMappingLoader() {
	}

	public static Map<String, ImmutableMappingMethod> getMapping() {
		return MAPPING.get();
	}
	
	private static Map<String, ImmutableMappingMethod> getCustomActionMapping(){
		final String content;
		try {
			content = getCustomActionMappingFileContent();
		} catch (final IOException ioException) {
			LOGGER.error("Error while reading file " + CUSTOM_ACTION_MAPPING_YAML, ioException);
			return Maps.newHashMap();
		}
		return parseYaml(content);
	}

	private static String getCustomActionMappingFileContent() throws IOException {
		final InputStream in = CustomActionMappingLoader.class.getResourceAsStream(CUSTOM_ACTION_MAPPING_YAML);
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
			return buffer.lines().collect(Collectors.joining("\n"));
		}
	}

	private static Map<String, ImmutableMappingMethod> parseYaml(final String content) {
		final Map<String, ImmutableMappingMethod> methodMappings = new HashMap<>();
		try {			
			for(Entry<?,?> entry : ((Map<?,?>)YAML.loadAs(content, Map.class)).entrySet()){
				final Object methodName = entry.getKey();
				final Object methodMappingMap = entry.getValue();
				if(methodName == null || !(methodMappingMap instanceof Map)){
					LOGGER.error("Error while parsing file " + CUSTOM_ACTION_MAPPING_YAML);
					return Maps.newHashMap();
				}
				final ImmutableMappingMethod methodMapping = ImmutableMappingMethod.build((Map<?,?>) methodMappingMap);
				if(methodMapping == null){
					return Maps.newHashMap();
				}
				methodMappings.put(methodName.toString(), methodMapping);				
			}		
			return methodMappings;
		} catch (final YAMLException yamlException) {
			LOGGER.error("Error while parsing file " + CUSTOM_ACTION_MAPPING_YAML, yamlException);
			return Maps.newHashMap();
		}		
	}	
}
