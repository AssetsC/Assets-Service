package com.example.project.models.crud;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.project.models.Items;

public interface Items_Crud extends CrudRepository<Items, Integer> {
	
	@Query(value="Select * From bf_item Where "
			+ "item_status Like %:type% "
			+ "Order By item_code",nativeQuery=true)
	public Iterable<Items> findAllAndType(@Param("type") int type);
	
	@Query(value="Select * From bf_item Where "
			+ "item_code Like %:code% ",nativeQuery=true)
	public Optional<Items> findOneItemCode(@Param("code") String code);
	
	@Query(value="Select * From bf_item Where "
			+ "item_code Like %:code% "
			+ "Order By item_code",nativeQuery=true)
	public Iterable<Items> findByCode(@Param("code") String code);
	
	@Query(value="Select * From bf_item Where "
			+ "item_name Like %:code% "
			+ "Order By item_code",nativeQuery=true)
	public Iterable<Items> findByName(@Param("code") String code);
	
	@Query(value="Select * From bf_item Where "
			+ "item_model Like %:code% "
			+ "Order By item_code",nativeQuery=true)
	public Iterable<Items> findByModel(@Param("code") String code);
	
	@Query(value="Select it.* From bf_item as it , bf_item_detail as itd Where "
			+ "itd.detail_name Like %:code% AND "
			+ "itd.detail_value Like %:value% And "
			+ "it.item_code = itd.item_code "
			+ "Group By it.item_code "
			+ "Order By it.item_code",nativeQuery=true)
	public Iterable<Items> findByDetail(@Param("code") String code,@Param("value")String value);
	
    @Query(value="Select it.* From bf_item as it , bf_item_detail as itd Where "
    		+ "itd.detail_name Like %:code% AND "
    		+ "itd.detail_value Like %:value% AND "
    		+ "it.item_code = itd.item_code AND "
    		+ "it.item_status Like %:type% "
    		+ "Group By it.item_code "
    		+ "Order By it.item_code",nativeQuery=true)
	public Iterable<Items> findByDetailAndType(@Param("code") String code,@Param("value")String value,@Param("type")int type);
	
    @Query(value="Select * From bf_item "
    		+ "Order By item_code Desc",nativeQuery=true)
    public List<Items> findAllItemByDesc();
     
	@Query(value="Select * From bf_item Where "
			+ "item_code Like %:code% AND "
			+ "item_status Like %:type% "
			+ "Order By item_code",nativeQuery=true)
	public Iterable<Items> findByCodeAndType(@Param("code") String code , @Param("type") int type);
	
	@Query(value="Select * From bf_item Where "
			+ "item_name Like %:code% AND "
			+ "item_status Like %:type% "
			+ "Order By item_code",nativeQuery=true)
	public Iterable<Items> findByNameAndType(@Param("code") String code , @Param("type") int type);
	
	@Query(value="Select * From bf_item Where "
			+ "item_model Like %:code% AND "
			+ "item_status Like %:type% "
			+ "Order By item_code",nativeQuery=true)
	public Iterable<Items> findByModelAndType(@Param("code") String code , @Param("type") int type);
	
	@Query(value="Select it.* From bf_item as it , bf_fix_detail as fd Where "
			+ "fd.fix_detail_status = :status AND "
			+ "fd.item_code = it.item_code "
			+ "Group By it.item_code "
			+ "Order By it.item_code",nativeQuery=true)
	public Iterable<Items> findByFDByCode(@Param("status") int status);
	
	@Query(value="Select * From bf_item Where "
			+ "item_status = :status "
			+ "Order By item_code",nativeQuery=true)
	public Iterable<Items> findByStatusByCode(@Param("status") int status);
	
	@Query(value="Select * From bf_item Where "
			+ "item_code = :code "
			+ "Order By item_code",nativeQuery=true)
	public List<Items> findListByCode(@Param("code") String code);
	
	@Query(value="Select * From bf_item "
			+ "Order By item_code",nativeQuery=true)
	public Iterable<Items> findAllDesc();
	
	@Query(value="Select i.* From bf_item as i , bf_available_item as a Where "
			+ "a.item_code = i.item_code AND "
			+ "a.user_id = :user",nativeQuery=true)
	public Iterable<Items> getAvaiItem(@Param("user")int user);
	
	@Query(value="Select * From bf_item Where "
			+ "item_code = :code Limit 1",nativeQuery=true)
	public Optional<Items> findByCodeLimit(@Param("code") String code);
}
