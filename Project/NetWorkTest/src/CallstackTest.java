import java.util.ArrayList;


public class CallstackTest {

	public ArrayList<String> call;
	public CallstackTest()
	{
		call = new ArrayList<String>();
	}
	public CallstackTest(int a)
	{
		call = new ArrayList<String>();
		for(int i =0;i<4;i++)
			call.add("Exception !!!!!"+i+" asdasd");
	}
}