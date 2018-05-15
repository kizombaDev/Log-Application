package org.kizombadev.pipeline.filter;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("RegexFilter")
public class RegexFilter implements Filter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Pattern pattern = null;

    @Override
    public void handle(Map<String, Object> json) {
        String origin = String.valueOf(json.get("origin"));
        Matcher matcher = pattern.matcher(origin);
        if (!matcher.find()) {
            return;
        }

        List<Map<String, String>> maps = matcher.namedGroups();

        if (maps.isEmpty()) {
            return;
        }

        Map<String, String> properties = maps.get(0);

        for (Map.Entry<String, String> pair : properties.entrySet()) {
            json.put(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public void init(Map<String, String> configuration) {
        pattern = Pattern.compile(configuration.get("regex"));

    }

    @Override
    public Filter instanceCopy() {
        return new RegexFilter();
    }
}
