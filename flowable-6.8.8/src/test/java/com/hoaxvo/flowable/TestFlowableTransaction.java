package com.hoaxvo.flowable;

import com.hoaxvo.flowable.sevice.ProcessService;
import liquibase.pro.packaged.A;
import org.flowable.common.engine.impl.db.DbSqlSession;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;


@SpringBootTest(classes = FlowableOldApplication.class)
public class TestFlowableTransaction {
    private final Logger logger = LoggerFactory.getLogger(TestFlowableTransaction.class);

    @Autowired
    private ProcessService processService;

    @Autowired
    private RuntimeService runtimeService;

    private final String proc_inst_id = "91570891-11fe-11ef-906e-e02e0bd7358f";

    @Test
    public void startProcess() {
        ProcessInstance processInstance = processService.startProcess();
        logger.info("Process inst {}", processInstance.getId());
    }


    @Test
    public void transactionTest() {
        Assertions.assertDoesNotThrow(() -> processService.concurrentUpdate(proc_inst_id));
    }
}
