# Calculator_for_Android
这就是一个简单的安卓计算器，其中包括了自己写的一个计算简单数学表达式的calculator JAVA类，可以计算带括号、加减乘除、幂运算(^)、及百分号的规范的数学表达式String！

# 一些说明
+ app/java/com.example.zzx.mycalculator/MainActivity
> 这个是主要的界面逻辑实现，在这个文件的末尾可以看到一个类的私有函数 * calculate() * ;
在这个函数中调用了同文件夹下的另外一个类 * calculator *（是一个静态类）中的一个功能接口** .do_calculate(mathLine) **，
这个接口接受一个String的数学表达式如："((((1-2%)+3)*4)/5)^3"，要求表达式尽量规整（括号对称，符号不乱写），而规整的表达式输入
是在用户输入是限定其输入标准的表达式，目前只支持简单的运算{"+","-","*","/","^","%"}，运算可以拓展，只是目前没有接着写。而这个
功能接口返回一个运算结果String;运算结果中包含了java自带的运算异常（/0,NaN）。

+ app/java/com,example.zzx.mycalculator/calculator
> 这便是上文中提到的计算实现类：其中包含
>+ calcultor   //主静态类
>>+ do_calculate(String mathLine)  //**计算的接口函数**
>>>+ **输入：**  (String)算术式，如：可以复杂到 "((((1-2%)+3)*4)/5)^3"
>>>+ **输出**  (String) 异常处理后的运算结果，精度默认为Double的精度
>>+ makeArray(String mathLine)  //处理用户输入并将其存入一个缓存堆栈 tempStack<String>,可以不用管
>>+ makeStak(String pushTem)  //从tempStack中逐项入栈的函数功能。可以不用管，但是如果拓展运算功能的话需要多匹配一些运算符号的正则表达式！
>>+ calculate()  //正真运算功能实现的地方，如果需要拓展运算功能需要在这里添加对应运算符号的匹配和功能实现！

## 反正代码写的很low，还在入门，留做实验记录用！
