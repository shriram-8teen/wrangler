package io.cdap.wrangler.core.directive;

import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.DirectiveContext;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.annotations.Name;
import io.cdap.wrangler.api.annotations.Description;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.DirectiveArguments;
import io.cdap.wrangler.api.parser.TokenType;
import io.cdap.wrangler.api.parser.Text;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;
import io.cdap.wrangler.api.ExecutorContext;

import java.util.ArrayList;
import java.util.List;

@Name("aggregate-stats")
@Description("Aggregates total byte size and time duration into target columns.")
public class AggregateStats implements Directive {

    private String sizeCol;
    private String timeCol;
    private String targetSizeCol;
    private String targetTimeCol;
    private long totalBytes = 0;
    private long totalTimeMillis = 0;

    @Override
    public void initialize(DirectiveContext context, DirectiveArguments arguments) throws Exception {
        this.sizeCol = ((ColumnName) arguments.value("size")).value();
        this.timeCol = ((ColumnName) arguments.value("duration")).value();
        this.targetSizeCol = ((Text) arguments.value("targetSize")).value();
        this.targetTimeCol = ((Text) arguments.value("targetTime")).value();
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) throws Exception {
        for (Row row : rows) {
            Object sizeObj = row.getValue(sizeCol);
            Object timeObj = row.getValue(timeCol);

            if (sizeObj instanceof String) {
                ByteSize bs = new ByteSize((String) sizeObj);
                totalBytes += bs.getBytes();
            }
            if (timeObj instanceof String) {
                TimeDuration td = new TimeDuration((String) timeObj);
                totalTimeMillis += td.getMilliseconds();
            }
        }

        List<Row> output = new ArrayList<>();
        Row result = new Row();
        result.add(targetSizeCol, totalBytes / (1024.0 * 1024.0)); // MB
        result.add(targetTimeCol, totalTimeMillis / 1000.0); // seconds
        output.add(result);
        return output;
    }
}
