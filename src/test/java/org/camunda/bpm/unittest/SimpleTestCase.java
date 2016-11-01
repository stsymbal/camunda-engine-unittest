/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.unittest;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

import java.util.List;

import javax.script.ScriptEngine;

import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.scripting.engine.ScriptingEngines;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Meyer
 * @author Martin Schimak
 */
public class SimpleTestCase {

  protected static final String SCRIPT = "println 'hello world'";

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"testProcess.bpmn"})
  public void evalGroovyWithInstanceData() {
    // Given we create a new process instance
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("testProcess","myBusinessKey");
    // Then it should be active
    assertThat(processInstance).isActive();
    //get executions to pull the instance variables from
    //List<ExecutionEntity> executions = (List<ExecutionEntity>)(List<?>)runtimeService().createExecutionQuery().processInstanceId(processInstance.getId()).list();
    //getting groovy scripting engine outside of ProcessInstance

    ScriptingEngines scriptingEngines = rule.getProcessEngineConfiguration().getScriptingEngines();
    ScriptEngine groovyScriptEngine = scriptingEngines.getScriptEngineForLanguage("groovy");
    //TODO: execute groovy script with instance execution's variables available
  }

}
