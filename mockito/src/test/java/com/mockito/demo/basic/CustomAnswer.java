package com.mockito.demo.basic;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class CustomAnswer implements Answer<Boolean> {

	@Override
	public Boolean answer(InvocationOnMock invocation) throws Throwable {
		return false;
	}

}
