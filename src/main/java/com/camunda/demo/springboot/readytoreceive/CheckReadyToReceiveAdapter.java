package com.camunda.demo.springboot.readytoreceive;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CheckReadyToReceiveAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    String uuid = (String) ctx.getVariable("targetCorrelationId");

    long count = ctx.getProcessEngineServices().getRuntimeService().createExecutionQuery() //
        .messageEventSubscription() //
        .processVariableValueEquals("correlationId", uuid) //
        .count();
    ctx.setVariable("readyToReceive", (count > 0));
  }

}
