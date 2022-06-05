package com.mtcn.androidroomkotlin.custom.pdfgenerator;


import com.mtcn.androidroomkotlin.custom.pdfgenerator.model.FailureResponse;
import com.mtcn.androidroomkotlin.custom.pdfgenerator.model.SuccessResponse;

interface PdfGeneratorContract {
    void onSuccess(SuccessResponse response);

    void showLog(String log);

    void onFailure(FailureResponse failureResponse);
}

public abstract class PdfGeneratorListener implements PdfGeneratorContract {
    @Override
    public void showLog(String log) {

    }

    @Override
    public void onSuccess(SuccessResponse response) {

    }

    @Override
    public void onFailure(FailureResponse failureResponse) {

    }
}
