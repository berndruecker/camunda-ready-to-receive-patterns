package com.camunda.demo.springboot.parallel;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class SendResponseToMyselfAdapter implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("Message_Response") //
        .correlateWithResult();
  }

}
