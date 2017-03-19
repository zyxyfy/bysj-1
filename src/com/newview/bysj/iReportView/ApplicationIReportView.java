package com.newview.bysj.iReportView;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import java.util.Map;

/**
 * 视图处理扩展
 * Created 2016/5/1,16:06.
 * Author 张战.
 */
public class ApplicationIReportView extends JasperReportsMultiFormatView {
    private JasperReport jasperReport;

    public ApplicationIReportView() {
        super();
    }


    /**
     * 重写fillReport方法，并指定setUrl的实现，这样可以controller中动态设定报表模版的url
     * 设置url的key为"url"
     *
     * @param model 需要生成报表的模型数据
     * @return 生成对应的报表
     * @throws Exception
     */
    protected JasperPrint fillReport(Map<String, Object> model) throws Exception {
        if (model.containsKey("url")) {
            setUrl(String.valueOf(model.get("url")));
            this.jasperReport = loadReport();
        }

        return super.fillReport(model);
    }

    protected JasperReport getReport() {
        return this.jasperReport;
    }
}
