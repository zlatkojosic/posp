package com.posp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * A Mapper which creates the formatted PDF out of a receipt
 * represented as a Collection of Strings
 *
 * Author: Florian Stallmach
 */
public class ReceiptPdfMapper {

    private String filename;
    private Context context;



    public ReceiptPdfMapper(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    /**
     * Puts the given Collection into a temporary PDF file
     *
     * @param receipt the data which shall be formatted
     * @return the temporary file
     */
    public File mapReceiptToPdf(Collection<String> receipt) {
        View content = mapReceiptToView(receipt);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getLayoutParams().width, content.getLayoutParams().height, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        measureContent(page, content);  //measure the specs and draw content to page

        document.finishPage(page);
        File file = new File(context.getCacheDir(), filename + ".pdf"); //change dir to externalStorage to view file
        if (file.exists()) {
            file.delete();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            document.writeTo(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.close();
        return file;
    }

    /**
     * draws the content onto the canvas of a page
     * measures it with the given layout params of the content
     *
     * @param page    a PdfDocument page
     * @param content a View with content
     */
    private void measureContent(PdfDocument.Page page, View content) {

        int measureWidth = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getWidth(), View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getHeight(), View.MeasureSpec.EXACTLY);

        content.measure(measureWidth, measuredHeight);
        content.layout(0, 0, page.getCanvas().getWidth(), page.getCanvas().getHeight());
        content.draw(page.getCanvas());
    }

    /**
     * Puts the data of the receipt into an Android View
     *
     * @param receipt data to display
     * @return the View filled with data
     */
    private View mapReceiptToView(Collection<String> receipt) {

        LinearLayout layout = new LinearLayout(context);

        String result = makeResult(receipt);

        TextView text = new TextView(context);
        text.setTextSize(12);  //Change if you want to (depends on the length of your single lines)
        text.setTypeface(Typeface.MONOSPACE); //Change if you do not need all characters to be same size
        text.setTextColor(Color.BLACK);
        text.setText(result);
        text.measure(0, 0);  //dummy call to get the height and width of the textView

        int pagewidth = text.getMeasuredWidth();
        int pageheight = text.getMeasuredHeight();

        layout.setLayoutParams(new LinearLayout.LayoutParams(pagewidth + 20, pageheight *3));  //the higher the page gets, the smaller the characters are
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(text);
        layout.setBackgroundColor(Color.WHITE);
        return layout;
    }


    /**
     * Appends all lines of the Collection on a single String
     *
     * @param receipt Collection with String data
     * @return the string with the data
     */
    private String makeResult(Collection<String> receipt) {
        StringBuilder builder = new StringBuilder();
        for (String line : receipt) {
            builder.append(line + "\n");
        }
        return builder.toString();
    }

}
