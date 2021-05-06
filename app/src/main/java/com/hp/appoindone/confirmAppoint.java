package com.hp.appoindone;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.WriterException;
import com.hp.appoindone.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class confirmAppoint extends AppCompatActivity {

    int tokenNo;
    Random random = new Random();

    TextView nameTxt,appointIdTxt,ageTxt,emailTxt,contactTxt,doctorNameTxt,specialistTxt,hospitalTxt,addressTxt,hospitalContactTxt
            ,appointDateTxt,appointTimeTxt,amtTxt,descTxt,genderTxt,paymentIdTxt,tokenTxt;

    FloatingActionButton fab;

    Paragraph app_name,nameH,patientN,appointId,appointIdVal,ageH,ageVal,emailH,emailVal,patientCnH,patientCnVal,genderH,genderVal
            ,appointDateH,appointDateVal,appointTimeH,appointTimeVal,bookingAmtH,bookingAmtVal,paymentIdH,paymentIdVal,doctorNH
            ,doctorNVal,specialistH,specialistVal,hospitalNH,hospitalNVal,hospitalCnH,hospitalCnVal,addressH,addressVal,descH,descVal,contactMailId,tokenVal;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_appoint);

        initView();
//        getIntentValues();
//        setValues();

        tokenNo = random.nextInt(200);
        tokenTxt.setText("Token No. : "+String.valueOf(tokenNo));

        Toast.makeText(this,nameTxt.getText().toString(),Toast.LENGTH_LONG).show();

        fab = (FloatingActionButton)findViewById(R.id.fab_ca_pdf);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Toast.makeText(confirmAppoint.this,"Hello Toast",Toast.LENGTH_LONG).show();
                try {
                    createPdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initView() {
        nameTxt = (TextView)findViewById(R.id.tv_ca_patientName);
        appointIdTxt = (TextView)findViewById(R.id.tv_ca_appointmentID);
        ageTxt = (TextView)findViewById(R.id.tv_ca_patientAge);
        genderTxt = (TextView)findViewById(R.id.tv_ca_gender);
        tokenTxt = (TextView)findViewById(R.id.tv_ca_token);
        emailTxt = (TextView)findViewById(R.id.tv_ca_emailId);
        contactTxt = (TextView)findViewById(R.id.tv_ca_patient_ContantN);
        doctorNameTxt = (TextView)findViewById(R.id.tv_ca_consultingDr);
        specialistTxt = (TextView)findViewById(R.id.tv_ca_specialist);
        hospitalTxt = (TextView)findViewById(R.id.tv_ca_hospital);
        addressTxt = (TextView)findViewById(R.id.tv_ca_hospitalAddress);
        hospitalContactTxt = (TextView)findViewById(R.id.tv_ca_hospitalNo);
        appointDateTxt = (TextView)findViewById(R.id.tv_ca_appointDate);
        appointTimeTxt = (TextView)findViewById(R.id.tv_ca_appointTime);
        paymentIdTxt = (TextView)findViewById(R.id.tv_va_paymentID);
        amtTxt = (TextView)findViewById(R.id.tv_ca_bookingAmt);
        descTxt = (TextView)findViewById(R.id.tv_ca_desc);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createPdf() throws IOException, DocumentException
    {
        String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());

        // Location of PDF
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File folder = new File(pdfPath,"Appoindone");
        if(!folder.exists())
        {
            folder.mkdir();
            Log.i("folder","Folder Created");
        }
        File file = new File(folder+"/"+currentDateTime+".pdf");
        OutputStream outputStream = new FileOutputStream(file);

        //Document Instance created
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,outputStream);

        //Values for QR Code
        String patientNV = nameTxt.getText().toString().substring(nameTxt.getText().toString().indexOf(":")+2);
        String appointIDV = appointIdTxt.getText().toString().substring(appointIdTxt.getText().toString().indexOf(":")+2);
        String consultingDrV = doctorNameTxt.getText().toString().substring(doctorNameTxt.getText().toString().indexOf(":")+2);
        String appointDV = appointDateTxt.getText().toString().substring(appointDateTxt.getText().toString().indexOf(":")+2);
        String appointTV = appointTimeTxt.getText().toString().substring(appointTimeTxt.getText().toString().indexOf(":")+2);


        document.open();


        //Instance Created
        PdfContentByte pdfContentByte = writer.getDirectContent();
        ColumnText ct = new ColumnText(pdfContentByte);
        ColumnText ct1 = new ColumnText(pdfContentByte);
        Font appFont = new Font(Font.FontFamily.TIMES_ROMAN);
        Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN);
        Font textFont = new Font(Font.FontFamily.TIMES_ROMAN);
        Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN);

        //Fonts are set
        appFont.setStyle("bold");
        appFont.setSize(20);
        headingFont.setStyle("bold");
        headingFont.setSize(18);
        textFont.setSize(16);
        footerFont.setSize(14);
        footerFont.setColor(30,30,30);


        //Horizontal Line at Top and Bottom
        pdfContentByte.setLineWidth(2.0f);
        pdfContentByte.setGrayStroke(0.75f);

        pdfContentByte.moveTo(20f,740f);
        pdfContentByte.lineTo(580f,740f);           // Header Seperator Line

        pdfContentByte.moveTo(20f,50f);
        pdfContentByte.lineTo(580f,50f);            //Footer Seperator Line

        pdfContentByte.stroke();


        // AD Logo created and positioned
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.adlogopdf);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
        Image myImg = Image.getInstance(stream.toByteArray());
        myImg.setAbsolutePosition(470f,755f);
        myImg.scaleToFit(100f,100f);
        document.add(myImg);

        // QR Code created and positioned
        int width1 = 80;
        int height1 = 90;
        int smallerDimen = width1<height1?width1:height1;
        smallerDimen=smallerDimen*3/4;
        Bitmap bitmap1;
        QRGEncoder qrgEncoder = new QRGEncoder(patientNV+"\n"+appointIDV+"\n"+consultingDrV+"\n"+appointDV+"\n"+appointTV+"\n"+String.valueOf(tokenNo),null, QRGContents.Type.TEXT, (int) smallerDimen);
        try {
            bitmap1 = qrgEncoder.encodeAsBitmap();
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG,100,stream1);
            Image qrImg = Image.getInstance(stream1.toByteArray());
            qrImg.setAbsolutePosition(340f,430f);
            qrImg.scaleToFit(260f,260f);
            document.add(qrImg);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // Token Value set in document
        tokenVal = new Paragraph(tokenTxt.getText().toString());
        tokenVal.setFont(textFont);
        ct.setSimpleColumn(420f,420f,550f,480f);
        ct.addElement(tokenVal);
        ct.go();


        // Appoindone Title positioned in document
        app_name = new Paragraph("APPOINDONE");
        app_name.setFont(appFont);
        ct.setSimpleColumn(30f,760f,200f,790f);
        ct.addElement(app_name);
        ct.go();


        nameH = new Paragraph("Name");
        nameH.setFont(headingFont);
        ct.setSimpleColumn(30f,670f,120f,720f);
        ct.addElement(nameH);
        ct.go();

        patientN = new Paragraph(nameTxt.getText().toString().substring(nameTxt.getText().toString().indexOf(":")+2));
        patientN.setFont(textFont);
        ct.setSimpleColumn(30f,640f,200f,690f);
        ct.addElement(patientN);
        ct.go();

        appointId = new Paragraph("Appointment ID");
        appointId.setFont(headingFont);
        ct.setSimpleColumn(200f,670f,350f,720f);
        ct.addElement(appointId);
        ct.go();

        appointIdVal = new Paragraph(appointIdTxt.getText().toString().substring(appointIdTxt.getText().toString().indexOf(":")+2));
        appointIdVal.setFont(textFont);
        ct.setSimpleColumn(200f,640f,450f,690f);
        ct.addElement(appointIdVal);
        ct.go();

        ageH = new Paragraph("Age");
        ageH.setFont(headingFont);
        ct.setSimpleColumn(30f,590f,200f,640f);
        ct.addElement(ageH);
        ct.go();

        ageVal = new Paragraph(ageTxt.getText().toString().substring(ageTxt.getText().toString().indexOf(":")+2));
        ageVal.setFont(textFont);
        ct.setSimpleColumn(30f,560f,200f,610f);
        ct.addElement(ageVal);
        ct.go();


        emailH = new Paragraph("Email ID");
        emailH.setFont(headingFont);
        ct.setSimpleColumn(200f,590f,350f,640f);
        ct.addElement(emailH);
        ct.go();

        emailVal = new Paragraph(emailTxt.getText().toString().substring(emailTxt.getText().toString().indexOf(":")+2));
        emailVal.setFont(textFont);
        ct.setSimpleColumn(200f,560f,480f,610f);
        ct.addElement(emailVal);
        ct.go();

        patientCnH = new Paragraph("Patient Contact No.");
        patientCnH.setFont(headingFont);
        ct.setSimpleColumn(30f,510f,200f,560f);
        ct.addElement(patientCnH);
        ct.go();

        patientCnVal = new Paragraph(contactTxt.getText().toString().substring(contactTxt.getText().toString().indexOf(":")+2));
        patientCnVal.setFont(textFont);
        ct.setSimpleColumn(30f,480f,200f,530f);
        ct.addElement(patientCnVal);
        ct.go();

        genderH = new Paragraph("Gender");
        genderH.setFont(headingFont);
        ct.setSimpleColumn(200f,510f,350f,560f);
        ct.addElement(genderH);
        ct.go();

        genderVal = new Paragraph(genderTxt.getText().toString().substring(genderTxt.getText().toString().indexOf(":")+2));
        genderVal.setFont(textFont);
        ct.setSimpleColumn(200f,480f,480f,530f);
        ct.addElement(genderVal);
        ct.go();

        appointDateH = new Paragraph("Appointment Date");
        appointDateH.setFont(headingFont);
        ct.setSimpleColumn(30f,430f,200f,480f);
        ct.addElement(appointDateH);
        ct.go();

        appointDateVal = new Paragraph(appointDateTxt.getText().toString().substring(appointDateTxt.getText().toString().indexOf(":")+2));
        appointDateVal.setFont(textFont);
        ct.setSimpleColumn(30f,400f,200f,450f);
        ct.addElement(appointDateVal);
        ct.go();

        appointTimeH = new Paragraph("Appointment Time");
        appointTimeH.setFont(headingFont);
        ct.setSimpleColumn(200f,430f,350f,480f);
        ct.addElement(appointTimeH);
        ct.go();

        appointTimeVal = new Paragraph(appointTimeTxt.getText().toString().substring(appointTimeTxt.getText().toString().indexOf(":")+2));
        appointTimeVal.setFont(textFont);
        ct.setSimpleColumn(200f,400f,480f,450f);
        ct.addElement(appointTimeVal);
        ct.go();

        bookingAmtH = new Paragraph("Booking Amount");
        bookingAmtH.setFont(headingFont);
        ct.setSimpleColumn(30f,350f,200f,400f);
        ct.addElement(bookingAmtH);
        ct.go();

        bookingAmtVal = new Paragraph(amtTxt.getText().toString().substring(amtTxt.getText().toString().indexOf(":")+2));
        bookingAmtVal.setFont(textFont);
        ct.setSimpleColumn(30f,320f,200f,370f);
        ct.addElement(bookingAmtVal);
        ct.go();

        paymentIdH = new Paragraph("Transaction ID");
        paymentIdH.setFont(headingFont);
        ct.setSimpleColumn(200f,350f,350f,400f);
        ct.addElement(paymentIdH);
        ct.go();

        paymentIdVal = new Paragraph(paymentIdTxt.getText().toString().substring(paymentIdTxt.getText().toString().indexOf(":")+2));
        paymentIdVal.setFont(textFont);
        ct.setSimpleColumn(200f,320f,480f,370f);
        ct.addElement(paymentIdVal);
        ct.go();

        doctorNH = new Paragraph("Consulting Doctor");
        doctorNH.setFont(headingFont);
        ct.setSimpleColumn(30f,270f,200f,320f);
        ct.addElement(doctorNH);
        ct.go();

        doctorNVal = new Paragraph(doctorNameTxt.getText().toString().substring(doctorNameTxt.getText().toString().indexOf(":")+2));
        doctorNVal.setFont(textFont);
        ct.setSimpleColumn(200f,267f,450f,317f);
        ct.addElement(doctorNVal);
        ct.go();

        specialistH = new Paragraph("Specialist");
        specialistH.setFont(headingFont);
        ct.setSimpleColumn(395f,270f,470f,320f);
        ct.addElement(specialistH);
        ct.go();

        specialistVal = new Paragraph(specialistTxt.getText().toString().substring(specialistTxt.getText().toString().indexOf(":")+2));
        specialistVal.setFont(textFont);
        ct.setSimpleColumn(490f,267f,590f,317f);
        ct.addElement(specialistVal);
        ct.go();

        hospitalNH = new Paragraph("Hospital");
        hospitalNH.setFont(headingFont);
        ct.setSimpleColumn(30f,220f,200f,270f);
        ct.addElement(hospitalNH);
        ct.go();

        hospitalNVal = new Paragraph(hospitalTxt.getText().toString().substring(hospitalTxt.getText().toString().indexOf(":")+2));
        hospitalNVal.setFont(textFont);
        ct.setSimpleColumn(200f,217f,450f,263f);
        ct.addElement(hospitalNVal);
        ct.go();

        hospitalCnH = new Paragraph("Hospital"+"\n"+"Contact No.");
        hospitalCnH.setFont(headingFont);
        ct.setSimpleColumn(395f,180f,500f,270f);
        ct.addElement(hospitalCnH);
        ct.go();

        hospitalCnVal = new Paragraph(hospitalContactTxt.getText().toString().substring(hospitalContactTxt.getText().toString().indexOf(":")+2));
        hospitalCnVal.setFont(textFont);
        ct.setSimpleColumn(490f,217f,590f,267f);
        ct.addElement(hospitalCnVal);
        ct.go();

        addressH = new Paragraph("Hospital Address");
        addressH.setFont(headingFont);
        ct.setSimpleColumn(30f,100f,200f,190f);
        ct.addElement(addressH);
        ct.go();

        addressVal = new Paragraph(addressTxt.getText().toString().substring(addressTxt.getText().toString().indexOf(":")+2));
        addressVal.setFont(textFont);
        ct.setSimpleColumn(200f,97f,620f,187f);
        ct.addElement(addressVal);
        ct.go();

        descH = new Paragraph("Sickness Description");
        descH.setFont(headingFont);
        ct.setSimpleColumn(30f,40f,200f,130f);
        ct.addElement(descH);
        ct.go();

        descVal = new Paragraph(descTxt.getText().toString().substring(descTxt.getText().toString().indexOf(":")+2));
        descVal.setFont(textFont);
        ct.setSimpleColumn(200f,37f,620f,127f);
        ct.addElement(descVal);
        ct.go();


        contactMailId = new Paragraph("codeblack.mah@gmail.com");
        contactMailId.setFont(footerFont);
        ct.setSimpleColumn(220f,10f,500f,50f);
        ct.addElement(contactMailId);
        ct.go();



        document.close();

    }

}