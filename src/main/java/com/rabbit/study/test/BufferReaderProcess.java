package com.rabbit.study.test;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by WuTing on 2017/11/24.
 */
@FunctionalInterface
public interface BufferReaderProcess {
	String process(BufferedReader reader) throws IOException;
}
