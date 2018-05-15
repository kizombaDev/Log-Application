package org.kizombadev.pipeline.filter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

public class FilterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testHandleMissingProperty() {
        TestFilter filter = new TestFilter();

        thrown.expectMessage("The property 'foo' is missing in the entry");
        thrown.expect(IllegalStateException.class);

        filter.handle(new HashMap<>());
    }

    @Test
    public void testInitMissingConfiguration() {
        TestFilter filter = new TestFilter();

        thrown.expectMessage("The configuration property 'foo' is missing");
        thrown.expect(IllegalStateException.class);

        filter.init(new HashMap<>());
    }

    private static class TestFilter extends Filter {

        @Override
        public void handle(Map<String, Object> source) {
            getPropertyOrThrow("foo", source);
        }

        @Override
        public void init(Map<String, String> configuration) {
            getConfigurationOrThrow("foo", configuration);
        }

        @Override
        public Filter instanceCopy() {
            return new TestFilter();
        }
    }
}