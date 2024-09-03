package com.yomahub.liteflow.test.parsecustom;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.test.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.noear.solon.annotation.Import;
import org.noear.solon.annotation.Inject;
import org.noear.solon.test.SolonTest;

/**
 * springboot环境的自定义json parser单元测试
 *
 * @author dongguo.tao
 * @since 2.5.0
 */
@Import(profiles ="classpath:/parsecustom/application-custom-json.properties")
@SolonTest(classes = CustomParserJsonELDeclMultiSpringbootTest.class)
public class CustomParserJsonELDeclMultiSpringbootTest extends BaseTest {

	@Inject
	private FlowExecutor flowExecutor;

	// 测试springboot场景的自定义json parser
	@Test
	public void testJsonCustomParser() {
		LiteflowResponse response = flowExecutor.execute2Resp("chain1", "args");
		Assertions.assertTrue(response.isSuccess());
	}

}
