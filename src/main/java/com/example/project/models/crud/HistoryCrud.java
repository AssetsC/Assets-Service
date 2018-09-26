package com.example.project.models.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.History;

public interface HistoryCrud extends CrudRepository<History, Integer> {
	@Query(value="Select * From bf_history Where "
			+ "user_id = :user_id",nativeQuery=true)
	public List<History> findHistory(@Param("user_id")int user_id);
}
