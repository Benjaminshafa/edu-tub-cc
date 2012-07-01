package de.tuberlin.cit.cc.uebung4.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.stratosphere.pact.common.type.Value;
import eu.stratosphere.pact.common.type.base.PactString;

public class CustomerSummary implements Value {

	public int customerId;

	public PactString customerName;

	public int noOfOrders;
	
	public double totalMoneySpent;

	public void write(DataOutput out) throws IOException {
		out.writeInt(customerId);
		customerName.write(out);
		out.writeInt(noOfOrders);
		out.writeDouble(totalMoneySpent);
	}

	public void read(DataInput in) throws IOException {
		customerId = in.readInt();
		customerName = new PactString();
		customerName.read(in);
		noOfOrders = in.readInt();
		totalMoneySpent = in.readDouble();
	}
}
