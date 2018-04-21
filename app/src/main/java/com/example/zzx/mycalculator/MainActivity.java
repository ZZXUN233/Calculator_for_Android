package com.example.zzx.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import java.util.regex.*;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[20];
    private HashMap btnId = new HashMap<String, Integer>();
    private TextView textProcess;
    private TextView textResult;
    private int braClick = 0;  //用来记录括号的输入次数
    private int AcClick = 0;  //用来记录AC的输入次数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //此行代码可以去掉标题栏
        setContentView(R.layout.activity_main);


        //--------------------------------------------------
        textProcess = (TextView) findViewById(R.id.Process);
        textProcess.setMovementMethod(new ScrollingMovementMethod());
        textResult = (TextView) findViewById(R.id.Result);
        btnListener myBtnListener = new btnListener();
        makeIdMap(btnId);
        // 批量绑定按钮
        for (int i = 0; i < 20; i++) {
            String btnName = btnId.get(i).toString();
            int btnID = getResources().getIdentifier(btnName, "id", getPackageName());
            buttons[i] = ((Button) findViewById(btnID));
            buttons[i].setOnClickListener(myBtnListener);
        }

        if (savedInstanceState != null) {
        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
    }

    private void makeIdMap(HashMap btnId) {
        String[] btnName = {
                "btn0", "btn1", "btn2", "btn3", "btn4",
                "btn5", "btn6", "btn7", "btn8", "btn9",
                "AC", "del", "braL", "braR", "add",
                "sub", "mul", "div", "dot", "equal"
        };
        for (int i = 0; i < btnName.length; i++) {
            btnId.put(i, btnName[i]);

        }


    }


    // 设置按钮的绑定事件类
    private class btnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn0:
                    numInput("0");
                    break;
                case R.id.btn1:
                    numInput("1");
                    break;
                case R.id.btn2:
                    numInput("2");
                    break;
                case R.id.btn3:
                    numInput("3");
                    break;
                case R.id.btn4:
                    numInput("4");
                    break;
                case R.id.btn5:
                    numInput("5");
                    break;
                case R.id.btn6:
                    numInput("6");
                    break;
                case R.id.btn7:
                    numInput("7");
                    break;
                case R.id.btn8:
                    numInput("8");
                    break;
                case R.id.btn9:
                    numInput("9");
                    break;
                //----------------------------------------------以下是符号键
                case R.id.AC:
                    opInput("AC");
                    break;
                case R.id.del:
                    opInput("del");
                    break;
                case R.id.braL:
                    opInput("braL");
                    break;
                case R.id.braR:
                    opInput("braR");
                    break;
                case R.id.add:
                    opInput("add");
                    break;

                case R.id.sub:
                    opInput("sub");
                    break;
                case R.id.mul:
                    opInput("mul");
                    break;
                case R.id.div:
                    opInput("div");
                    break;
                case R.id.dot:
                    opInput("dot");
                    break;
                case R.id.equal:
                    opInput("equal");
                    break;
            }

        }
    }


    //数字输入时的函数调用
    private void numInput(String num) {
        String NUM = num;
        textProcess.append(num);
        calculate();
    }

    private void opInput(String opBtn) {
        String btnText = opBtn;
        String prev = textProcess.getText().toString();
        String prevOne;
        if (prev.length() == 0) {
            prev = " ";
        }
        prevOne = prev.substring(prev.length() - 1);  //代表输入的前一个字符
        System.out.println("前一个字符为：" + prevOne);
        switch (btnText) {
            case "AC":
                AcClick++;
                textProcess.setText("");
                braClick = 0;
                break;
            case "del":
                if (prevOne.charAt(0) == '(') {
                    braClick--;
                } else if (prevOne.charAt(0) == ')') {
                    braClick++;
                }
                textProcess.setText(prev.subSequence(0, prev.length() - 1));
                calculate();
                break;
            case "braL":
                //如果当前输入框的最后一个字符不是数字则可以输入左括号
                if (Pattern.matches("[^\\d\\)\\.]", prev.substring(prev.length() - 1))) {
                    textProcess.append("(");
                    braClick++;
                }
                calculate();
                break;
            case "braR":
                //如果前一个字符不是操作符号才可以输入右括号
                if (Pattern.matches("[^\\+\\-\\×\\÷\\(\\.]", prevOne)) {
                    if (braClick > 0) {
                        textProcess.append(")");
                        braClick--;  //没输入一个右括号抵消一个左括
                    }
                }
                calculate();
                break;
            case "add":
                // 只有前一个字符不为操作符还有空格时才能输入+
                if (Pattern.matches("[^\\+\\-\\×\\÷\\(\\s\\.]", prevOne)) {
                    textProcess.append("+");
                }
                break;
            case "sub":
                // 前一个不为减号的时候才可以输入
                if (Pattern.matches("[^\\-\\.]", prevOne)) {
                    textProcess.append("-");
                }
                break;
            case "mul":
                if (Pattern.matches("[^\\+\\-\\×\\÷\\(\\s\\.]", prevOne)) {
                    textProcess.append("×");
                }
                break;
            case "div":
                if (Pattern.matches("[^\\+\\-\\×\\÷\\(\\s\\.]", prevOne)) {
                    textProcess.append("÷");
                }
                break;
            case "dot":
                //输入小数点的条件
                //1.前面不能为空或是操作符
                //2.不能在一个小数后面追加小数点
                if (Pattern.matches("[^\\+\\-\\×\\÷\\(\\)\\s\\.]", prevOne)) {
                    if (!Pattern.matches("(.*[0-9]+\\.[0-9]+)$", prev)) {
                        textProcess.append(".");
                    }
                }
                break;
            case "equal":
                calculate();

                break;
        }
        System.out.println(braClick);

    }

    private void calculate() {
        String mathLine = textProcess.getText().toString();
        mathLine = mathLine.replace("×", "*");
        mathLine = mathLine.replace("÷", "/");
        double result = Calculator.conversion(mathLine);
        String temp = Double.toString(result);
        if (braClick == 0) {  //括号抵消状态时
            if (Pattern.matches("[^0-9\\.]", temp)) {
                System.out.println("输入异常！");
                textResult.setText("简单点！输入的方式简单点！"); //只有当计算出结果时才显示
            } else {
                textResult.setText(temp);
            }
        }

    }


}
