package com.middleyun.card;

import com.middleyun.common.card.CardController;
import com.middleyun.util.CardControlConfUtils;
import org.junit.Test;

/**
 * CardControlConfUtils 测试
 */
public class CardControlConfUtilsTest {

    @Test
    public void getCardControllerInstance() {
        CardController cardControlInstance = CardControlConfUtils.getCardControlInstance("G:\\temp\\card-controller.conf");
        System.out.println(cardControlInstance);
        System.out.println(cardControlInstance.getScreenBlocks());
    }
}
