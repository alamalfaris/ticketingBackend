package com.backend.ticketing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.backend.ticketing.entity.Parameter;

public interface IParameterRepository extends PagingAndSortingRepository<Parameter, String> {
	
	@Query(value = "select ap.param_code, "
			+ "ap.param_name, "
			+ "ap.param_desc, "
			+ "ap.param_group_code, "
			+ "ap.created_by, "
			+ "ap.created_time, "
			+ "ap.updated_by, "
			+ "ap.updated_time, "
			+ "ap.record_flag, "
			+ "ap.line_no "
			+ "from app_param_group apg "
			+ "inner join app_param ap "
			+ "on apg.param_group_code = ap.param_group_code "
			+ "where ap.record_flag != 'D' "
			+ "and (:parameterGroupCd = '' or lower(apg.param_group_code) = lower(:parameterGroupCd)) "
			+ "order by ap.line_no asc"
			, nativeQuery = true)
	List<Object[]> search(@Param("parameterGroupCd") String parameterGroupCd);
	
	@Transactional
	@Modifying
	@Query(value = "update app_param "
			+ "set record_flag = 'D', "
			+ "updated_by = :updatedBy, "
			+ "updated_time = now() "
			+ "where param_group_code = :parameterGroupCd "
			+ "and param_code not in (:listParamCode) " 
			, nativeQuery = true)
	void updateRecordFlag(@Param("updatedBy") String updatedBy, 
			@Param("parameterGroupCd") String parameterGroupCd,
			@Param("listParamCode") List<String> listParamCode);
	
	@Query(value = "select ap.param_code "
			+ "from app_param ap "
			+ "where record_flag = 'D' "
			+ "and param_group_code = :parameterGroupCd "
			+ "and param_code not in (:listParamCode) " 
			, nativeQuery = true)
	List<String> getParamCodeDeleted(@Param("parameterGroupCd") String parameterGroupCd,
			@Param("listParamCode") List<String> listParamCode);
	
	@Transactional
	@Modifying
	@Query(value = "update app_setting "
			+ "set record_flag = 'D', "
			+ "updated_by = :updatedBy, "
			+ "updated_time = now() "
			+ "where setting_value_type in (:listParamCodeDeleted) "
			, nativeQuery = true)
	void updateRecordFlag(@Param("updatedBy") String updatedBy,
			@Param("listParamCodeDeleted") List<String> listParamCodeDeleted);
	
	@Query(value = "select max(line_no) as maxLineNo "
			+ "from app_param "
			+ "where param_group_code = :parameterGroupCd " 
			, nativeQuery = true)
	Integer getMaxLineNo(@Param("parameterGroupCd") String parameterGroupCd);
}
