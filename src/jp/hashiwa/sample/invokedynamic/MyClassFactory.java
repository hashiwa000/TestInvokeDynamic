package jp.hashiwa.sample.invokedynamic;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

import static jdk.internal.org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static jdk.internal.org.objectweb.asm.Opcodes.*;
import static jdk.internal.org.objectweb.asm.Opcodes.ALOAD;
import static jdk.internal.org.objectweb.asm.Opcodes.ARETURN;

/**
 * Created by hashiwa on 16/10/02.
 */
public class MyClassFactory {
  private static final MethodType CREATE_METHOD_TYPE = MethodType.methodType(byte[].class);
  private Map<String, MethodHandle> createMethods;
  {
    createMethods = new HashMap<>();
    registerFactoryMethod("TestInvokeDynamic", "createTestInvokeDynamic");
    registerFactoryMethod("TestInvokeDynamic2", "createTestInvokeDynamic2");
    registerFactoryMethod("TestInvokeDynamic3", "createTestInvokeDynamic3");
  }

  public boolean isSupportClassName(String className) {
    if (createMethods.containsKey(className)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Create class bytecode.
   * Return null if the class is not supported or cannot be created.
   * @param className class name
   * @return byte array or null
   */
  public byte[] createClass(String className) {
    if (isSupportClassName(className)) {
      MethodHandle handle = createMethods.get(className);
      try {
        System.out.println("create: " + className);
        return (byte[]) handle.invoke(this);
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    }
    return null;
  }

  private void registerFactoryMethod(String className, String factoryMethodName) {
    try {
      createMethods.put(
              className,
              MethodHandles.lookup().findVirtual(
                      MyClassFactory.class,
                      factoryMethodName,
                      CREATE_METHOD_TYPE)
      );
    } catch (NoSuchMethodException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private byte[] createTestInvokeDynamic() {
    String className = "TestInvokeDynamic";
    String methodName = "callInvokeDynamic";
    ClassWriter cw = new ClassWriter(COMPUTE_MAXS);
    MethodVisitor mv;
    MethodType bootstrapMethodType = MethodType.methodType(
            CallSite.class,
            MethodHandles.Lookup.class,
            String.class,
            MethodType.class);
    Handle bootstrapMethodHandle = new Handle(
            H_INVOKESTATIC,
            MyMethodFactory.class.getName().replace('.', '/'),
            "bootstrap",
            bootstrapMethodType.toMethodDescriptorString());


    cw.visit(V1_8, ACC_PUBLIC, className, null, "java/lang/Object", null);

    mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, methodName, "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
    mv.visitCode();
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitInvokeDynamicInsn(
            "adder",
            "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            bootstrapMethodHandle
    );
    mv.visitInsn(ARETURN);
    mv.visitMaxs(2,1);
    mv.visitEnd();

    cw.visitEnd();

    return cw.toByteArray();
  }

  private byte[] createTestInvokeDynamic2() {
    String className = "TestInvokeDynamic2";
    String methodName = "callInvokeDynamic";
    ClassWriter cw = new ClassWriter(COMPUTE_MAXS);
    MethodVisitor mv;
    MethodType bootstrapMethodType = MethodType.methodType(
            CallSite.class,
            MethodHandles.Lookup.class,
            String.class,
            MethodType.class);
    Handle bootstrapMethodHandle = new Handle(
            H_INVOKESTATIC,
            MyMethodFactory.class.getName().replace('.', '/'),
            "bootstrap",
            bootstrapMethodType.toMethodDescriptorString());


    cw.visit(V1_8, ACC_PUBLIC, className, null, "java/lang/Object", null);

    mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, methodName, "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
    mv.visitCode();
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitVarInsn(ALOAD, 2);
    mv.visitInvokeDynamicInsn(
            "adder",
            "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            bootstrapMethodHandle
    );
    mv.visitInsn(ARETURN);
    mv.visitMaxs(2,1);
    mv.visitEnd();

    cw.visitEnd();

    return cw.toByteArray();
  }

  private byte[] createTestInvokeDynamic3() {
    String className = "TestInvokeDynamic3";
    String methodName = "callInvokeDynamic";
    ClassWriter cw = new ClassWriter(COMPUTE_MAXS);
    MethodVisitor mv;
    MethodType bootstrapMethodType = MethodType.methodType(
            CallSite.class,
            MethodHandles.Lookup.class,
            String.class,
            MethodType.class);
    Handle bootstrapMethodHandle = new Handle(
            H_INVOKESTATIC,
            MyMethodFactory.class.getName().replace('.', '/'),
            "bootstrap",
            bootstrapMethodType.toMethodDescriptorString());


    cw.visit(V1_8, ACC_PUBLIC, className, null, "java/lang/Object", null);

    mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, methodName, "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
    mv.visitCode();
    mv.visitVarInsn(ALOAD, 0);
    mv.visitVarInsn(ALOAD, 1);
    mv.visitInvokeDynamicInsn(
            "adder",
            "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            bootstrapMethodHandle
    );
    mv.visitVarInsn(ALOAD, 2);
    mv.visitInvokeDynamicInsn(
            "adder",
            "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            bootstrapMethodHandle
    );
    mv.visitInsn(ARETURN);
    mv.visitMaxs(2,1);
    mv.visitEnd();

    cw.visitEnd();

    return cw.toByteArray();
  }
}
