package com.guohuai.points.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.guohuai.points.entity.PointGoodsEntity;


public interface GoodsDao extends JpaRepository<PointGoodsEntity, String>, JpaSpecificationExecutor<PointGoodsEntity> {
	
	/**
	 * 查询并锁定某条商品数据
	 * @param oid
	 * @return
	 */
	@Query(value = "SELECT *  FROM t_tulip_goods WHERE oid=?1  FOR UPDATE ", nativeQuery = true)
	PointGoodsEntity forUpdateByOid(String oid);
	
	/**
	 * 回退库存
	 * @param oid
	 * @param quantity
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE t_tulip_goods SET remainCount = remainCount + ?2, exchangedCount = exchangedCount - ?2 WHERE oid = ?1 AND exchangedCount >= ?2 ", nativeQuery = true)
	int addStockGoods(String oid, BigDecimal quantity);
	
	/**
	 * 减库存
	 * @param oid
	 * @param quantity
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE t_tulip_goods SET remainCount = remainCount - ?2, exchangedCount = exchangedCount + ?2 WHERE oid = ?1 AND remainCount >= ?2 ", nativeQuery = true)
	int subtractStockGoods(String oid, BigDecimal quantity);
	
}
