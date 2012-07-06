package de.tuberlin.cit.cc.ex4.stratosphere.stubs;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.tuberlin.cit.cc.ex4.stratosphere.types.LineItem;
import de.tuberlin.cit.cc.ex4.stratosphere.types.LineItemSummary;
import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.common.contract.OutputContract.SameKey;
import eu.stratosphere.pact.common.stub.Collector;
import eu.stratosphere.pact.common.stub.ReduceStub;
import eu.stratosphere.pact.common.type.base.PactInteger;

@SameKey
public class LineItemFilter extends
    ReduceStub<PactInteger, LineItem, PactInteger, LineItemSummary> {

  public static final String CONFIG_MIN_ORDER_VOLUME = "minOrderVolume";

  private static final Log LOG = LogFactory.getLog(LineItemFilter.class);

  private int minOrderVolume;

  @Override
  public void configure(Configuration parameters) {
    super.configure(parameters);

    minOrderVolume = parameters.getInteger(CONFIG_MIN_ORDER_VOLUME, 0);
    LOG.info("Will ignore all orders with volume less or equal than "
        + minOrderVolume);
  }

  @Override
  public void reduce(PactInteger orderId, Iterator<LineItem> lineItems,
      Collector<PactInteger, LineItemSummary> out) {

    LineItemSummary summary = new LineItemSummary();
    summary.orderId = orderId.getValue();
    summary.totalItemQuantity = 0;

    int numberOfItems = 0;

    while (lineItems.hasNext()) {
      LineItem lineItem = lineItems.next();
      summary.totalItemQuantity += lineItem.quantity;
      numberOfItems++;
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("OrderID " + summary.orderId + ": (Key: " + orderId.getValue()
          + ") Received " + numberOfItems + " line items");
      LOG.debug("OrderID " + summary.orderId + ": Calculated ItemQuantity "
          + summary.totalItemQuantity);
    }

    if (summary.totalItemQuantity > minOrderVolume) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Emitting order with total item quantity of '"
            + summary.totalItemQuantity + "'");
      }
      out.collect(orderId, summary);

    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Ignoring order with total item quantity of '"
            + summary.totalItemQuantity + "'");
      }
    }
  }
}
