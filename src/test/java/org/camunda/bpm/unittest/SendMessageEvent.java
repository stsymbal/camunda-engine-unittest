/**
 * 
 */
package org.camunda.bpm.unittest;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.bpmn.instance.ThrowEvent;

/**
 * @author Sergii Tsymbal (stsymbal@bp-3.com)
 *
 */
public class SendMessageEvent implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	/* (non-Javadoc)
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
    	//Assuming execution from ThrowEvent (Intermediate or End), so getting Message Name from the model.
    	ThrowEvent throwEvent = (ThrowEvent) execution.getBpmnModelElementInstance();
    	MessageEventDefinition eventDefinition = (MessageEventDefinition) throwEvent.getEventDefinitions().iterator().next();
    	
    	String eventName = eventDefinition.getMessage().getName();
    	//businessKey is input variable with with value #{execution.processBusinessKey}
    	String businessKey = (String) execution.getVariableLocal("businessKey");
    	
    	LOGGER.severe("Sending event: "+eventName+" for businessKey: " + businessKey);
    	
    	execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation(eventName).processInstanceBusinessKey(businessKey).correlate();
	}

}
