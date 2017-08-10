package com.camunda.demo.springboot.readytoreceive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.stereotype.Component;

@Component
public class CheckAndAckAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    String uuid = (String) ctx.getVariable("correlationId");

    List<Execution> waitingExecutions = ctx.getProcessEngineServices().getRuntimeService().createExecutionQuery() //
        .messageEventSubscriptionName("MessageCollected") //
        .processVariableValueEquals("targetCorrelationId", uuid) //
        .list();

    if (waitingExecutions.size() == 1) {
      Map<String, Object> variables = new HashMap<String, Object>( //
          ctx.getProcessEngineServices().getRuntimeService() //
              .getVariables(waitingExecutions.get(0).getId()));
      variables.remove("targetCorrelationId");
      variables.remove("targetMessage");

      ctx.setVariables(variables);

      // TODO: Get variables from response and hand over
      ctx.getProcessEngineServices().getRuntimeService().createMessageCorrelation("MessageCollected") //
          .processInstanceVariableEquals("targetCorrelationId", uuid) //
          .correlateWithResult();

      ctx.setVariable("messageCollected", true);
    } else if (waitingExecutions.size() > 1) {
      throw new RuntimeException("boom");
    } else {
      ctx.setVariable("messageCollected", false);
    }
  }

}
