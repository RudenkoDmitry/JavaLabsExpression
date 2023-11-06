import org.example.Expression;
import org.example.ExpressionAbstract;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class ExpressionTest {
    @Before
    public void setupData() {
        testExpression = new Expression();
        testExpression.set("(10 + 3) * 2 - (5 / 2) * 4");
    }

    @Test
    public void get() {
        Assert.assertEquals(testExpression.get(), String.valueOf("(10 + 3) * 2 - (5 / 2) * 4"));
    }

    @Test
    public void set() {
        testExpression.set("2+5");
        Assert.assertEquals(testExpression.get(), "2+5");
    }

    @Test
    public void solve() {
        Assert.assertEquals(testExpression.solve(),16.0);
    }

    private Expression testExpression;
}
