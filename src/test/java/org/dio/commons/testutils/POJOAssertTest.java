package org.dio.commons.testutils;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * User: Alex.Chumachev
 * Date: 12/20/13
 */
public class POJOAssertTest {
    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setAge(30);
        user.setCreationDate(new Date(123));
        user.setEmail("test@email.com");
        user.setMale(true);
        user.setName("Test");
        user.setPassword("passwd");
        user.setSalary(123.2f);
    }

    @Test
    public void testOk() {
        new POJOAssert()
                .expect("age", 30)
                .expect("creationDate", new Date(123))
                .expect("email", "test@email.com")
                .expect("male", true)
                .expect("salary", 123.2f)
                .ignore("password", "name")
                .assertPOJO(user);
    }

    @Test(expected = AssertionError.class)
    public void testNotSpecified() {
        new POJOAssert()
                .expect("age", 30)
                .expect("creationDate", new Date(123))
                .expect("email", "test@email.com")
                .expect("male", true)
                .ignore("password", "name")
                .assertPOJO(user);
    }

    @Test(expected = AssertionError.class)
    public void testWrongValue() {
        new POJOAssert()
                .expect("age", 30)
                .expect("creationDate", new Date(123))
                .expect("email", "wrong@email.com")
                .expect("male", true)
                .expect("salary", 123.2f)
                .ignore("password", "name")
                .assertPOJO(user);
    }
}
