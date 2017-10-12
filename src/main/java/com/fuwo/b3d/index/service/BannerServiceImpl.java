package com.fuwo.b3d.index.service;

import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.index.enums.BannerEnum;
import com.fuwo.b3d.index.model.Banner;
import com.fuwo.b3d.index.service.repository.BannerRepository;
import com.fuwo.b3d.learning.model.GeneralDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.*;

@CacheConfig(cacheNames = "com.fuwo.b3d.banners")
@Transactional
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerRepository repository;

    private List<Banner> bannerList;

    @Override
    public List<Banner> getBannersInfo(BannerEnum bannerEnum) {
        bannerList = repository.findByStatusAndTypeOrderBySeqAsc(0, bannerEnum.getCode());
        if (bannerList == null) {
            bannerList = new ArrayList<>();
        }

        return bannerList;
    }

    @Override
    public List<Banner> resetSequenceBanners(BannerEnum bannerEnum, int fromPosition, int toPosition) {
        if (bannerList == null) {
            getBannersInfo(bannerEnum);
        }

        Banner fromBanner = bannerList.get(fromPosition);
        bannerList.remove(fromPosition);
        if (fromPosition > toPosition) {
            bannerList.add(toPosition, fromBanner);
        } else if (fromPosition < toPosition) {
            bannerList.add(toPosition - 1, fromBanner);
        }

        for (int i = Math.min(fromPosition, toPosition), size = bannerList.size(); i < size; i++) {
            Banner banner = bannerList.get(i);
            banner.setSeq(i + 1);
            bannerList.set(i, banner);
        }

        return repository.save(bannerList);
    }

    @Override
    public List<Banner> addBanner(Banner banner) {
        Banner mBanner = repository.save(banner);
        if (bannerList == null) {
            //getBannersInfo(BannerEnum.getByCode(banner.getType()));
        }
        mBanner.setSeq(bannerList.size());
        bannerList.add(mBanner);
        return bannerList;
    }

    @Override
    public List<Banner> deleteBanner(BannerEnum bannerEnum, int position) {
        if (bannerList == null) {
            getBannersInfo(bannerEnum);
        }
        if (bannerList.size() <= position) {
            return bannerList;
        }

        long id = bannerList.get(position).getId();
        Banner banner = bannerList.get(position);
        banner.setStatus(StatusEnum.ENABLE);
        int deleteSeqPosition = bannerList.get(position).getSeq();
        repository.save(banner);
        bannerList.remove(position);

        if (deleteSeqPosition > 0) {
            for (int i = deleteSeqPosition - 1, size = bannerList.size(); i < size; i++) {
                Banner mBanner = bannerList.get(i);
                mBanner.setSeq(i + 1);
                bannerList.set(i, mBanner);
            }
        }
        bannerList = repository.save(bannerList);
        return bannerList;
    }

    @Override
    public Page<Banner> findAll(Example<Banner> example, Pageable pageable) {
        Assert.notNull(example);
        Assert.notNull(pageable);
        return repository.findAll(example, pageable);
    }

}


