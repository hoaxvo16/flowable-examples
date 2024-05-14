package com.hoaxvo.flowable.sevice;

import org.flowable.common.engine.api.FlowableOptimisticLockingException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class ProcessService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ManagementService managementService;
    private final String key = "approve-process";

    public ProcessInstance startProcess() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "HOAVO");
        return runtimeService.startProcessInstanceByKey(key, variables);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(value = FlowableOptimisticLockingException.class, maxAttempts = 10)
    public void concurrentUpdate(String processInstanceId) {
        List<String> variableNames = List.of("var1", "var2", "var3", "var4", "var5", "var6", "var7", "var8", "var9", "var10");
        IntStream.range(0, 20).parallel().forEach(x -> {
            Random random = new Random();
            int idx = random.nextInt(variableNames.size());
            runtimeService.setVariable(processInstanceId, variableNames.get(idx), UUID.randomUUID().toString());
        });
    }

    public void updateUseManagementService(List<String> variableNames, String processInstanceId) {
        Random random = new Random();
        int idx = random.nextInt(variableNames.size());
        // Update logic using ManagementService
        managementService.executeCommand(new Command<Void>() {
            @Override
            public Void execute(CommandContext commandContext) {

                // Get ExecutionEntity for the process instance
                ExecutionEntity execution =
                        CommandContextUtil.getExecutionEntityManager(commandContext)
                                .findByRootProcessInstanceId(processInstanceId);
                if (execution != null) {
                    execution.setVariable(variableNames.get(idx), UUID.randomUUID().toString());
                }
                return null;
            }
        });
    }
}

