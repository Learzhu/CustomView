package com.learzhu.customview.customview;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/7/15 9:51
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  9:51
 * @updateDes ${TODO}
 */
public class Test {
    public void test() {
        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        System.out.println((a == c));
        System.out.println((a == e));
    }

    public static void main(String[] args) {
//        Test test = new Test();
//        test.test();
        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        System.out.println((a == c));
        System.out.println((a == e));
    }
}
