package com.prodapt.billingsystem.email;

import lombok.Data;

import java.io.File;

@Data
public class InlineContent {
    private String cid;
    private File file;

    public InlineContent(String cid, File file)
    {
        this.cid = cid;
        this.file = file;
    }
}
