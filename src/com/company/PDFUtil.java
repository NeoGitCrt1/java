package com.company;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.NoCustomContextException;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.Tag;
import com.itextpdf.tool.xml.WorkerContext;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.HTML;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.ref.SoftReference;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ysy
 * @version v1.0
 * @description PDFUtil
 * @date 2018-12-20 15:29
 */
public class PDFUtil {
    private PDFUtil() {
    }

    private static final String PATH = "D:/";
    /**
     * 需要配置化外部传入
     * <p>
     * key : css内的font-family英名
     * val : 系统路径下的字体文件
     */
    private static final Map<String, String> FONT_CONF = ImmutableMap.of(
            "Microsoft YaHei", "C:/Windows/Fonts/msyh.ttc,1",
            "Impact", "C:/Windows/Fonts/impact.ttf",
            "Arial", "C:/Windows/Fonts/arial.ttf",
            "geoSlab703 MdCn BT", "C:/Windows/Fonts/1019.TTF",
            "GeoSlab703 MdCn BT Medium", "C:/Windows/Fonts/1019.TTF");
    private static final String testfreemarker = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\t<head>\n" +
            "\t\t<meta charset=\"utf-8\"/>\n" +
            "\t\t<style>\n" +
            "\t\t\thtml{font-size:100px}\n" +
            "\t\t\tdiv{ box-sizing: border-box;}\n" +
            "\t\t\t.bodyBox{background: blue ;width: 21.02rem; margin:1rem auto; overflow: hidden; font-family: \"Microsoft YaHei\";}\n" +
            "\t\t\t.tagBox{background: red; width: 10.51rem; height: 4.73rem; float: left; border: 1px solid #ccc;position: relative; overflow: hidden;}\n" +
            "\t\t\t.tagBox img{ width: 100%;}\n" +
            "\t\t\t.num{ text-align: center; font-family: \"Impact\";}\n" +
            "\t\t\t.tagBox span{position: absolute; color: rgba(0,161,233,1);}\n" +
            "\t\t\t.retailPrice{ top: 1.18rem; left: .8rem; width: 2.3rem; height: 1.1rem; font-size: 1.2rem; font-family: \"geoSlab703 MdCn BT\"; letter-spacing:-.04rem}\n" +
            "\t\t\t.vipPrice{ top: 1.18rem; left: 3.12rem; width: 3.8rem; height: 1.35rem; line-height: 1.35rem; font-size: 1.2rem;}\n" +
            "\t\t\t.countPrice{ top: 1.21rem; left: 6.75rem; width: 1.7rem; height: .7rem; line-height: .7rem; font-size: .58rem; font-style: italic;}\n" +
            "\t\t\t.boxPrice{ top: 1.81rem; left: 7.98rem; width: 1.7rem; height: .7rem; line-height: .7rem; font-size: .58rem; font-style: italic;}\n" +
            "\t\t\t.name{ top: 2.7rem; left: 2.2rem; width: 7.5rem; height: .9rem; font-size: .36rem; overflow: hidden; /* font-weight: bold; */}\n" +
            "\t\t\t.spec{ top: 3.74rem; left: 2.2rem; width: 4rem; height: .4rem; font-size: .34rem; }\n" +
            "\t\t\t.place{ top: 3.73rem; left: 7.8rem; width: 2.2rem; height: .4rem; font-size: .34rem; }\n" +
            "\t\t</style>\n" +
            "\t</head>\n" +
            "\t<body>\n" +
            "\t\t<div class='bodyBox'>\n" +
            "\t\t<#list dd as d>\n" +
            "\t\t<div class=\"tagBox\">\n" +
            "\t\t\t\t<img src=\"data:image/png;base64,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\" />\n" +
            "\t\t\t\t<span class=\"retailPrice num\">${d.sys_mkt}</span>\n" +
            "\t\t\t\t<span class=\"vipPrice num\">${d.sys_mem}</span>\n" +
            "\t\t\t\t<span class=\"countPrice num\">${d.sys_1inbox}</span>\n" +
            "\t\t\t\t<span class=\"boxPrice num\">${d.sys_1inbox?number * d.sys_cntinbox?number}</span>\n" +
            "\t\t\t\t<span class=\"name\">${d.sys_pdnm}</span>\n" +
            "\t\t\t\t<span class=\"spec\">${d.sys_spec}</span>\n" +
            "\t\t\t\t<span class=\"place\">${d.sys_made}</span>\n" +
            "\t\t\t</div>\n" +
            "\t\t</#list>\n" +
            "\t\t</div>\n" +
            "\t</body>\n" +
            "</html>\n";
    //    private static final String testfreemarker = "<@compress single_line=true><html>\n" +
//            "<style> \n" +
//            ".divcss5_italic{ font-style:italic;font-size: 36px;font-family:\"GeoSlab703 MdCn BT Medium\"} \n" +
//            ".divcss5_oblique{ font-style:normal;font-size: 16px;font-family:\"Microsoft YaHei\"}\n" +
//            ".divcss5_oblique_o{ font:12px/1.5 Microsoft YaHei, Helvetica, sans-serif}\n" +
//            ".divcss5_normal i{ font-style:normal} \n" +
//            "body {\n" +
//            "width: 820px;\n" +
//            "height: 1160px;\n" +
//            "margin: 0 auto;\n" +
//            "}\n" +
//            "</style> \n" +
//            "<body>\n" +
//            "<#list dd as d>\n" +
//            "<span class=\"divcss5_italic\">${d.price}</span> for<span class=\"divcss5_oblique\"> ${d.pc}</span> Euros\n" +
//            "</#list>\n" +
//            "</body>\n" +
//            "</html>\n" +
//            "</@compress>";
    private static final String compressedFm = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"/><style>html{font-size:100px}div{ box-sizing: border-box;}.bodyBox{ width: 21.02rem; margin:1rem auto; overflow: hidden; font-family: \"Microsoft YaHei\";}.tagBox{ width: 10.51rem; height: 4.73rem; float: left; border: 1px solid #ccc;position: relative; overflow: hidden;}.tagBox img{ width: 10%;}.num{ text-align: center; font-family: \"Impact\";}.tagBox span{position: absolute; color: rgba(0,161,233,1);}.retailPrice{ top: 1.18rem; left: .8rem; width: 2.3rem; height: 1.1rem; font-size: 1.2rem; font-family: \"geoSlab703 MdCn BT\"; letter-spacing:-.04rem}.vipPrice{ top: 1.18rem; left: 3.12rem; width: 3.8rem; height: 1.35rem; line-height: 1.35rem; font-size: 1.2rem;}.countPrice{ top: 1.21rem; left: 6.75rem; width: 1.7rem; height: .7rem; line-height: .7rem; font-size: .58rem; font-style: italic;}.boxPrice{ top: 1.81rem; left: 7.98rem; width: 1.7rem; height: .7rem; line-height: .7rem; font-size: .58rem; font-style: italic;}.name{ top: 2.7rem; left: 2.2rem; width: 7.5rem; height: .9rem; font-size: .36rem; overflow: hidden; }.spec{ top: 3.74rem; left: 2.2rem; width: 4rem; height: .4rem; font-size: .34rem; }.place{ top: 3.73rem; left: 7.8rem; width: 2.2rem; height: .4rem; font-size: .34rem; }</style></head><body><div class='bodyBox'><#list dd as d><div class=\"tagBox\"><img src=\"data:image/png;base64,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\" /><span class=\"retailPrice num\">${d.sys_mkt}</span><span class=\"vipPrice num\">${d.sys_mem}</span><span class=\"countPrice num\">${d.sys_1inbox}</span><span class=\"boxPrice num\">${d.sys_1inbox?number * d.sys_cntinbox?number}</span><span class=\"name\">${d.sys_pdnm}</span><span class=\"spec\">${d.sys_spec}</span><span class=\"place\">${d.sys_made}</span></div></#list></div></body></html>";

    public static void main(String[] args) throws IOException {
        String source = testfreemarker;
        if (args.length > 0) {
            File file = new File(args[0]);
            FileInputStream fi = new FileInputStream(file);
            BufferedReader r = new BufferedReader(new FileReader(file)) ;
            StringBuilder sb = new StringBuilder(1000);
            r.lines().forEach( line -> {
                sb.append(line);
            });
            source = sb.toString();
        }
        List<Map<String, Object>> data = new ArrayList<>(200);
        Map<String, Object> d = new HashMap<>();
        d.put("sys_mkt", "999.01");
        d.put("sys_mem", "56");
        d.put("sys_cntinbox", "6");
        d.put("sys_1inbox", "55.5");
        d.put("sys_pdnm", "西班牙阿兰达田帕尼优干红西班牙阿兰达田帕尼优干红");
        d.put("sys_spec", "750ml*6瓶");
        d.put("sys_made", "西班牙");
        for (int j = 0; j < 20; j++) {
            data.add(d);
        }
        data2Html(data, source, new FileOutputStream(PATH + "test_pricemodel_result_html5.html"));
        htmlToPDF(data, source, new FileOutputStream(PATH + "test_pricemodel_result_single5.pdf"), FONT_CONF);
    }

    private static final Configuration FM_CONF = new Configuration(Configuration.VERSION_2_3_26);

    {
        FM_CONF.setDefaultEncoding(Charset.defaultCharset().name());
        FM_CONF.setOutputEncoding(Charset.defaultCharset().name());
    }

    ;
    private static final ConcurrentHashMap<Integer, SoftReference<Template>> FREEMARKER_TMPL_CACHE = new ConcurrentHashMap<>();
    private static final HashFunction HASH_FUNCTION32 = Hashing.murmur3_32();
    public static void data2Html(final List<Map<String, Object>> data, final String freemarkerSource, final OutputStream os) throws IOException {
        Integer tmplKey = HASH_FUNCTION32.hashUnencodedChars(freemarkerSource).asInt();
        SoftReference<Template> templateRef = FREEMARKER_TMPL_CACHE.get(tmplKey);
        if (templateRef == null || templateRef.get() == null) {
            synchronized (FREEMARKER_TMPL_CACHE) {
                templateRef = new SoftReference<>(new Template(tmplKey.toString(), freemarkerSource, FM_CONF));
                FREEMARKER_TMPL_CACHE.put(tmplKey, templateRef);
            }
        }
        Template template = templateRef.get();
        try {
            template.process(ImmutableMap.of("dd", data), new OutputStreamWriter(os, Charset.defaultCharset()));
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }

    public static void htmlToPDF(final List<Map<String, Object>> data, final String freemarkerSource, final OutputStream os, final Map<String, String> fontConf) throws IOException {
        ByteArrayOutputStream op = new ByteArrayOutputStream();
        data2Html(data, freemarkerSource, op);

        // step 1
        Document document = new Document();
        AsianFontProvider myFontProvider = new AsianFontProvider(fontConf);
// step 2
        PdfWriter pdfWriter;
        try {
            pdfWriter = PdfWriter.getInstance(document, os);
        } catch (DocumentException e) {
            throw new IOException(e);
        }
// step 3
        document.open();

        final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
        tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
        tagProcessorFactory.addProcessor(ImageTagProcessor.INSTANCE, HTML.Tag.IMG);


        final CssFilesImpl cssFiles = new CssFilesImpl();
        cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
        final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
        final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(myFontProvider));
        hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
        final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, pdfWriter));
        final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);

        final XMLWorker worker = new XMLWorker(pipeline, true);

        final Charset charset = Charset.defaultCharset();
        final XMLParser xmlParser = new XMLParser(true, worker, charset);
        xmlParser.parse(new ByteArrayInputStream(op.toByteArray()), charset);

// step 5
        document.close();


//        Document document = new Document(PageSize.A4);
//
//        PdfWriter pdfWriter;
//        try {
//            pdfWriter = PdfWriter.getInstance(document, os);
//        } catch (DocumentException e) {
//            throw new IOException(e);
//        }
//        document.open();
//        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
//        worker.parseXHtml(pdfWriter, document, new ByteArrayInputStream(op.toByteArray()), null, Charset.defaultCharset(), new AsianFontProvider(fontConf));
//        document.close();
    }

    private static class AsianFontProvider extends XMLWorkerFontProvider {

        /**
         * key : css内的font-family英名
         * val : 系统路径下的字体文件
         */
        private final Map<String, String> font;
        private final String def;

        public AsianFontProvider(Map<String, String> font) {
            this.font = font;
            def = font.values().stream().findFirst().get();
        }

        @Override
        public Font getFont(final String fontname, String encoding, float size, final int style) {
            try {
                return new Font(BaseFont.createFont(
                        font.getOrDefault(fontname, def)
                        , BaseFont.IDENTITY_H, BaseFont.EMBEDDED), size, style);
            } catch (DocumentException | IOException e) {
                try {
                    return new Font(BaseFont.createFont(), size, style);
                } catch (DocumentException | IOException e1) {
                    return null;
                }
            }
        }
    }


    protected static class ImageTagProcessor extends com.itextpdf.tool.xml.html.Image {

        public static final ImageTagProcessor INSTANCE = new ImageTagProcessor();

        private ImageTagProcessor() {
        }

        ;

        /*
        * (non-Javadoc)
        *
        * @see com.itextpdf.tool.xml.TagProcessor#endElement(com.itextpdf.tool.xml.Tag, java.util.List, com.itextpdf.text.Document)
        */
        @Override
        public List end(final WorkerContext ctx, final Tag tag, final List currentContent) {
            final Map attributes = tag.getAttributes();
            String src = attributes.get(HTML.Attribute.SRC).toString();
            List elements = new LinkedList();
            if (null != src && src.length() > 0) {
                Image img = null;
                if (src.startsWith("data:image/")) {
                    final String base64Data = src.substring(src.indexOf(",") + 1);
                    try {
                        img = Image.getInstance(Base64.decode(base64Data));
                    } catch (BadElementException e) {
                        throw new RuntimeWorkerException(e);
                    } catch (IOException e) {
                        throw new RuntimeWorkerException(e);
                    }
                    if (img != null) {
                        try {
                            final HtmlPipelineContext htmlPipelineContext = getHtmlPipelineContext(ctx);
                            elements.add(getCssAppliers().apply(new Chunk((com.itextpdf.text.Image) getCssAppliers().apply(img, tag, htmlPipelineContext), 0, 0, true), tag,
                                    htmlPipelineContext));
                        } catch (NoCustomContextException e) {
                            throw new RuntimeWorkerException(e);
                        }
                    }
                }
                if (img == null) {
                    return super.end(ctx, tag, currentContent);
                }
            }
            return elements;
        }
    }
}



