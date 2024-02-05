package com.gojek.tests;

import com.gojek.client.BaseUrlHelper;
import com.gojek.client.HttpClient;
import com.gojek.util.ComparatorImplement;
import com.gojek.validator.Pilot;
import com.gojek.validator.ResponseExecutor;
import com.gojek.validator.UrlValid;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class CompareTest {

    @Test(description = "1)parse the files 2)get one url from each file 3)fetch json responses and verify they are same or not")
    public void testJsonComparatorUsingStreamsWithFilePath() {
        ResponseExecutor jsonDataProcessor = new ResponseExecutor(new ComparatorImplement(), new UrlValid(), new BaseUrlHelper());
        Pilot pilot = new Pilot(jsonDataProcessor, new ArrayList<>());
        ClassLoader classLoader = this.getClass().getClassLoader();
        pilot.runParallelComparatorWithStreams(new File(Objects.requireNonNull(classLoader.getResource("UrlListFirst.txt")).getFile()), new File(classLoader.getResource("UrlListSecond.txt").getFile()));
    }

    @Test(description = "1)get http response of 2 apis  2) check there responses are same or not")
    public void testJsonComparatorForNonMatchingResponse() {
        HttpClient processor1 = new HttpClient("https://reqres.in/api/users/3");
        HttpClient processor2 = new HttpClient("https://reqres.in/api/users/1");
        Assert.assertFalse(
                new ComparatorImplement().compare(processor1.getServiceResponse(), processor2.getServiceResponse()),
                "JsonComparator Failed");

    }
}
