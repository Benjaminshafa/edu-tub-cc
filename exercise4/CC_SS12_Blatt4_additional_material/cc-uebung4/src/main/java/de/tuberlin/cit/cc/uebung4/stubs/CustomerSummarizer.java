package de.tuberlin.cit.cc.uebung4.stubs;

import de.tuberlin.cit.cc.uebung4.types.Customer;
import de.tuberlin.cit.cc.uebung4.types.CustomerSummary;
import de.tuberlin.cit.cc.uebung4.types.OrderSummary;
import eu.stratosphere.pact.common.stub.Collector;
import eu.stratosphere.pact.common.stub.MatchStub;
import eu.stratosphere.pact.common.type.base.PactInteger;
import eu.stratosphere.pact.common.type.base.PactNull;

public class CustomerSummarizer
		extends
		MatchStub<PactInteger, Customer, OrderSummary, PactNull, CustomerSummary> {

	@Override
	public void match(PactInteger key, Customer customer,
			OrderSummary orderSummary, Collector<PactNull, CustomerSummary> out) {

		CustomerSummary customerSummary = new CustomerSummary();
		customerSummary.customerId = customer.customerId;
		customerSummary.customerName = customer.name;
		customerSummary.noOfOrders = orderSummary.noOfOrders;
		customerSummary.totalMoneySpent = orderSummary.orderPriceSum;

		out.collect(new PactNull(), customerSummary);
	}
}
