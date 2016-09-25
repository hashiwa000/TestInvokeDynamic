package jp.hashiwa.sample.invokedynamic;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static jdk.internal.org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * Created by hashiwa on 16/09/25.
 */
class MyClassLoader extends ClassLoader {
  private final String className = "TestInvokeDynamic";

  @Override
  public Class findClass(String name) throws ClassNotFoundException {
    if (className.equals(name)) {
      byte[] b = createRunnerClass();
      return defineClass(name, b, 0, b.length);
    } else {
      return super.findClass(name);
    }
  }

  private byte[] createRunnerClass() {
    ClassWriter cw = new ClassWriter(COMPUTE_MAXS);
    MethodVisitor mv;
    MethodType bootstrapMethodType = MethodType.methodType(
            CallSite.class,
            MethodHandles.Lookup.class,
            String.class,
            MethodType.class);
    Handle bootstrapMethodHandle = new Handle(H_INVOKESTATIC,
            MyMethodFactory.class.getName().replace('.', '/'),
            "bootstrap",
            bootstrapMethodType.toMethodDescriptorString());


    cw.visit(V1_8, ACC_PUBLIC, this.className, null, "java/lang/Object", null);

//    mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
//    mv.visitCode();
//    mv.visitVarInsn(ALOAD, 0);
//    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
//    mv.visitInsn(RETURN);
//    mv.visitMaxs(1,1);
//    mv.visitEnd();

    mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "callInvokeDynamic", "()Ljava/lang/Object;", null, null);
    mv.visitCode();
//    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//    mv.visitLdcInsn("Hello, ASM!");
//    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//    mv.visitInsn(RETURN);
//    mv.visitMaxs(2,1);
    mv.visitInsn(ICONST_1);
    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
    mv.visitInsn(ICONST_2);
    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
    mv.visitInvokeDynamicInsn(
            "adder",
            "(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;",
            bootstrapMethodHandle
    );
    mv.visitInsn(ARETURN);
    mv.visitMaxs(2,1);
    mv.visitEnd();

    cw.visitEnd();

    return cw.toByteArray();
  }
}
