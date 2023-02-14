package com.be.ticketing.repository.parameter;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.be.ticketing.entity.ParameterGroup;


public interface ParameterGroupRepository extends PagingAndSortingRepository<ParameterGroup, String> {
	
	@Query(value = "select apg.param_group_code from app_param_group apg where apg.record_flag != 'D' ", 
			nativeQuery = true)
	List<String> searchParameterGroupCode();
	
	@Query(value = "select distinct apg.param_group_code, "
			+ "apg.param_group_name, "
			+ "apg.created_by, "
			+ "apg.created_time, "
			+ "apg.updated_by, "
			+ "apg.updated_time, "
			+ "apg.record_flag, "
			+ "coalesce(to_char(apg.created_time, 'DD-Mon-YYYY')) as created_time_str, "
			+ "coalesce(to_char(apg.updated_time, 'DD-Mon-YYYY')) as updated_time_str, "
			+ "string_agg(ap.param_name, ', ') over (partition by apg.param_group_code order by ap.line_no asc "
			+ "range between unbounded preceding and unbounded following) as param_name "
			+ "from app_param_group apg "
			+ "inner join app_param ap "
			+ "on apg.param_group_code = ap.param_group_code "
			+ "where apg.record_flag != 'D' "
			+ "and ap.record_flag != 'D' "
			+ "and (:parameterGroupCd = '' or lower(apg.param_group_code) like lower(concat('%', :parameterGroupCd, '%'))) "
			+ "and (:parameterGroupName = '' or lower(apg.param_group_name) like lower(concat('%', :parameterGroupName, '%'))) "
			+ "and (:parameterName = '' or lower(ap.param_name) like lower(concat('%', :parameterName, '%'))) "
			+ "order by apg.created_time desc"
			
			, countQuery = "select count(distinct apg.param_group_code) "
			+ "from app_param_group apg "
			+ "inner join app_param ap "
			+ "on apg.param_group_code = ap.param_group_code "
			+ "where apg.record_flag != 'D' "
			+ "and (:parameterGroupCd = '' or lower(apg.param_group_code) like lower(concat('%', :parameterGroupCd, '%'))) "
			+ "and (:parameterGroupName = '' or lower(apg.param_group_name) like lower(concat('%', :parameterGroupName, '%'))) "
			+ "and (:parameterName = '' or lower(ap.param_name) like lower(concat('%', :parameterName, '%')))"
			
			, nativeQuery = true)
	List<Object[]> searchParameterGroup(@Param("parameterGroupCd") String parameterGroupCd, 
			@Param("parameterGroupName") String parameterGroupName,
			@Param("parameterName") String parameterName);
	
	
	@Transactional
	@Modifying
	@Query(value = "update app_param_group "
			+ "set record_flag = 'D', "
			+ "updated_by = :updatedBy, "
			+ "updated_time = now() "
			+ "where param_group_code = :paramGroupCode "
			, nativeQuery = true)
	void updateRecordFlag(@Param("updatedBy") String updatedBy,
			@Param("paramGroupCode") String paramGroupCode);
}
