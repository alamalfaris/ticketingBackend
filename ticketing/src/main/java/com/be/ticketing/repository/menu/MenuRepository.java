package com.be.ticketing.repository.menu;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.be.ticketing.entity.Menu;

public interface MenuRepository extends PagingAndSortingRepository<Menu, Long> {
	
	
	@Query(value = "select menu_code "
			+ "from app_menu "
			+ "where menu_code = :menuCode "
			+ "limit 1"
			,nativeQuery = true)
	String findMenuCode(@Param("menuCode") String menuCode);
	
	@Query(value = "select param_code "
			+ "from app_param "
			+ "where param_group_code = 'MENU_TYPE' "
			+ "and lower(param_code) = lower(:menuType) "
			+ "limit 1 "
			,nativeQuery = true)
	String findMenuType(@Param("menuType") String menuType);
	
	@Query(value = "select menu_id "
			+ "from app_menu "
			+ "where menu_code = :menuCode "
			+ "limit 1"
			,nativeQuery = true)
	Long findMenuIdByCode(@Param("menuCode") String menuCode);
	
	@Query(value = "select menu_id "
			+ "from app_menu "
			+ "where lower(menu_name) = lower(:menuName) "
			+ "limit 1"
			,nativeQuery = true)
	Long findMenuIdByName(@Param("menuName") String menuName);
	
	@Query(value = "select menu_id, menu_name "
			+ "from app_menu "
			+ "where parent_id is null "
			,nativeQuery = true)
	List<Object[]> getMenuParent();
	
	@Query(value = "select am.menu_code, " +
			"am.menu_name, " +
			"am.menu_cmd, " +
			"case " +
			"	when am.parent_id is not null " +
			"		then (select am2.menu_name from app_menu am2 where am2.menu_id = am.parent_id) " +
			"	else '' " +
			"end parent, " +
			"ap.param_name menuType, " +
			"am.created_by, " +
			"am.created_time, " +
			"am.updated_by, " +
			"am.updated_time, " +
			"coalesce(to_char(am.created_time, 'DD-Mon-YYYY')) created_time_str, " +
			"coalesce(to_char(am.updated_time, 'DD-Mon-YYYY')) updated_time_str " +
			"from app_menu am " +
			"inner join app_param ap on am.menu_type = ap.param_code " +
			"where am.record_flag != 'D' " +
			"and (:menuCode = '' or lower(am.menu_code) = lower(:menuCode)) " +
			"and (:menuName = '' or lower(am.menu_name) = lower(:menuName)) " +
			"and (:parentId = 0 or am.parent_id = :parentId) " +
			"order by am.created_time desc "
			
			,countQuery = "select count(1) " +
			"from app_menu am " +
			"inner join app_param ap on am.menu_type = ap.param_code " +
			"where am.record_flag != 'D' " +
			"and (:menuCode = '' or lower(am.menu_code) = lower(:menuCode)) " +
			"and (:menuName = '' or lower(am.menu_name) = lower(:menuName)) " +
			"and (:parentId = 0 or am.parent_id = :parentId) "
			
			,nativeQuery = true)
	List<Object[]> search(@Param("menuCode") String menuCode,
			@Param("menuName") String menuName,
			@Param("parentId") Long parentId); 
}
