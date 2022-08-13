package edu.school21.spring.preprocessor;

import java.util.Locale;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public String preProcessMessage(String message) {
        return message.toLowerCase(Locale.ROOT);
    }
}
