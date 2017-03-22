/*
 * Copyright (C) 2017 Kasirgalabs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kasirgalabs.etumulator.processor.visitor;

import com.kasirgalabs.arm.ArmBaseVisitor;
import com.kasirgalabs.arm.ArmParser;
import com.kasirgalabs.etumulator.linker.Symbol;
import com.kasirgalabs.etumulator.processor.CPSR;
import java.util.Set;

public class BranchVisitor extends ArmBaseVisitor<Integer> {
    private CPSR cpsr;
    private Set<Symbol> symbols;

    public BranchVisitor(CPSR cpsr) {
        this.cpsr = cpsr;
    }

    public void setSymbols(Set<Symbol> symbols) {
        this.symbols = symbols;
    }

    @Override
    public Integer visitB(ArmParser.BContext ctx) {
        return addressOfSymbol(ctx.LABEL().getText());
    }

    @Override
    public Integer visitBeq(ArmParser.BeqContext ctx) {
        if(cpsr.isZero()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBne(ArmParser.BneContext ctx) {
        if(!cpsr.isZero()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBcs(ArmParser.BcsContext ctx) {
        if(cpsr.isCarry()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBhs(ArmParser.BhsContext ctx) {
        if(cpsr.isCarry()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBcc(ArmParser.BccContext ctx) {
        if(!cpsr.isCarry()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBlo(ArmParser.BloContext ctx) {
        if(!cpsr.isCarry()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBmi(ArmParser.BmiContext ctx) {
        if(cpsr.isNegative()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBpl(ArmParser.BplContext ctx) {
        if(!cpsr.isNegative()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBvs(ArmParser.BvsContext ctx) {
        if(cpsr.isOverflow()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBvc(ArmParser.BvcContext ctx) {
        if(!cpsr.isOverflow()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBhi(ArmParser.BhiContext ctx) {
        if(cpsr.isCarry() && !cpsr.isZero()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBls(ArmParser.BlsContext ctx) {
        if(!cpsr.isCarry() || cpsr.isZero()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBge(ArmParser.BgeContext ctx) {
        if(cpsr.isNegative() == cpsr.isOverflow()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBlt(ArmParser.BltContext ctx) {
        if(cpsr.isNegative() != cpsr.isOverflow()) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBgt(ArmParser.BgtContext ctx) {
        if(!cpsr.isZero() && (cpsr.isNegative() == cpsr.isOverflow())) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBle(ArmParser.BleContext ctx) {
        if(cpsr.isZero() || (cpsr.isNegative() != cpsr.isOverflow())) {
            return addressOfSymbol(ctx.LABEL().getText());
        }
        return null;
    }

    @Override
    public Integer visitBal(ArmParser.BalContext ctx) {
        return addressOfSymbol(ctx.LABEL().getText());
    }

    @Override
    public Integer visitBl(ArmParser.BlContext ctx) {
        return null;
    }

    private Integer addressOfSymbol(String name) {
        for(Symbol symbol : symbols) {
            if(symbol.getName().equals(name)) {
                return symbol.getAddress();
            }
        }
        return null;
    }
}