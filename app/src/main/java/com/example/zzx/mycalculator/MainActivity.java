package com.example.zzx.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[20];
    private HashMap btnId = new HashMap<String, Integer>();
    private TextView textProcess;
    private TextView textResult;
    private int braClick = 0;
    private Calculator myCal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //--------------------------------------------------
        textProcess = (TextView) findViewById(R.id.Process);
        textProcess.setMovementMethod(new ScrollingMovementMethod());
        textResult = (TextView) findViewById(R.id.Result);
        btnListener myBtnListener = new btnListener();
        makeIdMap(btnId);
        for (int i = 0; i < 20; i++) {
            String btnName = btnId.get(i).toString();
            int btnID = getResources().getIdentifier(btnName, "id", getPackageName());
            buttons[i] = ((Button) findViewById(btnID));
            buttons[i].setOnClickListener(myBtnListener);
        }


    }

    private void makeIdMap(HashMap btnId) {
        String[] btnName = {
                "btn0", "btn1", "btn2", "btn3", "btn4",
                "btn5", "btn6", "btn7", "btn8", "btn9",
                "AC", "del", "bra", "per", "add",
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
                case R.id.bra:
                    opInput("bra");
                    break;
                case R.id.per:
                    opInput("per");
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
        switch (btnText) {
            case "AC":
                textProcess.setText("");
                braClick = 0;
                break;
            case "del":
                String temp = textProcess.getText().toString();
                if (temp.length() > 0) {
                    if (temp.charAt(temp.length() - 1) == '(' || temp.charAt(temp.length() - 1) == ')') {
                        braClick--;
                    }
                    textProcess.setText(temp.subSequence(0, temp.length() - 1));
                }
                calculate();
                break;
            case "bra":
                if (braClick % 2 == 0) {
                    textProcess.append("(");
                    braClick++;
                } else {
                    textProcess.append(")");
                    calculate();
                    braClick++;
                }
                break;
            case "per":
                textProcess.append("%");
                break;
            case "add":
                textProcess.append("+");
                break;
            case "sub":
                textProcess.append("-");
                break;
            case "mul":
                textProcess.append("×");
                break;
            case "div":
                textProcess.append("÷");
                break;
            case "dot":
                textProcess.append(".");
                break;
            case "equal":
                calculate();

                break;
        }

    }

    private void calculate() {
        String mathLine = textProcess.getText().toString();
        mathLine = mathLine.replace("×", "*");
        mathLine = mathLine.replace("÷", "/");
        double result = Calculator.conversion(mathLine);
        textResult.setText(Double.toString(result));

    }


}
