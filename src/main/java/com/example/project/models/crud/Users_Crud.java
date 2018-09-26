package com.example.project.models.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.Users;

public interface Users_Crud extends CrudRepository<Users, Integer> {
	
	@Query(value="Select * From bf_users Where "
			+ "firstname Like %:code% AND "
			+ "lastname Like %:value%",nativeQuery=true)
	Iterable<Users> findAvailableFullname(@Param("code") String code,@Param("value") String value);
	
	@Query(value="Select user.* From bf_users as user , bf_item as it , bf_available_item as ava Where "
			+ "it.item_code = ava.item_code AND "
			+ "ava.item_code Like %:code% AND "
			+ "ava.user_id = user.id "
			+ "Group By user.id",nativeQuery=true)
	Iterable<Users> findAvailableCode(@Param("code") String code);
	
	@Query(value="Select user.* From bf_users as user , bf_item as it , bf_available_item as ava Where "
			+ "it.item_code = ava.item_code AND "
			+ "it.item_name Like %:code% AND "
			+ "ava.user_id = user.id "
			+ "Group By user.id",nativeQuery=true)
	Iterable<Users> findAvailableName(@Param("code") String code);
	
	@Query(value="Select user.* From bf_users as user , bf_item as it , bf_available_item as ava Where "
			+ "it.item_code = ava.item_code AND "
			+ "it.item_model Like %:code% AND "
			+ "ava.user_id = user.id "
			+ "Group By user.id",nativeQuery=true)
	Iterable<Users> findAvailableModel(@Param("code") String code);
	
	@Query(value="Select user.* From bf_users as user , bf_item as it , bf_available_item as ava ,bf_item_detail as itd Where "
			+ "it.item_code = ava.item_code AND "
			+ "ava.user_id = user.id AND "
			+ "it.item_code = itd.item_code AND "
			+ "itd.detail_name Like %:code% AND "
			+ "itd.detail_value Like %:value% "
			+ "Group By user.id",nativeQuery=true)
	Iterable<Users> findAvailableDetail(@Param("code") String code,@Param("value") String value);
	
	@Query(value="Select * From bf_users Where "
			+ "role_id = 1",nativeQuery=true)
	List<Users> findAdminId();
	
}
