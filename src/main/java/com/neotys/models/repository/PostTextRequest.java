package com.neotys.models.repository;

import org.immutables.value.Value;


@Value.Immutable
public interface PostTextRequest extends PostRequest {
    String getData();
}
