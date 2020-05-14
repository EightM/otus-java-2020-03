package com.otus.hw05.changer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM8;

public class ClassChanger extends ClassVisitor {

    public ClassChanger(ClassVisitor cv) {
        super(ASM8, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        var mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        if (mv != null) {
            mv = new MethodChanger(mv, descriptor, name);
        }
        return mv;
    }


}
