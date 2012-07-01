package de.tuberlin.cit.cc.uebung4;

import de.tuberlin.cit.cc.uebung4.format.CustomerSummaryOutputFormat;
import de.tuberlin.cit.cc.uebung4.format.CustomerTableInputFormat;
import de.tuberlin.cit.cc.uebung4.format.OrderTableInputFormat;
import de.tuberlin.cit.cc.uebung4.stubs.CustomerSummarizer;
import de.tuberlin.cit.cc.uebung4.stubs.OrderSummarizer;
import de.tuberlin.cit.cc.uebung4.types.Customer;
import de.tuberlin.cit.cc.uebung4.types.CustomerSummary;
import de.tuberlin.cit.cc.uebung4.types.Order;
import de.tuberlin.cit.cc.uebung4.types.OrderSummary;
import eu.stratosphere.pact.common.contract.FileDataSinkContract;
import eu.stratosphere.pact.common.contract.FileDataSourceContract;
import eu.stratosphere.pact.common.contract.MatchContract;
import eu.stratosphere.pact.common.contract.OutputContract.UniqueKey;
import eu.stratosphere.pact.common.contract.ReduceContract;
import eu.stratosphere.pact.common.plan.Plan;
import eu.stratosphere.pact.common.plan.PlanAssembler;
import eu.stratosphere.pact.common.plan.PlanAssemblerDescription;
import eu.stratosphere.pact.common.type.base.PactInteger;
import eu.stratosphere.pact.common.type.base.PactNull;

public class CustomerSummaryJob implements PlanAssembler, PlanAssemblerDescription {

	/**
	 * {@inheritDoc}
	 */
	public Plan getPlan(String... args) {

		int noSubTasks = 1;
		String customersFile = "file:///path/to/tpch_2_14_3/dbgen/customer.tbl";
		String ordersFile = "file:///path/to/tpch_2_14_3/dbgen/orders.tbl";
		String outputFile = "file:///path/to/customerinfo.tbl";

		FileDataSourceContract<PactInteger, Customer> customerSource = new FileDataSourceContract<PactInteger, Customer>(
				CustomerTableInputFormat.class, customersFile,
				"Customer Source");
		customerSource.setDegreeOfParallelism(noSubTasks);
		customerSource.setOutputContract(UniqueKey.class);

		FileDataSourceContract<PactInteger, Order> orderSource = new FileDataSourceContract<PactInteger, Order>(
				OrderTableInputFormat.class, ordersFile, "Order Source");
		orderSource.setDegreeOfParallelism(noSubTasks);

		ReduceContract<PactInteger, Order, PactInteger, OrderSummary> reducer = new ReduceContract<PactInteger, Order, PactInteger, OrderSummary>(
				OrderSummarizer.class, "OrderSummarizer");
		reducer.setDegreeOfParallelism(noSubTasks);

		MatchContract<PactInteger, Customer, OrderSummary, PactNull, CustomerSummary> matcher = new MatchContract<PactInteger, Customer, OrderSummary, PactNull, CustomerSummary>(
				CustomerSummarizer.class, "CustomerSummarizer");
		matcher.setDegreeOfParallelism(noSubTasks);

		FileDataSinkContract<PactNull, CustomerSummary> out = new FileDataSinkContract<PactNull, CustomerSummary>(
				CustomerSummaryOutputFormat.class, outputFile, "Output");
		out.setDegreeOfParallelism(noSubTasks);

		out.setInput(matcher);
		matcher.setFirstInput(customerSource);
		matcher.setSecondInput(reducer);
		reducer.setInput(orderSource);

		return new Plan(out, "Customer Summary");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return "Customer Summary Example";
	}
}
