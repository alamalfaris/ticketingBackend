package com.be.ticketing.repository.setting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.be.ticketing.entity.Setting;

public interface SettingRepository extends JpaRepository<Setting, String> {
	
	@Query(value = "select s.setting_code, s.setting_group_code, s.setting_desc, "
			+ "s.setting_value, s.setting_value_type, s.record_flag, s.created_by, "
			+ "s.created_time, s.updated_by, s.updated_time, "
			+ "coalesce(to_char(s.created_time, 'DD-Mon-YYYY')) as created_time_str, "
			+ "coalesce(to_char(s.updated_time, 'DD-Mon-YYYY')) as updated_time_str "
			+ "from app_setting s "
			+ "where s.record_flag != 'D' "
			+ "and (:settingCode = '' or lower(s.setting_code) like lower(concat('%', :settingCode, '%'))) "
			+ "and (:settingGroupCode = '' or lower(s.setting_group_code) like lower(concat('%', :settingGroupCode, '%'))) "
			+ "and (:settingValue = '' or lower(s.setting_value) like lower(concat('%', :settingValue, '%'))) "
			+ "and (:settingValueType = '' or lower(s.setting_value_type) like lower(concat('%', :settingValueType, '%'))) "
			+ "order by s.created_time desc "
			, nativeQuery = true)
	List<Object[]> search(@Param("settingCode") String settingCode,
			@Param("settingGroupCode") String settingGroupCode,
			@Param("settingValue") String settingValue,
			@Param("settingValueType") String settingValueType);
	
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
}
