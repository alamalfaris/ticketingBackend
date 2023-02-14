package com.be.ticketing.dao.role;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.ticketing.repository.role.RoleRepository;
import com.be.ticketing.request.role.RoleRequest;
import com.be.ticketing.response.role.RoleResponse;
import com.be.ticketing.response.role.RoleSearchResponse;
import com.be.ticketing.service.CommonService;

@Repository
public class RoleDao extends CommonService implements IRoleDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public RoleResponse getRole(String roleCode) {

		RoleResponse result = new RoleResponse();
		
		Object[][] obj = roleRepo.findByCode(roleCode);
		
		if (obj != null) {
			result.setRoleCode((String)obj[0][0]);
			result.setRoleName((String)obj[0][1]);
			result.setRoleType((String)obj[0][2]);
			result.setRoleDesc((String)obj[0][3]);
		}
		
		return result;
	}

	@Override
	public List<RoleSearchResponse> search(RoleRequest request) {

		StringBuilder sb = new StringBuilder();
		List<RoleSearchResponse> results = new ArrayList<>();
		
		sb.append("select role_code, ");
		sb.append("role_name, role_type, role_desc, ");
		sb.append("created_by, created_time, updated_by, updated_time, ");
		sb.append("coalesce(to_char(created_time, 'DD-Mon-YYYY')) created_time_str, ");
		sb.append("coalesce(to_char(updated_time, 'DD-Mon-YYYY')) updated_time_str ");
		sb.append("from app_role ");
		sb.append("where record_flag != 'D' ");
		setSearchCriteria(sb, this.convertValueForLike(request.getRoleCode()),
				this.convertValueForLike(request.getRoleName()));
		sb.append("order by created_time desc ");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setFirstResult((request.getPageNumber() - 1) * request.getPageSize());
		query.setMaxResults(request.getPageSize());
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				RoleSearchResponse dto = new RoleSearchResponse();
				dto.setRoleCode((String)obj[0]);
				dto.setRoleName((String)obj[1]);
				dto.setRoleType((String)obj[2]);
				dto.setRoleDesc((String)obj[3]);
				dto.setCreatedBy(obj[4] != null ? (String)obj[4] : "");
				dto.setCreatedTime(obj[8] != null ? (String)obj[8] : "");
				dto.setUpdatedBy(obj[6] != null ? (String)obj[6] : "");
				dto.setUpdatedTime(obj[9] != null ? (String)obj[9] : "");
				
				results.add(dto);
			}
		}
		
		return results;
	}
	
	@Override
	public Integer searchCount(RoleRequest request) {

		StringBuilder sb = new StringBuilder();
		
		sb.append("select count(1) ");
		sb.append("from app_role ");
		sb.append("where record_flag != 'D' ");
		setSearchCriteria(sb, this.convertValueForLike(request.getRoleCode()),
				this.convertValueForLike(request.getRoleName()));
		
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger result = (BigInteger) query.getSingleResult();

		return result.intValue();
	}
	
	private void setSearchCriteria(StringBuilder sb, String roleCode, String roleName) {
		
		if (roleCode != "") {
			sb.append("and (lower(role_code) = lower('" + roleCode + "')) ");
		}
		if (roleName != "") {
			sb.append("and (lower(role_name) like lower(concat('%','" + roleName + "', '%'))) ");
		}
	}

	@Override
	public List<RoleSearchResponse> searchDownload(RoleRequest request) {

		StringBuilder sb = new StringBuilder();
		List<RoleSearchResponse> results = new ArrayList<>();
		
		sb.append("select role_code, ");
		sb.append("role_name, role_type, role_desc ");
		sb.append("from app_role ");
		sb.append("where record_flag != 'D' ");
		setSearchCriteria(sb, this.convertValueForLike(request.getRoleCode()),
				this.convertValueForLike(request.getRoleName()));
		
		Query query = entityManager.createNativeQuery(sb.toString());
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				RoleSearchResponse dto = new RoleSearchResponse();
				dto.setRoleCode((String)obj[0]);
				dto.setRoleName((String)obj[1]);
				dto.setRoleType((String)obj[2]);
				dto.setRoleDesc((String)obj[3]);
				
				results.add(dto);
			}
		}
		
		return results;
	}

	
}
