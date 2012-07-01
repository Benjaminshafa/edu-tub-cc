package de.tuberlin.cit.cc.uebung4.stubs;

import java.util.Iterator;

import de.tuberlin.cit.cc.uebung4.types.Order;
import de.tuberlin.cit.cc.uebung4.types.OrderSummary;
import eu.stratosphere.pact.common.contract.OutputContract.UniqueKey;
import eu.stratosphere.pact.common.stub.Collector;
import eu.stratosphere.pact.common.stub.ReduceStub;
import eu.stratosphere.pact.common.type.base.PactInteger;

@UniqueKey
public class OrderSummarizer extends ReduceStub<PactInteger, Order, PactInteger, OrderSummary> {
	
	@Override
	public void reduce(PactInteger customerId, Iterator<Order> orders,
			Collector<PactInteger, OrderSummary> out) {
		
		OrderSummary summary = new OrderSummary();
		summary.customerId = customerId.getValue();
		summary.noOfOrders = 0;
		summary.orderPriceSum = 0;
		
		while(orders.hasNext()) {
			Order order = orders.next();
			summary.noOfOrders++;
			summary.orderPriceSum += order.price;
		}
		
		out.collect(customerId, summary);
	}
}
