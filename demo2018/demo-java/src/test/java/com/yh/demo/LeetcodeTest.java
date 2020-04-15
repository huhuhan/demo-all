package com.yh.demo;

import com.yh.demo.leetcode.ListNode;
import org.junit.Test;

/**
 * @author yanghan
 * @date 2020/1/15
 */
public class LeetcodeTest {

    /**
     * T7: 整数反转
     *
     * @throws Exception
     */
    @Test
    public void reverseInteger() throws Exception {
        int x = Integer.MIN_VALUE;
        this.outParam(x);
        int ans = 0;
        while (x != 0) {
            int pop = x % 10;
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7)) {
                throw new Exception("整数溢出");
            }
            if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8)) {
                throw new Exception("整数溢出");
            }
            ans = ans * 10 + pop;
            x /= 10;
        }
        this.outResult(ans);
    }

    /**
     * T5: 最长回文串
     */
    @Test
    public void longestPalindrome() {
        String s = "";
        this.outParam(s);
        String subString = "";
        if (null != s && s.length() > 0) {
            //最长回文串的头尾下标
            int start = 0, end = 0;
            for (int i = 0; i < s.length(); i++) {
                //假设中心数为s.charAt(i), 向两边扩展，得到回文长度
                int len1 = expandAroundCenter(s, i, i);
                //假设中心数为s.charAt(i)和s.charAt(i+1)和, 向两边扩展，得到回文长度
                int len2 = expandAroundCenter(s, i, i + 1);
                //取最长回文长度
                int len = Math.max(len1, len2);
                //当前算出的长度大于之前算出的，更新最长回文串头尾下标
                if (len > end - start) {
                    //当前中心数可能为i或i,i+1
                    //头下标=中心数-
                    start = i - (len - 1) / 2;
                    //尾下标=中心数+
                    end = i + len / 2;
                }
            }
            //substring函数区间范围为[start, end)
            subString = s.substring(start, end + 1);
        }
        this.outResult(subString);
    }

    //回文字符串长度
    private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        //中位数向两边扩展，下标不能超出字符串的下标
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    /**
     * T3:无重复字符的最长子串
     */
    @Test
    public void lengthOfLongestSubstring() {
        String s = "abcafraa123456";
        this.outParam(s);
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // i为左区间，j为右区间，j向右移动，index存储j字符的后一个下标
        for (int j = 0, i = 0; j < n; j++) {
            //判断j是否为重复字符，是，就j+1赋值给1，即i移动到j+1位置
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            //index存储j字符的后一个下标
            index[s.charAt(j)] = j + 1;
            this.outLog(s.substring(i, i + ans));
        }
        this.outResult(ans);
    }

    /**
     * T2：两数相加
     */
    @Test
    public void addTwoNumbers() {
        ListNode l1 = new ListNode(2);
        l1.initNext(4).initNext(3);
        ListNode l2 = new ListNode(5);
        l2.initNext(6).initNext(4);
        this.outParam(l1.toString(), l2.toString());

        ListNode pre = new ListNode(0);
        //pre先和cur指向相同地址，定位在头节点
        ListNode cur = pre;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.getVal();
            int y = l2 == null ? 0 : l2.getVal();
            int sum = x + y + carry;

            carry = sum / 10;
            cur.setNext(new ListNode(sum % 10));

            //更换下标数值
            cur = cur.getNext();
            if (l1 != null)
                l1 = l1.getNext();
            if (l2 != null)
                l2 = l2.getNext();
        }
        if (carry == 1) {
            cur.setNext(new ListNode(carry));
        }
        //pre同cur地址，直接获取后续节点拼接字符串
        //cur此时定位尾节点，为null
        this.outResult(pre.getNext().toString());
    }

    public void outParam(Object... objects) {
        for (Object object : objects) {
            System.out.println("输出参数：" + object);
        }
        System.out.println("-----------------------------------");
    }

    public void outLog(Object... objects) {
        for (Object object : objects) {
            System.out.println("输出日志：" + object);
        }
    }

    public void outResult(Object... objects) {
        System.out.println("-----------------------------------");
        for (Object object : objects) {
            System.out.println("输出结果：" + object);
        }
    }
}
