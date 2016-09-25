package jp.hashiwa.sample.invokedynamic;

import java.lang.invoke.*;

/**
 * Created by hashiwa on 16/09/25.
 */
public class MyMethodFactory {
  public static CallSite bootstrap(MethodHandles.Lookup callerClass,
                               String dynMethodName,
                               MethodType dynMethodType) throws Throwable
  {
    System.out.println("In bootstrap method: " + callerClass + ", " + dynMethodName + ", " + dynMethodType);
    MethodHandle mh =
            callerClass.findStatic(
                    IntegerOps.class,
                    "adder",
                    MethodType.methodType(Integer.class, Integer.class, Integer.class));

    if (!dynMethodType.equals(mh.type())) {
      mh = mh.asType(dynMethodType);
    }

    return new ConstantCallSite(mh);
  }
}