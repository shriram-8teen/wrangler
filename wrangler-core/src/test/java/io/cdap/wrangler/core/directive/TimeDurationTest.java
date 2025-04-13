import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.junit.Test;

public class TimeDurationTest {

    @Test
    public void testTimeDurationParsing() {
        Assert.assertEquals(150, new TimeDuration("150ms").getMilliseconds());
        Assert.assertEquals(2100, new TimeDuration("2.1s").getMilliseconds());
        Assert.assertEquals(180000, new TimeDuration("3min").getMilliseconds());
        Assert.assertEquals(3600000, new TimeDuration("1h").getMilliseconds());
    }
}
