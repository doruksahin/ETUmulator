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

import com.kasirgalabs.arm.ProcessorBaseVisitor;
import com.kasirgalabs.arm.ProcessorParser;

public class NumberVisitor extends ProcessorBaseVisitor<Integer> {
    @Override
    public Integer visitImm16(ProcessorParser.Imm16Context ctx) {
        return visitNumber(ctx.number());
    }

    @Override
    public Integer visitImm12(ProcessorParser.Imm12Context ctx) {
        return visitNumber(ctx.number());
    }

    @Override
    public Integer visitImm8m(ProcessorParser.Imm8mContext ctx) {
        return visitNumber(ctx.number());
    }

    @Override
    public Integer visitSh(ProcessorParser.ShContext ctx) {
        return visitNumber(ctx.number());
    }

    @Override
    public Integer visitNumber(ProcessorParser.NumberContext ctx) {
        if(ctx.DECIMAL() != null) {
            return (int) Long.parseLong(ctx.DECIMAL().getText());
        }
        return (int) Long.parseLong(ctx.HEX().getText().substring(2), 16);
    }

    @Override
    public Integer visitOffset(ProcessorParser.OffsetContext ctx) {
        return visitNumber(ctx.number());
    }
}
