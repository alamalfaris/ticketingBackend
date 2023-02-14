package com.be.ticketing.repository.city;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.be.ticketing.entity.City;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {
	
	@Query(value = "select ac.city_code, " +
			"ac.city_name, " +
			"ac.province_id, " +
			"concat(ap.province_code, ' - ', ap.province_name) as province_cd, " +
			"ac.created_by, " +
			"ac.created_time, " +
			"ac.updated_by, " +
			"ac.updated_time, " +
			"coalesce(to_char(ac.created_time, 'DD-Mon-YYYY')) as created_time_str, " +
			"coalesce(to_char(ac.updated_time, 'DD-Mon-YYYY')) as updated_time_str " +
			"from app_city ac " +
			"inner join app_province ap " +
			"on ac.province_id = ap.province_id " +
			"where ac.record_flag != 'D' " +
			"and (:cityCd = '' or lower(ac.city_code) like lower(concat('%', :cityCd, '%'))) " +
			"and (:cityName = '' or lower(ac.city_name) like lower(concat('%', :cityName, '%'))) " +
			"and (:provinceId = 0 or ap.province_id = :provinceId) " +
			"order by ac.created_time desc "
			, countQuery = "select count(1) " +
					"from app_city ac " +
					"inner join app_province ap " +
					"on ac.province_id = ap.province_id " +
					"where ac.record_flag != 'D' " +
					"and (:cityCd = '' or lower(ac.city_code) like lower(concat('%', :cityCd, '%'))) " +
					"and (:cityName = '' or lower(ac.city_name) like lower(concat('%', :cityName, '%'))) " +
					"and (:provinceId = 0 or ap.province_id = :provinceId) "
			, nativeQuery = true)
	List<Object[]> searchCity(@Param("cityCd") String cityCd, @Param("cityName") String cityName, 
			@Param("provinceId") Long provinceId);
	
	@Query(value = "select ac.city_id from app_city ac "
			+ "where "
			+ "(ac.city_code = :cityCode) "
			+ "limit 1"
			, nativeQuery = true)
	Long searchId(@Param("cityCode") String cityCode);
	
	@Query(value = "select ac.city_code from app_city ac "
			+ "where "
			+ "(:cityCode = '' or lower(ac.city_code) like lower(concat('%', :cityCode, '%'))) "
			+ "limit 1"
			, nativeQuery = true)
	String searchCode(@Param("cityCode") String cityCode);
	
	@Query(value = "select ac.city_name from app_city ac "
			+ "where "
			+ "(:cityName = '' or lower(ac.city_name) like lower(concat('%', :cityName, '%'))) "
			+ "limit 1"
			, nativeQuery = true)
	String searchName(@Param("cityName") String cityName);
	
	@Transactional
	@Modifying
	@Query(value = "update app_city "
			+ "set record_flag = 'D' "
			+ "where province_id = :provinceId "
			, nativeQuery = true)
	void updateDeleteByProvince(@Param("provinceId") Long provinceId);
}
