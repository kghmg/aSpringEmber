package org.example;

import org.assertj.core.api.Assertions;
import org.example.domain.Reservation;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

public class MainAppTest {
    @Test
    public void create() throws Exception{
        Reservation rs = new Reservation(1, "Hisham");
        Assert.assertEquals(rs.getName(), "Hisham");
        Assert.assertThat(rs.getName(), Matchers.equalToIgnoringCase("Hisham"));
        Assert.assertThat(rs.getName(), new BaseMatcher<String>() {
            @Override
            public boolean matches(Object item) {
                return Character.isUpperCase(((String) item).charAt(0));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("The name should be valid uppercase");
            }
        });
        Assertions.assertThat(rs.getName()).isEqualToIgnoringCase("Hisham");
    }

}
