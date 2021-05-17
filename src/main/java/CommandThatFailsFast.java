import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandThatFailsFast extends HystrixCommand<String> {

    private final boolean throwException;

    public CommandThatFailsFast(boolean throwException) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.throwException = throwException;
    }

    @Override
    protected String run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String test = null;
        test.charAt(1);
        return test;
    }

    public static void main(String args[]){
        for(int i=0;i<100;i++) {
            CommandThatFailsFast test = new CommandThatFailsFast(false);
            try {
                test.execute();
            }catch(Exception  e){

            }
            System.out.println(i + "  " + test.isCircuitBreakerOpen());
        }
    }
}