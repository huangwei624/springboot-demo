package com.middleyun;

import com.middleyun.common.card.CardController;
import com.middleyun.common.card.ScreenBlock;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * java 反射测试
 */
public class JavaReflectTest {

    /**
     * java.lang.String
     * java.lang.Integer
     * java.lang.Integer
     * java.lang.String
     * java.util.List<com.middleyun.common.card.ScreenBlock>
     */
    @Test
    public void getFieldType() {
        CardController cardController = new CardController();
        Class<? extends CardController> aClass = cardController.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(item -> {
            System.out.println(item.getGenericType().getTypeName());
        });

        ScreenBlock screenBlock = new ScreenBlock();
        Class<? extends ScreenBlock> aClass1 = screenBlock.getClass();
        Field[] declaredFields1 = aClass1.getDeclaredFields();
        Arrays.stream(declaredFields1).forEach(item -> {
            System.out.println(item.getGenericType().getTypeName());
        });
    }
}
