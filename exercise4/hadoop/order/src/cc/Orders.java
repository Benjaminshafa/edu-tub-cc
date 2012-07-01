package cc;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;




public class Orders extends Configured implements Tool {
	
	public static class OrderIDMapper extends MapReduceBase implements 
	Mapper<LongWritable,Text,Text,Text>{

		@Override
		public void map(LongWritable arg0, Text arg1,
				OutputCollector<Text, Text> arg2, Reporter arg3)
				throws IOException {
			String input = arg1.toString();
			
			System.out.println("OrderIDMapper: Input:"+input);
			
			String[] fields = input.split("\\|");
					
			arg2.collect(new Text(fields[0]),new Text(input.substring(fields[0].length()+1)));
		}
		
	}
	
	public static class JoinOrder extends MapReduceBase implements
	Reducer<Text,Text,Text,Text>{

		@Override
		public void reduce(Text arg0, Iterator<Text> arg1,
				OutputCollector<Text, Text> arg2, Reporter arg3)
				throws IOException {			
			String result="";
			String temp;
			int counter = 0;
			
			while(arg1.hasNext()){
				temp = arg1.next().toString();
				String[] parts = temp.split("\\|");
				if(parts.length > 1){
					result = parts[0]+"|"+arg0+"|"+result;
				}else{
					result = result + parts[0];
				}
				counter++;
			}
			
			System.out.println("Join: Result:"+result );
			
			if(counter == 2)
				arg2.collect(null,new Text(result));
		}
		
	}
	
	public static class ClientIDMapper extends MapReduceBase implements 
	Mapper<LongWritable,Text,Text,Text>{

		@Override
		public void map(LongWritable arg0, Text arg1,
				OutputCollector<Text, Text> arg2, Reporter arg3)
				throws IOException {
			String input = arg1.toString();
			
			System.out.println("ClientIDMapper: Input:"+input);
			
			String[] fields = input.split("\\|");
					
			arg2.collect(new Text(fields[0]),new Text(input.substring(fields[0].length()+1)));
		}
		
	}
	
	public static class JoinClient extends MapReduceBase implements
	Reducer<Text,Text,Text,Text>{

		@Override
		public void reduce(Text arg0, Iterator<Text> arg1,
				OutputCollector<Text, Text> arg2, Reporter arg3)
				throws IOException {			
			String result="";
			String temp;
			int counter = 0;
			
			while(arg1.hasNext()){
				temp = arg1.next().toString();
				String[] parts = temp.split("\\|");
				if(parts.length > 2){
					result = arg0+"|"+ parts[0] + "|" + result;
				}else{
					result = result + parts[0] + "|" + parts[1]+"|";
				}
				counter++;
			}
			
			System.out.println("JoinClient: Result:"+result );
			
			if(counter == 2)
				arg2.collect(null,new Text(result));
		}
		
	}
	
	
	public static class GroupLineItemsByOrderID extends MapReduceBase implements 
	Mapper<LongWritable,Text,Text,Text>{

		@Override
		public void map(LongWritable arg0, Text arg1,
				OutputCollector<Text, Text> arg2, Reporter arg3)
				throws IOException {
			String input = arg1.toString();
			
			String[] fields = input.split("\\|");
					
			arg2.collect(new Text(fields[0]),new Text(fields[4]));
		}
		
	}
	
	public static class SumLineItems extends MapReduceBase implements
	Reducer<Text,Text,Text,Text>{
		
		private int threshold=0;
		
		@Override
		public void configure(JobConf job){
			threshold = job.getInt("orders.threshold", 0);
		}

		@Override
		public void reduce(Text arg0, Iterator<Text> arg1,
				OutputCollector<Text, Text> arg2, Reporter arg3)
				throws IOException {
			int sum=0;
			
			
			while(arg1.hasNext()){
				try{
					sum += Integer.parseInt(arg1.next().toString());
				}catch(NumberFormatException ex){
					ex.printStackTrace();
				}
			}
						
			if(sum >= threshold){
				arg2.collect(null, new Text(arg0 + "|"+Double.toString(sum)));
			}			
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		int res = ToolRunner.run(new Configuration(),new Orders(),args);
		System.exit(res);
	}

	@Override
	public int run(String[] arg0) throws Exception {
	   JobConf conf = new JobConf(getConf(),Orders.class);
	   conf.setJobName("filterorders");
	   
	   conf.setOutputKeyClass(Text.class);
	   conf.setOutputValueClass(Text.class);
	   
	   conf.setMapperClass(GroupLineItemsByOrderID.class);
	   conf.setReducerClass(SumLineItems.class);
	   
	   conf.setInputFormat(TextInputFormat.class);
	   conf.setOutputFormat(TextOutputFormat.class);
	   
	   conf.setInt("orders.threshold", Integer.parseInt(arg0[0]));
	   
	   FileInputFormat.setInputPaths(conf, new Path(arg0[1]));
	   FileOutputFormat.setOutputPath(conf, new Path(arg0[4]+"/filter"));
	   
	   JobClient.runJob(conf);
	   
	   JobConf conf2 = new JobConf(getConf(),Orders.class);
	   conf2.setJobName("joinorders");
	   
	   conf2.setOutputKeyClass(Text.class);
	   conf2.setOutputValueClass(Text.class);
	   
	   conf2.setMapperClass(OrderIDMapper.class);
	   conf2.setReducerClass(JoinOrder.class);
	   
	   conf2.setInputFormat(TextInputFormat.class);
	   conf2.setOutputFormat(TextOutputFormat.class);
	   
	   FileInputFormat.setInputPaths(conf2,new Path(arg0[4]+"/filter/part-00000"),new Path(arg0[2]));
	   FileOutputFormat.setOutputPath(conf2,new Path(arg0[4]+"/joinOrder"));
	   
	   JobClient.runJob(conf2);
	   
	   JobConf conf3 = new JobConf(getConf(),Orders.class);
	   conf3.setJobName("joinclients");
	   
	   conf3.setOutputKeyClass(Text.class);
	   conf3.setOutputValueClass(Text.class);
	   
	   conf3.setMapperClass(ClientIDMapper.class);
	   conf3.setReducerClass(JoinClient.class);
	   
	   FileInputFormat.setInputPaths(conf3, new Path(arg0[3]),new Path(arg0[4]+"/joinOrder/part-00000"));
	   FileOutputFormat.setOutputPath(conf3, new Path(arg0[4]+"/result"));
	   
	   JobClient.runJob(conf3);
	   
		return 0;
	}

}
