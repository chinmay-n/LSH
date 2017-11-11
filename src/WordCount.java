
import java.io.IOException;
import java.util.ArrayList;
import com.idealista.tlsh.TLSH;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WordCount {

	
  public static class TokenizerMapper
       extends Mapper<Object, Text, Text,Text>{

    //private final static IntWritable one = new IntWritable(1);
    private Text hash = new Text();
    private Text doc = new Text();

    public boolean isSplitable(Context context,Path args[])
	{
	   return false;
	}

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
    String name = ((FileSplit)context.getInputSplit()).getPath().toString();
    System.out.println(value.toString());
    TLSH tlsh = new TLSH(value.toString());
    
//      StringTokenizer itr = new StringTokenizer(value.toString());
//      
//      while (itr.hasMoreTokens()) {
//    	  itr.nextToken();
//        word.set(name);
//        
//      }
    doc.set(name);
    String hash1 = tlsh.hash();
    hash.set(hash1);
    context.write(doc,hash);
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,Text,Text,ArrayList<Text>> {
    private ArrayList<Text> result;

    public void reduce(Text key, Text values,
                       Context context
                       ) throws IOException, InterruptedException {
//      int sum = 0;
//      for (IntWritable val : values) {
//        sum += val.get();
//      }
      result.add(values);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

