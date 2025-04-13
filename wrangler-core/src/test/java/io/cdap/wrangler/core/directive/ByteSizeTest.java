import io.cdap.wrangler.api.parser.ByteSize;
import org.junit.Assert;
import org.junit.Test;

public class ByteSizeTest {

    @Test
    public void testByteSizeParsing() {
        Assert.assertEquals(10240, new ByteSize("10KB").getBytes());
        Assert.assertEquals(1572864, new ByteSize("1.5MB").getBytes());
        Assert.assertEquals(2147483648L, new ByteSize("2GB").getBytes());
        Assert.assertEquals(1, new ByteSize("1B").getBytes());
    }
}
