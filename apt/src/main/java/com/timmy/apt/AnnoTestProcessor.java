package com.timmy.apt;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.timmy.anno.AnnoTest;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

/**
 *
 * * Created by admin on 2017/9/21.
 * Java model:用于存注解处理器逻辑
 */

/**
 * 该注解会自动在指定路径下生成一个配置文件
 * apt/build/classes/main/META-INF/services/javax.annotation.processing.Processor；
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.timmy.anno.AnnoTest")//配置这个类所要处理的注解类型
public class AnnoTestProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        AnnotationSpec.Builder ab = AnnotationSpec.builder()

        //1.生成TestClass类
        TypeSpec.Builder tb = TypeSpec.classBuilder("TestClass")
                .addModifiers(Modifier.PUBLIC);

        //2.生成main方法
        MethodSpec.Builder mb = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class,"args");

        //3.生成代码块,并添加到main方法中,遍历所有被@AnnoTest注解过的类,取出注解内容及类名打印出来
        Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(AnnoTest.class));
        for (TypeElement te : typeElements) {
            CodeBlock cb = CodeBlock.builder()
                    .addStatement("$T.out.print(\"$L + $L\")",
                            System.class,
                            te.getAnnotation(AnnoTest.class).value(),
                            te.getSimpleName())
                    .build();
            mb.addCode(cb);
        }

        tb.addMethod(mb.build());

        JavaFile jf = JavaFile.builder("com.timmy.apt",tb.build()).build();

        //4.将代码写入Java文件中
        try {
            jf.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }
}
