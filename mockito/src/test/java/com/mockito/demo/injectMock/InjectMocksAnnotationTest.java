package com.mockito.demo.injectMock;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InjectMocksAnnotationTest {
	
	/*
	 * Now – let's discuss how to use @InjectMocks annotation – to inject mock
	 * fields into the tested object automatically.
	 * 
	 * In the following example – we use @InjectMocks to inject the mock wordMap
	 * into the MyDictionary dic:
	 */
	
	
	@Mock
	Map<String, String> wordMap;
	 
	@InjectMocks
	MyDictionary dic = new MyDictionary();
	 
	@Test
	public void whenUseInjectMocksAnnotation_thenCorrect() {
	    Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");
	 
	    assertEquals("aMeaning", dic.getMeaning("aWord"));
	}

}
