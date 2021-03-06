package com.neotys.neoload.model.readers.loadrunner.method;

import static com.neotys.neoload.model.readers.loadrunner.LoadRunnerReaderTestUtil.LOAD_RUNNER_VISITOR;
import static com.neotys.neoload.model.readers.loadrunner.LoadRunnerReaderTestUtil.METHOD_CALL_CONTEXT;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.neotys.neoload.model.readers.loadrunner.ImmutableMethodCall;
import com.neotys.neoload.model.repository.CustomAction;
import com.neotys.neoload.model.repository.CustomActionParameter.Type;
import com.neotys.neoload.model.repository.ImmutableCustomAction;
import com.neotys.neoload.model.repository.ImmutableCustomActionParameter;
@SuppressWarnings("squid:S2699")
public class CustomActionMethodTest {

	private static final String CONNECTION_STRING_LR = "   /SAP_CODEPAGE=1100   /FULLMENU SNC_PARTNERNAME=\"\" SNC_QOP=-1 /H/{SAP_IP_ADDRESS}/S/3217 /UPDOWNLOAD_CP=2";
	private static final String CONNECTION_STRING_NL = "   /SAP_CODEPAGE=1100   /FULLMENU SNC_PARTNERNAME=\"\" SNC_QOP=-1 /H/${SAP_IP_ADDRESS}/S/3217 /UPDOWNLOAD_CP=2";

	@Test
	public void test_sapgui_open_connection_ex() {
		final CustomAction actualCustomAction = (CustomAction) (new CustomActionMethod()).getElement(LOAD_RUNNER_VISITOR,
				ImmutableMethodCall.builder()
					.name("sapgui_open_connection_ex")
					.addParameters("\"" + CONNECTION_STRING_LR + "\"")
					.addParameters("\"\"")
					.addParameters("\"con[0]\"")
					.build()
				, METHOD_CALL_CONTEXT).get(0);
	
		final CustomAction expectedCustomAction = ImmutableCustomAction.builder()
				.name("openConnectionEx")
				.type("SapConnect")
				.isHit(true)
				.parameters(ImmutableList.of(ImmutableCustomActionParameter.builder()
						.name("connectionString")
						.value(CONNECTION_STRING_NL)
						.type(Type.TEXT)
						.build()))
				.build();
		assertEquals(expectedCustomAction, actualCustomAction);
	}
}
