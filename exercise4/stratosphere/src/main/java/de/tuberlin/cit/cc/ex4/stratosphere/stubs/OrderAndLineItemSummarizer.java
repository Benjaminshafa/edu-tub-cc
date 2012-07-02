package de.tuberlin.cit.cc.ex4.stratosphere.stubs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.tuberlin.cit.cc.ex4.stratosphere.types.LineItemSummary;
import de.tuberlin.cit.cc.ex4.stratosphere.types.Order;
import de.tuberlin.cit.cc.ex4.stratosphere.types.OrderSummary;
import eu.stratosphere.pact.common.stub.Collector;
import eu.stratosphere.pact.common.stub.MatchStub;
import eu.stratosphere.pact.common.type.base.PactInteger;

public class OrderAndLineItemSummarizer extends
    MatchStub<PactInteger, Order, LineItemSummary, PactInteger, OrderSummary> {

  private static final Log LOG = LogFactory
      .getLog(OrderAndLineItemSummarizer.class);

  @Override
  public void match(PactInteger key, Order order,
      LineItemSummary lineItemSummary, Collector<PactInteger, OrderSummary> out) {

    OrderSummary orderSummary = new OrderSummary();
    orderSummary.orderId = key.getValue();
    orderSummary.customerId = order.customerId;
    orderSummary.totalItemQuantity = lineItemSummary.totalItemQuantity;

    if (LOG.isDebugEnabled()) {
      LOG.debug("OrderID " + orderSummary.orderId
          + ": Matched with ItemQuantity " + orderSummary.totalItemQuantity);
    }

    out.collect(new PactInteger(orderSummary.customerId), orderSummary);
  }
}
