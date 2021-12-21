package com.mzik.ui;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;

public class CustomPromptProvider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return null;
    }
}
