package de.tuberlin.cit.cc.uebung4.format;

import de.tuberlin.cit.cc.uebung4.types.Customer;
import eu.stratosphere.pact.common.contract.OutputContract.UniqueKey;
import eu.stratosphere.pact.common.io.TextInputFormat;
import eu.stratosphere.pact.common.type.KeyValuePair;
import eu.stratosphere.pact.common.type.base.PactInteger;
import eu.stratosphere.pact.common.type.base.PactString;

@UniqueKey
public class CustomerTableInputFormat extends
		TextInputFormat<PactInteger, Customer> {

	@Override
	public boolean readLine(KeyValuePair<PactInteger, Customer> pair,
			byte[] line) {

		String[] columns = (new String(line)).split("\\|");

		Customer c = new Customer();
		c.customerId = Integer.parseInt(columns[0]);
		c.name = new PactString(columns[1]);

		pair.setKey(new PactInteger(c.customerId));
		pair.setValue(c);
		return true;
	}
}
