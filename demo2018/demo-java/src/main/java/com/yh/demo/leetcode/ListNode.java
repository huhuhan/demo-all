package com.yh.demo.leetcode;

import lombok.Data;

/**
 * @author yanghan
 * @date 2020/1/15
 */
@Data
public class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public ListNode initNext(int x) {
        ListNode ln = new ListNode(x);
        this.setNext(ln);
        return this.getNext();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(val);
        ListNode nextLn = next;
        while (nextLn != null) {
            buffer.append(nextLn.getVal());
            nextLn = nextLn.getNext();
        }
        return buffer.toString();
    }
}
