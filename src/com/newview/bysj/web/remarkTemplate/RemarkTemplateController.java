package com.newview.bysj.web.remarkTemplate;

import com.newview.bysj.domain.RemarkTemplate;
import com.newview.bysj.domain.RemarkTemplateItems;
import com.newview.bysj.domain.RemarkTemplateItemsOption;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.service.RemarkTemplateItemsService;
import com.newview.bysj.service.RemarkTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 设置评语模版的控制器
 * Created by zhan on 2016/3/25.
 */
@Controller
@RequestMapping("evaluate")
public class RemarkTemplateController extends BaseRemarkTemplate {

    @Autowired
    protected RemarkTemplateItemsService remarkTemplateItemsService;
    @Autowired
    protected RemarkTemplateService remarkTemplateService;

//    private static final Logger logger = Logger.getLogger(RemarkTemplateController.class);

    /**
     * 根据jsp的名字得到完整的jsp路径
     *
     * @param jspName jsp的文件名
     * @return 相应的jsp
     */
    private static String getUrlToJsp(String jspName) {
        return "evaluate/remarkTemplate/" + jspName;
    }

    //设置评语模板

    /**
     * 点击设置评语模版时执行此方法
     *
     * @param httpSession        当前会话
     * @param modelMap           存储需要在jsp中获取的数据
     * @param pageNo             当前页，如果当前页为空则默认从0开始
     * @param pageSize           每页的条数，如果条数为空则默认每页10条
     * @param httpServletRequest 当前请求，用于获取请求的url
     * @return evaluate/remarkTemplate/setRemarkTemplate.jsp
     */
    @RequestMapping("/setRemarkTemplate.html")
    public String setRemarkTemplate(HttpSession httpSession, ModelMap modelMap, Integer pageNo, Integer pageSize, HttpServletRequest httpServletRequest) {
        //获取当前tutor
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        //根据当前的指导老师得到评语模版
        Page<RemarkTemplate> remarkTemplatePage = remarkTemplateService.getPageByDirectior(tutor, pageNo, pageSize);
        //分页相关
        CommonHelper.pagingHelp(modelMap, remarkTemplatePage, "remarkTemplateList", httpServletRequest.getRequestURI(), remarkTemplatePage.getTotalElements());
        //将内容的总条数添加model中
        modelMap.put("remarkTemplateSize", remarkTemplatePage.getContent().size());
        return "evaluate/remarkTemplate/setRemarkTemplate";
    }


    /**
     * 修改评语模版的get方法，点击修改时执行此方法
     *
     * @param editId             需要修改的模版的id
     * @param modelMap           存储需要在jsp中获取的数据
     * @param httpServletRequest 当前请求，用于获取请求的url
     * @return evaluate/remarkTemplate/saveRemarkTemplate.jsp
     */
    @RequestMapping(value = "/editRemarkTemplate.html", method = RequestMethod.GET)
    public String editRemarkTemplate(Integer editId, ModelMap modelMap, HttpServletRequest httpServletRequest) {

        //根据id获取需要修改的模版
        RemarkTemplate remarkTemplate = remarkTemplateService.findById(editId);
        //添加到model中
        modelMap.put("remarkTemplate", remarkTemplate);
        modelMap.put("actionURL", httpServletRequest.getRequestURI());
        return getUrlToJsp("saveRemarkTemplate");
    }


    /**
     * 修改评语模版的post方法，点击保存时执行此方法
     *
     * @param title              修改后的评语模版的标题
     * @param httpSession        当前会话
     * @param remarkTemplateId   修改的评语模版的id
     * @param httpServletRequest 当前请求
     * @param lineNumber         修改后的评语选项的条数
     * @return 重定向到本类中的setRemarkTemplate方法
     */
    @RequestMapping(value = "/editRemarkTemplate.html", method = RequestMethod.POST)
    public String editRemarkTemplate(HttpServletResponse httpServletResponse, String title, HttpSession httpSession, Integer remarkTemplateId, HttpServletRequest httpServletRequest, Integer lineNumber) {
        System.out.println("-------------" + lineNumber);
        //得到评语模版
        RemarkTemplate remarkTemplate = remarkTemplateService.findById(remarkTemplateId);
        //得到当前的指导老师
        Tutor tutor = CommonHelper.getCurrentTutor(httpSession);
        //设置修改人
        remarkTemplate.setBuilder(tutor);
        //设置标题
        remarkTemplate.setTitle(title);
        //是否为默认模版
        remarkTemplate.setDefaultRemarkTemplate(null);
        //获取原来的评语选项
        Collection<RemarkTemplateItems> remarkTemplateItemsCollection = remarkTemplate.getRemarkTemplateItems();

        /*此处的设置新的评语选项较为复杂
        * 能否找到更合适的方法？*/

        //获取所有的评语选项，并将其中的选项置为空
        for (RemarkTemplateItems remarkTemplateItems : remarkTemplateItemsCollection) {
            remarkTemplateItems.setRemarkTemplate(null);
            //更新
            remarkTemplateItemsService.saveOrUpdate(remarkTemplateItems);
        }
        //取消与评语选项的关联
        remarkTemplate.setRemarkTemplateItems(null);
        remarkTemplateService.saveOrUpdate(remarkTemplate);
        //删除评语选项
        for (RemarkTemplateItems remarkTemplateItems : remarkTemplateItemsCollection) {
            remarkTemplateItems.setRemarkTemplate(null);
            remarkTemplateItemsService.deleteObject(remarkTemplateItems);
        }

        return super.saveRemarkTemplate(httpServletResponse, httpSession, httpServletRequest, title, lineNumber, remarkTemplate);

        /*List<RemarkTemplateItems> remarkTemplateItemsCollectionAdd = this.getRemarkTemplateItems(httpServletRequest, lineNumber, remarkTemplate);
        for (RemarkTemplateItems remarkTemplateItems : remarkTemplateItemsCollectionAdd) {
            System.out.println(remarkTemplateItems.getPreText());
            System.out.println(remarkTemplateItems.getRemarkTemplateItemsOption().size());
            System.out.println(remarkTemplateItems.getPostText());
        }
        remarkTemplate.setRemarkTemplateItems(remarkTemplateItemsCollectionAdd);
        remarkTemplateService.update(remarkTemplate);
        remarkTemplateService.save(remarkTemplate);
        return "redirect:/evaluate/setRemarkTemplate.html";*/
    }


    /*private List<RemarkTemplateItems> getRemarkTemplateItem(HttpServletRequest httpServletRequest, Integer lineNumber, RemarkTemplate remarkTemplate){
        List<RemarkTemplateItems> remarkTemplateItemsList = new ArrayList<>();
        String remarkTemplateItemParamName = "remarkTemplateItem";
        String preText = "preText";
        String postText = "postText";
        String itemOptionParamName = "itemOptions";
        for (int i = 0; i < lineNumber; i++) {
            String index = "[" + i + "]";
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
        return remarkTemplateItemsList;
    }*/

    //获取从前台输入的自定义的内容
    public List<RemarkTemplateItems> getRemarkTemplateItems(HttpServletRequest httpServletRequest, Integer lineNumbers, RemarkTemplate remarkTemplate) {
        String remarkTemplateItemParameName = "remarkTemplateItem";
        String preText = "preText";
        String postText = "postText";
        String itemOptionParameName = "itemOptions";
        List<RemarkTemplateItems> remarkTemplateItemsCollection = new ArrayList<>();
        for (int i = 0; i < lineNumbers; i++) {
            String index = "[" + i + "]";
            RemarkTemplateItems remarkTemplateItems = new RemarkTemplateItems();
            remarkTemplateItems.setItemIndex(i + 1);
            remarkTemplateItems.setPreText(httpServletRequest.getParameter(remarkTemplateItemParameName + index + preText));
            remarkTemplateItems.setPostText(httpServletRequest.getParameter(remarkTemplateItemParameName + index + postText));
            //评语选项
            List<RemarkTemplateItemsOption> remarkTemplateItemsOptionCollection = new ArrayList<>();
            String[] remarkTemplateOption = httpServletRequest.getParameter(remarkTemplateItemParameName + index + itemOptionParameName).split(",");
            for (int j = 0; j < remarkTemplateOption.length; j++) {
                RemarkTemplateItemsOption remarkTemplateItemsOption = new RemarkTemplateItemsOption();
                //设置评语选项
                remarkTemplateItemsOption.setNo(j);
                remarkTemplateItemsOption.setItemOption(remarkTemplateOption[j]);
                //关联评语内容
                remarkTemplateItemsOption.setRemarkTemplateItems(remarkTemplateItems);
                //添加到数组中，便于和评语条目进行关联
                remarkTemplateItemsOptionCollection.add(remarkTemplateItemsOption);
            }
            remarkTemplateItems.setRemarkTemplateItemsOption(remarkTemplateItemsOptionCollection);
            remarkTemplateItemsCollection.add(remarkTemplateItems);
        }
        return remarkTemplateItemsCollection;
    }


    /**
     * 删除评语模版
     *
     * @param delId               需要删除的模版的id
     * @param httpServletResponse 对当前请求的响应
     */
    @RequestMapping("/delRemarkTemplate.html")
    public void delRemarkTemplate(Integer delId, HttpServletResponse httpServletResponse) {
        if (remarkTemplateService.findById(delId) == null)
            throw new MessageException("删除的模版不存在");
        remarkTemplateService.deleteById(delId);
        CommonHelper.buildSimpleJson(httpServletResponse, "删除成功");
    }

    /**
     * 查看评语模版
     *
     * @param templateId 需要查看的模版的id
     * @param modelMap   存储需要在jsp中获取的数据
     * @return evaluate/remarkTemplate/viewRemarkTemplate.jsp
     */
    @RequestMapping("/viewRemarkTemplate.html")
    public String viewRemarkTemplate(Integer templateId, ModelMap modelMap) {
        RemarkTemplate remarkTemplate = remarkTemplateService.findById(templateId);
        modelMap.put("remarkTemplate", remarkTemplate);
        return "evaluate/remarkTemplate/viewRemarkTemplate";
    }

}
