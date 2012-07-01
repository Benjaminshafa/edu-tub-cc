package de.tuberlin.cit.cc.uebung4.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.stratosphere.pact.common.type.Value;

public class OrderSummary implements Value {

	public int customerId;

	public int noOfOrders;

	public double orderPriceSum;

	public void write(DataOutput out) throws IOException {
		out.writeInt(customerId);
		out.writeInt(noOfOrders);
		out.writeDouble(orderPriceSum);
	}

	public void read(DataInput in) throws IOException {
		customerId = in.readInt();
		noOfOrders = in.readInt();
		orderPriceSum = in.readDouble();
	}
}
