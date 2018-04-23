package com.example.zzx.mycalculator;
// 在此重新写计算的实现类
//添加一个静态方法

import java.util.Stack;
import java.util.regex.*;
import java.util.HashMap;

public class calculator {
	// 两个全局堆栈
	public static Stack<String> tempStack = new Stack<String>();
	public static Stack<String> opStack = new Stack<String>();
	public static Stack<String> expStack = new Stack<String>();
	public static Stack<Double> numStack = new Stack<Double>();
	public static Pattern pattern = Pattern.compile("[\\+\\-\\*\\/\\%\\#\\!\\√]+"); // 定义匹配所有运算符号的正则表达式
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
			put("#", 5); // 用#来代替单个负号
			put("%", 4);
			put("√", 4); // 根号的支持
			put("!", 4); // 阶乘的支持
			put("(", 6);
			put(")", 6);
		}
	};

	public static String do_calculate(String mathLine) {
		String RESULT = "";
		System.out.println("计算式的长度为" + mathLine.length());
		try{

			makeArray(mathLine);
			for (String input : tempStack) {
				if (Pattern.matches("[^\\s]+", input)) {
					makeStak(input);
				}
			}
			RESULT = calculate();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}finally {

		}
		// 计算完成一次清空所有堆栈
		tempStack.clear();
		expStack.clear();
		opStack.clear();
		numStack.clear();
		System.out.println("计算的结果为：" + RESULT);
		return RESULT;
	}

	// 将字符串按照特定的规则切开成数组
	public static Stack<String> makeArray(String mathLine) {
		mathLine = ":" + mathLine; // 添加一个起始位
		mathLine = mathLine.replace("×", "*");
		mathLine = mathLine.replace("÷", "/");
		mathLine = mathLine.replace("/-", "/#");
		mathLine = mathLine.replace("*-", "/#");
		mathLine = mathLine.replace("+-", "+#");
		mathLine = mathLine.replace("--", "-#");
		mathLine = mathLine.replace("(-", "(#");
		mathLine = mathLine.replace(":-", ":#");
		mathLine = mathLine.replace(":", "");
		System.out.println(mathLine);
		Stack<String> target = new Stack<String>();
		String numTemp = "";
		for (int i = 0; i < mathLine.length(); i++) {
			if (Pattern.matches("[\\d\\.]+", mathLine.substring(i, i + 1))) {
				numTemp += mathLine.substring(i, i + 1);
			} else {
				// 当扫描到不是数字时，就将数字缓存入栈，并清空缓存
				if (numTemp != "") {
					tempStack.push(numTemp);
					numTemp = "";
				} else {
					numTemp = "";
				}
				tempStack.push(mathLine.substring(i, i + 1));
			}
		}

		// 如果最后一个为数字
		if (numTemp != null) {
			tempStack.push(numTemp);
			numTemp = "";
		}
		target = tempStack;
		return target;
	}

	// 建立全局堆栈
	// 接受一个用户当前的输入
	public static void makeStak(String pushTem) {

		// 这里响应多种输入的入栈
		// 数字
		if (Pattern.matches("[\\d\\.]+", pushTem)) {
			System.out.println("数字入栈！" + pushTem);
			expStack.push(pushTem);
		} else if (Pattern.matches("[\\(]+", pushTem)) { // 左括号
			opStack.push(pushTem);
		} else if (Pattern.matches("[\\)]+", pushTem)) { // 右括号
			while (!Pattern.matches("[\\(]+", opStack.peek())) {
				if (opStack.isEmpty()) {
					break;
				}
				expStack.push(opStack.pop());
			}
			opStack.pop();
		} else {
			System.out.println(pushTem + "符号入栈！");
			for (String op : opStack) {
				System.out.println(op + "--------在符号缓存栈中");
			}
			if (!opStack.isEmpty()) {
				while (op_relus.get(pushTem) <= op_relus.get(opStack.peek())) { // 当前操作符的优先级小于或等于栈顶操作符号的优先级时
					if (Pattern.matches("[\\(]+", opStack.peek())) { // 栈顶为左括号就不管
						break;
					}
					expStack.push(opStack.pop());
					if (opStack.isEmpty()) {
						break;
					}
				}
				// 栈顶优先级低于当前准备入栈的元素
				opStack.push(pushTem);
			} else {
				opStack.push(pushTem);
			}
		}
	}

	// 用于判断后缀栈并且计算值Dimensions
	public static String calculate() {
		// 每计算一次先初始化后缀栈
		for (String tem : opStack) {
			System.out.println(tem);
		}

		while (!opStack.isEmpty()) {
			String tem = opStack.peek();
			System.out.println(tem + "从op-->exp");
			expStack.push(opStack.pop());
		}

		System.out.println("-----------后缀表达式-----------");
		for (String tem : expStack) {
			System.out.println(tem);
		}
		// 做运算
		for (String tem : expStack) {
			if (Pattern.matches("[\\d\\.]+", tem)) { // 数字入栈
				numStack.push(Double.parseDouble(tem));
			} else if (Pattern.matches("[\\#]+", tem)) { // 负数的回归
				Double numTem = numStack.pop();
				numStack.push(-numTem);
				System.out.println(numStack.peek() + "在数字栈顶！");
			} else if (Pattern.matches("[\\*]+", tem)) {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(numL * numR);
			} else if (Pattern.matches("[\\+]+", tem)) {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(numL + numR);
			} else if (Pattern.matches("[\\-]+", tem)) {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(numL - numR);
			} else if (Pattern.matches("[\\/]+", tem)) {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(numL / numR);
			} else if (Pattern.matches("[\\^]+", tem)) {
				Double numR = numStack.pop();
				Double numL = numStack.pop();
				numStack.push(Math.pow(numL, numR));
			} else if (Pattern.matches("[\\%]+", tem)) {
				Double numTem = numStack.pop();
				numStack.push(numTem / 100);
			} else if (Pattern.matches("[\\√]+", tem)) {
				Double numTem = numStack.pop();
				numStack.push(Math.sqrt(numTem));
			} else if (Pattern.matches("[\\!]+", tem)) {
				Double numTem = numStack.pop();
				if (numTem > 0 && numTem%1 == 0) {
					numStack.push(fac(numTem));
				} else if(numTem == 0){
					numStack.push(fac(numTem));
				}else{
					numStack.push(1/0.0);
					RESULT =Double.toString(1/0.0);
					break;
				}
			}
		}

		for (Double num : numStack) {
			System.out.println(num + "在数字栈中");
		}
		System.out.println("最终数字栈大小为" + numStack.size());
		if (numStack.size() == 1) {
			RESULT = Double.toString(numStack.peek());
			System.out.println(RESULT + "是计算结果！");
		}
		RESULT = Double.toString(numStack.peek());
		return RESULT;
	}

	private static double fac(double num) {
		if (num == 1 || num == 0) {
			return 1;
		} else {
			return num * fac(num - 1);
		}
	}
}
