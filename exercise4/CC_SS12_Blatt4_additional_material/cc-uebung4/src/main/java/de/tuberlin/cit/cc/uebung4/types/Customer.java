package de.tuberlin.cit.cc.uebung4.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import eu.stratosphere.pact.common.type.Value;
import eu.stratosphere.pact.common.type.base.PactString;

public class Customer implements Value {

	public PactString name;
	
	public int customerId;
	
	public void write(DataOutput out) throws IOException {
		name.write(out);
		out.writeInt(customerId);
	}

	public void read(DataInput in) throws IOException {
		name = new PactString();
		name.read(in);
		customerId = in.readInt();
	}
}
