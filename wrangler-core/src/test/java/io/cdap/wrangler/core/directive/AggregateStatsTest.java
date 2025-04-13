import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.core.directive.AggregateStats;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.parser.DirectiveArguments;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.Text;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregateStatsTest {

    @Test
    public void testAggregateStats() throws Exception {
        List<Row> input = Arrays.asList(
            new Row("size", "1MB").add("duration", "1s"),
            new Row("size", "2MB").add("duration", "2s")
        );

        AggregateStats directive = new AggregateStats();
        Map<String, Object> args = new HashMap<>();
        args.put("size", new ColumnName("size"));
        args.put("duration", new ColumnName("duration"));
        args.put("targetSize", new Text("total_size_mb"));
        args.put("targetTime", new Text("total_time_sec"));

        directive.initialize(null, () -> args);
        List<Row> result = directive.execute(input, new ExecutorContext());

        Assert.assertEquals(1, result.size());
        Row row = result.get(0);
        Assert.assertEquals(3.0, (double) row.getValue("total_size_mb"), 0.01);
        Assert.assertEquals(3.0, (double) row.getValue("total_time_sec"), 0.01);
    }
}
