package com.example.project.models.crud;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.Fix_Detail;

public interface Fix_Detail_Crud extends CrudRepository<Fix_Detail, Integer>{
	@Query(value="Select * From bf_fix_detail Where "
			+ "fix_id = :fix_id",nativeQuery=true)
	public Iterable<Fix_Detail> findFDByFixId(@Param("fix_id") int fix_id);
	
}
