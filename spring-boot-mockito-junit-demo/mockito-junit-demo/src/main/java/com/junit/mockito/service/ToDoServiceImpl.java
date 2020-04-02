package com.junit.mockito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junit.mockito.model.ToDo;
import com.junit.mockito.repo.ToDoRepository;


@Service("toDoService")
public class ToDoServiceImpl implements ToDoService {
	
	@Autowired
	private ToDoRepository toDoRepository;

	@Override
	public List<ToDo> getAllToDo() {
		return toDoRepository.findAll();
	}

	@Override
	public ToDo getToDoById(long id) {
		Optional<ToDo> toDo = toDoRepository.findById(id);
		
		
		if(!toDo.isPresent()) {
			return null;
		}else {
			return toDo.get();
		}
		
	}

	@Override
	public ToDo saveToDo(ToDo todo) {
		return toDoRepository.save(todo);
	}

	@Override
	public void removeToDo(ToDo todo) {
		toDoRepository.delete(todo);
	}

}
