package jp.hashiwa.sample.invokedynamic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hashiwa on 16/09/25.
 */
class MyClassLoader extends ClassLoader {
  private final MyClassFactory factory = new MyClassFactory();

  @Override
  public Class findClass(String name) throws ClassNotFoundException {
    byte[] b = factory.createClass(name);
    if (b == null) {
      return super.findClass(name);
    } else {
      return defineClass(name, b, 0, b.length);
    }
  }
}
