package com.fuwo.b3d.bulletin.controller;

import com.fuwo.b3d.GlobalConstant;
import com.fuwo.b3d.bulletin.model.Bulletin;
import com.fuwo.b3d.bulletin.service.BulletinService;
import com.fuwo.b3d.config.OSSProperties;
import com.fuwo.b3d.enums.StateEnum;
import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.integration.aliyunoss.AliyunOssClient;
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
import java.security.Principal;
import java.util.Date;
import java.util.UUID;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;
import static com.fuwo.b3d.GlobalConstant.OSS_STORE_ROOT_PATH;


@Controller
@RequestMapping(BASE_MAPPING + "/bulletin")
public class AdminBulletinController {

    private final String SEPARATOR = "/";

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    private AliyunOssClient aliyunOssClient;

    @Autowired
    private OSSProperties ossProperties;


    @GetMapping("/save")
    public String add(Model model, Bulletin bulletin) {
        if (bulletin.getId() != null) {
            //编辑
            bulletin = bulletinService.get(bulletin.getId());
        }
        model.addAttribute("states", StateEnum.values());
        model.addAttribute("bulletin", bulletin);
        return "bulletin/bulletin";
    }

    @PostMapping("/save")
    public String add(Bulletin bulletinDto, Model model, @NotNull Principal principal) {

        StringBuffer errorMessages = new StringBuffer();
        if (StringUtils.isBlank(bulletinDto.getName())) {
            errorMessages.append("标题不能为空！");
        }
        if (bulletinDto.getState() == null) {
            errorMessages.append("状态不能为空！");
        }
        if (StringUtils.isBlank(bulletinDto.getCont())) {
            errorMessages.append("内容不能为空！");
        }

        if (StringUtils.isNotBlank(errorMessages.toString())) {
            model.addAttribute("errorMessages", errorMessages.toString());
            model.addAttribute("modelPack", bulletinDto);
            model.addAttribute("states", StateEnum.values());
            return "bulletin/bulletin";
        }

        Bulletin bulletin = null;
        String filePath = "";
        String cont = bulletinDto.getCont();

        if (bulletinDto.getId() == null) {
            //创建
            if (StringUtils.isNotBlank(cont)) {
                String fileName = UUID.randomUUID().toString() + ".html";
                String date = DateFormatUtils.format(new Date(), "yyyyMMdd");

                filePath = new StringBuffer(OSS_STORE_ROOT_PATH).append(SEPARATOR).append("bulletin").append(SEPARATOR)
                        .append(StringUtils.substring(date, 0, 6)).append(SEPARATOR)
                        .append(StringUtils.substring(date, 6, 8)).append(SEPARATOR)
                        .append(fileName).toString();
            }

            bulletinDto.setCont(filePath);
            bulletinDto.setCreated(new Date());
            bulletinDto.setCreator(principal.getName());
            bulletin = bulletinDto;
        } else {
            bulletin = bulletinService.get(bulletinDto.getId());
            BeanUtils.copyProperties(bulletinDto, bulletin, "id", "created", "creator");
            filePath = bulletin.getCont();
        }

        //内容不为空时再上传oss
        if (StringUtils.isNotBlank(cont)) {
            InputStream inputStream = new ByteArrayInputStream(cont.getBytes());
            aliyunOssClient.uploadByStream(ossProperties.getImage().getBucketName(), filePath, inputStream);
        }

        bulletin.setModified(new Date());
        bulletin.setModifier(principal.getName());
        bulletin.setStatus(StatusEnum.ENABLE);

        bulletinService.save(bulletin);

        return "redirect:./list";
    }
}
