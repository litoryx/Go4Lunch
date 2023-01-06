package com.example.go4lunch;

import com.example.go4lunch.ListStaff.UserRepository;
import com.example.go4lunch.Net.LocationRepository;
import com.example.go4lunch.Net.NetRepository;
import com.example.go4lunch.Net.NetServiceRetrofit;
import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.objetGoogle.Place;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ExampleUnitTest {

    UserRepository service;
    NetRepository serviceNet;

    @Before
    public void setup() {
        service = UserRepository.getInstance();
        serviceNet = new NetRepository(NetServiceRetrofit.getnetService());

    }

    @Test
    public void getRestaurantWithSuccess() {

    }

}