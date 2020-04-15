package com.yh.demo

import com.yh.demo.util.CamelTransferUtil
import org.junit.Test


/**
 * 驼峰字母转化
 * @author yanghan
 * @date 2019/7/18
 */
class CamelTransferUtilTest {
    def amap = ["code"      : 200,
                "msg"       : "successful",
                "data"      : [
                        "total"    : 2,
                        "list"     : [
                                ["isBuyGoods": "a little", "feeling": ["isHappy": "ok"]],
                                ["isBuyGoods": "ee", "feeling": ["isHappy": "haha"]],
                        ],
                        "extraInfo": [
                                "totalFee": 1500, "totalTime": "3d",
                                "nestInfo": [
                                        "travelDestination": "xiada",
                                        "isIgnored"        : true
                                ],
                                "buyList" : ["Food", "Dress", "Daily"]
                        ]
                ],
                "extendInfo": [
                        "involveNumber": "40",
                ]
    ]

    @Test
    void test1() {
        [null, "", " "].each {
            assert "" == CamelTransferUtil.camelToUnderline(it)
        }
        [null, "", " "].each {
            assert "" == CamelTransferUtil.underlineToCamel(it)
        }
    }

    @Test
    void test2() {
        ["isBuyGoods": "is_buy_goods", "feeling": "feeling", "G": "G", "GG": "GG"].each {
            key, value -> assert CamelTransferUtil.camelToUnderline(key) == value
        }
        ["is_buy_goods": "isBuyGoods", "feeling": "feeling", "b": "b", "_b": "B"].each {
            key, value -> assert CamelTransferUtil.underlineToCamel(key) == value
        }
    }

    @Test
    void test3() {
        def resultMap = [:]
        def ignoreSets = new HashSet();
        ignoreSets.add("isIgnored");
        CamelTransferUtil.transferKeyToUnderline(amap, resultMap, ignoreSets)
        println(resultMap)
    }

    @Test
    void test4() {
        def ignoreSets = new HashSet();
        ignoreSets.add("isIgnored");
        def resultMap = CamelTransferUtil.transferKeyToUnderline2(amap, ignoreSets)
        println(resultMap)
    }

    @Test
    void test5() {
        def ignoreSets = new HashSet();
        ignoreSets.add("isIgnored");
        def resultMap = CamelTransferUtil.generalMapProcess(amap, CamelTransferUtil.&camelToUnderline, ignoreSets)
        println(resultMap)
        def resultMap2 = CamelTransferUtil.generalMapProcess(resultMap, CamelTransferUtil.&underlineToCamel, ignoreSets)
        println(resultMap2)
    }

    @Test
    void test6() {
        def ignoreSets = new HashSet();
        ignoreSets.add("isIgnored");
        def list = [
                ["isBuyGoods": "a little", "feeling": ["isHappy": "ok"]],
                ["isBuyGoods": "ee", "feeling": ["isHappy": "haha"]],
        ]
        println(CamelTransferUtil.generalListProcess(list, CamelTransferUtil.&camelToUnderline, ignoreSets))
    }
}
