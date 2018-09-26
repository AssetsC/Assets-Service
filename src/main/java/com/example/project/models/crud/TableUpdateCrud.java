package com.example.project.models.crud;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.TableUpdate;

public interface TableUpdateCrud extends CrudRepository<TableUpdate,Integer> {
	@Query(value="Select * From bf_tableupdate Where"
			+ " table_name = :table_name",nativeQuery=true)
	public Optional<TableUpdate> findByTableName(@Param("table_name")String table_name);
	
}
