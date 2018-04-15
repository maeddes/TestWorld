package de.hsesslingen.TestWorld;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestWorldApplication {

	@Autowired
	ToDoItemRepository repository;

	@GetMapping("/todos")
	String todos(){

		return repository.findAll().toString();

	}

	@PostMapping("/todos/{toDo}")
	String create(@PathVariable String toDo) {

		repository.save(new ToDoItem(toDo));
		return toDo+" created";
	}

	@GetMapping("/todos/{toDo}")
	String read(@PathVariable String toDo) {

		return repository.findByToDo(toDo).toString();
	}

	@PutMapping("/todos/setDone/{toDo}")
	String update(@PathVariable String toDo) {

		ToDoItem item = repository.findByToDo(toDo).get(0);
		if(item != null){

			item.setDone(true);
			repository.save(item);
		}
		return toDo+" updated";
	}

	@DeleteMapping("/todos/{toDo}")
	String delete(@PathVariable String toDo) {

		repository.deleteByToDo(toDo);
		return toDo+" deleted";
	}

	public static void main(String[] args) {
		SpringApplication.run(TestWorldApplication.class, args);
	}
}

interface ToDoItemRepository extends CrudRepository<ToDoItem, Long>{

	List<ToDoItem> findByToDo(String toDo);

	@Transactional
	void deleteByToDo(String toDo);

}

@Entity
class ToDoItem{

	@Id
	@GeneratedValue
	Long id;

	String toDo;
	boolean isDone;

	public ToDoItem(){}

	public ToDoItem(String toDo){

		this.toDo = toDo;
		this.isDone = false;

	}

	public String getToDo(){

		return toDo;
	}

	public boolean isDone(){

		return isDone;
	}

	public void setDone(boolean done){

		this.isDone = done;
	}

	public String toString(){

		return id+" Item: "+toDo+" done: "+isDone;

	}

	public boolean equals(Object o){

		return ((ToDoItem)o).getToDo().compareTo(toDo) == 0;

	}


	





}