package com.yomahub.liteflow.test.cmpStep;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.Inject;
import org.noear.solon.test.SolonTest;
import java.util.*;

/**
 * springboot环境最普通的例子测试
 *
 * @author Bryan.Zhang
 * @since 2.6.4
 */
@Import(profiles ="classpath:/cmpStep/application.properties")
@SolonTest
public class CmpStepELDeclSpringbootTest extends BaseTest {

	@Inject
	private FlowExecutor flowExecutor;

	@Test
	public void testStep() throws Exception {
		LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
		Assertions.assertFalse(response.isSuccess());
		Assertions.assertTrue(response.getExecuteSteps().get("a").get(0).isSuccess());
		Assertions.assertTrue(response.getExecuteSteps().get("b").get(0).isSuccess());
		Assertions.assertFalse(response.getExecuteSteps().get("c").get(0).isSuccess());
		Assertions.assertFalse(response.getExecuteSteps().get("d").get(0).isSuccess());
		Assertions.assertTrue(response.getExecuteSteps().get("c").get(0).getTimeSpent() >= 2000);
		Assertions.assertEquals(RuntimeException.class, response.getExecuteSteps().get("c").get(0).getException().getClass());
		Assertions.assertEquals(RuntimeException.class, response.getExecuteSteps().get("d").get(0).getException().getClass());
	}

	@Test
	public void testStep2() throws Exception {
		LiteflowResponse response = flowExecutor.execute2Resp("chain2", "arg");
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertEquals("a==>b", response.getExecuteStepStrWithoutTime());
	}

	@Test
	public void testStep3() throws Exception {
		LiteflowResponse response = flowExecutor.execute2Resp("chain3", "arg");
		Assertions.assertTrue(response.isSuccess());
		Map<String, List<CmpStep>> stepMap = response.getExecuteSteps();
		Assertions.assertEquals(2, stepMap.size());
		Queue<CmpStep> queue = response.getExecuteStepQueue();
		Assertions.assertEquals(5, queue.size());

		Set<String> tagSet = new HashSet<>();
		response.getExecuteStepQueue()
			.stream()
			.filter(cmpStep -> cmpStep.getNodeId().equals("a"))
			.forEach(cmpStep -> tagSet.add(cmpStep.getTag()));

		Assertions.assertEquals(3, tagSet.size());

	}

}
