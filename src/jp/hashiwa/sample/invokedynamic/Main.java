package jp.hashiwa.sample.invokedynamic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * https://docs.oracle.com/javase/jp/8/docs/technotes/guides/vm/multiple-language-support.html#invokedynamic
 * https://docs.oracle.com/javase/jp/8/docs/technotes/guides/vm/multiple-language-support.html#challenges
 * Created by hashiwa on 16/09/25.
 */
public class Main {

  public static void main(String[] args) {
    try {
      ClassLoader loader = new MyClassLoader();
      Class clazz;
      Method method;
      Object ret;

      clazz = loader.loadClass("TestInvokeDynamic");
      method = clazz.getMethod("callInvokeDynamic", new Class[]{Object.class, Object.class});
      ret = method.invoke(null, new Object[]{1, 2});
      System.out.println(ret);

      clazz = loader.loadClass("TestInvokeDynamic");
      method = clazz.getMethod("callInvokeDynamic", new Class[]{Object.class, Object.class});
      ret = method.invoke(null, new Object[]{2, 3});
      System.out.println(ret);

      clazz = loader.loadClass("TestInvokeDynamic2");
      method = clazz.getMethod("callInvokeDynamic", new Class[]{Object.class, Object.class, Object.class});
      ret = method.invoke(null, new Object[]{1, 2, 3});
      System.out.println(ret);

      clazz = loader.loadClass("TestInvokeDynamic3");
      method = clazz.getMethod("callInvokeDynamic", new Class[]{Object.class, Object.class, Object.class});
      ret = method.invoke(null, new Object[]{1, 2, 3});
      System.out.println(ret);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }


}
