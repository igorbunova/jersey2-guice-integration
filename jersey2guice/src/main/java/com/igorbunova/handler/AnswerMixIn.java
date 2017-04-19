package com.igorbunova.handler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AnswerMixIn.
 */
public abstract class AnswerMixIn {

    @JsonCreator
    public AnswerMixIn(@JsonProperty(value = "ok", required = true) boolean ok) {}
}
