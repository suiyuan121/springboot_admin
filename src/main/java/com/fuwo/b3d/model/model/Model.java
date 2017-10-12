package com.fuwo.b3d.model.model;

import com.fuwo.b3d.user.model.UserInfo;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ifuwo_ifuwoitem")
public class Model implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "no")
    private String no;

    @Column(name = "preview_fpath")
    private String previewFpath;

    @Column(name = "fcoin_price")
    private Integer discountPrice;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "favorite_count")
    private Integer favoriteCount;

    @Column(name = "[describe]")
    private String describe;

    @Column(name = "product_brand")
    private String productBrand;

    @Column(name = "trim_width")
    private Double trimWidth;

    @Column(name = "trim_length")
    private Double trimLength;

    @Column(name = "trim_height")
    private Double trimHeight;

    @Column(name = "material")
    private String material;

    @Column(name = "cargoNo")
    private String cargoNo;

    @Column(name = "create_time")
    private Date created;

    @Column(name = "modify_time")
    private Date modified;

    @Column(name = "state")
    @Where(clause = "status=1")
    private Integer state;

    //普通模型和精品模型
    @Column(name = "perfect")
    private PerfectEnum perfect;

    @Column(name = "category")
    //模型包里的分类。只有三种
    private CategoryEnum category;

    //详细的分类，因为老的是用item_no 进行关联，所以这里不做关联，单表维护
    @Transient
    private Category itemCategory;

    @Column(name = "user_id")
    private Integer userId;

    @Transient
    private UserInfo userInfo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPreviewFpath() {
        return previewFpath;
    }

    public void setPreviewFpath(String previewFpath) {
        this.previewFpath = previewFpath;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public Double getTrimWidth() {
        return trimWidth;
    }

    public void setTrimWidth(Double trimWidth) {
        this.trimWidth = trimWidth;
    }

    public Double getTrimLength() {
        return trimLength;
    }

    public void setTrimLength(Double trimLength) {
        this.trimLength = trimLength;
    }

    public Double getTrimHeight() {
        return trimHeight;
    }

    public void setTrimHeight(Double trimHeight) {
        this.trimHeight = trimHeight;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getCargoNo() {
        return cargoNo;
    }

    public void setCargoNo(String cargoNo) {
        this.cargoNo = cargoNo;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public Category getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Category itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public PerfectEnum getPerfect() {
        return perfect;
    }

    public void setPerfect(PerfectEnum perfect) {
        this.perfect = perfect;
    }

    public enum CategoryEnum {

        JJ("0", "家具"),

        YZ("1", "硬装"),

        PS("2", "配饰");

        private String desc;
        private String code;

        public String getDesc() {
            return desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private CategoryEnum(String code, String desc) {
            this.desc = desc;
            this.code = code;
        }

    }

    public enum PerfectEnum {

        NORMAL("0", "普通"),

        PERFECT("1", "精品");


        private String desc;
        private String code;

        public String getDesc() {
            return desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private PerfectEnum(String code, String desc) {
            this.desc = desc;
            this.code = code;
        }

    }
}
