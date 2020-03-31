package com.mockito.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

//@RunWith(MockitoJUnitRunner.class) - By mistake if we forgot to use this annotation, while we try to access @Mock or @spy marked objects results in NullPointerException
public class MockitoAnnotationsUninitializedUnitTest {

	@Mock
	List<String> mockedList ;
	
	/*
	 * Most of the time, this happens simply because we forgot to properly enable
	 * Mockito annotations.
	 * 
	 * So, we have to keep in mind that each time we want to use Mockito annotations, we must take an extra step and initialize them as below in init() method.
	 */
	
	@Before
	public void init() {
	    MockitoAnnotations.initMocks(this);
	}

	@Test //(expected = NullPointerException.class) - if we don't initialize as in init() method, accessing mockedList will throw error.
	public void whenMockitoAnnotationsUninitialized_thenNPEThrown() {
		mockedList.add("1");
		Mockito.when(mockedList.size()).thenReturn(1);
	}

}
