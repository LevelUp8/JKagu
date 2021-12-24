package com.kagu.edit.jkagu.engine.query;

import java.util.Optional;

public interface QueryParser {
    Optional<String> execute(String row);
}
