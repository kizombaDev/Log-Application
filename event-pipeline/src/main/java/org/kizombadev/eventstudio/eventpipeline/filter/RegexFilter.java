package org.kizombadev.eventstudio.eventpipeline.filter;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;
import org.kizombadev.eventstudio.common.EventKeys;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("RegexFilter")
public class RegexFilter implements Filter {
    private Pattern pattern = null;

    @Override
    public void handle(Map<EventKeys, Object> json) {
        String origin = String.valueOf(getPropertyOrThrow(EventKeys.DATA, json));
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
            json.put(EventKeys.forValue(pair.getKey()), pair.getValue());
        }
    }

    @Override
    public void init(Map<String, String> configuration) {
        pattern = Pattern.compile(getConfigurationOrThrow("regex", configuration));
    }

    @Override
    public Filter instanceCopy() {
        return new RegexFilter();
    }
}
