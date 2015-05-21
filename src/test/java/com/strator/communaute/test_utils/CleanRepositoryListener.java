package com.strator.communaute.test_utils;

import com.strator.communaute.data.HardCodedDataBase;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.annotation.Annotation;

/**
 * Permet d'asserter sur des exceptions lancées par les tests
 */
public class CleanRepositoryListener extends AbstractTestExecutionListener {


    @Override
    public void afterTestMethod(TestContext testContext) throws Exception{
             for (Annotation annotation : testContext.getTestMethod().getAnnotations()) {
                 if(annotation instanceof CleanRepository){
                     testContext.getApplicationContext().getBean(HardCodedDataBase.class).cleanDatas();
                 }
             }
    }


}
