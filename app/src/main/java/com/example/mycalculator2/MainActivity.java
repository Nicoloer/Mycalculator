package com.example.mycalculator2;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Stack stack=new Stack();
    //    结果集
    private EditText editText;

    //数字1-9
    private Button main_btn1;
    private Button main_btn2;
    private Button main_btn3;
    private Button main_btn4;
    private Button main_btn5;
    private Button main_btn6;
    private Button main_btn7;
    private Button main_btn8;
    private Button main_btn9;
    private  Button main_btn0;

    //运算符
    private  Button main_btnadd  ;// +
    private  Button main_btnsub;  // -
    private  Button main_btnmul;  // *
    private  Button main_btndiv;  // /
    private  Button main_btnd;  //小数点
    private  Button main_btneq;  //=

    //清除
    private  Button main_btndel;
    private  Button main_btnsin;
    private  Button main_btnex;
    private  Button main_btnclr;

    boolean clear_flag;//清空标识
    boolean repeat_flag;//标点符号重复

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //数字1-9
        main_btn1 = findViewById(R.id.bt_1);
        main_btn2 = findViewById(R.id.bt_2);
        main_btn3= findViewById(R.id.bt_3);
        main_btn4 = findViewById(R.id.bt_4);
        main_btn5 = findViewById(R.id.bt_5);
        main_btn6 = findViewById(R.id.bt_6);
        main_btn7 = findViewById(R.id.bt_7);
         main_btn8 = findViewById(R.id.bt_8);
         main_btn9 = findViewById(R.id.bt_9);
         main_btn0 = findViewById(R.id.bt_0);
        //运算符
         main_btnadd = findViewById(R.id.bt_add);// +
         main_btnsub = findViewById(R.id.bt_sub);// -
         main_btnmul = findViewById(R.id.bt_mul);// *
         main_btndiv = findViewById(R.id.bt_div); //

         main_btnd = findViewById(R.id.bt_pt);//小数点
         main_btneq = findViewById(R.id.bt_eq);//=
         main_btndel = findViewById(R.id.bt_del);//删除
         main_btnclr = findViewById(R.id.bt_clr);//清空
        main_btnsin = findViewById(R.id.bt_sin);//sin
        main_btnex = findViewById(R.id.bt_ex);//正负号转换
        editText = (EditText) findViewById(R.id.et_input);//结果集


        //添加点击事件
        main_btn0.setOnClickListener(this);
        main_btn1.setOnClickListener(this);
        main_btn2.setOnClickListener(this);
        main_btn3.setOnClickListener(this);
        main_btn4.setOnClickListener(this);
        main_btn5.setOnClickListener(this);
        main_btn6.setOnClickListener(this);
        main_btn7.setOnClickListener(this);
        main_btn8.setOnClickListener(this);
        main_btn9.setOnClickListener(this);

        main_btnd.setOnClickListener(this);
        main_btndel.setOnClickListener(this);
        main_btnsin.setOnClickListener(this);
        main_btnclr.setOnClickListener(this);
        main_btnex.setOnClickListener(this);

        main_btnadd.setOnClickListener(this);
        main_btnsub.setOnClickListener(this);
        main_btnmul.setOnClickListener(this);
        main_btndiv.setOnClickListener(this);
        main_btneq.setOnClickListener(this);
    }

    //读取每个按钮的点击的内容
    @Override
    public void onClick(View view) {
        //获取文本内容
        String input = editText.getText().toString();
        switch (view.getId()){
            case R.id.bt_0:
            case R.id.bt_1:
            case R.id.bt_2:
            case R.id.bt_3:
            case R.id.bt_4:
            case R.id.bt_5:
            case R.id.bt_6:
            case R.id.bt_7:
            case R.id.bt_8:
            case R.id.bt_9:
            case R.id.bt_pt:
                if(clear_flag){
                    clear_flag = false;
                    editText.setText("");//先赋值为空
                }
                editText.setText(input + ((Button)view).getText());//后显示结果集就为本身
                break;
            case R.id.bt_add:
            case R.id.bt_sub:
            case R.id.bt_mul:
            case R.id.bt_div:

 //               if(repeat_flag) {
//                    Toast.makeText(MainActivity.this, "运算符号有误请检查", Toast.LENGTH_LONG).show();
//                    editText.setText(input.substring(0, input.length() - 2)+input.charAt(input.length()-1)+" ");//新的运算符会替换旧的运算符
                editText.setText(input + " " + ((Button) view).getText() + " ");
              //  check();
                   //              }
//                if (clear_flag) {
//                    clear_flag = false;
//                    input = "";
//                    editText.setText("");
//                }在进行完一次运算的时候下次运算的时候清空不需要
 //               repeat_flag=true;
           //     editText.setText(input + " " + ((Button) view).getText() + " ");

                break;
            case R.id.bt_del:

                if(input==null) {
                    input = "";
                    editText.setText(input);
                }
                else if(clear_flag){
                    clear_flag = false;
                    input = "";
                    editText.setText(input);
                }else if(input != null || !input.equals("")) {//如果获取到的内容不空
                    editText.setText(input.substring(0, input.length() - 1));//结果集为空
                }

                break;
            case R.id.bt_clr:
                editText.setText("");
                break;
            case R.id.bt_ex:
                shift();
                break;
            case R.id.bt_sin:
                editText.setText(" "+input + ((Button)view).getText());
                break;
            case R.id.bt_eq://运算结果  =
                getResult();//调用处理结果集的方法
                break;
        }
    }
    //正变负
    private void shift(){
        String input = editText.getText().toString();//获取文本框的内容
        if(input==null||input.equals("")) {
            input = "-";
            editText.setText(input);
            return;
        }
        //else if(input != null || !input.equals("")) //如果获取到的内容不空
        String s1 = input;
        Pattern p = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        Matcher m = p.matcher(s1);
        if(m.matches()){
            double d1 = Double.parseDouble(s1);
            double d=0-d1;
            editText.setText(""+d);
            stack.push(d);
            return;
        }
        else {
            editText.setText(input);
        }
    }
    //运算符的合法性
    private void check(){
        String input = editText.getText().toString();//获取文本框的内容
        if(input.length()>=7) {
            String s1 = input.substring(input.length() - 7);
            if (s1.contains("+") && (s1.contains("+") || s1.contains("-") || s1.contains("*") || s1.contains("÷"))) {
                editText.setText(input.substring(0, input.length() - 6) + " " + input.charAt(input.length() - 2) + " ");//新的运算符会替换旧的运算符
                Toast.makeText(MainActivity.this, "运算符非法", Toast.LENGTH_LONG).show();

            }
//            if (s1.contains("+") && (input.contains("+") || input.contains("-") || input.contains("*") || input.contains("÷"))) {
//                //   editText.setText(input.substring(0, input.length() - 6) + " " + input.charAt(input.length() - 2) + " ");//新的运算符会替换旧的运算符
//                Toast.makeText(MainActivity.this, "运算符非法", Toast.LENGTH_LONG).show();
//
//            }
        }
        else
            editText.setText(input);
    }
    //运算结果的方法
    private void getResult(){
        String exp = editText.getText().toString();//获取文本框的内容
        if(exp==null||exp.equals("")){
            return;
        }
        if(!exp.contains(" ")){
            return;
        }
        if(clear_flag){
            clear_flag = false;
            return;
        }
        clear_flag = true;//显示结果时需要清空
        double result = 0;
        if(exp.contains("sin")) {
            String s1 = exp.substring(exp.indexOf("n")+1);
            Pattern p = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
            Matcher m = p.matcher(s1);
            if (m.matches()) {
                double d1 = Double.parseDouble(s1);
                result = Math.sin(d1);
                editText.setText(result + "");
                stack.push(result);
                return;
            } else {
                editText.setText("");
                Toast.makeText(MainActivity.this, "sin对象非法", Toast.LENGTH_LONG).show();
                return;
            }
        }
        //进行截取(start,end)
        //运算符前的数字

        String s1 = exp.substring(0,exp.indexOf(" "));
        //运算符
        String op = exp.substring(exp.indexOf(" ")+1,exp.indexOf(" ")+2);
        //运算符后的数字
        String s2 = exp.substring(exp.indexOf(" ")+3);

        if(s1.contains(".")||s2.contains(".")) {//如果包含小数点的运算
            double d1 = Double.parseDouble(s1);//则数字都是double类型
            double d2 = Double.parseDouble(s2);
            if (op.equals("+")) {//如果是 +
                result = d1 + d2;
            } else if (op.equals("-")) {
                result = d1 - d2;
            } else if (op.equals("*")) {
                result = d1 * d2;
            } else if (op.equals("÷")) {
                if (d2 == 0) { //如果被除数是0
                    result = 0; //则结果是0
                    Toast.makeText(MainActivity.this, "除数非法", Toast.LENGTH_LONG).show();
                }
                else {//否则执行正常是除法运算
                    result = d1 / d2;
                }

            }
            editText.setText(result + "");
            stack.push(result);
            return;
            }

        else if (!s1.contains(".") && !s2.contains(".")) {//如果是整数类型
            int d1=Integer.parseInt(s1);
            int d2=Integer.parseInt(s2);
            if (op.equals("+")) {//如果是 +
                result = d1 + d2;
            } else if (op.equals("-")) {
                result = d1 - d2;
            } else if (op.equals("*")) {
                result = d1 * d2;
            } else if (op.equals("÷")) {
                if (d2 == 0) {
                    //如果被除数是0
                    result = 0; //则结果是0
                    Toast.makeText(MainActivity.this, "除数非法", Toast.LENGTH_LONG).show();
                    editText.setText("");
                }
                else {//否则执行正常是除法运算
                    double db1=(double)d1;
                    double db2=(double)d2;
                    result = db1/db2;
                    return;
                }

            }
            editText.setText(result + "");
            stack.push(result);
            return;
        }

        if(!s1.equals("") && (s2.equals("")||s2.equals(" "))){//如果是只输入运算符前的数
            editText.setText(exp);//直接返回当前文本框的内容
            return;
            }

        else if(s1.equals("") && !s2.equals("")){//如果是只输入运算符后面的数
            double d2 = Double.parseDouble(s2);
            double d1=0;
            if(!stack.empty()) {
                d1= (double) stack.peek();
            }            //运算符前没有输入数字
            if (op.equals("+")) {
                result = d1 + d2;
            } else if (op.equals("-")) {
                result = d1 - d2;
            } else if (op.equals("*")) {
                result = d1 * d2;
            } else if (op.equals("÷")) {
                if (d2 == 0) { //如果除数是0
                    result = 0; //则结果是0
                    Toast.makeText(MainActivity.this, "除数非法", Toast.LENGTH_LONG).show();
                }
                else {//否则执行正常是除法运算
                    result = d1 / d2;
                }

            }
            editText.setText(result + "");
            stack.push(result);
            return;

        }
            editText.setText("");
            return;
}
}
