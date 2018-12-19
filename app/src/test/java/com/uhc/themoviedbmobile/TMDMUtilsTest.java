package com.uhc.themoviedbmobile;

import com.uhc.themoviedbmobile.utils.TMDMUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by const on 12/18/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class TMDMUtilsTest {
    /**
     * Assert that the date is formated correctly
     */
    @Test
    public void assertDateFormarted() {
        final String date = "2018-12-15";
        String formated_date = TMDMUtils.formatDate(date);
        assert(formated_date.equals("12/15/2018"));
    }

    /**
     * Assert that the date stills the same because the format is invalida
     */
    @Test
    public void assertDateInvalidIsNotFormarted() {
        final String date = "2018/12/15";
        String formated_date = TMDMUtils.formatDate(date);
        assert(formated_date.equals(date));
    }

    /**
     * Assert that the it will not crash when is passed a null value and return a empty string
     */
    @Test
    public void assertDateNullIsNotFormartedAndReturnEmpty() {
        final String date = null;
        String formated_date = TMDMUtils.formatDate(date);
        assert(formated_date.isEmpty());
    }
}
