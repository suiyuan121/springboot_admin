package com.fuwo.b3d.index.controller;

import com.fuwo.b3d.GlobalConstant;
import com.fuwo.b3d.config.OSSProperties;
import com.fuwo.b3d.index.enums.BannerEnum;
import com.fuwo.b3d.index.model.Banner;
import com.fuwo.b3d.index.service.BannerService;
import com.fuwo.b3d.integration.aliyunoss.AliyunOssClient;
import com.fuwo.b3d.learning.model.GeneralDocument;
import org.apache.commons.lang.time.DateFormatUtils;
import org.attoparser.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.fuwo.b3d.GlobalConstant.BASE_MAPPING;

/**
 * Author: zz
 * Date: 2017/8/18  15:52
 */

@Controller
@RequestMapping(BASE_MAPPING + "/banner")
public class AdminBannerController {

    @Autowired
    BannerService bannerService;
    @Autowired
    private AliyunOssClient aliyunOssClient;

    @Autowired
    private OSSProperties ossProperties;

    @GetMapping("/banner")
    public String display(Model model) {
        List<Banner> bannerList = bannerService.getBannersInfo(BannerEnum.INDEX);
        model.addAttribute("banners", bannerList);
        return "/banner/banner";
    }

    @PostMapping("/add")
    public String add(Model model, Banner banner, @RequestParam("url") MultipartFile picFile) {
        String originalFilename = picFile.getOriginalFilename();
        if ("".equals(originalFilename)) {
            //无法获取正确的图片地址
            return "/banner/banner";
        }
        String filePath;
        try {
            InputStream inputStream = picFile.getInputStream();
            String suffix = picFile.getOriginalFilename().substring(picFile.getOriginalFilename().indexOf("."));
            String fileName = UUID.randomUUID().toString() + suffix;
            filePath = new StringBuffer("banner").append(File.separator).append(DateFormatUtils.format(new Date(), "yyyyMMdd")).append(File.separator).append(fileName).toString();

            aliyunOssClient.uploadByStream(ossProperties.getImage().getBucketName(), UUID.randomUUID().toString(), inputStream);
        } catch (IOException e) {
            //上传失败
            return "/banner/banner";
        }

        Banner mBanner = new Banner();
        mBanner.setDescription(banner.getDescription());
        mBanner.setLink(banner.getLink());
        mBanner.setFilePath(filePath);
        mBanner.setType(Banner.BannerEnum.INDEX);
        List<Banner> bannerList = bannerService.addBanner(mBanner);
        model.addAttribute("banners", bannerList);
        return "/banner/banner";
    }

    @PostMapping(value = "/delete")
    public String delete(Model model, @RequestParam("deletePosition") int position) {
        List<Banner> bannerList = bannerService.deleteBanner(BannerEnum.INDEX, position);
        model.addAttribute("banners", bannerList);
        return "/banner/banner";
    }

    @PostMapping("reset")
    public String reset(Model model, @RequestParam("fromPosition") int fromPosition, @RequestParam("toPosition") int toPosition) {
        List<Banner> bannerList = bannerService.resetSequenceBanners(BannerEnum.INDEX, fromPosition, toPosition);
        model.addAttribute("banners", bannerList);
        return "/banner/banner";
    }


}
