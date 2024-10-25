package org.samly.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;

public class Args {
    @Parameter(description = "Input File", converter = FileConverter.class)
    private File file;

    public File getFile() {
        return file;
    }
}
