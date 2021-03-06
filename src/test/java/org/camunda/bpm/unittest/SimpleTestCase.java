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

import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Meyer
 * @author Martin Schimak
 */
public class SimpleTestCase {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"topProcess.bpmn", "testProcess.bpmn"})
  public void shouldExecuteProcess() {
    // Given we create a new process instance
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("topProcess","myBusinessKey");
    // Then it should be active
    assertThat(processInstance).isActive();
    // And it should be 3 instances (1 for top process and 2 for called process)
    assertThat(processInstanceQuery().count()).isEqualTo(3);
    
    // When we complete that task of testProcess the flow throws Cancel Parent Instance event.
    complete(task(processInstanceQuery().processDefinitionKey("testProcess").list().get(0)));
    // Then the process instance should be ended
    assertThat(processInstance).isEnded();
  }

}
