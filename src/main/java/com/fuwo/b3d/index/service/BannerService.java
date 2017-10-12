package com.fuwo.b3d.index.service;

import com.fuwo.b3d.index.enums.BannerEnum;
import com.fuwo.b3d.index.model.Banner;
import com.fuwo.b3d.learning.model.Course;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannerService {
    List<Banner> getBannersInfo(BannerEnum bannerEnum);

    List<Banner> resetSequenceBanners(BannerEnum bannerEnum, int fromPosition, int toPosition);

    List<Banner> addBanner(Banner banner);

    List<Banner> deleteBanner(BannerEnum bannerEnum, int position);

    Page<Banner> findAll(Example<Banner> example, Pageable pageable);

}
