package com.example.zzx.mycalculator;
// 在此重新写计算的实现类
//添加一个静态方法

import java.util.Stack;
import java.util.regex.*;
import java.util.HashMap;
import java.math.*;

public class calculator {
	// 两个全局堆栈
	public static Stack<String> opStack = new Stack<String>();
	public static Stack<String> expStack = new Stack<String>();
	public static Stack<Double> numStack = new Stack<Double>();
	public static String RESULT; // 记录全局的结果
	// 记录操作符号的优先级
	@SuppressWarnings("serial")
	public static HashMap<String, Integer> op_relus = new HashMap<String, Integer>() {
		{

			put("+", 1);
			put("-", 1);
			put("*", 2);
			put("/", 2);
			put("^", 3);
			put("!", 5); // 用！来代替单个负号
			put("%", 4);
			put("(", 6);
			put(")", 6);
		}
	};

	// 建立全局堆栈
	// 接受一个用户当前的输入
	public static void makeStak(String pushTem) {

		// 这里响应多种输入的入栈
		// 数字
		if (Pattern.matches("[\\d\\.]+", pushTem)) {
			expStack.push(pushTem);
		} else if (pushTem == "(") { // 左括号
			opStack.push(pushTem);
		} else if (pushTem == ")") { // 右括号
			while (true) {
				if (!opStack.isEmpty()) {
					String temp = opStack.pop();
					if (temp != "(") {
						expStack.push(temp);
					} else {
						break;
					}
				} else {
					break;
				}
			}
		} else { // 运算符号入栈
			// else if (Pattern.matches("[!\\+\\-\\*\\/]+", pushTem)) {
			System.out.println("有符号入栈！\n");
			if (!opStack.isEmpty() && opStack.peek() != "(" && opStack.peek() != ")") {
				String temPop = opStack.peek(); // 查看当前栈顶元素
				System.out.println(temPop + "是当前栈顶元素！");
				System.out.println("的优先级为" + op_relus.get(temPop));
				while (true) { // 当当前操作符的优先级小于或等于栈顶操作符号的优先级时
					if (op_relus.get(pushTem) <= op_relus.get(opStack.peek())) {
						expStack.push(opStack.pop());  //就弹入到后缀栈中
					} else {
						opStack.push(pushTem);
						break;
					}
				}
			} else {
				opStack.push(pushTem);
			}
		}
		// 最后将操作符号栈中全部弹出到expStack
	}

	// 用于判断后缀栈并且计算值Dimensions
	public static String do_calculate() {
		// 每计算一次先初始化后缀栈
		while (!opStack.isEmpty()) {
			String tem = opStack.pop();
			System.out.println(tem + "从op-->exp");
			expStack.push(tem);
		}
		System.out.println("----------------------");
		for (String tem : expStack) {
			System.out.println(tem + "在后缀栈中");
		}
		int exp_len = expStack.size();
		String[] exp_list = new String[exp_len];
		exp_list = expStack.toArray(new String[0]);
		System.out.println("----------------------");
		for (String tem : exp_list) {
			System.out.println(tem + "  在栈中！");
			if (Pattern.matches("[\\d\\.]+", tem)) { // 数字入栈
				numStack.push(Double.parseDouble(tem));

			} else if (tem == "!") { // 负数的回归
				Double numTem = numStack.pop();
				numStack.push(-numTem);
			} else if (tem == "*") {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(numL * numR);
			} else if (tem == "+") {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(numL + numR);
			} else if (tem == "-") {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(numL - numR);
			} else if (tem == "/") {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(numL / numR);
			} else if (tem == "^") {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(Math.pow(numL, numR));
			} else if (tem == "%") {
				Double numTem = numStack.pop();
				numStack.push(numTem / 100);
			}
		}
		for (Double num : numStack) {
			System.out.println(num + "在数字栈中");
		}
		if (numStack.size() == 1) {
			RESULT = Double.toString(numStack.peek());
			System.out.println(RESULT + "是计算结果！");
		}
		return RESULT;
	}
}
