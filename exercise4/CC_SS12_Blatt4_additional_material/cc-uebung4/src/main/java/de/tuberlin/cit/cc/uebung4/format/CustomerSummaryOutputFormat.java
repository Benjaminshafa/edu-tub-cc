package de.tuberlin.cit.cc.uebung4.format;

import de.tuberlin.cit.cc.uebung4.types.CustomerSummary;
import eu.stratosphere.pact.common.io.TextOutputFormat;
import eu.stratosphere.pact.common.type.KeyValuePair;
import eu.stratosphere.pact.common.type.base.PactNull;


public class CustomerSummaryOutputFormat extends TextOutputFormat<PactNull, CustomerSummary> {

	@Override
	public byte[] writeLine(KeyValuePair<PactNull, CustomerSummary> pair) {
		CustomerSummary info = pair.getValue();
		StringBuilder builder = new StringBuilder();
		builder.append(info.customerId);
		builder.append('|');
		builder.append(info.customerName.getValue());
		builder.append('|');
		builder.append(info.noOfOrders);
		builder.append('|');
		builder.append(info.totalMoneySpent);
		builder.append('\n');
		
		return builder.toString().getBytes();
	}
}
