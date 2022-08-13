package edu.school21.spring.preprocessor;

import java.util.Locale;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String preProcessMessage(String message) {
        return message.toUpperCase(Locale.ROOT);
    }
}
