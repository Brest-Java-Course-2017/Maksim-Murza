import com.github.charadziej.project.dao.Type;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing Type's methods.
 */

public class TypeTest {

    private static final String NAME = "CPU";
    private static final Integer ID = 1;

    Type type = new Type();

    @Test
    public void getName() throws Exception {
        type.setName(NAME);
        Assert.assertEquals("Check type's name: ", NAME, type.getName());
    }

    @Test
    public void getTypeIdTest() throws Exception {
        type.setTypeId(ID);
        Assert.assertEquals("Check type's id: ", ID, type.getTypeId());
    }

}