package com.neotys.models.scenario;

import org.immutables.value.Value;

@Value.Immutable
public interface TimeDurationPolicy extends DurationPolicy {

    // test duration in seconds
    long getDuration();
}
