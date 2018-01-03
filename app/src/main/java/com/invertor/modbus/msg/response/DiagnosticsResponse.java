package com.invertor.modbus.msg.response;

import com.invertor.modbus.exception.ModbusNumberException;
import com.invertor.modbus.msg.base.ModbusResponse;
import com.invertor.modbus.net.stream.base.ModbusInputStream;
import com.invertor.modbus.net.stream.base.ModbusOutputStream;
import com.invertor.modbus.utils.DiagnosticsSubFunctionCode;
import com.invertor.modbus.utils.ModbusFunctionCode;

import java.io.IOException;

/*
 * Copyright (C) 2016 "Invertor" Factory", JSC
 * [http://www.sbp-invertor.ru]
 *
 * This file is part of JLibModbus.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors: Vladislav Y. Kochedykov, software engineer.
 * email: vladislav.kochedykov@gmail.com
 */
public class DiagnosticsResponse extends ModbusResponse {

    private DiagnosticsSubFunctionCode subFunctionCode;
    private int subFunctionData = 0;

    public DiagnosticsResponse(int serverAddress) throws ModbusNumberException {
        super(serverAddress);
    }

    @Override
    protected void readResponse(ModbusInputStream fifo) throws IOException, ModbusNumberException {
        setSubFunctionCode(DiagnosticsSubFunctionCode.get(fifo.readShortBE()));
        setSubFunctionData(fifo.readShortBE());
    }

    @Override
    protected void writeResponse(ModbusOutputStream fifo) throws IOException {
        fifo.writeShortBE(getSubFunctionCode().toInt());
        fifo.writeShortBE(getSubFunctionData());
    }

    @Override
    protected int responseSize() {
        return 4;
    }

    @Override
    public int getFunction() {
        return ModbusFunctionCode.DIAGNOSTICS.toInt();
    }

    public DiagnosticsSubFunctionCode getSubFunctionCode() {
        return subFunctionCode;
    }

    public void setSubFunctionCode(DiagnosticsSubFunctionCode subFunctionCode) {
        this.subFunctionCode = subFunctionCode;
    }

    public int getSubFunctionData() {
        return subFunctionData;
    }

    public void setSubFunctionData(int subFunctionData) {
        this.subFunctionData = subFunctionData;
    }
}
