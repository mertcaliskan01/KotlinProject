package com.mtcn.androidroomkotlin.custom.pdfgenerator.model;

import android.graphics.pdf.PdfDocument;
import android.text.TextUtils;

import java.io.File;

public class SuccessResponse {
    PdfDocument pdfDocument;
    File file;
    String path;

    public SuccessResponse(PdfDocument pdfDocument, File file) {
        this.pdfDocument = pdfDocument;
        this.file = file;
        if (file != null && !TextUtils.isEmpty(file.getAbsolutePath()))
            path = file.getAbsolutePath();
    }

    public PdfDocument getPdfDocument() {
        return pdfDocument;
    }

    public void setPdfDocument(PdfDocument pdfDocument) {
        this.pdfDocument = pdfDocument;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPath() {
        if (file != null && !TextUtils.isEmpty(file.getAbsolutePath()))
            return file.getAbsolutePath();
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
