package com.example.go4lunch;

import com.example.go4lunch.liststaff.StaffViewModel;
import com.example.go4lunch.liststaff.UserRepository;
import com.example.go4lunch.models.RestaurantChoose;
import com.example.go4lunch.models.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class StaffViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private final UserRepository userRepository = mock(UserRepository.class);

    private final StaffViewModel mStaffViewModel = new StaffViewModel(userRepository);

    @Test
    public void staffViewModel() throws InterruptedException {
        List<User> usersTest = new ArrayList<>();
        RestaurantChoose restChosed = new RestaurantChoose();
        List<String> favoris = new ArrayList<>();
        User user1 = new User("1","Dad",restChosed,"mick@gmail.com",favoris,"http:/finissonsen");
        usersTest.add(user1);

        MutableLiveData<List<User>> userTest = new MutableLiveData<>(usersTest);
        when(userRepository.getUserData()).thenReturn(userTest);

        List<User> actual = LiveDataTestUtil.getOrAwaitValue(mStaffViewModel.getUsers());

        verify(userRepository).getUserData();

        assertEquals("1", actual.get(0).getUid());
    }
}
