package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;

@Service
public class TaskService {
	private final TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) 
	{
		this.taskRepository = taskRepository;
	}
	
	public List<Task> getAllTasks()
	{
		return taskRepository.findAll();
	}
	
	public Task getTaskById(Long id) 
	{
		return taskRepository.findById(id).orElse(null);
	}
	
	public Task createTask(Task task) 
	{
		return taskRepository.save(task);
	}
	
	public Task updateTask(Long id, Task updatedTask) 
	{
		Task task = taskRepository.findById(id).orElse(null);
		if(task!=null) {
		task.setTitle(updatedTask.getTitle());
		task.setDescription(updatedTask.getDescription());
		task.setCompleted(updatedTask.isCompleted());
		return taskRepository.save(task);
		}
		return null;
	}
	
	public boolean deleteTask(Long id) 
	{
		if(taskRepository.existsById(id)) 
		{
			taskRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
