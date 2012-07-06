package de.tuberlin.cit.cc.ex4.stratosphere.format;

import de.tuberlin.cit.cc.ex4.stratosphere.types.Order;
import eu.stratosphere.pact.common.contract.OutputContract.UniqueKey;
import eu.stratosphere.pact.common.io.TextInputFormat;
import eu.stratosphere.pact.common.type.KeyValuePair;
import eu.stratosphere.pact.common.type.base.PactInteger;

@UniqueKey
public class OrderTableInputFormat extends TextInputFormat<PactInteger, Order> {

  @Override
  public boolean readLine(KeyValuePair<PactInteger, Order> pair, byte[] line) {

    String[] columns = (new String(line)).split("\\|");
    Order order = new Order();
    order.orderId = Integer.parseInt(columns[0]);
    order.customerId = Integer.parseInt(columns[1]);

    pair.setKey(new PactInteger(order.orderId));
    pair.setValue(order);
    return true;
  }

}
