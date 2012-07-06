package de.tuberlin.cit.cc.ex4.stratosphere.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.stratosphere.pact.common.type.Value;

public class LineItem implements Value {

  public int orderId;

  public int quantity;

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(orderId);
    out.writeInt(quantity);
  }

  @Override
  public void read(DataInput in) throws IOException {
    orderId = in.readInt();
    quantity = in.readInt();
  }
}
