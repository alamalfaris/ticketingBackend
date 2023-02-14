package com.be.ticketing.repository.company;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.be.ticketing.entity.Company;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Long>{
	
	@Query(value = "select company_code "
			+ "from app_company "
			+ "where lower(company_code) = lower(:companyCode) "
			+ "limit 1 "
			, nativeQuery = true)
	String findCode(@Param("companyCode") String companyCode);
	
	@Query(value = "select company_id "
			+ "from app_company "
			+ "where lower(company_code) = lower(:companyCode) "
			+ "limit 1 "
			, nativeQuery = true)
	Long findId(@Param("companyCode") String companyCode);
	
	@Query(value = 
			"select ac.company_code, " +
			"ac.company_name, " +
			"ac.company_type, " +
			"ac.company_business, " +
			"ac.city_id, " +
			"ac.address, " +
			"ap.param_name companyType," +
			"ap2.param_name companyBusiness, " +
			"ap3.province_name, " +
			"ac.created_by, " +
			"ac.created_time, " +
			"ac.updated_by, " +
			"ac.updated_time, " +
			"coalesce(to_char(ac.created_time, 'DD-Mon-YYYY')) as created_time_str, " +
			"coalesce(to_char(ac.updated_time, 'DD-Mon-YYYY')) as updated_time_str " +
			"from app_company ac " +
			"inner join app_param ap on ac.company_type = ap.param_code " +
			"inner join app_param ap2 on ac.company_business = ap2.param_code " +
			"inner join app_city ac2 on ac.city_id = ac2.city_id " +
			"inner join app_province ap3 on ap3.province_id = ac2.province_id " +
			"where ac.record_flag != 'D' " +
			"and (:companyCd = '' or lower(ac.company_code) = lower(:companyCd)) " +
			"and (:companyName = '' or lower(ac.company_name) like lower(concat('%', :companyName, '%'))) " +
			"and (:companyBusiness = '' or lower(ac.company_business) = lower(:companyBusiness)) " +
			"order by ac.created_time desc ",
			
			countQuery = 
			"select count(1) " +
			"from app_company ac " +
			"inner join app_param ap on ac.company_type = ap.param_code " +
			"inner join app_param ap2 on ac.company_business = ap2.param_code " +
			"inner join app_city ac2 on ac.city_id = ac2.city_id " +
			"inner join app_province ap3 on ap3.province_id = ac2.province_id " +
			"where ac.record_flag != 'D' " +
			"and (:companyCd = '' or lower(ac.company_code) = lower(:companyCd)) " +
			"and (:companyName = '' or lower(ac.company_name) like lower(concat('%', :companyName, '%'))) " +
			"and (:companyBusiness = '' or lower(ac.company_business) = lower(:companyBusiness)) ", 
			
			nativeQuery = true)
	List<Object[]> search(@Param("companyCd") String companyCd,
			@Param("companyName") String companyName,
			@Param("companyBusiness") String companyBusiness);
	
	@Query(value = "select " +
			"ac.city_id, " +
			"ac.city_name, " +
			"ap.province_name " +
			"from app_city ac " +
			"inner join app_province ap on ap.province_id = ac.province_id ",
			nativeQuery = true)
	List<Object[]> getCityCompany();
	
	@Query(value = "select " +
			"ap.param_code, " +
			"ap.param_name " +
			"from app_param ap " +
			"where param_group_code = 'BUSINESS' ",
			nativeQuery = true)
	List<Object[]> getBusinessCompany();
	
	@Query(value = "select " +
			"ap.param_code, " +
			"ap.param_name " +
			"from app_param ap " +
			"where param_group_code = 'RELATION' ",
			nativeQuery = true)
	List<Object[]> getTypeCompany();
}
