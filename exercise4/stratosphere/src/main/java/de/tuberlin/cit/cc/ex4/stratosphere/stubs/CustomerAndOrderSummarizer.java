package de.tuberlin.cit.cc.ex4.stratosphere.stubs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.tuberlin.cit.cc.ex4.stratosphere.types.Customer;
import de.tuberlin.cit.cc.ex4.stratosphere.types.CustomerSummary;
import de.tuberlin.cit.cc.ex4.stratosphere.types.OrderSummary;
import eu.stratosphere.pact.common.stub.Collector;
import eu.stratosphere.pact.common.stub.MatchStub;
import eu.stratosphere.pact.common.type.base.PactInteger;
import eu.stratosphere.pact.common.type.base.PactNull;

public class CustomerAndOrderSummarizer extends
    MatchStub<PactInteger, Customer, OrderSummary, PactNull, CustomerSummary> {

  private static final Log LOG = LogFactory
      .getLog(CustomerAndOrderSummarizer.class);

  @Override
  public void match(PactInteger key, Customer customer,
      OrderSummary orderSummary, Collector<PactNull, CustomerSummary> out) {

    CustomerSummary customerSummary = new CustomerSummary();
    customerSummary.customerId = key.getValue();
    customerSummary.customerName = customer.name;
    customerSummary.orderId = orderSummary.orderId;
    customerSummary.totalItemQuantity = orderSummary.totalItemQuantity;

    if (LOG.isDebugEnabled()) {
      LOG.debug("OrderID " + orderSummary.orderId
          + ": Matched with ItemQuantity " + customerSummary.totalItemQuantity
          + " and CustomerID " + customerSummary.customerId);
    }

    out.collect(PactNull.getInstance(), customerSummary);
  }
}
