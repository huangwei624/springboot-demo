package com.middleyun;

import com.middleyun.common.card.ScreenBlock;
import com.middleyun.util.HoconUtils;
import com.typesafe.config.Config;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * conf 配置类解析工具测试
 */
public class HoconUtilsTest {

    @Test
    public void getConfig() {
        Config config = HoconUtils.getConfig("G:\\temp\\card-controller.conf");
        if (config != null) {
            String machineNo = config.getString("screenNo");
            System.out.println(machineNo);
            System.out.println();
            List<?> blocks = config.getAnyRefList("screen.block");
//            Config config1 = config.getConfig("screen.block");
            blocks.forEach(item -> {
                HashMap<String, Object> map = (HashMap<String, Object>)item;
                for (String key : map.keySet()) {
                    System.out.println(key+ ": " +map.get(key));
                }
            });
        }
    }
}
