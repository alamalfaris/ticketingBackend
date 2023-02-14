package com.be.ticketing.repository.role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.be.ticketing.entity.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
	
	@Query(value = "select role_code "
			+ "from app_role "
			+ "where role_code = :roleCode "
			+ "limit 1 "
			,nativeQuery = true)
	String findRoleCode(@Param("roleCode") String roleCode);
	
	@Query(value = "select param_code "
			+ "from app_param "
			+ "where param_group_code = 'ROLE_TYPE' "
			+ "and lower(param_code) = lower(:roleType) "
			+ "limit 1 "
			,nativeQuery = true)
	String findRoleType(@Param("roleType") String roleType);
	
	@Query(value = "select role_id "
			+ "from app_role "
			+ "where role_code = :roleCode "
			+ "limit 1 "
			,nativeQuery = true)
	Long findRoleIdByCode(@Param("roleCode") String roleCode);
	
	@Query(value = "select role_code, "
			+ "role_name, role_type, role_desc "
			+ "from app_role "
			+ "where record_flag != 'D' "
			+ "and role_code = :roleCode "
			,nativeQuery = true)
	Object[][] findByCode(@Param("roleCode") String roleCode);
}
