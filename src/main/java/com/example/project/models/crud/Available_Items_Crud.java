package com.example.project.models.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.Available_Items;

public interface Available_Items_Crud extends CrudRepository<Available_Items, Integer>{
	@Query(value="Select * From bf_available_item Where "
			+ "item_code = :code",nativeQuery=true)
	List<Available_Items> findAvailableByCode(@Param("code") String code);

	@Query(value="Select * From bf_available_item Where "
			+ "user_id = :user AND "
			+ "item_code Like %:code% "
			+ "Order by available_Item_id Desc",nativeQuery=true)
	List<Available_Items> findAvailableByCodeUser(@Param("code") String code,@Param("user") int user);
	
	@Query(value="Select ava.*  From bf_available_item as ava , bf_item as it Where "
			+ "ava.user_id = :user AND "
			+ "ava.item_code = it.item_code AND "
			+ "it.item_name Like %:code% "
			+ "Group By ava.available_Item_id "
			+ "Order by ava.available_Item_id DESC",nativeQuery=true)
	List<Available_Items> findAvailableByNameUser(@Param("code") String code,@Param("user") int user);
	
	@Query(value="Select ava.*  From bf_available_item as ava , bf_item as it Where "
			+ "ava.user_id = :user AND "
			+ "ava.item_code = it.item_code AND "
			+ "it.item_model Like %:code% "
			+ "Group By ava.available_Item_id "
			+ "Order by ava.available_Item_id DESC",nativeQuery=true)
	List<Available_Items> findAvailableByModelUser(@Param("code") String code,@Param("user") int user);
	
	@Query(value="Select ava.* From bf_available_item as ava , bf_item as it , bf_item_detail as itd Where "
			+ "ava.user_id = :user AND "
			+ "ava.item_code = it.item_code AND "
			+ "it.item_code = itd.item_code AND "
			+ "itd.detail_name Like %:code% AND "
			+ "itd.detail_value Like %:value% "
			+ "Group By ava.available_Item_id "
			+ "Order by ava.available_Item_id DESC",nativeQuery=true)
	List<Available_Items> findAvailableByDetailUser(@Param("code") String code,@Param("value") String value,@Param("user") int user);
	
	@Query(value="Select * From bf_available_item Where "
			+ "user_id = :user",nativeQuery=true)
	List<Available_Items> findAvailableUser(@Param("user") int user);

}
