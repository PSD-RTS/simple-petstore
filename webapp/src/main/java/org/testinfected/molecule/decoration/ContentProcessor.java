package org.testinfected.molecule.decoration;

import java.util.Map;

public interface ContentProcessor {

    Map<String, String> process(String content);
}
