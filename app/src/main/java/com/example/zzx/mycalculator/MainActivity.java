package com.example.zzx.mycalculator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

    private String[] btnName = {
            "btn0", "btn1", "btn2", "btn3", "btn4",
            "btn5", "btn6", "btn7", "btn8", "btn9",
            "AC", "del", "braL", "braR", "add",
            "sub", "mul", "div", "dot", "equal",
            "pow", "per", "sqrt", "fac",
    };
    private int btnNUM = btnName.length;
    private Button[] buttons = new Button[btnNUM];
    private HashMap btnId = new HashMap<String, Integer>();
    private TextView textProcess;
    private TextView textResult;
    private int braClick = 0;  //用来记录括号的输入次数
    private Configuration myConf;
    //    private String inputText = "";
//    private String resultText = "";
//    private Intent intent = null;
    private Context context = this;

    private void makeIdMap(HashMap btnId) {
        for (int i = 0; i < btnNUM; i++) {
            btnId.put(i, btnName[i]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //此行代码可以去掉标题栏
        setContentView(R.layout.activity_main);


        //--------------------------------------------------
        textProcess = (TextView) findViewById(R.id.Process);
        textProcess.setMovementMethod(new ScrollingMovementMethod());  //设置输入过程框为可滑动
        textResult = (TextView) findViewById(R.id.Result);

//        inputText = textProcess.getText().toString();
//        resultText = textResult.getText().toString();

        btnListener myBtnListener = new btnListener();
        //-------------------------判断当前屏幕方向，并加载不同的按钮个数
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏时按钮个数为原来的个数
            btnNUM = 24;
            System.out.println("现在是横屏时的按钮个数" + btnNUM);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            btnNUM = 20;
            System.out.println("现在是竖屏时的按钮个数" + btnNUM);
        }
        makeIdMap(btnId);
        // 批量绑定按钮
        for (int i = 0; i < btnNUM; i++) {
            String btnName = btnId.get(i).toString();
            int btnID = getResources().getIdentifier(btnName, "id", getPackageName());
            buttons[i] = ((Button) findViewById(btnID));
            buttons[i].setOnClickListener(myBtnListener);
        }

        if (savedInstanceState != null) {
        }
//        intent = getIntent();
//        String input = intent.getStringExtra("process");
//        String result = intent.getStringExtra("result");
    }

    // 当前activity将被销毁时调用
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("process", textProcess.getText().toString());
//        intent.putExtra("result", textResult.getText().toString());
//        setResult(1, intent);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏时按钮个数为原来的个数
            btnNUM = 24;
            System.out.println("现在是横屏时的按钮个数" + btnNUM);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            btnNUM = 20;
            System.out.println("现在是竖屏时的按钮个数" + btnNUM);
        }
        outState.putString("process", textProcess.getText().toString());
        outState.putString("result", textResult.getText().toString());
        System.out.println("屏幕发生旋转！");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//       textProcess.setText(intent.get);
        String inputTem = savedInstanceState.getString("process");
        String resultTem = savedInstanceState.getString("result");
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏时按钮个数为原来的个数
            btnNUM = 24;
            System.out.println("现在是横屏时的按钮个数" + btnNUM);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            btnNUM = 20;
            System.out.println("现在是竖屏时的按钮个数" + btnNUM);
        }
        textProcess.setText(inputTem);
        textResult.setText(resultTem);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent date) {
        super.onActivityResult(requestCode, resultCode, date);
//        if (resultCode == 1) {
//            String input = date.getStringExtra("process");
//            String result = date.getStringExtra("result");
//            textProcess.setText(input);
//            textResult.setText(result);
//        }
    }

    //    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//
//    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("屏幕旋转！");
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setContentView(R.layout.activity_main);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.activity_main);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
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
//---------------------------------------------------
                case R.id.pow:
                    opInput("pow");
                    break;
                case R.id.per:
                    opInput("per");
                    break;
                case R.id.sqrt:
                    opInput("sqrt");
                    break;
                case R.id.fac:
                    opInput("fac");
                    break;
            }

        }
    }


    //数字输入时的函数调用
    private void numInput(String num) {
        String prev = textProcess.getText().toString();
        String prevOne;
        if (prev.length() == 0) {
            prev = " ";
        }
        prevOne = prev.substring(prev.length() - 1);  //代表输入的前一个字符
        // 只有当前一个字符不为右括号时才可以输入数字
        if (Pattern.matches("[^\\)]$", prevOne)) {
            textProcess.append(num);
        }
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
//                calculate();
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

            //-----------------------------------高级计算功能
            case "pow":
                if (Pattern.matches("[0-9\\)]+", prevOne)) {  //以数字结尾时可以输入
                    textProcess.append("^");
                }
                calculate();
                break;
            case "per":
                if (Pattern.matches("[0-9\\)]+", prevOne)) {
                    textProcess.append("%");

                }
                calculate();
                break;
            case "sqrt":
                if (Pattern.matches("[^\\d\\(\\)\\%]", prevOne)) {
                    textProcess.append("√");
                }
                break;
            case "fac":
                if (Pattern.matches("[0-9\\)]+", prevOne)) {
                    textProcess.append("!");
                }
                calculate();
                break;
        }
        System.out.println(braClick);

    }

    // 实时计算功能的调用
    private void calculate() {
        String mathLine = textProcess.getText().toString();
        mathLine = mathLine.replace("×", "*");
        mathLine = mathLine.replace("÷", "/");
        String RESULT = "";
        try {
            RESULT = calculator.do_calculate(mathLine);
        } catch (Exception e) {
            RESULT = "error!";
            System.out.println(e.getMessage());
        } finally {
            textResult.setText(RESULT);
        }

    }
}

