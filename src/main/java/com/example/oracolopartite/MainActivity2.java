package com.example.oracolopartite;


/**
 * @author Dott.Ing.Carlo Maria Conti
 * merc 24 feb 21:00 2021
 */

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {


    Interpreter interpreter, interpreter2;

    Button carica;
    TextView eventi;
    Button over;
    float input[][];
    ArrayList<String> sqC;
    ArrayList<String> sqf;

    ArrayList<String> numeroGiocateCasa;
    ArrayList<String> numeroGiocateFuori;

    ArrayList<String> vinteCasa;
    ArrayList<String> vinteFuori;
    ArrayList<String> perseCasa;
    ArrayList<String> perseFuori;
    ArrayList<String> pareggiCasa, pareggiFuori;






    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        try {
            interpreter2 = new Interpreter(loadModelFile2(this), null);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        carica = findViewById(R.id.carica);
        StringBuffer tmp = new StringBuffer();

        tmp.append("Cliccare su 'Prossime partite serie A' per vedere le quote effettive (in verde le quote potenzialmente più probabili)");

        eventi = findViewById(R.id.eventi);
        over = (Button) findViewById(R.id.goToOver);


        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Over05.class));
            }
        });

        carica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            //Your code goes here


                           // new DownloadFilesTask().execute();

                            //http://www.legaseriea.it/it/serie-a/classifica
                            String sq []= {
                                    "INTER",
                                    "MILAN",
                                    "JUVENTUS",
                                    "ATALANTA",
                                    "NAPOLI",
                                    "ROMA",
                                    "LAZIO",
                                    "SASSUOLO",
                                    "VERONA",
                                    "SAMPDORIA",
                                    "BOLOGNA",
                                    "UDINESE",
                                    "GENOA",
                                    "FIORENTINA",
                                    "SPEZIA",
                                    "BENEVENTO",
                                    "TORINO",
                                    "CAGLIARI",
                                    "PARMA",
                                    "CROTONE"
                            };
                            String ngc[]={
                                    "13",
                                    "14",
                                    "14",
                                    "14",
                                    "13",
                                    "15",
                                    "13",
                                    "13",
                                    "14",
                                    "14",
                                    "13",
                                    "14",
                                    "14",
                                    "15",
                                    "14",
                                    "14",
                                    "13",
                                    "14",
                                    "15",
                                    "14"

                            };
                            String ngf[]={
                                    "14",
                                    "14",
                                    "13",
                                    "14",
                                    "14",
                                    "13",
                                    "14",
                                    "14",
                                    "14",
                                    "14",
                                    "15",
                                    "14",
                                    "14",
                                    "13",
                                    "14",
                                    "14",
                                    "14",
                                    "14",
                                    "13",
                                    "14"

                            };
                            String vc[]={
                                    "11",
                                    "6",
                                    "10",
                                    "8",
                                    "9",
                                    "10",
                                    "8",
                                    "4",
                                    "6",
                                    "6",
                                    "6",
                                    "5",
                                    "4",
                                    "5",
                                    "3",
                                    "2",
                                    "1",
                                    "3",
                                    "2",
                                    "4"
                            };
                            String vf[]={
                                    "9",
                                    "12",
                                    "6",
                                    "8",
                                    "8",
                                    "5",
                                    "7",
                                    "6",
                                    "4",
                                    "4",
                                    "3",
                                    "3",
                                    "3",
                                    "2",
                                    "4",
                                    "5",
                                    "3",
                                    "2",
                                    "1",
                                    "0"

                            };
                            String  pc[]={
                                    "1",
                                    "4",
                                    "2",
                                    "3",
                                    "3",
                                    "2",
                                    "3",
                                    "3",
                                    "5",
                                    "6",
                                    "4",
                                    "6",
                                    "5",
                                    "5",
                                    "6",
                                    "7",
                                    "5",
                                    "8",
                                    "8",
                                    "9"


                            };
                            String  pf[]={
                                    "1",
                                    "1",
                                    "2",
                                    "2",
                                    "5",
                                    "6",
                                    "5",
                                    "5",
                                    "5",
                                    "7",
                                    "8",
                                    "5",
                                    "6",
                                    "8",
                                    "7",
                                    "6",
                                    "7",
                                    "8",
                                    "7",
                                    "12"
                            };
                            String dc[]={
                                    "1",
                                    "4",
                                    "2",
                                    "3",
                                    "1",
                                    "3",
                                    "2",
                                    "6",
                                    "3",
                                    "2",
                                    "3",
                                    "3",
                                    "5",
                                    "5",
                                    "5",
                                    "5",
                                    "7",
                                    "3",
                                    "5",
                                    "1"

                            };
                            String   df[]={
                                    "4",
                                    "1",
                                    "5",
                                    "4",
                                    "1",
                                    "2",
                                    "2",
                                    "3",
                                    "5",
                                    "3",
                                    "4",
                                    "6",
                                    "5",
                                    "3",
                                    "3",
                                    "3",
                                    "4",
                                    "4",
                                    "5",
                                    "2"



                            };
                            sqC= new ArrayList<>();
                            sqf= new ArrayList<>();
                            numeroGiocateFuori= new ArrayList<>();
                            numeroGiocateCasa = new ArrayList<>();
                            vinteFuori = new ArrayList<>();
                            vinteCasa= new ArrayList<>();
                            perseCasa= new ArrayList<>();
                            perseFuori= new ArrayList<>();
                            pareggiFuori= new ArrayList<>();
                            pareggiCasa= new ArrayList<>();

                            for(String x : sq) {
                                sqC.add(x);
                                sqf.add(x);
                            }
                            for(String x : ngc)
                                numeroGiocateCasa.add(x);
                            for(String x : ngf)
                                numeroGiocateFuori.add(x);
                            for(String x : dc)
                                pareggiCasa.add(x);
                            for(String x : df)
                                pareggiFuori.add(x);
                            for(String x : vc)
                                vinteCasa.add(x);
                            for(String x : vf)
                                vinteFuori.add(x);
                            for(String x : pc)
                                perseCasa.add(x);
                            for(String x : pf)
                                perseFuori.add(x);







                            StringBuffer tmp=  new StringBuffer();
                            tmp.append("Attendere il caricamento...");

                            eventi.setText(tmp);
                            String link = "https://www.superscommesse.it/scommesse/calcio/1-serie_a.html";


                            Connection conn = Jsoup.connect(link);
                            conn.timeout(10 * 1000);
                            conn.followRedirects(true);
                            Document doc = conn.get();




                            Elements partite = doc.getElementsByClass("el_container info_evento");

                            ArrayList<String>match= new ArrayList<>();
                            ArrayList<String>quoteCasa = new ArrayList<>();
                            ArrayList<String>quoteFuori=new ArrayList<>();
                            ArrayList<String> quotePari=new ArrayList<>();

                           for (Element partita : partite) {
                                for(Element x: partita.getElementsByClass("evento_link"))
                                    match.add(x.text());

                                for(Element y: partita.getElementsByClass("quota quota_1"))
                                    quoteCasa.add(y.text());
                                for(Element z: partita.getElementsByClass("quota quota_2"))
                                    quotePari.add(z.text());
                                for(Element j: partita.getElementsByClass("quota quota_3"))
                                    quoteFuori.add(j.text());

                            }
                            ArrayList<String> p= new ArrayList<>();
                            for(String x: match){
                                if(x.contains("Risultato"))
                                    break;
                                p.add(x);
                            }
                            input = new float[1][3];

                            float[][] r;//quote
                            SpannableStringBuilder builder = new SpannableStringBuilder();



                            for(int i=0;i<quoteCasa.size();i++){

                                input[0][0]=Float.parseFloat(quoteCasa.get(i).trim());
                                input[0][1]=Float.parseFloat(quotePari.get(i).trim());
                                input[0][2]=Float.parseFloat(quoteFuori.get(i).trim());
                                r = doInference2(input);

                                float quota1Snai = 100 / input[0][0];
                                float quotaXSnai = 100 / input[0][1];
                                float quota2Snai = 100 / input[0][2];


                                DecimalFormat d = new DecimalFormat("##.##");

                                String quoteMODELLO= d.format(1/Float.valueOf(r[0][2]))+" "+d.format(1/Float.valueOf(r[0][3]))+" "+d.format(1/Float.valueOf(r[0][4]));
                               //String segno=calcolaSegno(Float.valueOf(r[0][2]), Float.valueOf(r[0][3]), Float.valueOf(r[0][4]));
                               // String segno=calcolaSegno(quota1Snai, quotaXSnai, quota2Snai);

                               String segno = segno(p.get(i));
                                SpannableString str1= new SpannableString("\n"+p.get(i)+"\t"+input[0][0]
                                        +"\t\t"+input[0][1]+"\t\t"+input[0][2]+ "\t\t"+segno+" "+quoteMODELLO);
                                if(quota1Snai<Float.valueOf(r[0][2]) * 100)
                                    str1.setSpan(new ForegroundColorSpan(Color.GREEN), ("\n"+p.get(i)+"\t").length(), (str1.length()-("\t\t"+segno+" "+quoteMODELLO).length()), 0);
                                else
                                    str1.setSpan(new ForegroundColorSpan(Color.RED), ("\n"+p.get(i)+"\t").length(),(str1.length()-("\t\t"+segno+" "+quoteMODELLO).length()), 0);
                                if(quotaXSnai<Float.valueOf(r[0][3]) * 100)
                                    str1.setSpan(new ForegroundColorSpan(Color.GREEN), ("\n"+p.get(i)+"\t"+input[0][0]
                                            +"\t\t").length(),(str1.length()-("\t\t"+segno+" "+quoteMODELLO).length()), 0);
                                else
                                    str1.setSpan(new ForegroundColorSpan(Color.RED), ("\n"+p.get(i)+"\t"+input[0][0]
                                            +"\t\t").length(), (str1.length()-("\t\t"+segno+" "+quoteMODELLO).length()), 0);
                                if(quota2Snai<Float.valueOf(r[0][4]) * 100)
                                    str1.setSpan(new ForegroundColorSpan(Color.GREEN), ("\n"+p.get(i)+"\t"+input[0][0]
                                            +"\t\t"+input[0][1]+"\t\t").length(), (str1.length()-("\t\t"+segno+" "+quoteMODELLO).length()), 0);
                                else
                                    str1.setSpan(new ForegroundColorSpan(Color.RED), ("\n"+p.get(i)+"\t"+input[0][0]
                                            +"\t\t"+input[0][1]+"\t\t").length(), (str1.length()-("\t\t"+segno+" "+quoteMODELLO).length()), 0);



                                builder.append(str1);



                            }
                            eventi.setText( builder, TextView.BufferType.SPANNABLE);




                      /*  } catch (IOException ex) {
                            ex.printStackTrace();
*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }



                    private String segno(String s) {

                        String sq[] = s.split("-");
                        int countCasa = 0, countFuori = 0;
                        for (int i = 0; i < sqC.size(); i++) {
                            if (sq[0].trim().toUpperCase().equals(sqC.get(i).trim().toUpperCase()))
                                countCasa = i;
                        }
                        for (int i = 0; i < sqf.size(); i++){
                            if (sq[1].trim().toUpperCase().equals(sqf.get(i).trim().toUpperCase()))
                                countFuori = i;
                        }

                        if(countCasa==0 && countFuori==0 )
                            return "non trovato";


                        float res1 = (Float.parseFloat(vinteCasa.get(countCasa)) + Float.parseFloat(perseFuori.get(countFuori))) / (Float.parseFloat(numeroGiocateCasa.get(countCasa)) + Float.parseFloat(numeroGiocateFuori.get(countFuori)));
                        float resX = (Float.parseFloat(pareggiCasa.get(countCasa)) + Float.parseFloat(pareggiFuori.get(countFuori))) / (Float.parseFloat(numeroGiocateCasa.get(countCasa)) + Float.parseFloat(numeroGiocateFuori.get(countFuori)));
                        float res2 = (Float.parseFloat(perseCasa.get(countCasa)) + Float.parseFloat(vinteFuori.get(countFuori))) / (Float.parseFloat(numeroGiocateCasa.get(countCasa)) + Float.parseFloat(numeroGiocateFuori.get(countFuori)));
                        ArrayList<Float> lista = new ArrayList<>();
                        String res = "";
                        lista.add(res1);
                        lista.add(resX);
                        lista.add(res2);
                        Collections.sort(lista);
                       if (lista.get(2) > 0.5 && lista.get(1) < 0.25) {
                            if (res1 == lista.get(2))
                                return "1";
                            if (resX == lista.get(2))
                                return "X";
                            if (res2 == lista.get(2))
                                return "2";
                        } else if (lista.get(0) >= 0.15) {
                            if (res1 == lista.get(0))
                                return "X2";
                            if (resX == lista.get(0))
                                return "12";
                            if (res2 == lista.get(0))
                                return "1X";
                        }else if(lista.get(0)< 0.15){
                            if (res1 == lista.get(1))
                                return  "X2";
                            if (resX==lista.get(1))
                                return "12";
                            if (res2 == lista.get(1))
                                return "1X";
                        }
                        return res;
                    }

                    private String calcolaSegno(Float segno1, Float segnoX, Float segno2) {
                        ArrayList<Float> lista = new ArrayList<>();
                        String res = "";
                        lista.add(segno1);
                        lista.add(segnoX);
                        lista.add(segno2);
                        Collections.sort(lista);
                        if (lista.get(2) > 0.5 && lista.get(1) < 0.25) {
                            if (segno1 == lista.get(2))
                                res = "1";
                            if (segnoX == lista.get(2))
                                res = "X";
                            if (segno2 == lista.get(2))
                                res = "2";
                        } else if (lista.get(0) >= 0.15) {
                            if (segno1 == lista.get(0))
                                res = "X2";
                            if (segnoX == lista.get(0))
                                res = "12";
                            if (segno2 == lista.get(0))
                                res = "1X";
                        }else if(lista.get(0)< 0.15){
                            if (segno1 == lista.get(1))
                                return  "X2";
                            if (segnoX==lista.get(1))
                                return "12";
                            if (segno2== lista.get(1))
                                return "1X";
                        }
                        return res;

                    }

                });

                thread.start();

            }
        });


    }


    private MappedByteBuffer loadModelFile2(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd("model2.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public float[][] doInference2(float[][] c) {
        float[][] output = new float[1][5];
        Map<Integer, Object> outputs = new HashMap<>();

        outputs.put(0, output);
        interpreter2.runForMultipleInputsOutputs(c, outputs);
        return output;

    }
    public void caricaClassifica(){



    }


class DownloadFilesTask extends AsyncTask<URL, Integer, Void> {
        protected Void doInBackground(URL... urls) {
           // String link2 = "https://www.statistichesulcalcio.com/mainstats/italia/Serie-A_71/anno_129.html";
          // String link2= "https://www.google.com";

            /*String link2 ="http://www.legaseriea.it/it/serie-a/classifica";
            try {
                Connection conn2 = Jsoup.connect(link2);
                conn2.timeout(5 * 1000);
                sqC= new ArrayList<>();

                conn2.followRedirects(true);
                Document doc2 = conn2.get();

              // Element table = doc2.getElementById("#table-2");






            sqC.add(doc2.title());

           /*ArrayList<String> s = new ArrayList<>();
            for (Element tr : table.select("tr")) {
                for (Element td : tr.select("td")) {

                    s.add(td.text().trim());

                }

            }

            sqC= new ArrayList<>();
            sqf= new ArrayList<>();

            numeroGiocateCasa= new ArrayList<>();
            numeroGiocateFuori= new ArrayList<>();

            vinteCasa=new ArrayList<>();
            vinteFuori= new ArrayList<>();
            pareggiCasa= new ArrayList<>();
            pareggiFuori= new ArrayList<>();
            perseCasa= new ArrayList<>();
            perseFuori= new ArrayList<>();
            int z = 1;
            int n = 2;
            int t = 5;
            int k= 6;
            int j= 7;


            for (int i = 0; i < 20; i++) {

                sqC.add(s.get(z));
                numeroGiocateCasa.add(s.get(n));
                vinteCasa.add(s.get(t));
                perseCasa.add(s.get(j));
                pareggiCasa.add(s.get(k));
                k +=9;
                z += 9;
                n += 9;
                t += 9;
            }
*/

            /*

            Element table2 = doc2.getElementById("awayClassification");





            ArrayList<String> s2 = new ArrayList<>();
            for (Element tr : table2.select("tr")) {
                for (Element td : tr.select("td")) {

                    s2.add(td.text().trim());

                }

            }
            z = 1;
            n = 2;
            t = 5;
            k=6;
            j= 7;

            for (int i = 0; i < 20; i++) {

                sqf.add(s2.get(z));
                numeroGiocateFuori.add(s2.get(n));
                vinteFuori.add(s2.get(t));
                perseFuori.add(s2.get(j));
                pareggiFuori.add(s2.get(k));
                z += 9;
                n += 9;
                t += 9;
                k +=9;
            }
            } catch (IOException e) {
                e.printStackTrace();
            }

*/



            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }

}





