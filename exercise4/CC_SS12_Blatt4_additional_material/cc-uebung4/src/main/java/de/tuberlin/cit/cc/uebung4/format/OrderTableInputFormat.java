package de.tuberlin.cit.cc.uebung4.format;

import de.tuberlin.cit.cc.uebung4.types.Order;
import eu.stratosphere.pact.common.io.TextInputFormat;
import eu.stratosphere.pact.common.type.KeyValuePair;
import eu.stratosphere.pact.common.type.base.PactInteger;

public class OrderTableInputFormat extends TextInputFormat<PactInteger, Order> {

	@Override
	public boolean readLine(KeyValuePair<PactInteger, Order> pair, byte[] line) {
		
		String[] columns = (new String(line)).split("\\|");
		Order order = new Order();		
		order.orderId =  Integer.parseInt(columns[0]);
		order.customerId = Integer.parseInt(columns[1]);
		order.price = Double.parseDouble(columns[3]);
		
		pair.setKey(new PactInteger(order.customerId));
		pair.setValue(order);
		return true;
	}

}
