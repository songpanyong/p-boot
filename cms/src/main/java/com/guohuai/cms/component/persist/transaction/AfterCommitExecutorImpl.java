package com.guohuai.cms.component.persist.transaction;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
 
@Component
public class AfterCommitExecutorImpl extends TransactionSynchronizationAdapter implements AfterCommitExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AfterCommitExecutorImpl.class);
    private static final ThreadLocal<List<Runnable>> RUNNABLES = new ThreadLocal<List<Runnable>>();
 
    @Override
    public void execute(Runnable runnable) {
        LOGGER.info("Submitting new runnable {} to run after commit", runnable);
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            LOGGER.debug("Transaction synchronization is NOT ACTIVE. Executing right now runnable {}", runnable);
            runnable.run();
            return;
        }
        List<Runnable> threadRunnables = RUNNABLES.get();
        if (threadRunnables == null) {
            threadRunnables = new ArrayList<Runnable>();
            RUNNABLES.set(threadRunnables);
            TransactionSynchronizationManager.registerSynchronization(this);
        }
        threadRunnables.add(runnable);
    }
 
    @Override
    public void afterCommit() {
        List<Runnable> threadRunnables = RUNNABLES.get();
        LOGGER.debug("Transaction successfully committed, executing {} runnables", threadRunnables.size());
        for (int i = 0; i < threadRunnables.size(); i++) {
            Runnable runnable = threadRunnables.get(i);
            LOGGER.debug("Executing runnable {}", runnable);
            try {
                runnable.run();
            } catch (RuntimeException e) {
                LOGGER.error("Failed to execute runnable " + runnable, e);
            }
        }
    }
 
    @Override
    public void afterCompletion(int status) {
        LOGGER.debug("Transaction completed with status {}", status == STATUS_COMMITTED ? "COMMITTED" : "ROLLED_BACK");
        RUNNABLES.remove();
    }
 
}