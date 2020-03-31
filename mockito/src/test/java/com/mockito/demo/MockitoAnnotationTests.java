package com.mockito.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationTests {

	/*
	 * @RunWith(MockitoJUnitRunner.class) - Alternatively, we can enable Mockito
	 * annotations programmatically as well, by invoking
	 * MockitoAnnotations.initMocks():
	 * 
	 * @Before public void init() { MockitoAnnotations.initMocks(this); }
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void whenNotUseMockAnnotation_thenCorrect() {
		List<String> mockList = Mockito.mock(ArrayList.class);

		mockList.add("one");
		Mockito.verify(mockList).add("one");

		mockList.add("two");
		Mockito.when(mockList.size()).thenReturn(1);
		assertEquals(1, mockList.size());

		Mockito.when(mockList.add("one")).thenReturn(false);
		assertEquals(false, mockList.add("one"));

		Mockito.when(mockList.size()).thenReturn(100);
		assertEquals(100, mockList.size());

	}

	/*
	 * And now we'll do the same but we'll inject the mock using the @Mock
	 * annotation:
	 */

	@Mock
	List<String> mockedList;

	@Test
	public void whenUseMockAnnotation_thenMockIsInjected() {
		mockedList.add("one");
		mockedList.add("two");
		Mockito.verify(mockedList).add("one");
		Mockito.verify(mockedList).add("two");
		assertEquals(0, mockedList.size());

		Mockito.when(mockedList.size()).thenReturn(100);
		assertEquals(100, mockedList.size());
	}

	/*
	 * In the following example – we create a spy of a List with the old way without
	 * using @Spy annotation:
	 */

	@Test
	public void whenNotUseSpyAnnotation_thenCorrect() {
		List<String> spyList = Mockito.spy(new ArrayList<>());
		spyList.add("1");
		spyList.add("2");

		Mockito.verify(spyList).add("1");
		Mockito.verify(spyList).add("2");
		assertEquals(2, spyList.size());
	}

	/*
	 * Let's now do the same – spy on the list – but do so using the @Spy
	 * annotation:
	 */

	@Spy
	List<String> spiedList = new ArrayList<String>();

	@Test
	public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
		spiedList.add("1");
		spiedList.add("2");

		Mockito.verify(spiedList).add("1");
		Mockito.verify(spiedList).add("2");
		assertEquals(2, spiedList.size());

		Mockito.doReturn(100).when(spiedList).size();
		assertEquals(100, spiedList.size());

	}

	/*
	 * Note how, as before – we're interacting with the spy here to make sure that
	 * it behaves correctly. In this example we:
	 * 
	 * 1.Used the real method spiedList.add() to add elements to the spiedList.
	 * 2.Stubbed the method spiedList.size() to return 100 instead of 2 using
	 * Mockito.doReturn().
	 */

	/*
	 * In the following example – we create an ArgumentCaptor with the old way
	 * without using @Captor annotation:
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void whenNotUseCaptorAnnotation_thenCorrect() {
		List mockList = Mockito.mock(List.class);
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

		mockList.add("1");
		Mockito.verify(mockList).add(arg.capture());
		assertEquals("1", arg.getValue());

	}

	/*
	 * Let's now make use of @Captor for the same purpose – to create an
	 * ArgumentCaptor instance:
	 */
	@Mock
	List<String> mockList;

	@Captor
	ArgumentCaptor<String> arg;

	@Test
	public void whenUseCaptorAnnotation_thenTheSam() {
		mockList.add("1");
		Mockito.verify(mockList).add(arg.capture());
		assertEquals("1", arg.getValue());
	}

}
