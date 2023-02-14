package com.be.ticketing.repository.settinggroup;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.be.ticketing.entity.SettingGroup;

public interface SettingGroupRepository extends JpaRepository<SettingGroup, String> {
	
	@Query(value = "from SettingGroup s where s.recordFlag != 'D' "
			+ "and (:settingGroupCd = '' or lower(s.settingGroupCd) like lower(concat('%', :settingGroupCd, '%'))) "
			+ "and (:settingGroupName = '' or lower(s.settingGroupName) like lower(concat('%', :settingGroupName, '%'))) "
			+ "order by s.createdTime desc")
	Page<SettingGroup> search(@Param("settingGroupCd") String settingGroupCd, @Param("settingGroupName") String settingGroupName,
			Pageable pageable);
	
	@Query(value = "from SettingGroup s where s.recordFlag != 'D' "
			+ "and (:settingGroupCd = '' or lower(s.settingGroupCd) like lower(concat('%', :settingGroupCd, '%'))) "
			+ "and (:settingGroupName = '' or lower(s.settingGroupName) like lower(concat('%', :settingGroupName, '%'))) "
			+ "order by s.createdTime desc")
	List<SettingGroup> searchByCodeOrByName(@Param("settingGroupCd") String settingGroupCd, @Param("settingGroupName") String settingGroupName);
	
	@Query(value = "from SettingGroup s where s.recordFlag != 'D' "
			+ "order by s.createdTime desc")
	List<SettingGroup> getSettingGroupCode();
}
