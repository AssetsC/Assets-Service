package com.example.project.models.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.Reclaim;

public interface ReclaimCrud extends CrudRepository<Reclaim, Integer>{
	@Query(value="Select * From bf_reclaim "
			+ "Order By id DESC",nativeQuery=true)
	public Iterable<Reclaim> findAllDesc();
	
	@Query(value="Select * From bf_reclaim Where "
			+ "user_id = :user "
			+ "Order By id DESC",nativeQuery=true)
	public Iterable<Reclaim> findAllUserDesc(@Param("user")int user);
	
	@Query(value="Select * From bf_reclaim Where "
			+ "item_code Like %:name% "
			+ "Order By id DESC",nativeQuery=true)
	public Iterable<Reclaim> findByItemCode(@Param("name") String name);
	
	@Query(value="Select rc.* From bf_reclaim as rc , bf_item as it Where "
			+ "it.item_code = rc.item_code AND "
			+ "it.item_name Like %:name% "
			+ "Group By rc.id "
			+ "Order By rc.id DESC",nativeQuery=true)
	public Iterable<Reclaim> findByName(@Param("name") String name);
	
	@Query(value="Select rc.* From bf_reclaim as rc , bf_item as it Where "
			+ "it.item_code = rc.item_code AND "
			+ "it.item_model Like %:name% "
			+ "Group By rc.id "
			+ "Order By rc.id DESC",nativeQuery=true)
	public Iterable<Reclaim> findByModel(@Param("name") String name);
	
	@Query(value="Select rc.* From bf_reclaim as rc , bf_users as user Where "
			+ "rc.user_id = user.id AND "
			+ "user.firstname Like %:name% AND "
			+ "user.lastname Like %:value% "
			+ "Group by rc.id "
			+ "Order By rc.id DESC",nativeQuery=true)
	public Iterable<Reclaim> findByUser(@Param("name") String name,@Param("value") String value);
	
	@Query(value="Select rc.* From bf_reclaim as rc , bf_item as it , bf_item_detail as itd Where "
			+ "it.item_code = rc.item_code AND "
			+ "it.item_code = itd.item_code AND "
			+ "itd.detail_name Like %:name% AND "
			+ "itd.detail_value Like %:value% "
			+ "Group By rc.id "
			+ "Order By rc.id DESC",nativeQuery=true)
	public Iterable<Reclaim> findByDetail(@Param("name") String name,@Param("value") String value);

	@Query(value="Select * From bf_reclaim Where "
			+ "user_id = :user AND "
			+ "item_code Like %:name% "
			+ "Order By id DESC",nativeQuery=true)
	public Iterable<Reclaim> findByItemCodeUser(@Param("name") String name , @Param("user") int user);
	
	@Query(value="Select rc.* From bf_reclaim as rc , bf_item as it Where "
			+ "it.item_code = rc.item_code AND "
			+ "it.item_name Like %:name% AND  "
			+ "rc.user_id = :user "
			+ "Group By rc.id "
			+ "Order By rc.id DESC",nativeQuery=true)
	public Iterable<Reclaim> findByNameUser(@Param("name") String name , @Param("user")int user);

	@Query(value="Select rc.* From bf_reclaim as rc , bf_item as it Where "
			+ "it.item_code = rc.item_code AND "
			+ "it.item_model Like %:name% AND "
			+ "rc.user_id = :user "
			+ "Group By rc.id "
			+ "Order By rc.id DESC",nativeQuery=true)
	public Iterable<Reclaim> findByModelUser(@Param("name") String name , @Param("user")int user);
	
	@Query(value="Select rc.* From bf_reclaim as rc , bf_item as it , bf_item_detail as itd Where "
			+ "it.item_code = rc.item_code AND "
			+ "it.item_code = itd.item_code AND "
			+ "itd.detail_name Like %:name% AND "
			+ "itd.detail_value Like %:value% AND "
			+ "rc.user_id = :user "
			+ "Group By rc.id "
			+ "Order By rc.id DESC",nativeQuery=true)
	public Iterable<Reclaim> findByDetailUser(@Param("name") String name , @Param("value") String value ,@Param("user")int user);

	@Query(value="Select * From bf_reclaim Where "
			+ "user_id = :id AND "
			+ "reclaim_date between :date AND "
			+ "DATE_ADD(:date, INTERVAL 1 MONTH)",nativeQuery=true)
	List<Reclaim> findAllbetweenMonthUser(@Param("id") int id,@Param("date") String date);
	
	@Query(value="Select * From bf_reclaim Where "
			+ "reclaim_date between :date AND "
			+ "DATE_ADD(:date, INTERVAL 1 MONTH)",nativeQuery=true)
	List<Reclaim> findAllbetweenMonth(@Param("date") String date);
}
