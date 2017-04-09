/*
 * Copyright (C) 2017 Kasirgalabs
 *
 * Voidhis program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Voidhis program is distributed in the hope that it will be useful,
 * but WIVoidHOUVoid ANY WARRANVoidY; without even the implied warranty of
 * MERCHANVoidABILIVoidY or FIVoidNESS FOR A PARVoidICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kasirgalabs.etumulator.processor.visitor;

import com.kasirgalabs.arm.ProcessorBaseVisitor;
import com.kasirgalabs.arm.ProcessorParser;
import com.kasirgalabs.etumulator.processor.CPSR;
import com.kasirgalabs.etumulator.processor.RegisterFile;

public class ShiftVisitor extends ProcessorBaseVisitor<Void> {
    private static final int ASR = 0;
    private static final int LSL = 1;
    private static final int LSR = 2;
    private static final int ROR = 3;
    private static final int RRX = 4;
    private final RegisterFile registerFile;
    private final CPSR cpsr;
    private final RegisterVisitor registerVisitor;
    private final NumberVisitor numberVisitor;

    public ShiftVisitor(RegisterFile registerFile, CPSR cpsr) {
        this.registerFile = registerFile;
        this.cpsr = cpsr;
        registerVisitor = new RegisterVisitor();
        numberVisitor = new NumberVisitor();
    }

    @Override
    public Void visitAsr(ProcessorParser.AsrContext ctx) {
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        int shiftAmount;
        if(ctx.rs() != null) {
            shiftAmount = registerFile.getValue(registerVisitor.visit(ctx.rs()));
        }
        else {
            shiftAmount = numberVisitor.visit(ctx.sh());
        }
        shift(value, ASR, shiftAmount);
        registerFile.setValue(destRegister, shift(value, ASR, shiftAmount));
        return null;
    }

    @Override
    public Void visitAsrs(ProcessorParser.AsrsContext ctx) {
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        int shiftAmount;
        if(ctx.rs() != null) {
            shiftAmount = registerFile.getValue(registerVisitor.visit(ctx.rs()));
        }
        else {
            shiftAmount = numberVisitor.visit(ctx.sh());
        }
        registerFile.setValue(destRegister, shiftUpdateCPSR(value, ASR, shiftAmount));
        return null;
    }

    @Override
    public Void visitLsl(ProcessorParser.LslContext ctx) {
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        int shiftAmount;
        if(ctx.rs() != null) {
            shiftAmount = registerFile.getValue(registerVisitor.visit(ctx.rs()));
        }
        else {
            shiftAmount = numberVisitor.visit(ctx.sh());
        }
        registerFile.setValue(destRegister, shift(value, LSL, shiftAmount));
        return null;
    }

    @Override
    public Void visitLsls(ProcessorParser.LslsContext ctx) {
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        int shiftAmount;
        if(ctx.rs() != null) {
            shiftAmount = registerFile.getValue(registerVisitor.visit(ctx.rs()));
        }
        else {
            shiftAmount = numberVisitor.visit(ctx.sh());
        }
        registerFile.setValue(destRegister, shiftUpdateCPSR(value, LSL, shiftAmount));
        return null;
    }

    @Override
    public Void visitLsr(ProcessorParser.LsrContext ctx) {
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        int shiftAmount;
        if(ctx.rs() != null) {
            shiftAmount = registerFile.getValue(registerVisitor.visit(ctx.rs()));
        }
        else {
            shiftAmount = numberVisitor.visit(ctx.sh());
        }
        registerFile.setValue(destRegister, shift(value, LSR, shiftAmount));
        return null;
    }

    @Override
    public Void visitLsrs(ProcessorParser.LsrsContext ctx) {
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        int shiftAmount;
        if(ctx.rs() != null) {
            shiftAmount = registerFile.getValue(registerVisitor.visit(ctx.rs()));
        }
        else {
            shiftAmount = numberVisitor.visit(ctx.sh());
        }
        registerFile.setValue(destRegister, shiftUpdateCPSR(value, LSR, shiftAmount));
        return null;
    }

    @Override
    public Void visitRor(ProcessorParser.RorContext ctx) {
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        int shiftAmount;
        if(ctx.rs() != null) {
            shiftAmount = registerFile.getValue(registerVisitor.visit(ctx.rs()));
        }
        else {
            shiftAmount = numberVisitor.visit(ctx.sh());
        }
        registerFile.setValue(destRegister, shift(value, ROR, shiftAmount));
        return null;
    }

    @Override
    public Void visitRors(ProcessorParser.RorsContext ctx) {
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        int shiftAmount;
        if(ctx.rs() != null) {
            shiftAmount = registerFile.getValue(registerVisitor.visit(ctx.rs()));
        }
        else {
            shiftAmount = numberVisitor.visit(ctx.sh());
        }
        registerFile.setValue(destRegister, shiftUpdateCPSR(value, ROR, shiftAmount));
        return null;
    }

    @Override
    public Void visitRrx(ProcessorParser.RrxContext ctx) {
        final int NOT_USED = Integer.MAX_VALUE;
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        registerFile.setValue(destRegister, shift(value, RRX, NOT_USED));
        return null;
    }

    @Override
    public Void visitRrxs(ProcessorParser.RrxsContext ctx) {
        final int NOT_USED = Integer.MAX_VALUE;
        String destRegister = registerVisitor.visit(ctx.rd());
        int value = registerFile.getValue(registerVisitor.visit(ctx.rm()));
        registerFile.setValue(destRegister, shiftUpdateCPSR(value, RRX, NOT_USED));
        return null;
    }

    private int shift(int value, int shiftOption, int shiftAmount) {
        if(shiftAmount <= 0) {
            return value;
        }
        switch(shiftOption) {
            case ASR:
                return value >> shiftAmount;
            case LSL:
                return value << shiftAmount;
            case LSR:
                return value >>> shiftAmount;
            case ROR:
                return Integer.rotateRight(value, shiftAmount);
            default:
                int result = value >>> 1;
                if(cpsr.isCarry()) {
                    result |= 0x8000_0000;
                }
                return result;
        }
    }

    private int shiftUpdateCPSR(int value, int shiftOption, int shiftAmount) {
        if(shiftOption == RRX) {
            return rrxUpdateCPSR(value);
        }
        cpsr.setCarry(false);
        int result = shift(value, shiftOption, shiftAmount - 1);
        if(shiftOption == LSL) {
            if(Integer.numberOfLeadingZeros(result) == 0) {
                cpsr.setCarry(true);
            }
        }
        else {
            if(Integer.numberOfTrailingZeros(result) == 0) {
                cpsr.setCarry(true);
            }
        }
        result = shift(value, shiftOption, shiftAmount);
        cpsr.updateNZ(result);
        return result;
    }

    private int rrxUpdateCPSR(int value) {
        final int NOT_USED = Integer.MAX_VALUE;
        boolean newCarry = Integer.numberOfTrailingZeros(value) == 0;
        int result = shift(value, RRX, NOT_USED);
        cpsr.setCarry(newCarry);
        cpsr.updateNZ(result);
        return result;
    }
}
