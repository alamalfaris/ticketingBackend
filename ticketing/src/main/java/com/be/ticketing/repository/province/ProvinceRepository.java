package com.be.ticketing.repository.province;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.be.ticketing.entity.Province;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
	
	@Query(value = "from Province p where p.recordFlag != 'D' "
			+ "and (:provinceCode = '' or lower(p.provinceCode) like lower(concat('%', :provinceCode, '%'))) "
			+ "and (:provinceName = '' or lower(p.provinceName) like lower(concat('%', :provinceName, '%'))) "
			+ "order by p.createdTime desc")
	Page<Province> search(@Param("provinceCode") String provinceCode, @Param("provinceName") String provinceName,
			Pageable pageable);
	
	@Query(value = "from Province p where p.recordFlag != 'D' "
			+ "and (:provinceCode = '' or lower(p.provinceCode) like lower(concat('%', :provinceCode, '%'))) "
			+ "and (:provinceName = '' or lower(p.provinceName) like lower(concat('%', :provinceName, '%'))) "
			+ "order by p.createdTime desc")
	List<Province> searchByCodeOrByName(@Param("provinceCode") String provinceCode, 
			@Param("provinceName") String provinceName);
	
	@Query(value = "select province_id from app_province ap "
			+ "where "
			+ "(ap.province_code = :provinceCode) "
			+ "limit 1"
			, nativeQuery = true)
	Long searchId(@Param("provinceCode") String provinceCode);
	
	@Query(value = "select province_code from app_province ap "
			+ "where "
			+ "(:provinceCode = '' or lower(ap.province_code) like lower(concat('%', :provinceCode, '%'))) "
			+ "limit 1"
			, nativeQuery = true)
	String searchCode(@Param("provinceCode") String provinceCode);
	
	@Query(value = "select province_name from app_province ap "
			+ "where "
			+ "(:provinceName = '' or lower(ap.province_name) like lower(concat('%', :provinceName, '%'))) "
			+ "limit 1"
			, nativeQuery = true)
	String searchName(@Param("provinceName") String provinceName);
	
	@Query(value = "select ap.province_id, ap.province_name from app_province ap "
			+ "where ap.record_flag != 'D' "
			, nativeQuery = true)
	List<Object[]> getListProvince();
	
	@Transactional
	@Modifying
	@Query(value = "update app_province "
			+ "set record_flag = 'D' "
			+ "where province_id = :provinceId "
			, nativeQuery = true)
	void updateDeleteProvince(@Param("provinceId") Long provinceId);
}
