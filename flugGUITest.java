import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

/**
 * Created by vifillsverrissonMacBookPro on 30/03/16.
 */
public class flugGUITest {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(flugGUITest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test1() {
        String str= "Junit is working fine";
        assertEquals("Junit is working fine",str);
    }

    @Test
    public void test2() {

    }

}