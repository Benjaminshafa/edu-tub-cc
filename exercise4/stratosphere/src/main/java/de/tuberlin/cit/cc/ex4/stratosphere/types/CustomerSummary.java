package de.tuberlin.cit.cc.ex4.stratosphere.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.stratosphere.pact.common.type.Value;
import eu.stratosphere.pact.common.type.base.PactString;

public class CustomerSummary implements Value {

  public int customerId;

  public PactString customerName;

  public int orderId;

  public int totalItemQuantity;

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(customerId);
    customerName.write(out);
    out.writeInt(orderId);
    out.writeInt(totalItemQuantity);
  }

  @Override
  public void read(DataInput in) throws IOException {
    customerId = in.readInt();
    customerName = new PactString();
    customerName.read(in);
    orderId = in.readInt();
    totalItemQuantity = in.readInt();
  }
}
