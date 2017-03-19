package com.newview.bysj.web.remarkTemplate;

import com.newview.bysj.domain.RemarkTemplate;
import com.newview.bysj.domain.RemarkTemplateItems;
import com.newview.bysj.domain.RemarkTemplateItemsOption;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 所有评语模版的基类
 * Created 2016/3/27,20:38.
 * Author 张战.
 */
public class BaseRemarkTemplate extends BaseController {

    //private static final Logger logger = Logger.getLogger(BaseRemarkTemplate.class);

    /**
     * jsp中文件夹的位置
     */
    protected String folderName = "evaluate/remarkTemplate/";

    /**
     * 保存评语模版
     *
     * @param httpServletResponse 对当前请求的响应
     * @param httpSession         当前会话
     * @param httpServletRequest  当前请求
     * @param title               模版的标题
     * @param lineNumber          评语的行数
     * @param remarkTemplate      评语模版
     * @return 重定向到设置评语模版的界面
     */
    public String saveRemarkTemplate(HttpServletResponse httpServletResponse, HttpSession httpSession, HttpServletRequest httpServletRequest, String title, Integer lineNumber, RemarkTemplate remarkTemplate) {
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        //设置模版的创建者
        remarkTemplate.setBuilder(tutor);
        //设置标题
        remarkTemplate.setTitle(title);
        //不是默认的模版
        remarkTemplate.setDefaultRemarkTemplate(null);
        //用来存放评语选项的数组
        List<RemarkTemplateItems> remarkTemplateItemsList = new ArrayList<>();
        String remarkTemplateItemParamName = "remarkTemplateItem";
        String preText = "preText";
        String postText = "postText";
        String itemOptionParamName = "itemOptions";
        for (int i = 0; i < lineNumber; i++) {
            String index = "[" + i + "]";
            System.out.println("aaaa----->" + httpServletRequest.getParameter(remarkTemplateItemParamName + index + postText));

            RemarkTemplateItems remarkTemplateItems = new RemarkTemplateItems();
            //设置评语选项的序号
            remarkTemplateItems.setItemIndex(i + 1);
            //设置选项前的文字
            remarkTemplateItems.setPreText(httpServletRequest.getParameter(remarkTemplateItemParamName + index + preText));
            //设置选项后的文字
            remarkTemplateItems.setPostText(httpServletRequest.getParameter(remarkTemplateItemParamName + index + postText));
            remarkTemplateItems.setRemarkTemplate(remarkTemplate);
            List<RemarkTemplateItemsOption> remarkTemplateItemsOptionList = new ArrayList<>();
            String[] remarkTemplateItemsOptions = httpServletRequest.getParameter(remarkTemplateItemParamName + index + itemOptionParamName).split(",");

            for (int j = 0; j < remarkTemplateItemsOptions.length; j++) {
                RemarkTemplateItemsOption remarkTemplateItemsOption = new RemarkTemplateItemsOption();
                remarkTemplateItemsOption.setNo(j);
                //设置下拉的选项
                remarkTemplateItemsOption.setItemOption(remarkTemplateItemsOptions[j]);
                remarkTemplateItemsOption.setRemarkTemplateItems(remarkTemplateItems);
                //remarkTemplateItemsOptionService.saveOrUpdate(remarkTemplateItemsOption);
                //添加到数组
                remarkTemplateItemsOptionList.add(remarkTemplateItemsOption);
            }
            //给每条评语设置多个下拉选项
            remarkTemplateItems.setRemarkTemplateItemsOption(remarkTemplateItemsOptionList);
            // remarkTemplateItemsService.saveOrUpdate(remarkTemplateItems);
            remarkTemplateItemsList.add(remarkTemplateItems);
        }
        remarkTemplate.setRemarkTemplateItems(remarkTemplateItemsList);
        remarkTemplateService.saveOrUpdate(remarkTemplate);
        //设置评语与评语选项的关联
        //CommonHelper.buildSimpleJson(httpServletResponse);
        return "redirect:/evaluate/setRemarkTemplate.html";
    }
}
