import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MainTest {
    @Test

    public void sum() {
        Main main = new Main();
        int a = 2;
        int b = 3;
        int result = main.sum(a, b);
        assertThat(result, is(5));
    }
}
