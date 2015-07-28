package org.jllvm;

/*
 * Copyright (C) 2013 Trillian Mobile AB
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/gpl-2.0.html>.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.jllvm.bindings.Target;

/**
 * native library loader
 */
public class NativeLibrary {
    private static final String os;
    private static final String arch;
    private static final List<String> libNames;

    static {
        String osProp = System.getProperty("os.name").toLowerCase();
        String archProp = System.getProperty("os.arch").toLowerCase();
        String ext = null;
        if (osProp.startsWith("mac") || osProp.startsWith("darwin")) {
            os = "macosx";
            ext = "dylib";
        } else if (osProp.startsWith("linux")) {
            os = "linux";
            ext = "so";
        } else if (osProp.startsWith("windows")) {
            os = "windows";
            ext = "dll";
        } else {
            throw new Error("Unsupported OS: " + System.getProperty("os.name"));
        }
        if (archProp.matches("amd64|x86[-_]64")) {
            arch = "x86_64";
        } else if (archProp.matches("i386|x86")) {
            arch = "x86";
        } else {
            throw new Error("Unsupported arch: " + System.getProperty("os.arch"));
        }
        
        if(ext.equals("dll")) {
            libNames = Arrays.asList(new String[]{
                    "jllvm." + ext
            });
        } else {
            libNames = Arrays.asList(new String[]{
                    "libLLVM-3.4." + ext,
                    "libLTO." + ext,
                    "libjllvm." + ext
            });
        }
    }
    
    public static synchronized void load(String libName) {
        Runtime.getRuntime().load(NativeLibrary.class.getResource("bindings/" + os + "/" + arch + "/" + libName).getPath());
//    	String path = System.getProperty("user.dir");
//        Runtime.getRuntime().load(path + "/lib/" + libName);
    }
    
    public static synchronized void load() {
    	for (String libname : libNames) {
    		load(libname);
		}

    	Target.LLVMInitializeAllTargets();
    	Target.LLVMInitializeAllTargetInfos();
    	Target.LLVMInitializeAllTargetMCs();
    	Target.LLVMInitializeAllAsmPrinters();
    	Target.LLVMInitializeAllAsmParsers();
    }
    
}
