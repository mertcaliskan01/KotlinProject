package com.mtcn.androidroomkotlin.custom.pdfgenerator;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;


import com.mtcn.androidroomkotlin.custom.pdfgenerator.model.FailureResponse;

import java.util.ArrayList;
import java.util.List;

public class PdfGeneratorUtils {
    public static List<View> getViewListFromID(Activity activity, @IdRes List<Integer> viewIDList) {
        List<View> viewList = new ArrayList<>();
        if (activity != null) {
            for (int viewID : viewIDList)
                viewList.add(activity.findViewById(viewID));
        }
        return viewList;
    }

    public static List<View> getViewListFromLayout(Context context, PdfGeneratorListener pdfGeneratorListener, List<Integer> layoutList) {
        List<View> viewList = new ArrayList<>();

        try {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (int layout : layoutList) {
                View generatedView = inflater.inflate(layout, null);
                viewList.add(generatedView);
            }

        } catch (Exception e) {
            pdfGeneratorListener.onFailure(new FailureResponse(e, "Error is happening in" +
                    " getViewListFromLayout() while creating Java's view object(s) from layout resources"));
        }
        return viewList;
    }
}
