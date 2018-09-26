package com.example.project.models.crud;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.UserUpdate;

public interface UserUpdateCrud extends CrudRepository<UserUpdate, Integer> {
	@Query(value="Select * From bf_userupdate Where "
			+ "table_name = :tablename AND "
			+ "user_id = :user_id",nativeQuery=true)
	public  Optional<UserUpdate> findByCodeAndUser(@Param("tablename") String tablename,@Param("user_id") int user_id);
	
}
