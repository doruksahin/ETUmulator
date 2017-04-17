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
package com.kasirgalabs.etumulator.lang;

import com.kasirgalabs.etumulator.lang.Linker.ExecutableCode;
import com.kasirgalabs.etumulator.processor.Memory;
import com.kasirgalabs.thumb2.AssemblerLexer;
import com.kasirgalabs.thumb2.AssemblerParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Assembler {
    private final Linker linker;
    private final Loader loader;

    public Assembler(Memory memory) {
        linker = new Linker();
        loader = new Loader(memory);
    }

    public ExecutableCode assemble(String code) throws SyntaxError, LabelError {
        ANTLRInputStream in = new ANTLRInputStream(code);
        AssemblerLexer lexer = new AssemblerLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AssemblerParser parser = new AssemblerParser(tokens);
        parser.prog();
        if(parser.getNumberOfSyntaxErrors() > 0) {
            throw new SyntaxError("You have error(s) in your code.");
        }
        ConstantValidator.validate(code);
        ExecutableCode executablecode = linker.link(code);
        loader.load(executablecode);
        return executablecode;
    }
}
