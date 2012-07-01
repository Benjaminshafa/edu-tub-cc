package de.tuberlin.cit.cc.uebung4.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.stratosphere.pact.common.type.Value;

public class Order implements Value {
	
	public int orderId;
	
	public int customerId;
	
	public double price;

	public void write(DataOutput out) throws IOException {
		out.writeInt(orderId);
		out.writeInt(customerId);
		out.writeDouble(price);
	}

	public void read(DataInput in) throws IOException {
		orderId = in.readInt();
		customerId = in.readInt();
		price = in.readDouble();
	}
}
