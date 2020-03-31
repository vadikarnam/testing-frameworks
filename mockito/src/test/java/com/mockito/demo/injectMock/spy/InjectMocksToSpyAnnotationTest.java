package com.mockito.demo.injectMock.spy;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.mockito.demo.injectMock.MyDictionary;

@RunWith(MockitoJUnitRunner.class)
public class InjectMocksToSpyAnnotationTest {

	/*
	 * Now – let's discuss how to use @InjectMocks annotation – to inject mock
	 * fields into the tested object automatically.
	 * 
	 * In the following example – we use @InjectMocks to inject the mock wordMap
	 * into the MyDictionary dic:
	 */

	/*
	 * Similar to the above test, we might want to inject a mock into a spy:
	 * However, Mockito doesn't support injecting mocks into spies, and the
	 * following test results in an exception:
	 */

	/*
	 * If we want to use a mock with a spy, we can manually inject the mock through
	 * a constructor:add constructor into MyDictionary class
	 */

	@Mock
	Map<String, String> wordMap2;

	// This is spy map - but not used @spy annotation - becuase @spy annotation does not support injecting mocks
	MyDictionary spyDic;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		spyDic = Mockito.spy(new MyDictionary(wordMap2));
	}

	@Test
	public void whenUseInjectMocksAnnotationToSpy_thenCorrect() {
		Mockito.when(wordMap2.get("eWord")).thenReturn("eMeaning");

		assertEquals("eMeaning", spyDic.getMeaning("eWord"));
	}

}
