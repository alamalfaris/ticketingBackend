package com.be.ticketing.repository.supportedobject;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.be.ticketing.entity.SupportedObject;

public interface SupportedObjectRepository extends PagingAndSortingRepository<SupportedObject, Long> {
	
	@Query(value = "select ap.param_code, " +
			"ap.param_name " +
			"from app_param ap " +
			"where ap.param_group_code = 'OBJECT_TYPE' ",
			nativeQuery = true)
	List<Object[]> getObjectTypeSupportedObject();
	
	@Query(value = "select cast(ac.company_id as varchar) company_id, " +
			"ac.company_name " +
			"from app_company ac " +
			"order by ac.company_id asc ",
			nativeQuery = true)
	List<Object[]> getCompanySupportedObject();
	
	@Transactional
	@Modifying
	@Query(value = "call insert_supported_object(:i_object_code, :i_object_name, "
			+ ":i_object_type, :i_description, :i_company_id)",
			nativeQuery = true)
	void insert(@Param("i_object_code") String i_object_code,
			@Param("i_object_name") String i_object_name,
			@Param("i_object_type") String i_object_type,
			@Param("i_description") String i_description,
			@Param("i_company_id") Long i_company_id);
	
	@Transactional
	@Modifying
	@Query(value = "call update_supported_object(:i_object_code, :i_object_name, "
			+ ":i_object_type, :i_description, :i_company_id)",
			nativeQuery = true)
	void update(@Param("i_object_code") String i_object_code,
			@Param("i_object_name") String i_object_name,
			@Param("i_object_type") String i_object_type,
			@Param("i_description") String i_description,
			@Param("i_company_id") Long i_company_id);
	
	@Transactional
	@Modifying
	@Query(value = "update app_supported_object "
			+ "set "
			+ "record_flag = 'D', updated_by = :updatedBy, updated_time = now() "
			+ "where object_code = :objectCode ",
			nativeQuery = true)
	void updateDelete(@Param("objectCode") String objectCode,
			@Param("updatedBy") String updatedBy);
	
	@Query(value = "select aso.object_code "
			+ "from app_supported_object aso "
			+ "where lower(aso.object_code) = lower(:objectCode)"
			+ "limit 1 ",
			nativeQuery = true)
	String findCode(@Param("objectCode") String objectCode);
	
	
	@Query(value = "select ap.param_code "
			+ "from app_param ap "
			+ "where ap.param_group_code = 'OBJECT_TYPE' "
			+ "and lower(ap.param_code) = lower(:paramCode) "
			+ "limit 1",
			nativeQuery = true)
	String findType(@Param("paramCode") String paramCode);
	
	
	@Query(value = "select ac.company_id "
			+ "from app_company ac "
			+ "where ac.company_id = :companyId",
			nativeQuery = true)
	Long findCompany(@Param("companyId") Long companyId);
	
	@Query(value = "select ac.company_id "
			+ "from app_company ac "
			+ "where ac.company_code = :companyCode",
			nativeQuery = true)
	Long findCompanyByCode(@Param("companyCode") String companyCode);
	
	@Query(value = "select aso.object_id "
			+ "from app_supported_object aso "
			+ "where lower(aso.object_code) = lower(:objectCode)"
			+ "limit 1 ",
			nativeQuery = true)
	Long findId(@Param("objectCode") String objectCode);
	
	@Query(value = "select aso.object_code, " +
			"aso.object_name, " +
			"aso.object_type, " +
			"ap.param_name, " +
			"aso.description, " +
			"aso.company_id, " +
			"ac.company_name, " +
			"aso.created_by, " +
			"aso.created_time, " +
			"aso.updated_by, " +
			"aso.updated_time, " +
			"coalesce(to_char(aso.created_time, 'DD-Mon-YYYY')) as created_time_str, " +
			"coalesce(to_char(aso.updated_time, 'DD-Mon-YYYY')) as updated_time_str " +
			"from app_supported_object aso " +
			"inner join app_param ap on aso.object_type = ap.param_code " +
			"inner join app_company ac on aso.company_id = ac.company_id " +
			"where aso.record_flag != 'D' " +
			"and (:objectType = '' or lower(aso.object_type) = lower(:objectType)) " +
			"and (:objectName = '' or lower(aso.object_name) like lower(concat('%', :objectName, '%'))) " +
			"and (:companyName = '' or lower(ac.company_name) like lower(concat('%', :companyName, '%'))) " +
			"order by aso.created_time desc ",
			
			countQuery = "select count(1) " +
			"from app_supported_object aso " +
			"inner join app_param ap on aso.object_type = ap.param_code " +
			"inner join app_company ac on aso.company_id = ac.company_id " +
			"where aso.record_flag != 'D' " +
			"and (:objectType = '' or lower(aso.object_type) = lower(:objectType)) " +
			"and (:objectName = '' or lower(aso.object_name) like lower(concat('%', :objectName, '%'))) " +
			"and (:companyName = '' or lower(ac.company_name) like lower(concat('%', :companyName, '%'))) ",
			
			nativeQuery = true)
	List<Object[]> search(@Param("objectType") String objectType,
			@Param("objectName") String objectName,
			@Param("companyName") String companyName);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
