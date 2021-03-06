package com.neotys.neoload.model.readers.loadrunner.method;

import static com.neotys.neoload.model.readers.loadrunner.LoadRunnerReaderTestUtil.LOAD_RUNNER_VISITOR;
import static com.neotys.neoload.model.readers.loadrunner.LoadRunnerReaderTestUtil.METHOD_CALL_CONTEXT;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.neotys.neoload.model.readers.loadrunner.ImmutableMethodCall;
import com.neotys.neoload.model.readers.loadrunner.MethodCall;
import com.neotys.neoload.model.repository.Header;
import com.neotys.neoload.model.repository.ImmutableHeader;
@SuppressWarnings("squid:S2699")
public class WebAddHeaderMethodTest {

	public static final MethodCall WEB_ADD_HEADER = ImmutableMethodCall.builder().name("\"web_add_header\"").addParameters(
			"\"headerName\"").addParameters("\"headerValue\"").build();

	@Test
	public void testGetElement() {
		LOAD_RUNNER_VISITOR.getCurrentHeaders().clear();
		LOAD_RUNNER_VISITOR.getGlobalHeaders().clear();
		(new WebaddheaderMethod()).getElement(LOAD_RUNNER_VISITOR, WEB_ADD_HEADER, METHOD_CALL_CONTEXT);
		assertEquals(1, LOAD_RUNNER_VISITOR.getCurrentHeaders().size());
		assertEquals(0, LOAD_RUNNER_VISITOR.getGlobalHeaders().size());
		final Header actualHeader = LOAD_RUNNER_VISITOR.getCurrentHeaders().get(0);
		final Header expectedHeader = ImmutableHeader.builder()
				.headerName("headerName")
				.headerValue("headerValue")
				.build();
		assertEquals(expectedHeader, actualHeader);
	}
}
