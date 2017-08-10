package com.camunda.demo.springboot.readytoreceive;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ForwardResponseAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    String uuid = (String) ctx.getVariable("targetCorrelationId");
    String messageName = (String) ctx.getVariable("targetMessage");

    Map<String, Object> variables = new HashMap<String, Object>(ctx.getVariables());
    variables.remove("targetCorrelationId");
    variables.remove("targetMessage");

    ctx.getProcessEngineServices().getRuntimeService().createMessageCorrelation(messageName) //
        .processInstanceVariableEquals("correlationId", uuid) //
        .setVariables(variables) //
        .correlateWithResult();
  }

}
