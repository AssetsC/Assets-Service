package com.example.project.models.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.WidenDetails;

public interface WidenDetailCrud extends CrudRepository<WidenDetails, Integer>{
	@Query(value="Select * From bf_widen_detail Where "
			+ "widen_id = :id",nativeQuery=true)
	public List<WidenDetails> findFDByWidenId(@Param("id")int id);
}
