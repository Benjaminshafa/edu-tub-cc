package de.tuberlin.cit.cc.ex4.stratosphere.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.stratosphere.pact.common.type.Value;

public class Order implements Value {

  public int orderId;

  public int customerId;

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(orderId);
    out.writeInt(customerId);
  }

  @Override
  public void read(DataInput in) throws IOException {
    orderId = in.readInt();
    customerId = in.readInt();
  }
}
