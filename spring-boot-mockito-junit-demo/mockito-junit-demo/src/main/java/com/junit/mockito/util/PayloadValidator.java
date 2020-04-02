package com.junit.mockito.util;

import com.junit.mockito.model.ToDo;

public class PayloadValidator {

	public static boolean validateCreatePayload(ToDo toDo) {
		if (toDo.getId() > 0) {
			return false;
		}
		return true;
	}

}
