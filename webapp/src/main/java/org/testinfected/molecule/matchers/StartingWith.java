package org.testinfected.molecule.matchers;

import org.testinfected.molecule.util.Matcher;

public class StartingWith implements Matcher<String> {
    private final String prefix;

    public StartingWith(String prefix) {
        this.prefix = prefix;
    }

    public boolean matches(String actual) {
        return actual.startsWith(prefix);
    }

    public static Matcher<String> startingWith(String prefix) {
        return new StartingWith(prefix);
    }
}
