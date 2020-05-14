package com.otus.hw05.changer;

import org.objectweb.asm.*;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.objectweb.asm.Opcodes.*;

public class MethodChanger extends MethodVisitor {

    private final String descriptor;
    private final String methodName;
    private boolean hasLogAnno = false;
    private static final String LOG_ANNOTATION = "Lcom/otus/hw05/annotations/Log;";

    public MethodChanger(MethodVisitor mv, String descriptor, String methodName) {
        super(ASM8, mv);
        this.descriptor = descriptor;
        this.methodName = methodName;
    }

    @Override
    public void visitCode() {
        mv.visitCode();
        if (hasLogAnno) {
            addLogMethod();
        }
    }

    private void addLogMethod() {
        Handle handle = new Handle(
                H_INVOKESTATIC,
                Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants",
                MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class,
                        MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);

        var typesArray = Type.getArgumentTypes(descriptor);
        StringBuilder concatDescriptor = new StringBuilder();
        StringBuilder message = new StringBuilder("executed method: " + methodName + ",");

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        int index = 0;
        for (int i = 0; i < typesArray.length; i++) {
            mv.visitVarInsn(typesArray[i].getOpcode(ILOAD), index);
            concatDescriptor.append(typesArray[i]);
            message.append(" param");
            message.append(i+1);
            message.append(": \u0001 ");
            index += typesArray[i].equals(Type.DOUBLE_TYPE) || typesArray[i].equals(Type.LONG_TYPE) ? 2 : 1;
        }
        mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(" + concatDescriptor.toString() + ")Ljava/lang/String;",
                handle, message.toString());
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.equals(LOG_ANNOTATION)) {
            hasLogAnno = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

}
