package com.fuwo.b3d.learning.controller;

import com.fuwo.b3d.GlobalConstant;
import com.fuwo.b3d.config.OSSProperties;
import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.integration.aliyunoss.AliyunOssClient;
import com.fuwo.b3d.learning.model.BeginnerDocument;
import com.fuwo.b3d.learning.service.BeginnerDocumentService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;
import java.util.UUID;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;
import static com.fuwo.b3d.GlobalConstant.OSS_STORE_ROOT_PATH;

@Controller
@RequestMapping(BASE_MAPPING + "/learning/beginnerDocument")
public class AdminBeginnerDocumentController {
    private final String SEPARATOR = "/";

    @Autowired
    private BeginnerDocumentService beginnerDocumentService;


    @Autowired
    private AliyunOssClient aliyunOssClient;

    @Autowired
    private OSSProperties ossProperties;

    @GetMapping("/save")
    public String add(Model model, BeginnerDocument document) {

        if (document.getId() != null) {
            //来源于编辑
            document = beginnerDocumentService.get(document.getId());
        }
        model.addAttribute("document", document);
        return "learning/beginnerDocument";
    }

    @PostMapping(value = "/save")
    public String addPost(BeginnerDocument documentDto, Model model, @NotNull Principal principal) {

        StringBuffer errorMessages = new StringBuffer();
        if (StringUtils.isBlank(documentDto.getTitle())) {
            errorMessages.append("标题不能为空！");
        }
        if (StringUtils.isBlank(documentDto.getPoster())) {
            errorMessages.append("主讲人不能为空！");
        }
        if (StringUtils.isBlank(documentDto.getCont())) {
            errorMessages.append("视频链接不能为空！");
        }
        if (StringUtils.isNotBlank(errorMessages.toString())) {
            model.addAttribute("errorMessages", errorMessages.toString());
            model.addAttribute("document", documentDto);
            return "learning/beginnerDocument";
        }

        BeginnerDocument document = null;
        if (documentDto.getId() == null) {
            //新增的时候文件是新建的
            //创建
            documentDto.setCreated(new Date());
            documentDto.setCreator(principal.getName());
            document = documentDto;
        } else {
            //更新的时候是覆盖的文件
            document = beginnerDocumentService.get(documentDto.getId());

            BeanUtils.copyProperties(documentDto, document, "id", "viewsIncrease", "created", "creator", "priority");
        }

        document.setViewsInitial(0);//新手教程默认值为0
        document.setViewsIncrease(0);
        document.setModified(new Date());
        document.setModifier(principal.getName());
        document.setStatus(StatusEnum.ENABLE);
        document.setState(StateEnum.PUBLIC);

        beginnerDocumentService.save(document);

        return "redirect:./list";
    }


}
