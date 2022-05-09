package com.kenlib.capture;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by ken on 15/12/24.
 */
public abstract class IOUtil {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
