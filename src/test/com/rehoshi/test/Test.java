package com.rehoshi.test;

import com.rehoshi.dto.search.OrderPageSearch;
import com.rehoshi.service.OrderService;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.rehoshi.util.PYUtil.getPinYinHeadChar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Test {
    @Autowired
    private OrderService orderService ;
    @org.junit.Test
    public void test(){
        System.out.println(orderService.orderList(new OrderPageSearch()));
    }

    public static void main(String[] args) {

        System.out.println(getPinYinHeadChar("百香果30枚（本地）"));
    }


}
