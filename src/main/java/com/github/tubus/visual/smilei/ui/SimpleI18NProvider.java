package com.github.tubus.visual.smilei.ui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.vaadin.flow.i18n.I18NProvider;

/**
 * Simple implementation of {@link I18NProvider}.
 */
@Component
public class SimpleI18NProvider implements I18NProvider {

    @Override
    public List<Locale> getProvidedLocales() {
        return Collections.unmodifiableList(
                Arrays.asList(Locale.ENGLISH, Locale.CHINESE));
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (Locale.ENGLISH.equals(locale)) {
            if (key.equals("root.navigate_to_component")) {
                return "Navigate to another component";
            }
        } else if (Locale.CHINESE.equals(locale)) {
            if (key.equals("root.navigate_to_component")) {
                return "前往另一视图";
            }
        }
        return null;
    }

}