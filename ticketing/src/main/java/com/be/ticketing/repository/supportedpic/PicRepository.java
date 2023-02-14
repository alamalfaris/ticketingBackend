package com.be.ticketing.repository.supportedpic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.be.ticketing.entity.Pic;

public interface PicRepository extends PagingAndSortingRepository<Pic, Long> {
	
	@Query(value = "select pic_id "
			+ "from app_pic ap "
			+ "where full_name in (:listName)",
			nativeQuery = true)
	List<Long> findPicIdByName(@Param("listName") List<String> listName);
	
	@Query(value = "from Pic "
			+ "where recordFlag != 'D' ")
	List<Pic> findAllPic();
	
	@Query(value = "select pic_id, "
			+ "full_name, "
			+ "email_address, "
			+ "mobile_no "
			+ "from app_pic ap "
			+ "where record_flag != 'D' "
			,nativeQuery = true)
	List<Object[]> getAllPic();
}
