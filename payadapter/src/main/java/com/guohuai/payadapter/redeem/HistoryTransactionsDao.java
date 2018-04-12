package com.guohuai.payadapter.redeem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 对账记录
 * @author hans
 *
 */
public interface HistoryTransactionsDao extends JpaRepository<HistoryTransactions, String>, JpaSpecificationExecutor<HistoryTransactions>{

}
