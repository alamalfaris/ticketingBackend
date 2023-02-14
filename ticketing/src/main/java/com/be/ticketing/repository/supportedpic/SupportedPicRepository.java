package com.be.ticketing.repository.supportedpic;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.be.ticketing.entity.SupportedPic;

public interface SupportedPicRepository extends PagingAndSortingRepository<SupportedPic, Long> {
	
	@Query(value = "select distinct ac.company_id, " +
			"ac.company_code, " +
			"ac.company_name, " +
			"ac.created_by, " +
			"ac.created_time, " +
			"ac.updated_by, " +
			"ac.updated_time, " +
			"coalesce(to_char(ac.created_time, 'DD-Mon-YYYY')) as created_time_str, " +
			"coalesce(to_char(ac.updated_time, 'DD-Mon-YYYY')) as updated_time_str, " +
			"string_agg(ap.full_name || ', ' || ap.mobile_no || ', ' || ap.email_address, " +
			"'-') over " +
			"(partition by ac.company_id range between unbounded preceding and unbounded following) as pic " +
			"from app_company ac " +
			"left join app_pic_company apc on ac.company_id = apc.company_id " +
			"left join app_pic ap on apc.pic_id = ap.pic_id " +
			"where ac.record_flag != 'D' " +
			"and (:companyName = '' or lower(ac.company_name) like lower(concat('%', :companyName, '%'))) " +
			"and (:picEmail = '' or lower(ap.email_address) like lower(concat('%', :picEmail, '%'))) " +
			"and (:picName = '' or lower(ap.full_name) like lower(concat('%', :picName, '%'))) " +
			"order by ac.created_time desc ",
			
			countQuery = "select count(distinct ac.company_id) " +
			"from app_company ac " +
			"left join app_pic_company apc on ac.company_id = apc.company_id " +
			"left join app_pic ap on apc.pic_id = ap.pic_id " +
			"where ac.record_flag != 'D' " +
			"and (:companyName = '' or lower(ac.company_name) like lower(concat('%', :companyName, '%'))) " +
			"and (:picEmail = '' or lower(ap.email_address) like lower(concat('%', :picEmail, '%'))) " +
			"and (:picName = '' or lower(ap.full_name) like lower(concat('%', :picName, '%'))) ",
			nativeQuery = true)
	List<Object[]> search(@Param("companyName") String companyName,
			@Param("picEmail") String picEmail,
			@Param("picName") String picName);
	
	@Query(value = "select pic_company_id "
			+ "from app_pic_company apc "
			+ "where company_id = :companyId "
			+ "limit 1 ",
			nativeQuery = true)
	Long findId(@Param("companyId") Long companyId);
	
	@Transactional
	@Modifying
	@Query(value = "update app_pic_company "
			+ "set record_flag = 'D', "
			+ "updated_by = :updatedBy, "
			+ "updated_time = now() "
			+ "where company_id = :companyId " 
			+ "and pic_id not in (:listPicId) ",
			nativeQuery = true)
	void updateDeleteFlag(@Param("companyId") Long companyId,
			@Param("updatedBy") String updatedBy,
			@Param("listPicId") List<Long> listPicId);
	
	@Query(value = "from SupportedPic "
			+ "where companyId = :companyId "
			+ "and picId = :picId ")
	SupportedPic searchByPicAndCompany(@Param("companyId") Long companyId,
			@Param("picId") Long picId);
	
	@Query(value = "select company_id, "
			+ "company_code, "
			+ "company_name "
			+ "from app_company ac "
			+ "where company_id = :companyId ",
			nativeQuery = true)
	List<Object[]> searchcompany(@Param("companyId") Long companyId);
	
	@Query(value = "select apc.pic_id, "
			+ "ap.full_name, "
			+ "ap.mobile_no, "
			+ "ap.email_address "
			+ "from app_pic_company apc "
			+ "inner join app_pic ap on apc.pic_id = ap.pic_id "
			+ "where apc.company_id = :companyId ",
			nativeQuery = true)
	List<Object[]> searchCompanyPic(@Param("companyId") Long companyId);
	
	@Query(value = "select ac.company_code, " +
			"ac.company_name, " +
			"ap.full_name, " +
			"ap.mobile_no, " +
			"ap.email_address " +
			"from app_pic_company apc " +
			"inner join app_company ac on apc.company_id = ac.company_id " +
			"inner join app_pic ap on apc.pic_id = ap.pic_id " +
			"where apc.record_flag != 'D' " +
			"and (:companyName = '' or lower(ac.company_name) like lower(concat('%', :companyName, '%'))) " +
			"and (:picEmail = '' or lower(ap.email_address) like lower(concat('%', :picEmail, '%'))) " +
			"and (:picName = '' or lower(ap.full_name) like lower(concat('%', :picName, '%'))) ",
			nativeQuery = true)
	List<Object[]> searchDownload(@Param("companyName") String companyName,
			@Param("picEmail") String picEmail,
			@Param("picName") String picName);
}
