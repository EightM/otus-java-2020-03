package com.otus.hw05;

import com.otus.hw05.changer.ClassChanger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                if (className.equals("com/otus/hw05/Calculator")) {
                    return changeClass(classfileBuffer);
                }
                return classfileBuffer;
            }
        });
    }

    private static byte[] changeClass(byte[] classfileBuffer) {

        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassChanger cg = new ClassChanger(cw);
        cr.accept(cg, 0);

        return cw.toByteArray();
    }
}
