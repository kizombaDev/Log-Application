package org.kizombadev.eventstudio.eventpipeline.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kizombadev.eventstudio.common.EventKeys;
import org.kizombadev.eventstudio.common.elasticsearch.ElasticSearchService;
import org.kizombadev.eventstudio.eventpipeline.EventEntry;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SequentialTimeIdFilterTest {

    private Filter filter;

    @Mock
    private ElasticSearchService elasticSearchService;

    @Before
    public void init() {
        filter = new SequentialTimeIdFilter(elasticSearchService);
    }

    @Test
    public void test() {

        Mockito.when(elasticSearchService.getMaxValue(Mockito.anyList(), Mockito.eq(EventKeys.SEQUENTIAL_TIME_ID))).thenReturn(5.0);

        Map<String, Object> json = new HashMap<String, Object>() {{
            put(EventKeys.SOURCE_ID, "test");
        }};
        filter.handle(json);
        assertThat(json.get(EventKeys.SEQUENTIAL_TIME_ID).toString()).isEqualTo("5");

        filter.handle(json);
        assertThat(json.get(EventKeys.SEQUENTIAL_TIME_ID).toString()).isEqualTo("6");
    }

    @Test
    public void testNoSequentialTimeIdExist() {

        Mockito.when(elasticSearchService.getMaxValue(Mockito.anyList(), Mockito.eq(EventKeys.SEQUENTIAL_TIME_ID))).thenReturn(Double.NEGATIVE_INFINITY);

        Map<String, Object> json = new HashMap<String, Object>() {{
            put(EventKeys.SOURCE_ID, "test");
        }};
        filter.handle(json);
        assertThat(json.get(EventKeys.SEQUENTIAL_TIME_ID).toString()).isEqualTo("0");
    }
}