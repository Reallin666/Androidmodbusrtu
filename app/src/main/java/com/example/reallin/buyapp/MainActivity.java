package com.example.reallin.buyapp;

import android.app.Activity;
import android.os.Bundle;

import com.invertor.modbus.Modbus;
import com.invertor.modbus.ModbusMaster;
import com.invertor.modbus.ModbusMasterFactory;
import com.invertor.modbus.exception.ModbusIOException;
import com.invertor.modbus.exception.ModbusNumberException;
import com.invertor.modbus.exception.ModbusProtocolException;
import com.invertor.modbus.serial.SerialParameters;
import com.invertor.modbus.serial.SerialPort;
import com.invertor.modbus.serial.SerialPortFactoryJSSC;
import com.invertor.modbus.serial.SerialUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    public static ModbusMaster m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 modbus 串口
        initmod();

        // 定时修改寄存器地址测试
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                write();
            }
        }, 1000, 1000);
    }


    protected void initmod() {//这个方法就是配置modbus。建议写在全局class中。

        SerialParameters sp = new SerialParameters();
        Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
        try {
            sp.setDevice("/dev/ttyMT1");//设置端口
            sp.setBaudRate(SerialPort.BaudRate.BAUD_RATE_9600);//波特率
            sp.setDataBits(8);
            sp.setParity(SerialPort.Parity.NONE);
            sp.setStopBits(1);
            SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
            m = ModbusMasterFactory.createModbusMasterRTU(sp);
            m.connect();

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void read() {//读寄存器
        int[] registerValues = new int[0];

        try {
            registerValues = m.readHoldingRegisters(2, 1101, 1);//三个参数点击去看就知道是什么，这里不再赘述
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }

    }

    static int sRegisterValue = 0;
    protected void write() {//写寄存器

        try {
            sRegisterValue = sRegisterValue + 1;
            m.writeSingleRegister(2, 1101, sRegisterValue);
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {

            e.printStackTrace();
        }

    }

}
