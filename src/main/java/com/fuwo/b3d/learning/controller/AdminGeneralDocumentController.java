package com.fuwo.b3d.learning.controller;

import com.fuwo.b3d.config.OSSProperties;
import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.enums.TagEnum;
import com.fuwo.b3d.integration.aliyunoss.AliyunOssClient;
import com.fuwo.b3d.learning.model.GeneralDocument;
import com.fuwo.b3d.learning.service.GeneralDocumentService;
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
@RequestMapping(BASE_MAPPING + "/learning/generalDocument")
public class AdminGeneralDocumentController {
    private final String SEPARATOR = "/";


    @Autowired
    private GeneralDocumentService documentService;


    @Autowired
    private AliyunOssClient aliyunOssClient;

    @Autowired
    private OSSProperties ossProperties;

    @GetMapping("/save")
    public String add(Model model, GeneralDocument document) {

        if (document.getId() != null) {
            //来源于编辑
            document = documentService.get(document.getId());
        }

        model.addAttribute("document", document);
        model.addAttribute("tags", TagEnum.values());
        model.addAttribute("subjects", GeneralDocument.SubjectEnum.values());
        model.addAttribute("states", StateEnum.values());

        return "learning/generalDocument";
    }

    @PostMapping(value = "/save")
    public String addPost(GeneralDocument documentDto, Model model, @NotNull Principal principal) {

        StringBuffer errorMessages = new StringBuffer();
        if (StringUtils.isBlank(documentDto.getTitle())) {
            errorMessages.append("标题不能为空！");
        }
        if (StringUtils.isBlank(documentDto.getPoster())) {
            errorMessages.append("发帖人不能为空！");
        }
        if (documentDto.getSubject() == null) {
            errorMessages.append("板块不能为空！");
        }
        if (documentDto.getViewsInitial() == null) {
            errorMessages.append("浏览量不能为空！");
        }
        if (documentDto.getState() == null) {
            errorMessages.append("状态不能为空！");
        }
        if (StringUtils.isBlank(documentDto.getCont())) {
            errorMessages.append("内容不能为空！");
        }
        if (StringUtils.isNotBlank(errorMessages.toString())) {
            model.addAttribute("errorMessages", errorMessages.toString());
            model.addAttribute("document", documentDto);
            model.addAttribute("tags", TagEnum.values());
            model.addAttribute("subjects", GeneralDocument.SubjectEnum.values());
            model.addAttribute("states", StateEnum.values());
            return "learning/generalDocument";
        }

        GeneralDocument document = null;
        //create html file by content

        String filePath = "";
        String cont = documentDto.getCont();
        if (documentDto.getId() == null) {
            //新增的时候文件是新建的
            if (StringUtils.isNotBlank(cont)) {
                String fileName = UUID.randomUUID().toString() + ".html";
                String date = DateFormatUtils.format(new Date(), "yyyyMMdd");
                filePath = new StringBuffer(OSS_STORE_ROOT_PATH).append(SEPARATOR).append("learning").append(SEPARATOR).append("generalDocument").append(SEPARATOR)
                        .append(StringUtils.substring(date, 0, 6)).append(SEPARATOR)
                        .append(StringUtils.substring(date, 6, 8)).append(SEPARATOR)
                        .append(fileName).toString();
            }

            documentDto.setCont(filePath);
            //创建
            documentDto.setCreated(new Date());
            documentDto.setCreator(principal.getName());
            documentDto.setViewsIncrease(documentDto.getViewsInitial());
            document = documentDto;
        } else {
            //更新的时候是覆盖的文件
            document = documentService.get(documentDto.getId());

            BeanUtils.copyProperties(documentDto, document, "id", "viewsIncrease", "created", "creator", "priority", "cont");
            filePath = document.getCont();
            document.setViewsIncrease(document.getViewsIncrease() - document.getViewsInitial() + documentDto.getViewsInitial());
        }

        //内容不为空时再上传oss
        if (StringUtils.isNotBlank(cont)) {
            InputStream inputStream = null;
            try {
                inputStream = new ByteArrayInputStream(cont.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            aliyunOssClient.uploadByStream(ossProperties.getImage().getBucketName(), filePath, inputStream);
        }

        document.setModified(new Date());
        document.setModifier(principal.getName());
        document.setStatus(StatusEnum.ENABLE);

        documentService.save(document);

        return "redirect:./list";
    }


}
