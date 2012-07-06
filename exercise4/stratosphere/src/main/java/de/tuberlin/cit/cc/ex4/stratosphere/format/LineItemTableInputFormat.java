package de.tuberlin.cit.cc.ex4.stratosphere.format;

import de.tuberlin.cit.cc.ex4.stratosphere.types.LineItem;
import eu.stratosphere.pact.common.io.TextInputFormat;
import eu.stratosphere.pact.common.type.KeyValuePair;
import eu.stratosphere.pact.common.type.base.PactInteger;

public class LineItemTableInputFormat extends
    TextInputFormat<PactInteger, LineItem> {

  @Override
  public boolean readLine(KeyValuePair<PactInteger, LineItem> pair, byte[] line) {

    String[] columns = (new String(line)).split("\\|");

    LineItem li = new LineItem();
    li.orderId = Integer.parseInt(columns[0]);
    li.quantity = Integer.parseInt(columns[4]);

    pair.setKey(new PactInteger(li.orderId));
    pair.setValue(li);
    return true;
  }
}
