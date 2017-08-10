package com.camunda.demo.springboot.readytoreceive;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class LogResultAdapter implements JavaDelegate {

  @Override
  public void execute(DelegateExecution ctx) throws Exception {
    int z = (Integer) ctx.getVariable("z");

    System.out.println("##### Here it comes: " + z);
  }

}
