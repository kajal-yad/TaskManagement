package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.demo.entity.Task;
import com.example.demo.payload.ApiResponse;
import com.example.demo.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
	
	
    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> getAllTasks()
    {
    	List<Task> tasks = taskService.getAllTasks();
    	if(tasks.isEmpty()) 
    	{
    		return ResponseEntity.status(HttpStatus.NO_CONTENT)
    				.body(new ApiResponse<>("204","No tasks available",tasks));
    	}
    	
    	return ResponseEntity.ok(new ApiResponse<>("200","Tasks fetched successfully",tasks));
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Task>> getTaskById(@PathVariable Long id)
	{
		Task task = taskService.getTaskById(id);
		if(task == null) 
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("404","Task not found",null));
		}
		
		return ResponseEntity.ok(new ApiResponse<>("200","Task fetched successfully",task));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<Task>> createTask(@RequestBody Task task)
	{
		Task createdTask = taskService.createTask(task);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>("201","Task created successfully",createdTask));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Task>> updateTask(@PathVariable Long id,@RequestBody Task task)
	{
		Task updatedTask = taskService.updateTask(id,task);
		if(updatedTask == null) 
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("404","Task not found",null));
		}
		
		return ResponseEntity.ok(new ApiResponse<>("200","Task updated successfully",updatedTask));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id)
	{
		boolean deleted = taskService.deleteTask(id);
		if(!deleted) 
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>("404","Task not found",null));
		}
		return ResponseEntity.ok(new ApiResponse<>("200","Task deleted successfull",null));
	}
}
