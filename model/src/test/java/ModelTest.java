import com.github.charadziej.project.dao.Model;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Class for testing Model's methods.
 */

public class ModelTest {

    private static final String NAME = "Intel core i5";
    private static final String TYPE = "CPU";
    private static final Date RELEASE = new Date(2015, 11, 10); //Date(int year, int month, int date)
    private static final Integer ID = 1;

    Model model = new Model();

    @Test
    public void getModelId() throws Exception {
        model.setModelId(ID);
        Assert.assertEquals("check model's id: ", ID, model.getModelId());
    }

    @Test
    public void getName() throws Exception {
        model.setName(NAME);
        Assert.assertEquals("check model's name: ", NAME, model.getName());
    }

    @Test
    public void getType() throws Exception {
        model.setType(TYPE);
        Assert.assertEquals("check model's type: ", TYPE, model.getType());
    }

    @Test
    public void getReleaseDate() throws Exception {
        model.setReleaseDate(RELEASE);
        Assert.assertEquals("check model's release date: ", RELEASE, model.getReleaseDate());
    }

}