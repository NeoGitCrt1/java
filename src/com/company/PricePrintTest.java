//package com.company;
//
//
//import com.itextpdf.forms.PdfAcroForm;
//import com.itextpdf.forms.fields.PdfFormField;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfPage;
//import com.itextpdf.kernel.pdf.PdfReader;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.io.font.cmap.*;
//import com.itextpdf.kernel.utils.PdfMerger;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.AreaBreak;
//import com.itextpdf.layout.property.AreaBreakType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
///**
// * @author ysy
// * @version v1.0
// * @description PricePrintTest
// * @date 2018-12-20 10:08
// */
//public class PricePrintTest {
//    private static final String PATH = "E:/小程序&价签打印/";
//    static Logger log = LoggerFactory.getLogger(PricePrintTest.class);
//    public static void main(String[] args) throws IOException, InterruptedException {
//
//
//
//        // *****************************************
//        String templatePath = PATH + "test_pricemodel2.pdf";
//        String pdf2_path= PATH + "test_pricemodel_result_single.pdf";
////        PdfCopy pdf2 = new PdfDocument(new PdfWriter(pdf2_path));
////
////        PdfStamper stamper = new PdfStamper()
//
//        PdfDocument pdfTmp = new PdfDocument(new PdfReader(templatePath), new PdfWriter(pdf2_path + "x"));
//        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfTmp, false);
//        Map<String, PdfFormField> fields = form.getFormFields();
//        Map<String, String> filldata = data();
//        PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
//        for (String key : filldata.keySet()) {
//            log.info("key: {}", key);
//            String value = filldata.get(key);
//            fields.get(key).setValue(value).setFont(font).setFontSize(12); // 为字段赋值,注意字段名称是区分大小写的
//        }
////        form.flattenFields();//设置表单域不可编辑
//////        pdfTmp.copyPagesTo(1,1,pdf2);
////        pdfTmp.getPdfObject(1)
////        pdfTmp.getFirstPage().copyTo(pdf2);
////        pdfTmp.close();
////        pdf2.close();
//
//    }
//
//    static void old() throws IOException {
//        // 模板路径
//        String templatePath = PATH + "test_pricemodel2.pdf";
//        // 生成的新文件路径
//        String fileName_merge= PATH + "test_pricemodel_result2.pdf";
//        PdfDocument pdf = new PdfDocument(new PdfWriter(fileName_merge));
//        PdfMerger pdfMerger = new PdfMerger(pdf);
//
//
//
//
//        for(int i=0; i<3; i++){
//            String fileName = PATH + "test_pricemodel_result2_" + i+ ".pdf";
//            fillTemplate(templatePath, fileName);
//        }
//
//        for(int i=0; i<3; i++){
//            String fileName = PATH + "test_pricemodel_result2_" + i+ ".pdf";
//            PdfDocument pdfSingle = new PdfDocument(new PdfReader(fileName));
//            pdfMerger.merge(pdfSingle, 1, pdfSingle.getNumberOfPages());
//            pdfSingle.close();
//        }
//        pdfMerger.close();
//    }
//
//    /**
//     * 使用pdf 模板生成 pdf 文件
//     * 	 */
//    public static void fillTemplate(String templatePath, String fileName) {// 利用模板生成pdf
//
//        try {
//            //Initialize PDF document
//            PdfDocument pdf = new PdfDocument(new PdfReader(templatePath), new PdfWriter(fileName));
//
//            PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
//            Map<String, PdfFormField> fields = form.getFormFields();
//
//            //处理中文问题
//            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
//            Map<String, String> filldata = data();
//            for (String key : filldata.keySet()) {
//                log.info("key: {}", key);
//                String value = filldata.get(key);
//                fields.get(key).setValue(value).setFont(font).setFontSize(12); // 为字段赋值,注意字段名称是区分大小写的
//            }
//            form.flattenFields();//设置表单域不可编辑
////            PdfPage newPage = pdf.addNewPage();
//            pdf.close();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
//
//    public static Map<String, String> data() {
//        Map<String, String> data = new HashMap<String, String>();
////        data.put("price", "11.00");
////        data.put("product_code", "100136白酒");
////        data.put("fill_1", "中国山西");
////        data.put("fill_2", "test2");
////        data.put("fill_3", "test3");
////        data.put("fill_4", "test4");
////        data.put("fill_5", "test5");
//        for(int i=0; i<2; i++){
//            data.put("price"+i, "1"+i);
//            data.put("product_code"+i, "100136白酒"+i);
//            data.put("fill_1"+i, "中国山西"+i);
//            data.put("fill_2"+i, "test2"+i);
//            data.put("fill_3"+i, "test3"+i);
//            data.put("fill_4"+i, "test4"+i);
//            data.put("fill_5"+i, "test5"+i);
//        }
////        data.put("price#0", "11.00");
////        data.put("product_code#0", "100136白酒");
////        data.put("fill_1#0", "中国山西");
////        data.put("fill_2#0", "test2");
////        data.put("fill_3#0", "test3");
////        data.put("fill_4#0", "test4");
////        data.put("fill_5#0", "test5");
////        data.put("price#1", "12.00");
////        data.put("product_code#1", "100135葡萄酒");
////        data.put("fill_1#1", "英国");
////        data.put("fill_2#1", "test22");
////        data.put("fill_3#1", "test32");
////        data.put("fill_4#1", "test42");
////        data.put("fill_5#1", "test52");
//        return data;
//    }
//}
