package com.example.project.models.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.Item_Detail;

public interface Item_Detail_Crud  extends CrudRepository<Item_Detail, Integer>{
	
	@Query(value="Select * From bf_item_detail Where "
			+ "item_code = :code AND "
			+ "detail_name Like '%IP%'",nativeQuery=true)
	public List<Item_Detail> findByIP(@Param("code")String code);
}
