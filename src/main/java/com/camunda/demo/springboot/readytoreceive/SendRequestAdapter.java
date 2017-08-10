package com.camunda.demo.springboot.readytoreceive;

import java.util.UUID;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

@Component
public class SendRequestAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution arg0) throws Exception {
    String uuid = UUID.randomUUID().toString();
    arg0.setVariable("correlationId", uuid);

    arg0.getProcessEngineServices().getRuntimeService().startProcessInstanceByKey("ready-to-receive-b", //
        Variables //
            .putValue("correlationId", uuid) //
            .putValue("x", 15) //
            .putValue("y", 27));
  }

}
