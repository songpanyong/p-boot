package com.guohuai.points.dao;

import com.guohuai.points.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface AddressDao extends JpaRepository<AddressEntity, String>, JpaSpecificationExecutor<AddressEntity> {


	@Modifying
	@Query(value = "UPDATE T_TULIP_TAKE_ADDRESS SET  updateTime=now(),  isdel=?2 WHERE oid=?1", nativeQuery = true)
	int isDelete(String oid, String isdel);

	@Query(value = "UPDATE T_TULIP_TAKE_ADDRESS SET  name=?1 , takeAddress=?2 ,  updateTime=now(), isDefault=0, phone=?3 , zipCode=?4  WHERE oid=?5", nativeQuery = true)
	@Modifying
	int updateAddress(String name, String takeAddress, String phone, String zipCode, String oid);

	@Query(value = "UPDATE T_TULIP_TAKE_ADDRESS SET  isDefault = 0 WHERE userOid=?1 ", nativeQuery = true)
	@Modifying
	void setAllAddressNotDefault(String userOid);

	@Query(value = "UPDATE T_TULIP_TAKE_ADDRESS SET  isDefault = 1 WHERE oid=?1 AND isdel='no'", nativeQuery = true)
	@Modifying
	int setAddressDefault(String oid);

	@Query(value = "SELECT count(*) FROM t_tulip_take_address where userOid=?1 AND isdel='no'", nativeQuery = true)
	int countByUserOid(String userOid);
}
