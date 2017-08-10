package com.camunda.demo.springboot.readytoreceive;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Component
public class SendResponseAdapter implements JavaDelegate{

  @Override
  public void execute(DelegateExecution cty) throws Exception {
    String uuid = (String)cty.getVariable("correlationId");

    int x = (Integer)cty.getVariable("x");
    int y = (Integer)cty.getVariable("y");

    int z = x+y;
    
    cty.getProcessEngineServices().getRuntimeService().startProcessInstanceByKey(
        "ready-to-receive-proxy",
        Variables //
          .putValue("targetCorrelationId", uuid) //
          .putValue("targetMessage", "Message1") //
          .putValue("z", z));
  }

  
}
