import java.io.IOException;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.fs.Path;

public class ipclass extends FileInputFormat<NullWritable, BytesWritable> {
    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

	@Override
	public RecordReader<NullWritable, BytesWritable> createRecordReader(InputSplit arg0, TaskAttemptContext arg1)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}
}