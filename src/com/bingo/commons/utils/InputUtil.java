package com.bingo.commons.utils;

import com.bingo.commons.exception.BingoException;

import java.util.Scanner;

/**
 * @author nia
 * @description 输入类
 * @Date 2024/6/5
 */
public class InputUtil {
    private static Scanner sc = new Scanner(System.in);

    /**
     * 输入范围中的数字或q才返回
     *
     * @param scope
     * @return
     */
    public static int inputChar(int scope) {
        int input;
        while (true) {
            System.out.println("请输入您的选择: (输入q退出)");
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                if (input > 0 && input <= scope) {
                    break;
                }
                System.out.println("无效输入，请重新输入！");
            } else if (sc.hasNext()) {
                String s = sc.next();
                if (s.equals("q")) {
                    input = 0;
                    break;
                }
                System.out.println("无效输入，请重新输入！");
            }

        }
        sc.nextLine();
        return input;
    }

    public static int inputChar2(int start, int scope) {
        int input;
        while (true) {
            System.out.println("请输入您的选择: ");
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                if (input > start && input <= scope) {
                    break;
                }
                System.out.println("无效输入，请重新输入！");
            } else if (sc.hasNext()) {
                String s = sc.next();
                if (s.equals("q")) {
                    input = start;
                    break;
                }
                System.out.println("无效输入，请重新输入！");
            }

        }
        return input;
    }

    /**
     * 输入一个字符串
     *
     * @param msg
     * @return
     */
    public static String inputString(String msg) {
        System.out.println(msg);

        return sc.nextLine();
    }

    public static Double inputDouble(String msg) {
        System.out.println(msg);
        double s;
        while (true) {
            try {
                s = Double.parseDouble(sc.nextLine());
                if (s <= 0) {
                    throw new BingoException("金额不能为负数");
                }
            } catch (BingoException e) {
                System.out.println("金额不能为负数！");
                continue;
            } catch (Exception e) {
                System.out.println("请输入正确的金额！");
                continue;
            }
            break;
        }

        return s;
    }

    public static void close() {
        sc.close();
    }

}
