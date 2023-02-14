package com.be.ticketing.dao.city;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.ticketing.repository.city.CityRepository;
import com.be.ticketing.repository.province.ProvinceRepository;
import com.be.ticketing.request.city.CityRequest;
import com.be.ticketing.response.city.CitySearchResponse;
import com.be.ticketing.response.province.ProvinceResponse;
import com.be.ticketing.service.CommonService;

@Repository
public class CityDao extends CommonService implements ICityDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ProvinceRepository provinceRepo;
	
	@Autowired
	CityRepository cityRepo;
	
	public List<ProvinceResponse> getListProvince() {
		
		List<ProvinceResponse> results = new ArrayList<>();
		List<Object[]> rows = provinceRepo.getListProvince();
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				ProvinceResponse vo = new ProvinceResponse();
				
				BigInteger bi = (BigInteger)obj[0];
				String provinceId = bi.toString();
				
				vo.setProvinceId(provinceId != null ? provinceId : null);
				vo.setProvinceName((String)obj[1] != null ? (String)obj[1] : null);
				
				results.add(vo);
			}
		}
		return results;
	}

	@Override
	public List<CitySearchResponse> searchCities(CityRequest request) {

		StringBuilder sb = new StringBuilder();
		List<CitySearchResponse> results = new ArrayList<>();

		sb.append("select ac.city_code, ");
		sb.append("ac.city_name, ");
		sb.append("ac.province_id, ");
		sb.append("concat(ap.province_code, ' - ', ap.province_name) as province_cd, ");
		sb.append("ac.created_by, ");
		sb.append("ac.created_time, ");
		sb.append("ac.updated_by, ");
		sb.append("ac.updated_time, ");
		sb.append("coalesce(to_char(ac.created_time, 'DD-Mon-YYYY')) as created_time_str, ");
		sb.append("coalesce(to_char(ac.updated_time, 'DD-Mon-YYYY')) as updated_time_str ");
		sb.append("from app_city ac ");
		sb.append("inner join app_province ap ");
		sb.append("on ac.province_id = ap.province_id ");
		sb.append("where ac.record_flag != 'D' ");
		setSearchCriteria(sb, request);
		sb.append("order by ac.created_time desc ");
		

		Query query = entityManager.createNativeQuery(sb.toString());
		query.setFirstResult((request.getPageNumber() - 1) * request.getPageSize());
		query.setMaxResults(request.getPageSize());

		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				CitySearchResponse dto = new CitySearchResponse();
				
				dto.setCityCd((String)obj[0] != null ? (String)obj[0] : null);
				dto.setCityName((String)obj[1] != null ? (String)obj[1] : null);
				BigInteger provinceId = (BigInteger)obj[2] != null ? (BigInteger)obj[2] : null;
				dto.setProvinceId(provinceId.toString());
				dto.setProvinceCode((String)obj[3] != null ? (String)obj[3] : null);
				dto.setCreatedBy((String)obj[4] != null ? (String)obj[4] : null);
				dto.setCreatedTime((String)obj[8] != null ? (String)obj[8] : null);
				dto.setUpdatedBy((String)obj[6] != null ? (String)obj[6] : null);
				dto.setUpdatedTime((String)obj[9] != null ? (String)obj[9] : null);
				
				results.add(dto);
			}
		}
		
		return results;
	}
	
	@Override
	public Integer countSearchCities(CityRequest request) {

		StringBuilder sb = new StringBuilder();

		sb.append("select count(1) ");
		sb.append("from ");
		sb.append("app_city ");
		sb.append("where ");
		sb.append("record_flag != 'D' ");
		setSearchCriteria(sb, request);

		Query query = entityManager.createNativeQuery(sb.toString());
		query.setFirstResult((request.getPageNumber() - 1) * request.getPageSize());
		query.setMaxResults(request.getPageSize());

		BigInteger result = (BigInteger) query.getSingleResult();

		return result.intValue();
	}
	
	private void setSearchCriteria(StringBuilder sb, CityRequest request) {
		
		if (request.getCityCd().trim() != "") {
			sb.append("and lower(ac.city_code) like lower('%" + request.getCityCd() + "%') ");
		}
		if (request.getCityName().trim() != "") {
			sb.append("and lower(ac.city_name) like lower('%" + request.getCityName() + "%') ");
		}
		if (request.getProvinceCd().trim() != "") {
			sb.append("and lower(ap.province_code) like lower('%" + request.getProvinceCd() + "%') ");
		}
		if (request.getProvinceId().trim() != "") {
			sb.append("and ac.province_id =  " + request.getProvinceId());
		}
	}

	@Override
	public List<CitySearchResponse> searchCity(CityRequest request) {

		List<CitySearchResponse> results = new ArrayList<>();
		
		List<Object[]> rows = cityRepo.searchCity(this.convertValueForLike(request.getCityCd()), 
				this.convertValueForLike(request.getCityName()), this.convertId(request.getProvinceId()));
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				CitySearchResponse dto = new CitySearchResponse();
				
				dto.setCityCd((String)obj[0] != null ? (String)obj[0] : "");
				dto.setCityName((String)obj[1] != null ? (String)obj[1] : "");
				BigInteger provinceId = (BigInteger)obj[2] != null ? (BigInteger)obj[2] : null;
				dto.setProvinceId(provinceId.toString());
				dto.setProvinceCode((String)obj[3] != null ? (String)obj[3] : "");
				dto.setCreatedBy((String)obj[4] != null ? (String)obj[4] : "");
				dto.setCreatedTime((String)obj[8] != null ? (String)obj[8] : "");
				dto.setUpdatedBy((String)obj[6] != null ? (String)obj[6] : "");
				dto.setUpdatedTime((String)obj[9] != null ? (String)obj[9] : "");
				
				results.add(dto);
			}
		}
		
		return results;
	}

	@Override
	public List<CitySearchResponse> downloadCity(CityRequest request) {
		
		List<CitySearchResponse> results = new ArrayList<>();
		
		List<Object[]> rows = cityRepo.searchCity(this.convertValueForLike(request.getCityCd()), 
				this.convertValueForLike(request.getCityName()), this.convertId(request.getProvinceCd()));
		
		if (rows.size() > 0) {
			for (Object[] obj : rows) {
				
				CitySearchResponse dto = new CitySearchResponse();
				
				dto.setCityCd((String)obj[0] != null ? (String)obj[0] : null);
				dto.setCityName((String)obj[1] != null ? (String)obj[1] : null);
				BigInteger provinceId = (BigInteger)obj[2] != null ? (BigInteger)obj[2] : null;
				dto.setProvinceId(provinceId.toString());
				dto.setProvinceCode((String)obj[3] != null ? (String)obj[3] : null);
				dto.setCreatedBy((String)obj[4] != null ? (String)obj[4] : null);
				dto.setCreatedTime((String)obj[8] != null ? (String)obj[8] : null);
				dto.setUpdatedBy((String)obj[6] != null ? (String)obj[6] : null);
				dto.setUpdatedTime((String)obj[9] != null ? (String)obj[9] : null);
				
				results.add(dto);
			}
		}
		
		return results;
	}

	

	
	
}
