package com.mockito.demo.basic;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.TooLittleActualInvocations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoMethodsBasicTest {

	@Test
	public void testExecuteUsingMock() {

		// We will use this method to mock a class and set an expectation:
		MyList myList = mock(MyList.class);
		when(myList.add(anyString())).thenReturn(false);

		// Then execute a method on the mock:
		boolean added = myList.add(randomAlphabetic(6));

		// The following code confirms that the add method has been invoked on the mock,
		// and that the invocation returns a value which matches the expectation we set
		// before:
		verify(myList).add(anyString());
		assertEquals(false, added);

	}

	// To make sure that the provided name of a mock is included in the message of
	// an exception thrown from an unsuccessful verification, we will rely on a
	// JUnit implementation of the TestRule interface, called ExpectedException, and
	// include it in a test class:
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void mockingWithMockNameTest() {

		// we create a mock for the MyList class and name it myMock:
		MyList listMock = mock(MyList.class, "myMock");

		// Afterwards, set an expectation on a method of the mock and execute it:
		when(listMock.add(Mockito.anyString())).thenReturn(false);
		listMock.add(randomAlphabetic(6));

		thrown.expect(TooLittleActualInvocations.class);
		thrown.expectMessage(containsString("myMock.add"));

		// The following verification should fail and throw an exception matching what
		// were expected:
		verify(listMock, times(4)).add(anyString());

	}

	@Test
	public void mockWithDefaultAnswer() {

		// Let's start with the definition of an implementation of the Answer interface:

		// The CustomAnswer class above is used for the generation of a mock:
		MyList listMock = mock(MyList.class, new CustomAnswer());

		/*
		 * If we do not set an expectation on a method, the default answer, which was
		 * configured by the CustomAnswer type, will come into play. In order to prove
		 * it, we will skip over the expectation setting step and jump to the method
		 * execution:
		 */

		boolean added = listMock.add(randomAlphabetic(6));

		// The following verification and assertion confirm that the mock method with an
		// Answer argument has worked as expected:(false returned from Custom Answer)
		verify(listMock).add(anyString());
		assertThat(added, is(false));
	}

	@Test
	public void mockWithMockitoSettings() {

		/*
		 * There are several custom settings that are supported by methods of the
		 * MockSettings interface, such as registering a listener for method invocations
		 * on the current mock with invocationListeners, configuring serialization with
		 * serializable, specifying the instance to spy on with spiedInstance,
		 * configuring Mockito to attempt to use a constructor when instantiating a mock
		 * with useConstructor, and some others. For the convenience, we will reuse the
		 * CustomAnswer class introduced in the previous section to create a
		 * MockSettings implementation that defines a default answer.
		 */
		
		
		// A MockSettings object is instantiated by a factory method as follows:
		MockSettings customSettings = withSettings().defaultAnswer(new CustomAnswer());

		// That setting object will be used in the creation of a new mock:
		MyList listMock = mock(MyList.class, customSettings);

		// we will invoke the add method of a MyList instance and verify that a mock
		// method with a MockSettings argument works as it is meant to by using the
		// following code snippet:

		boolean added = listMock.add(randomAlphabetic(6));
		verify(listMock).add(anyString());
		assertThat(added, is(false));

	}

}
