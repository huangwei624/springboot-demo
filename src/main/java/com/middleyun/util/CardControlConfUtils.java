package com.middleyun.util;

import com.middleyun.common.card.CardController;
import com.middleyun.common.card.ScreenBlock;
import com.typesafe.config.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 控制卡（拼接屏）配置工具类
 */
public class CardControlConfUtils {
    // 私有化构造器
    private CardControlConfUtils() {}

    /**
     * 通过控制卡配置文件对象获取控制卡实例
     * @param config 配置对象
     * @return  CardController
     */
    @SuppressWarnings("unchecked")
    private static CardController getCardControlInstance(Config config) {
        CardController cardController = new CardController();
        // 使用反射给CardController 的每个属性进行赋值
        Class<? extends CardController> cardControllerClass = cardController.getClass();
        Field[] cardControllerFields = cardControllerClass.getDeclaredFields();
        // 迭代每个属性
        Arrays.stream(cardControllerFields).forEach(field -> {
            try {
                // 获取属性的名称
                String fieldName = field.getName();
                // 获取属性对象的set方法名称
                String fieldSetMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                // 获取属性的类型
                String typeName = field.getGenericType().getTypeName();
                if ("java.lang.String".equals(typeName)) {
                    Method setMethod = cardControllerClass.getMethod(fieldSetMethodName, String.class);
                    setMethod.invoke(cardController, config.getString(fieldName));
                } else if("java.lang.Integer".equals(typeName)){
                    Method setMethod = cardControllerClass.getMethod(fieldSetMethodName, Integer.class);
                    setMethod.invoke(cardController, config.getInt(fieldName));
                }else if ("java.lang.Boolean".equals(typeName)){
                    Method setMethod = cardControllerClass.getMethod(fieldSetMethodName, Boolean.class);
                    setMethod.invoke(cardController, config.getBoolean(fieldName));
                }else if (typeName != null && typeName.contains("java.util.List")) {
                    Method setMethod = cardControllerClass.getMethod(fieldSetMethodName, List.class);
                    // 给CardController 设置 ScreenBlock
                    ArrayList<ScreenBlock> screenBlocks = new ArrayList<>();
                    List<?> blocks = config.getAnyRefList(fieldName);
                    for (int i = 0; i < blocks.size(); i++) {
                        HashMap<String, Object> map = (HashMap<String, Object>)blocks.get(i);
                        ScreenBlock screenBlock = new ScreenBlock();
                        Class<? extends ScreenBlock> screenBlockClass = screenBlock.getClass();
                        Field[] screenBlockFields = screenBlockClass.getDeclaredFields();
                        Arrays.stream(screenBlockFields).forEach(field1 -> {
                            try {
                                String fieldName1 = field1.getName();
                                String field1SetMethodName = "set" + fieldName1.substring(0, 1).toUpperCase() + fieldName1.substring(1);
                                String field1TypeName = field1.getGenericType().getTypeName();
                                if ("java.lang.String".equals(field1TypeName)) {
                                    Method field1SetMethod = screenBlockClass.getMethod(field1SetMethodName, String.class);
                                    field1SetMethod.invoke(screenBlock, (String)map.get(fieldName1));
                                } else if("java.lang.Integer".equals(field1TypeName)){
                                    Method field1SetMethod = screenBlockClass.getMethod(field1SetMethodName, Integer.class);
                                    field1SetMethod.invoke(screenBlock, (Integer)map.get(fieldName1));
                                }else if ("java.lang.Boolean".equals(field1TypeName)){
                                    Method field1SetMethod = screenBlockClass.getMethod(field1SetMethodName, Boolean.class);
                                    field1SetMethod.invoke(screenBlock, (Boolean)map.get(fieldName1));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new RuntimeException();
                            }
                        });
                        screenBlocks.add(screenBlock);
                    }
                    setMethod.invoke(cardController, screenBlocks);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("获取控制卡实例失败");
            }
        });
        return cardController;
    }


    /**
     * 通过本地控制卡配置文件获取一个通知卡实例
     * @param configPath
     * @return
     */
    public static CardController getCardControlInstance(String configPath) {
        Config config = HoconUtils.getConfig(configPath);
        return getCardControlInstance(config);
    }

    /**
     * 通过远程控制卡配置文件获取一个通知卡实例
     * @param configUri
     * @return
     */
    public static CardController getCardControlInstance(URL configUri) {
        Config config = HoconUtils.getConfig(configUri);
        return getCardControlInstance(config);
    }
}
