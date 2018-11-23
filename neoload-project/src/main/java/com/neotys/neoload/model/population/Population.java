package com.neotys.neoload.model.population;

import java.util.List;

import javax.validation.Valid;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ValidationMethod;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neotys.neoload.model.core.Element;
import com.neotys.neoload.model.validation.constraints.RequiredCheck;
import com.neotys.neoload.model.validation.groups.NeoLoad;

@JsonInclude(value=Include.NON_EMPTY)
@JsonPropertyOrder({Element.NAME, Element.DESCRIPTION, Population.USER_PATHS})
@JsonDeserialize(as = ImmutablePopulation.class)
@Value.Immutable
@Value.Style(validationMethod = ValidationMethod.NONE)
public interface Population extends Element {
	public static final String USER_PATHS = "user_paths";

	@RequiredCheck(groups={NeoLoad.class})
	@Valid
	@JsonProperty(USER_PATHS)
	List<UserPathPolicy> getUserPaths();

	class Builder extends ImmutablePopulation.Builder {}
	public static Builder builder() {
		return new Builder();
	}
}
