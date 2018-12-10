package com.demo.proxy;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * @author Michael
 * @Date: 2018年12月10日_下午2:21:28
 * @Version: v0.1
 */
@Component
public class BossTaskHandler implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		delegateTask.setAssignee("Boss");
	}

}
