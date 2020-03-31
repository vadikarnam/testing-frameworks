package com.mockito.demo.argumentMatchers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArgumentMatcherExampleTest {

	abstract class FlowerService {
		public abstract boolean isABigFlower(String name, int petals);
	}

	@Test
	public void testFlowerService() {

		FlowerService mock = Mockito.mock(FlowerService.class);

		/*
		 * In case of a method has more than one argument, it isn't possible to use
		 * ArgumentMatchers for only some of the arguments. Mockito requires you to
		 * provide all arguments either by matchers or by exact values.
		 * 
		 * 
		 * when(mock.isABigFlower("poppy", anyInt())).thenReturn(true); To fix it and
		 * keep the String name “poppy” as it's desired, we'll use eq matcher as below:
		 */

		when(mock.isABigFlower(Mockito.eq("poppy"), Mockito.anyInt())).thenReturn(true); //setting expectations
		boolean flag = mock.isABigFlower("poppy", 6); // call/executing method
		verify(mock).isABigFlower("poppy", 6); // verify method is called/executed
		assertEquals(true, flag); // assert/check
		
		

	}

}
