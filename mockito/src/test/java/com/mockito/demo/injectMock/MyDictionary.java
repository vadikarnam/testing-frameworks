package com.mockito.demo.injectMock;

import java.util.HashMap;
import java.util.Map;

/*
 * Now – let's discuss how to use @InjectMocks annotation – to inject mock
 * fields into the tested object automatically.
 * 
 * In the following example – we use @InjectMocks to inject the mock wordMap
 * into the MyDictionary dic:
 */

public class MyDictionary {
	Map<String, String> wordMap1;

	public MyDictionary() {
		wordMap1 = new HashMap<String, String>();
	}

	public MyDictionary(Map<String, String> wordMap2) {
		wordMap1 = wordMap2;
	}

	public void add(final String word, final String meaning) {
		wordMap1.put(word, meaning);
	}

	public String getMeaning(final String word) {
		return wordMap1.get(word);
	}
}
