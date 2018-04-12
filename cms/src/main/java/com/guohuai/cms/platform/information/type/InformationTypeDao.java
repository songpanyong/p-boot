package com.guohuai.cms.platform.information.type;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.guohuai.cms.platform.information.InformationEntity;



public interface InformationTypeDao extends JpaRepository<InformationTypeEntity, String>, JpaSpecificationExecutor<InformationEntity>{
	
	/**
	 * 根据排序号获取资讯类型
	 * @param sort
	 * @return
	 */
	@Query("FROM InformationTypeEntity WHERE sort = ?1")
	public InformationTypeEntity findInformationTypeBySort(int sort);
	
	@Query("FROM InformationTypeEntity ORDER BY sort ASC")
	public List<InformationTypeEntity> findAllTypeList();
	
	/**
	 * 获取开启状态的资讯类型
	 * @return
	 */
	@Query("FROM InformationTypeEntity WHERE status = 1 ORDER BY sort ASC")
	public List<InformationTypeEntity> getInfoByStatus();
	
	/**
	 * 获取最大的排序号
	 * @return
	 */
	@Query("SELECT max(sort) FROM InformationTypeEntity WHERE status=1")
	public Integer findMaxSort();
	
	/**
	 * 排序
	 * @param oid
	 * @param sorting
	 * @return
	 */
	@Query("UPDATE InformationTypeEntity SET sort = ?2 WHERE oid = ?1")
	@Modifying
	public int setInfoTypeSort(String oid, Integer sorting);
	
	/**
	 * 获取开启状态的且排序大于0的资讯类型
	 * @return
	 */
	@Query("FROM InformationTypeEntity WHERE status=1 AND sort > 0 ORDER BY sort ASC")
	public List<InformationTypeEntity> getInformationTypeSort();
	/**
	 * 资讯类型是否有相同
	 * @param name
	 * @return
	 */
	@Query("SELECT count(*) FROM InformationTypeEntity WHERE name=?1")
	public int infoTypeNameIsSame(String name);
	
	public InformationTypeEntity findByName(String name);

}
