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
package com.kasirgalabs.etumulator.processor;

import com.kasirgalabs.etumulator.register.RegisterFile;
import org.junit.Test;

public class ProcessorTest {

    /**
     * Test of run method, of class Loader.
     */
    @Test
    public void testRun() {
        RegisterFile registerFile = new RegisterFile();
        Processor processor = new Processor(registerFile);

        char[] code = ("nop\n"
                + "nop\n"
                + "nop\n").toCharArray();
        processor.run(code);
    }
}