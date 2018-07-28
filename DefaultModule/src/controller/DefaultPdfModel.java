package controller;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.font.FontProvider;


import java.io.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dubai on 11/3/2017.
 */
public class DefaultPdfModel {


    /**
     * Chemin du dossier
     */
    private String path;
    private File file;
    private File filepdf;
    private String content;
    private File directory;
    private PdfWriter writer;


    public DefaultPdfModel(String username){
        int year =  Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        this.path = username+File.separator+String.valueOf(year)+File.separator+String.valueOf(month);
    }


    /**
     * Default constructor
     * @param content
     */
    public DefaultPdfModel(String content,String username){


        this.content = content;
        int year =  Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) +1;
        int day  = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.path = username+File.separator+String.valueOf(year)+File.separator+String.valueOf(month)+File.separator+String.valueOf(day);

        user_mkdir();



        String name = new Date().toString().replace(" ","");
        name = name.replace(":","");
        String pdf = name + ".pdf";
        name = name +".html";
        String filename = this.path +File.separator+name;
        String fpdf = this.path +File.separator+pdf;

        file = new File(filename);
        filepdf = new File(fpdf);


        //Pour ecrire le contenu du fichier en html
          BufferedWriter bufferedWriter=null;
        try {
             bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
                     //Files.newBufferedWriter(Paths.get(filename));
                     //new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            FileOutputStream fos = new FileOutputStream(filepdf);
            WriterProperties properties = new WriterProperties();
            properties.addXmpMetadata();
            writer = new PdfWriter(fos,properties);
            PdfDocument document = new PdfDocument(writer);


            document.getCatalog().setLang(new PdfString("fr-FR"));
            document.setTagged();
            document.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
            PdfDocumentInfo info = document.getDocumentInfo();
            info.setCreator("Univetech SARL");
            info.setAuthor("2IP");
            info.setTitle("Devis");
            info.addCreationDate();

            ConverterProperties props = new ConverterProperties();
            FontProvider font  = new FontProvider();

            font.addStandardPdfFonts();
            font.addDirectory(this.path);
            props.setFontProvider(font);
            props.setBaseUri(this.path);




            try {
                HtmlConverter.convertToPdf(new FileInputStream(file.getAbsoluteFile()),document,props);
            } catch (IOException e) {
                e.printStackTrace();
            }
            document.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }






    }

    /**
     * Generate PDF
     */

    public void generatePDF()  {


    }

    /**
     * Creer users current directories
     */
    public void user_mkdir(){

        File directory = new File(this.path);

        if (!directory.exists()){
            directory.mkdirs();
        }
        this.directory = directory;
    }



//        XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
//        try {
//            helper.parseXHtml(writer,document,new StringReader(content));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


}
