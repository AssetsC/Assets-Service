package com.example.project.models.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.Fix;

public interface FixCrud extends CrudRepository<Fix,Integer>{
	//Admin
	@Query(value="Select * From bf_fix "
			+ "Order By id DESC",nativeQuery=true)
	Iterable<Fix> findAllDesc();
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.item_code Like %:name% "
			+ "Group By f.id "
			+ "Order By f.id",nativeQuery=true)
	Iterable<Fix> findByCode(@Param("name") String name);
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd , bf_item as it Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.item_code = it.item_code AND "
			+ "it.item_name Like %:name% "
			+ "Group By f.id "
			+ "Order By f.id",nativeQuery=true)
	Iterable<Fix> findByName(@Param("name") String name);
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd , bf_item as it Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.item_code = it.item_code AND "
			+ "it.item_model Like %:name% "
			+ "Group By f.id "
			+ "Order By f.id",nativeQuery=true)
	Iterable<Fix> findByModel(@Param("name") String name);
	
	@Query(value="Select f.* From bf_fix as f,bf_users as u Where "
			+ "u.username Like '%:name%' AND "
			+ "u.id = f.user_id  "
			+ "Group By f.id "
			+ "Order By f.id Desc",nativeQuery=true)
	Iterable<Fix> findByUser(@Param("name") String name);
	
	@Query(value="Select f.* From bf_fix as f,bf_fix_detail as fd,bf_item as it,bf_item_detail as itd Where "
			+ "itd.detail_name Like %:name% AND "
			+ "itd.detail_value Like %:value% AND "
			+ "itd.item_code = it.item_code AND "
			+ "it.item_code = fd.item_code AND "
			+ "f.id = fd.fix_id "
			+ "Group By f.id "
			+ "Order By f.id Desc",nativeQuery=true)
	Iterable<Fix> findByDetail(@Param("name") String name , @Param("value") String value);
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.fix_detail_status = :type "
			+ "Group By f.id "
			+ "Order By f.id DESC",nativeQuery=true)
	Iterable<Fix> findAllTypeDesc(@Param("type") int type);
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.fix_detail_status = :type AND "
			+ "fd.item_code Like %:name% "
			+ "Group By f.id "
			+ "Order By f.id DESC",nativeQuery=true)
	Iterable<Fix> findByTypeCode(@Param("name") String name,@Param("type") int type);
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd , bf_item as it Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.fix_detail_status = :type AND "
			+ "fd.item_code = it.item_code AND "
			+ "it.item_name Like %:name% "
			+ "Group By f.id "
			+ "Order By f.id DESC",nativeQuery=true)
	Iterable<Fix> findByTypeName(@Param("name") String name,@Param("type") int type);
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd , bf_item as it Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.fix_detail_status = :type AND "
			+ "fd.item_code = it.item_code AND "
			+ "it.item_model Like %:name% "
			+ "Group By f.id "
			+ "Order By f.id DESC",nativeQuery=true)
	Iterable<Fix> findByTypeModel(@Param("name") String name,@Param("type") int type);
	
	@Query(value="Select f.* From bf_fix as f,bf_fix_detail,bf_users as u Where "
			+ "u.username Like '%:name%' AND "
			+ "u.id = f.user_id AND "
			+ "f.id = fd.fix_id AND "
			+ "fd.fix_detail_status = :type "
			+ "Group By f.id "
			+ "Order By f.id DESC",nativeQuery=true)
	Iterable<Fix> findByTypeUser(@Param("name") String name,@Param("type") int type);
	
	@Query(value="Select f.* From bf_fix as f,bf_fix_detail as fd,bf_item as it,bf_item_detail as itd Where "
			+ "itd.detail_name Like %:name% AND "
			+ "itd.detail_value Like %:value% AND "
			+ "itd.item_code = it.item_code AND "
			+ "it.item_code = fd.item_code AND "
			+ "f.id = fd.fix_id AND "
			+ "fd.fix_detail_status = :type "
			+ "Group By f.id "
			+ "Order By f.id Desc",nativeQuery=true)
	Iterable<Fix> findByTypeDetail(@Param("name") String name , @Param("value") String value,@Param("type") int type);
	
	//User
	@Query(value="Select * From bf_fix Where "
			+ "user_id = :user "
			+ "Order By id DESC",nativeQuery=true)
	Iterable<Fix> findAllDescUser(@Param("user") int user);
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.item_code Like %:name% AND "
			+ "f.user_id = :user "
			+ "Group By f.id "
			+ "Order By f.id DESC",nativeQuery=true)
	Iterable<Fix> findByCodeUser(@Param("name") String name , @Param("user") int user);
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.item_name Like %:name% AND "
			+ "f.user_id = :user "
			+ "Group By f.id "
			+ "Order By f.id DESC",nativeQuery=true)
	Iterable<Fix> findByNameUser(@Param("name") String name , @Param("user") int user);
	
	@Query(value="Select f.* From bf_fix as f , bf_fix_detail as fd Where "
			+ "f.id = fd.fix_id AND "
			+ "fd.item_model Like %:name% AND "
			+ "f.user_id = :user "
			+ "Group By f.id "
			+ "Order By f.id DESC",nativeQuery=true)
	Iterable<Fix> findByModelUser(@Param("name") String name , @Param("user") int user);

	@Query(value="Select f.* From bf_fix as f,bf_fix_detail as fd,bf_item as it,bf_item_detail as itd Where "
			+ "itd.detail_name Like %:name% AND "
			+ "itd.detail_value Like %:value% AND "
			+ "itd.item_code = it.item_code AND "
			+ "it.item_code = fd.item_code AND "
			+ "f.id = fd.fix_id AND "
			+ "f.user_id = :user "
			+ "Group By f.id "
			+ "Order By f.id Desc",nativeQuery=true)
	Iterable<Fix> findByDetailUser(@Param("name") String name , @Param("value") String value, @Param("user") int user);
	
	@Query(value="Select * From bf_fix Where "
			+ "user_id = :id AND "
			+ "fix_date between :date AND "
			+ "DATE_ADD(:date, INTERVAL 1 MONTH)",nativeQuery=true)
	List<Fix> findAllbetweenMonthUser(@Param("id") int id,@Param("date") String date);
	
	@Query(value="Select * From bf_fix Where "
			+ "fix_date between :date AND "
			+ "DATE_ADD(:date, INTERVAL 1 MONTH)",nativeQuery=true)
	List<Fix> findAllbetweenMonth(@Param("date") String date);
}
