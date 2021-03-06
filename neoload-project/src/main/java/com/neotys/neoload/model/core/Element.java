package com.neotys.neoload.model.core;

import java.util.Optional;
import java.util.stream.Stream;

import com.neotys.neoload.model.validation.constraints.RequiredCheck;
import com.neotys.neoload.model.validation.groups.NeoLoad;

public interface Element {
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";

	@RequiredCheck(groups={NeoLoad.class})
	String getName();
    Optional<String> getDescription();
    
    Element withName(String of);

    default Stream<Element> flattened() {
        return Stream.of(this);
    }
}
