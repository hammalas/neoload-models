package com.neotys.models.repository;

import org.immutables.value.Value;

@Value.Immutable
public interface RegexpValidator extends Validator {
	String getValidationRegex();
}
