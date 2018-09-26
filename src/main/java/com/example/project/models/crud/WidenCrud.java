package com.example.project.models.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.Widens;

public interface WidenCrud extends CrudRepository<Widens, Integer>{
	@Query(value="Select * From bf_widen Where "
			+ "user_id = :user "
			+ "Order by widen_id DESC",nativeQuery=true)
	Iterable<Widens> findWidenByUserId(@Param("user")int user);
	
	@Query(value="Select * From bf_widen Where "
			+ "widen_status Like %:search "
			+ "Order by widen_id DESC",nativeQuery=true)
	Iterable<Widens> findWidenBySearch(@Param("search")String search);
	
	@Query(value="Select * From bf_widen Where "
			+ "widen_status Like %:search",nativeQuery=true)
	List<Widens> findWidenByState(@Param("search")int search);
	
	@Query(value="Select w.* From bf_users as user , bf_widen as w Where "
			+ "user.username like %:name% AND "
			+ "w.user_id = user.id AND "
			+ "w.widen_status Like %:search% "
			+ "Group By w.widen_id "
			+ "Order by w.widen_id DESC",nativeQuery=true)
	Iterable<Widens> findWidenByUsername(@Param("name")String name,@Param("search")String search);
	
	@Query(value="Select w.* From bf_widen_detail as wd, bf_widen as w Where "
			+ "wd.item_code Like %:name% AND "
			+ "w.widen_id = wd.widen_id AND "
			+ "w.widen_status Like %:search% "
			+ "Group By w.widen_id Order "
			+ "by w.widen_id DESC",nativeQuery=true)
	Iterable<Widens> findWidenByCode(@Param("name")String name,@Param("search")String search);
	
	@Query(value="Select w.* From bf_item as item , bf_widen as w , bf_widen_detail as wd Where "
			+ "item.item_name Like %:name% AND "
			+ "wd.item_code = item.item_code AND "
			+ "wd.widen_id = w.widen_id AND "
			+ "w.widen_status Like %:search% "
			+ "Group By w.widen_id "
			+ "Order by w.widen_id desc",nativeQuery=true)
	Iterable<Widens> findWidenByName(@Param("name")String name,@Param("search")String search);
	
	@Query(value="Select * From bf_department as depart , bf_users as user , bf_widen as widen Where "
			+ "user.depart_id = depart.depart_id AND "
			+ "user.depart_id = :depart AND "
			+ "user.id = widen.user_id",nativeQuery=true)
	List<Widens> findAllDepartment(@Param("depart")int depart);
	
	@Query(value="Select * From bf_widen Where "
			+ "user_id = :id AND "
			+ "widen_date between :date AND "
			+ "DATE_ADD(:date, INTERVAL 1 MONTH)",nativeQuery=true)
	List<Widens> findAllbetweenMonthUser(@Param("id") int id,@Param("date") String date);
	
	@Query(value="Select * From bf_widen Where "
			+ "widen_date between :date AND "
			+ "DATE_ADD(:date, INTERVAL 1 MONTH)",nativeQuery=true)
	List<Widens> findAllbetweenMonth(@Param("date") String date);
	
	//User
	@Query(value="Select * From bf_widen Where "
			+ "widen_status Like %:search AND "
			+ "user_id = :user "
			+ "Order by widen_id desc",nativeQuery=true)
	Iterable<Widens> findWidenBySearchUser(@Param("user")int user , @Param("search")String search);
	
	@Query(value="Select * From bf_widen Where "
			+ "widen_status Like %:search AND "
			+ "user_id = :user ",nativeQuery=true)
	List<Widens> findWidenByStateUser(@Param("user")int user , @Param("search")int search);
	
	@Query(value="Select w.* From bf_widen_detail as wd, bf_widen as w Where "
			+ "wd.item_code Like %:name% AND "
			+ "w.widen_id = wd.widen_id AND "
			+ "w.widen_status Like %:search% AND "
			+ "w.user_id = :user "
			+ "Group By w.widen_id "
			+ "Order by w.widen_id DESC",nativeQuery=true)
	Iterable<Widens> findWidenByCodeUser(@Param("user")int user , @Param("name")String name,@Param("search")String search);
	
	@Query(value="Select w.* From bf_item as item , bf_widen as w , bf_widen_detail as wd Where "
			+ "item.item_name Like %:name% AND "
			+ "wd.item_code = item.item_code AND "
			+ "wd.widen_id = w.widen_id AND "
			+ "w.widen_status Like %:search% AND "
			+ "w.user_id = :user "
			+ "Group By w.widen_id "
			+ "Order by w.widen_id DESC",nativeQuery=true)
	Iterable<Widens> findWidenByNameUser(@Param("user")int user , @Param("name")String name,@Param("search")String search);
	
	@Query(value="Select w.* From bf_item as item , bf_widen as w , bf_widen_detail as wd Where "
			+ "item.item_model Like %:name% AND "
			+ "wd.item_code = item.item_code AND "
			+ "wd.widen_id = w.widen_id AND "
			+ "w.widen_status Like %:search% AND "
			+ "w.user_id = :user "
			+ "Group By w.widen_id "
			+ "Order by w.widen_id DESC",nativeQuery=true)
	Iterable<Widens> findWidenByModelUser(@Param("user")int user , @Param("name")String name,@Param("search")String search);
	
	@Query(value="Select w.* From bf_item as item , bf_item_detail as td , bf_widen as w , bf_widen_detail as wd Where "
			+ "wd.item_code = item.item_code AND "
			+ "item.item_code = td.item_code AND "
			+ "td.detail_name Like %:name% AND "
			+ "td.detail_value Like %:value% AND "
			+ "wd.widen_id = w.widen_id AND "
			+ "w.widen_status Like %:search% AND "
			+ "w.user_id = :user "
			+ "Group By w.widen_id "
			+ "Order by w.widen_id DESC",nativeQuery=true)
	Iterable<Widens> findWidenByDetailUser(@Param("user")int user , @Param("name")String name,@Param("value")String value,@Param("search")String search);
}
